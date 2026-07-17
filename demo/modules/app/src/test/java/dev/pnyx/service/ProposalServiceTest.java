package dev.pnyx.service;

import dev.pnyx.core.api.DeliberationApi;
import dev.pnyx.core.api.ProposalApi;
import dev.pnyx.core.api.ProposalApi.SubmitProposalCommand;
import dev.pnyx.core.domain.proposal.ProposalId;
import dev.pnyx.infrastructure.test.DatabaseTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ProposalServiceTest extends DatabaseTestBase {

    @Autowired
    private ProposalApi proposalApi;

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        DeliberationApi deliberationApi() {
            return new DeliberationApi() {
                @Override public void runPanel(ProposalId id) { }
                @Override public Optional<ProgressView> getProgress(ProposalId id) { return Optional.empty(); }
            };
        }
    }

    @Test
    void shouldSubmitAndRetrieveProposal() {
        var cmd = new SubmitProposalCommand(
            "School energy upgrades",
            "High energy costs in public schools",
            "Create municipal fund for insulation",
            "did:pnyx:test-user"
        );

        var view = proposalApi.submit(cmd);

        assertThat(view.id()).isNotNull();
        assertThat(view.title()).isEqualTo("School energy upgrades");
        assertThat(view.state()).isEqualTo("CLASSIFIED");

        var found = proposalApi.findById(view.id());
        assertThat(found).isPresent();
    }
}
