package org.bails.view;

import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Karl Bennett
 */
public class BailsView extends AbstractTemplateView {

    private String url;

    /**
	 * Set the URL of the resource that this view wraps.
	 * The URL must be appropriate for the concrete View implementation.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Return the URL of the resource that this view wraps.
	 */
	public String getUrl() {
		return this.url;
	}

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.getWriter().write("Hello Bails!<br/>");
        response.getWriter().write(getUrl());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
