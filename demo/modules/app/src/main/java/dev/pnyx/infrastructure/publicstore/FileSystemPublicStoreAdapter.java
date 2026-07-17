package dev.pnyx.infrastructure.publicstore;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.pnyx.core.common.ContentHash;
import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;
import dev.pnyx.core.spi.PublicStoreSpi;
import dev.pnyx.config.PnyxProperties;

/**
 * Filesystem-backed implementation of the content-addressed public artifact store.
 * <p>
 * Per {@code ../docs/90_Information/PUBLIC_STORAGE_MODEL.md}, public artifacts are stored as
 * canonical JSON files addressed by SHA-256 hash. Objects are organized by type under
 * {@code data/public/objects/{type}/{hash}.json}. The {@code manifests/} and {@code indexes/}
 * directories provide navigation structures.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 * @see ../docs/90_Information/SCHEMAS.md
 */
@Slf4j
@Component
public class FileSystemPublicStoreAdapter implements PublicStoreSpi {

  private final Path basePath;
  private final CanonicalJsonSerializer serializer;
  private final ObjectMapper objectMapper;

  private static final String CREATED_AT = "createdAt";

  private record PublicObjectIndexEntry(String hash, String location,
                                        String objectType, String publishedAt) { }

  private record PublicManifest(String networkName, int epoch, String createdAt,
                                LatestObject latestObject) { }

  private record LatestObject(String hash, String objectType, String location) { }

  public FileSystemPublicStoreAdapter(
    PnyxProperties properties,
    CanonicalJsonSerializer serializer,
    ObjectMapper objectMapper) {
    this.basePath = Path.of(properties.getPublicStoragePath());
    this.serializer = serializer;
    this.objectMapper = objectMapper;
  }

