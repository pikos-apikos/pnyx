package dev.pnyx.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pnyx.core.api.PublicCorpusApi;
import dev.pnyx.core.api.PublicCorpusImportApi;
import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Application service that verifies public artifacts as the canonical corpus.
 *
 * @see ../docs/90_Information/PUBLIC_STORAGE_MODEL.md
 * @see ../docs/90_Information/SCHEMAS.md
 */
@Service
@SuppressWarnings("PMD.GodClass")
public class PublicCorpusService implements PublicCorpusApi, PublicCorpusImportApi {

    private static final TypeReference<Map<String, Object>> JSON_OBJECT = new TypeReference<>() { };

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public VerificationReport verify(String path) {
        Path root = Path.of(path).normalize();
        VerificationAccumulator accumulator = new VerificationAccumulator(root.toString());
        verifyManifest(root, accumulator);
        verifyIndex(root, accumulator);
        verifyObjects(root, accumulator);
        return accumulator.report();
    }

    @Override
    public RebuildReport rebuild(String path) {
        VerificationReport verification = verify(path);
        if (!verification.ok()) {
            return new RebuildReport(verification.path(), false, 0, 0, 0, 0, verification.issues());
        }

        RebuildAccumulator accumulator = new RebuildAccumulator(verification.path());
        Path objects = Path.of(path).normalize().resolve("objects");
        try (var paths = Files.walk(objects)) {
            paths.filter(candidate -> candidate.getFileName().toString().endsWith(".json"))
                .forEach(candidate -> importObject(candidate, accumulator));
        } catch (IOException e) {
            accumulator.issue(objects, "could not walk objects directory: " + e.getMessage());
        }
        return accumulator.report();
    }

    private void importObject(Path objectPath, RebuildAccumulator accumulator) {
        readJsonObject(objectPath, accumulator).ifPresent(json -> {
            String schema = stringValue(json.get("schema"));
            Optional<PublicSchemaVersion> schemaVersion = Arrays.stream(PublicSchemaVersion.values())
                .filter(version -> version.value().equals(schema))
                .findFirst();
            if (schemaVersion.isEmpty()) {
                accumulator.skippedObject(objectPath, "unsupported schema: " + schema);
                return;
            }
            switch (schemaVersion.get()) {
                case PROPOSAL_V1 -> importProposal(objectPath, json, accumulator);
                case DECISION_V1 -> importDecision(objectPath, json, accumulator);
                case IMPLEMENTATION_FORK_V1 -> importImplementationFork(objectPath, json, accumulator);
                default -> accumulator.skippedObject(objectPath, "schema has no read-model importer: " + schema);
            }
        });
    }

    private void importProposal(Path objectPath, Map<String, Object> json, RebuildAccumulator accumulator) {
        if (json.get("proposal_id") instanceof String && json.get("body") instanceof Map<?, ?>) {
            accumulator.proposal();
        } else {
            accumulator.issue(objectPath, "proposal object is missing proposal_id or body");
        }
    }

    private void importDecision(Path objectPath, Map<String, Object> json, RebuildAccumulator accumulator) {
        if (json.get("proposalId") instanceof String && json.get("outcome") instanceof String) {
            accumulator.decision();
        } else {
            accumulator.issue(objectPath, "decision object is missing proposalId or outcome");
        }
    }

    private void importImplementationFork(Path objectPath, Map<String, Object> json,
                                          RebuildAccumulator accumulator) {
        if (json.get("forkId") instanceof String && json.get("decisionId") instanceof String
            && json.get("paths") instanceof List<?>) {
            accumulator.implementationFork();
        } else {
            accumulator.issue(objectPath, "implementation fork object is missing forkId, decisionId, or paths");
        }
    }

    private void verifyManifest(Path root, VerificationAccumulator accumulator) {
        Path manifest = root.resolve("manifests/latest.json");
        if (!Files.isRegularFile(manifest)) {
            accumulator.indexError(manifest, "latest manifest is missing");
            return;
        }
        readJsonObject(manifest, accumulator).ifPresent(json -> {
            if (!json.containsKey("latestObject")) {
                accumulator.indexError(manifest, "latest manifest does not reference a latestObject");
            }
        });
    }

    private void verifyIndex(Path root, VerificationAccumulator accumulator) {
        Path index = root.resolve("indexes/objects.jsonl");
        if (!Files.isRegularFile(index)) {
            accumulator.indexError(index, "object index is missing");
            return;
        }
        try {
            for (String line : Files.readAllLines(index, StandardCharsets.UTF_8)) {
                if (line.isBlank()) {
                    continue;
                }
                Map<String, Object> entry = mapper.readValue(line, JSON_OBJECT);
                verifyIndexEntry(root, index, entry, accumulator);
            }
        } catch (IOException e) {
            accumulator.indexError(index, "could not read object index: " + e.getMessage());
        }
    }

    private void verifyIndexEntry(Path root, Path index, Map<String, Object> entry,
                                  VerificationAccumulator accumulator) {
        String hash = stringValue(entry.get("hash"));
        String objectType = stringValue(entry.get("objectType"));
        String location = stringValue(entry.get("location"));
        if (hash == null || objectType == null || location == null) {
            accumulator.indexError(index, "index entry must contain hash, objectType, and location");
            return;
        }
        Path objectPath = root.resolve(location).normalize();
        if (!objectPath.startsWith(root) || !Files.isRegularFile(objectPath)) {
            accumulator.indexError(index, "indexed object is missing: " + location);
            return;
        }
        if (!hash.equals(computeHash(readString(objectPath, accumulator)))) {
            accumulator.hashError(objectPath, "indexed hash does not match object content");
        }
        if (Arrays.stream(PublicObjectType.values()).noneMatch(type -> type.value().equals(objectType))) {
            accumulator.schemaError(objectPath, "unknown public object type: " + objectType);
        }
    }

