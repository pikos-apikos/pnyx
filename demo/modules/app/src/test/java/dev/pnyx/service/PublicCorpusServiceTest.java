package dev.pnyx.service;

import dev.pnyx.core.common.PublicObjectType;
import dev.pnyx.core.common.PublicSchemaVersion;
import dev.pnyx.config.PnyxProperties;
import dev.pnyx.infrastructure.publicstore.CanonicalJsonSerializer;
import dev.pnyx.infrastructure.publicstore.FileSystemPublicStoreAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PublicCorpusServiceTest {

    private static final String TREE_CANOPY = "Tree canopy";
    private static final String DID_PNYX_TEST = "did:pnyx:test";
    private static final String PNYX_TEST = "pnyx-test";
    private static final String OBJECTS_PROPOSAL_PREFIX = "objects/proposal/";
    private static final String JSON_EXT = ".json";

    @TempDir
    Path tempDir;

    private record ProposalArtifact(String schema, String type, String title) { }

    private record RebuildProposalArtifact(String schema, String type, UUID proposal_id,
                                           Map<String, String> body) { }

    @Test
    void shouldVerifyPublicArtifactsFromFilesystemCorpus() {
        CanonicalJsonSerializer serializer = new CanonicalJsonSerializer();
        FileSystemPublicStoreAdapter publicStore = new FileSystemPublicStoreAdapter(properties(), serializer, new com.fasterxml.jackson.databind.ObjectMapper());
        String artifact = serializer.canonicalize(new ProposalArtifact(
            PublicSchemaVersion.PROPOSAL_V1.value(), PublicObjectType.PROPOSAL.value(), TREE_CANOPY));

        var hash = publicStore.write(PublicObjectType.PROPOSAL, PublicSchemaVersion.PROPOSAL_V1,
            DID_PNYX_TEST, artifact);
        publicStore.updateManifest(PNYX_TEST, hash, PublicObjectType.PROPOSAL,
            OBJECTS_PROPOSAL_PREFIX + hash.hexDigest() + JSON_EXT);

        var report = new PublicCorpusService().verify(tempDir.toString());

        assertThat(report.ok()).isTrue();
        assertThat(report.objectsChecked()).isEqualTo(1);
        assertThat(report.issues()).isEmpty();
    }

    @Test
    void shouldReportTamperedObjectHash() throws IOException {
        CanonicalJsonSerializer serializer = new CanonicalJsonSerializer();
        FileSystemPublicStoreAdapter publicStore = new FileSystemPublicStoreAdapter(properties(), serializer, new com.fasterxml.jackson.databind.ObjectMapper());
        String artifact = serializer.canonicalize(new ProposalArtifact(
            PublicSchemaVersion.PROPOSAL_V1.value(), PublicObjectType.PROPOSAL.value(), TREE_CANOPY));
        var hash = publicStore.write(PublicObjectType.PROPOSAL, PublicSchemaVersion.PROPOSAL_V1,
            DID_PNYX_TEST, artifact);
        publicStore.updateManifest(PNYX_TEST, hash, PublicObjectType.PROPOSAL,
            OBJECTS_PROPOSAL_PREFIX + hash.hexDigest() + JSON_EXT);
        Files.writeString(tempDir.resolve(OBJECTS_PROPOSAL_PREFIX + hash.hexDigest() + JSON_EXT),
            serializer.canonicalize(new ProposalArtifact(
                PublicSchemaVersion.PROPOSAL_V1.value(), PublicObjectType.PROPOSAL.value(), "Changed")),
            StandardCharsets.UTF_8);

        var report = new PublicCorpusService().verify(tempDir.toString());

        assertThat(report.ok()).isFalse();
        assertThat(report.hashErrors()).isGreaterThan(0);
        assertThat(report.issues()).anySatisfy(issue ->
            assertThat(issue.message()).contains("hash"));
    }

    @Test
    void shouldRebuildSupportedReadModelsFromPublicArtifacts() {
        CanonicalJsonSerializer serializer = new CanonicalJsonSerializer();
        FileSystemPublicStoreAdapter publicStore = new FileSystemPublicStoreAdapter(properties(), serializer, new com.fasterxml.jackson.databind.ObjectMapper());
        String artifact = serializer.canonicalize(new RebuildProposalArtifact(
            PublicSchemaVersion.PROPOSAL_V1.value(), PublicObjectType.PROPOSAL.value(), UUID.randomUUID(),
            Map.of("title", TREE_CANOPY, "problem", "Heat", "proposed_action", "Plant trees")));
        var hash = publicStore.write(PublicObjectType.PROPOSAL, PublicSchemaVersion.PROPOSAL_V1,
            DID_PNYX_TEST, artifact);
        publicStore.updateManifest(PNYX_TEST, hash, PublicObjectType.PROPOSAL,
            OBJECTS_PROPOSAL_PREFIX + hash.hexDigest() + JSON_EXT);

        var report = new PublicCorpusService().rebuild(tempDir.toString());

        assertThat(report.ok()).isTrue();
        assertThat(report.verified()).isTrue();
        assertThat(report.proposals()).isEqualTo(1);
        assertThat(report.decisions()).isZero();
        assertThat(report.implementationForks()).isZero();
        assertThat(report.issues()).isEmpty();
    }

    @Test
    void shouldRefuseRebuildWhenPublicCorpusVerificationFails() throws IOException {
        CanonicalJsonSerializer serializer = new CanonicalJsonSerializer();
        FileSystemPublicStoreAdapter publicStore = new FileSystemPublicStoreAdapter(properties(), serializer, new com.fasterxml.jackson.databind.ObjectMapper());
        String artifact = serializer.canonicalize(new RebuildProposalArtifact(
            PublicSchemaVersion.PROPOSAL_V1.value(), PublicObjectType.PROPOSAL.value(), UUID.randomUUID(),
            Map.of("title", TREE_CANOPY)));
        var hash = publicStore.write(PublicObjectType.PROPOSAL, PublicSchemaVersion.PROPOSAL_V1,
            DID_PNYX_TEST, artifact);
        publicStore.updateManifest(PNYX_TEST, hash, PublicObjectType.PROPOSAL,
            OBJECTS_PROPOSAL_PREFIX + hash.hexDigest() + JSON_EXT);
        Files.writeString(tempDir.resolve(OBJECTS_PROPOSAL_PREFIX + hash.hexDigest() + JSON_EXT),
            serializer.canonicalize(new RebuildProposalArtifact(
                PublicSchemaVersion.PROPOSAL_V1.value(), PublicObjectType.PROPOSAL.value(), UUID.randomUUID(),
                Map.of("title", "Changed"))),
            StandardCharsets.UTF_8);

        var report = new PublicCorpusService().rebuild(tempDir.toString());

        assertThat(report.ok()).isFalse();
        assertThat(report.verified()).isFalse();
        assertThat(report.proposals()).isZero();
        assertThat(report.issues()).isNotEmpty();
    }

    private PnyxProperties properties() {
        PnyxProperties properties = new PnyxProperties();
        properties.setPublicStoragePath(tempDir.toString());
        return properties;
    }
}