  /**
   * One-time migration: the demo corpus's index entries used {@code createdAt}
   * for the timestamp field; the current code writes {@code publishedAt}.
   * This hook normalizes any legacy lines in {@code indexes/objects.jsonl}
   * to the new field name. Idempotent — running again is a no-op.
   * <p>
   * Runs once at bean construction (which is when the adapter is first
   * instantiated by the Spring context at startup). Failures are logged
   * but do not crash startup; the {@link #listByType} reader also accepts
   * the legacy field name as a safety net.
   */
  @PostConstruct
  public void normalizeIndex() {
    Path indexFile = basePath.resolve("indexes").resolve("objects.jsonl");
    if (!Files.isRegularFile(indexFile)) {
      return;
    }
    try {
      List<String> lines = Files.readAllLines(indexFile, StandardCharsets.UTF_8);
      List<String> normalized = new ArrayList<>(lines.size());
      int renamed = 0;
      for (String line : lines) {
        if (line.isBlank()) {
          normalized.add(line);
          continue;
        }
        try {
          JsonNode node = objectMapper.readTree(line);
          if (!node.isObject() || !node.has(CREATED_AT)) {
            normalized.add(line);
            continue;
          }
          var objectNode = (com.fasterxml.jackson.databind.node.ObjectNode) node;
          if (node.has("publishedAt")) {
            // Entry already has publishedAt — just remove the legacy createdAt field
            objectNode.remove(CREATED_AT);
            normalized.add(objectMapper.writeValueAsString(objectNode));
            renamed++;
            continue;
          }
          // Capture the legacy timestamp value BEFORE removing the field (Java evaluates
          // method arguments left-to-right, so the fluent remove().put() would see the
          // field as already-removed when computing the put value).
          String legacyTimestamp = node.get(CREATED_AT).asText();
          objectNode.remove(CREATED_AT);
          objectNode.put("publishedAt", legacyTimestamp);
          normalized.add(objectMapper.writeValueAsString(objectNode));
          renamed++;
        } catch (Exception e) {
          // Keep malformed lines as-is so they don't get lost
          normalized.add(line);
        }
      }
      if (renamed > 0) {
        Files.writeString(indexFile, String.join(System.lineSeparator(), normalized)
            + System.lineSeparator(), StandardCharsets.UTF_8,
            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        log.info("Index migration: renamed createdAt -> publishedAt on {} entr{}",
          renamed, renamed == 1 ? "y" : "ies");
      } else {
        log.debug("Index migration: no legacy entries to rename");
      }
    } catch (IOException e) {
      log.warn("Index migration failed (continuing with reader's fallback handling): {}",
        e.getMessage());
    }
  }

  private static String bytesToHex(byte[] bytes) {
    var sb = new StringBuilder();
    for (byte b : bytes) { sb.append(String.format("%02x", b)); }
    return sb.toString();
  }

  @Override
  public ContentHash write(PublicObjectType objectType, PublicSchemaVersion schemaVersion,
                           String createdBy, String canonicalJson) {
    if (schemaVersion.objectType() != objectType) {
      throw new IllegalArgumentException("Schema %s does not belong to object type %s"
        .formatted(schemaVersion.value(), objectType.value()));
    }
    String canonical = serializer.canonicalize(canonicalJson);
    String hash = computeHash(canonical);
    Path objectPath = basePath.resolve("objects")
      .resolve(objectType.value())
      .resolve(hash.replace("sha256:", "") + ".json");

    try {
      Files.createDirectories(objectPath.getParent());
      Files.writeString(objectPath, canonical, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write public object", e);
    }

    return new ContentHash(hash);
  }

  @Override
  public String read(ContentHash hash) {
    try {
      var objectsDir = basePath.resolve("objects");
      if (!Files.exists(objectsDir)) { return null; }
      try (var dirs = Files.list(objectsDir)) {
        var found = dirs
          .map(d -> d.resolve(hash.hexDigest() + ".json"))
          .filter(Files::exists)
          .findFirst();
        if (found.isPresent()) {
          return Files.readString(found.get(), StandardCharsets.UTF_8);
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read public object", e);
    }
    throw new IllegalArgumentException("Object not found: " + hash.value());
  }

  @Override
  public void updateManifest(String networkName, ContentHash objectHash,
                             PublicObjectType objectType, String location) {
    Path manifestsDir = basePath.resolve("manifests");
    Path indexesDir = basePath.resolve("indexes");
    var now = Instant.now().toString();
    String objectLine = serializer.canonicalize(new PublicObjectIndexEntry(
      objectHash.value(), location, objectType.value(), now));
    String manifest = serializer.canonicalize(new PublicManifest(
      networkName, 1, now,
      new LatestObject(objectHash.value(), objectType.value(), location)));

    try {
      Files.createDirectories(manifestsDir);
      Files.createDirectories(indexesDir);
      Files.writeString(manifestsDir.resolve("latest.json"), manifest, StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
      Files.writeString(indexesDir.resolve("objects.jsonl"), objectLine + System.lineSeparator(),
                        StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to update public manifest", e);
    }
  }

  @Override
  public List<IndexEntry> listByType(PublicObjectType objectType) {
    Path indexFile = basePath.resolve("indexes").resolve("objects.jsonl");
    if (!Files.isRegularFile(indexFile)) {
      return List.of();
    }

    List<IndexEntry> results = new ArrayList<>();
    try {
      List<String> lines = Files.readAllLines(indexFile, StandardCharsets.UTF_8);
      for (String line : lines) {
        if (line.isBlank()) { continue; }
        try {
          var node = objectMapper.readTree(line);
          String hash = textOrNull(node.get("hash"));
          String type = textOrNull(node.get("objectType"));
          String location = textOrNull(node.get("location"));
          // The index has used two field names over time: publishedAt (current) and
          // createdAt (legacy demo corpus). Accept either.
          String publishedAt = textOrNull(node.get("publishedAt"));
          if (publishedAt == null) { publishedAt = textOrNull(node.get(CREATED_AT)); }
          if (hash == null || type == null || location == null) { continue; }
          if (!objectType.value().equals(type)) { continue; }

          results.add(new IndexEntry(
            new ContentHash(hash),
            PublicObjectType.fromValue(type),
            location,
            publishedAt != null ? publishedAt : ""));
        } catch (Exception e) {
          log.debug("Skipping malformed index line: {}", e.getMessage());
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("Failed to read object index", e);
    }

    // Most recent first; entries with missing timestamps sort last
    results.sort(Comparator.comparing(IndexEntry::publishedAt,
        Comparator.nullsLast(Comparator.naturalOrder())).reversed());
    return results;
  }

  private static String textOrNull(JsonNode node) {
    if (node == null || node.isNull() || !node.isValueNode()) { return null; }
    String s = node.asText();
    return (s == null || s.isBlank()) ? null : s;
  }

  private String computeHash(String content) {
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] digest = md.digest(content.getBytes(StandardCharsets.UTF_8));
      return "sha256:" + bytesToHex(digest);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException("Hash computation failed", e);
    }
  }
}
