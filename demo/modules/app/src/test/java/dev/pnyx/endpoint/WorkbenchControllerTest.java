package dev.pnyx.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the stable citizen/workbench surface routes.
 *
 * @see ../../../../../../../docs/80_Runtime/API_SPEC.md
 */
class WorkbenchControllerTest {

    private final WorkbenchController controller = new WorkbenchController();

    @Test
    void shouldRedirectWorkbenchHomeToWorkbenchSurface() {
        assertEquals("redirect:/?surface=workbench", controller.workbenchHome());
    }

    @Test
    void shouldRedirectProposalWorkbenchToExistingProposalReadModel() {
        UUID proposalId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        assertEquals(
            "redirect:/proposals/11111111-1111-1111-1111-111111111111?view=workbench",
            controller.proposalWorkbench(proposalId));
    }
}
