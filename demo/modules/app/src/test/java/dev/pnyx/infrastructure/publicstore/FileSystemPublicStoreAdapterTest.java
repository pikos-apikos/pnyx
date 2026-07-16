package dev.pnyx.infrastructure.publicstore;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pnyx.config.PnyxProperties;
import dev.pnyx.core.common.ContentHash;
import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;
import dev.pnyx.core.spi.PublicStoreSpi;
import dev.pnyx.infrastructure.test.DatabaseTestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class FileSystemPublicStoreAdapterTest extends DatabaseTestBase {

    @Autowired
    private PublicStoreSpi publicStore;

    @Test
    void shouldWriteAndReadContentAddressedObject() {
        String json = """
            {"body":{"title":"Test Proposal"},"created_at":"2026-01-01T00:00:00Z","schema":"proposal.v1","type":"proposal"}""";

        ContentHash hash = publicStore.write(PublicObjectType.PROPOSAL,
            PublicSchemaVersion.PROPOSAL_V1,
            "did:pnyx:test", json);

        assertThat(hash.value()).startsWith("sha256:");
        String content = publicStore.read(hash);
        assertThat(content).contains("Test Proposal");
    }

    @Test
    void identicalContentProducesIdenticalHash() {
        String json = "{\"schema\":\"proposal.v1\",\"type\":\"proposal\"}";

        ContentHash hash1 = publicStore.write(PublicObjectType.PROPOSAL,
            PublicSchemaVersion.PROPOSAL_V1,
            "did:pnyx:test", json);
        ContentHash hash2 = publicStore.write(PublicObjectType.PROPOSAL,
            PublicSchemaVersion.PROPOSAL_V1,
            "did:pnyx:test", json);

        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void normalizeIndexRenamesCreatedAtToPublishedAt(@TempDir Path tempDir) throws Exception {
        // Build a temp public-storage tree with mixed legacy + new entries
        Path indexesDir = tempDir.resolve("indexes");
        Files.createDirectories(indexesDir);
        Path indexFile = indexesDir.resolve("objects.jsonl");

        String legacy = """
            {"hash":"sha256:aaa111","objectType":"proposal","location":"objects/proposal/aaa111.json","createdAt":"2026-05-15T10:00:00Z"}
            """.trim();
        String mixed = """
            {"hash":"sha256:bbb222","objectType":"proposal","location":"objects/proposal/bbb222.json","createdAt":"2026-05-15T11:00:00Z","publishedAt":"2026-07-15T12:00:00Z"}
            """.trim();
        String newEntry = """
            {"hash":"sha256:ccc333","objectType":"decision","location":"objects/decision/ccc333.json","publishedAt":"2026-07-15T13:00:00Z"}
            """.trim();
        Files.writeString(indexFile, legacy + System.lineSeparator()
            + mixed + System.lineSeparator()
            + newEntry + System.lineSeparator(),
            StandardCharsets.UTF_8);

        // Construct the adapter directly with the temp dir
        var props = new PnyxProperties();
        props.setPublicStoragePath(tempDir.toString());
        var serializer = new CanonicalJsonSerializer();
        var mapper = new ObjectMapper();
        var adapter = new FileSystemPublicStoreAdapter(props, serializer, mapper);
        adapter.normalizeIndex();

        List<String> lines = Files.readAllLines(indexFile, StandardCharsets.UTF_8);
        var first = mapper.readTree(lines.get(0));
        var second = mapper.readTree(lines.get(1));
        var third = mapper.readTree(lines.get(2));

        // legacy: createdAt was renamed to publishedAt
        assertThat(first.has("createdAt")).isFalse();
        assertThat(first.get("publishedAt").asText()).isEqualTo("2026-05-15T10:00:00Z");

        // mixed: had both, only one remains (publishedAt)
        assertThat(second.has("createdAt")).isFalse();
        assertThat(second.get("publishedAt").asText()).isEqualTo("2026-07-15T12:00:00Z");

        // new: untouched
        assertThat(third.has("createdAt")).isFalse();
        assertThat(third.get("publishedAt").asText()).isEqualTo("2026-07-15T13:00:00Z");

        // Idempotency: a second pass is a no-op
        String firstLineBefore = lines.get(0);
        adapter.normalizeIndex();
        lines = Files.readAllLines(indexFile, StandardCharsets.UTF_8);
        assertThat(lines.get(0)).isEqualTo(firstLineBefore);
    }
}