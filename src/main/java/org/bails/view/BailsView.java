package org.bails.view;

import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Karl Bennett
 */
public class BailsView extends AbstractTemplateView {
    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write("Hello Bails!");
        response.getWriter().flush();
        response.getWriter().close();
    }
}
