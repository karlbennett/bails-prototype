package org.bails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * This is an abstract class that implements all the generic functionality for rendering a Bails page.
 *
 * @author Karl Bennett
 */
public class Page extends Element {

    public final void renderPage(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {

    }
}
