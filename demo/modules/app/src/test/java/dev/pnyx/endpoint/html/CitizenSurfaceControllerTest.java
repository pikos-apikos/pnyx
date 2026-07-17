package dev.pnyx.endpoint.html;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import dev.pnyx.core.api.DeliberationApi;
import dev.pnyx.core.api.ProposalApi;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.ExtendedModelMap;

/**
 * Regression tests proving that the active MVC routes serve the citizen templates.
 */
class CitizenSurfaceControllerTest {

    private final ProposalApi proposalApi = mock(ProposalApi.class);
    private final DeliberationApi deliberationApi = mock(DeliberationApi.class);

    @Test
    void indexRouteServesCitizenHome() throws IOException {
        when(proposalApi.listRecent()).thenReturn(List.of());
        var controller = new IndexController(proposalApi);
        var model = new ExtendedModelMap();

        assertEquals("index", controller.index(model));
        assertTrue(template("index.html").contains("What needs our attention?"));
        assertTrue(template("index.html").contains("Raise an Issue"));
    }

    @Test
    void proposalNewRouteServesGuidedIntake() throws IOException {
        var controller = new ProposalViewController(proposalApi, deliberationApi);

        assertEquals("proposal-form", controller.newForm());
        assertTrue(template("proposal-form.html").contains("Raise a Public Issue"));
        assertTrue(template("proposal-form.html").contains("Publish the Issue"));
    }

    private String template(String filename) throws IOException {
        var resource = new ClassPathResource("templates/" + filename);
        try (var input = resource.getInputStream()) {
            return new String(input.readAllBytes(), UTF_8);
        }
    }
}
