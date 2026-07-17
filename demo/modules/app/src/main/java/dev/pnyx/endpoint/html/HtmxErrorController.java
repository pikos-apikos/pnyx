package dev.pnyx.endpoint.html;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.webmvc.autoconfigure.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Handles HTMX error requests by returning HTTP 200 so the client
 * can swap the error HTML fragment properly.
 * <p>
 * Only active in a web application context (not CLI mode).
 *
 * @see ../docs/80_Runtime/API_SPEC.md
 */
@Controller
@RequiredArgsConstructor
@ConditionalOnWebApplication
public class HtmxErrorController {

    private final BasicErrorController basicErrorController;

    @RequestMapping(value = "${server.error.path:${error.path:/error}}",
                    headers = "HX-Request=true",
                    method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView errorHtmx(HttpServletRequest request,
                                   HttpServletResponse response) {
        return basicErrorController.errorHtml(request, response);
    }
}