    private void verifyObjects(Path root, VerificationAccumulator accumulator) {
        Path objects = root.resolve("objects");
        if (!Files.isDirectory(objects)) {
            accumulator.indexError(objects, "objects directory is missing");
            return;
        }
        try (var paths = Files.walk(objects)) {
            paths.filter(path -> path.getFileName().toString().endsWith(".json"))
                .forEach(path -> verifyObject(root, path, accumulator));
        } catch (IOException e) {
            accumulator.indexError(objects, "could not walk objects directory: " + e.getMessage());
        }
    }

    private void verifyObject(Path root, Path objectPath, VerificationAccumulator accumulator) {
        accumulator.objectChecked();
        String content = readString(objectPath, accumulator);
        String expectedHash = "sha256:" + objectPath.getFileName().toString().replace(".json", "");
        String actualHash = computeHash(content);
        if (!expectedHash.equals(actualHash)) {
            accumulator.hashError(objectPath, "filename hash does not match object content");
        }
        readJsonObject(objectPath, accumulator).ifPresent(json -> verifySchema(root, objectPath, json, accumulator));
    }

    private void verifySchema(Path root, Path objectPath, Map<String, Object> json,
                              VerificationAccumulator accumulator) {
        String schema = stringValue(json.get("schema"));
        Optional<PublicSchemaVersion> schemaVersion = Arrays.stream(PublicSchemaVersion.values())
            .filter(version -> version.value().equals(schema))
            .findFirst();
        if (schemaVersion.isEmpty()) {
            accumulator.schemaError(objectPath, "unknown or missing schema: " + schema);
            return;
        }
        Path relative = root.resolve("objects").relativize(objectPath);
        String directory = relative.getName(0).toString();
        if (!schemaVersion.get().objectType().value().equals(directory)) {
            accumulator.schemaError(objectPath, "schema does not match object directory: " + directory);
        }
    }

    private Optional<Map<String, Object>> readJsonObject(Path path, VerificationAccumulator accumulator) {
        try {
            return Optional.of(mapper.readValue(Files.readString(path, StandardCharsets.UTF_8), JSON_OBJECT));
        } catch (JsonProcessingException e) {
            accumulator.schemaError(path, "invalid JSON object: " + e.getOriginalMessage());
        } catch (IOException e) {
            accumulator.indexError(path, "could not read file: " + e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<Map<String, Object>> readJsonObject(Path path, RebuildAccumulator accumulator) {
        try {
            return Optional.of(mapper.readValue(Files.readString(path, StandardCharsets.UTF_8), JSON_OBJECT));
        } catch (JsonProcessingException e) {
            accumulator.issue(path, "invalid JSON object: " + e.getOriginalMessage());
        } catch (IOException e) {
            accumulator.issue(path, "could not read file: " + e.getMessage());
        }
        return Optional.empty();
    }

    private String readString(Path path, VerificationAccumulator accumulator) {
        try {
            return Files.readString(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            accumulator.indexError(path, "could not read file: " + e.getMessage());
            return "";
        }
    }

    private String stringValue(Object value) {
        return value instanceof String text ? text : null;
    }

    private String computeHash(String content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(content.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : bytes) {
                hex.append(String.format("%02x", b));
            }
            return "sha256:" + hex;
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is unavailable", e);
        }
    }

    private static final class VerificationAccumulator {
        private final String path;
        private final List<VerificationIssue> issues = new ArrayList<>();
        private long objectsChecked;
        private long hashErrors;
        private long schemaErrors;
        private long indexErrors;

        private VerificationAccumulator(String path) {
            this.path = path;
        }

        private void objectChecked() {
            objectsChecked++;
        }

        private void hashError(Path location, String message) {
            hashErrors++;
            issues.add(new VerificationIssue(location.toString(), message));
        }

        private void schemaError(Path location, String message) {
            schemaErrors++;
            issues.add(new VerificationIssue(location.toString(), message));
        }

        private void indexError(Path location, String message) {
            indexErrors++;
            issues.add(new VerificationIssue(location.toString(), message));
        }

        private VerificationReport report() {
            return new VerificationReport(path, objectsChecked, hashErrors, schemaErrors,
                indexErrors, List.copyOf(issues));
        }
    }

    private static final class RebuildAccumulator {
        private final String path;
        private final List<VerificationIssue> issues = new ArrayList<>();
        private long proposals;
        private long decisions;
        private long implementationForks;
        private long skippedObjects;

        private RebuildAccumulator(String path) {
            this.path = path;
        }

        private void proposal() {
            proposals++;
        }

        private void decision() {
            decisions++;
        }

        private void implementationFork() {
            implementationForks++;
        }

        private void skippedObject(Path location, String message) {
            skippedObjects++;
            issues.add(new VerificationIssue(location.toString(), message));
        }

        private void issue(Path location, String message) {
            issues.add(new VerificationIssue(location.toString(), message));
        }

        private RebuildReport report() {
            return new RebuildReport(path, true, proposals, decisions, implementationForks,
                skippedObjects, List.copyOf(issues));
        }
    }
}