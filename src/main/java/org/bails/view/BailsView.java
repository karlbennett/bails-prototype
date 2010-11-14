package org.bails.view;

import org.bails.Configuration;
import org.bails.element.Page;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Karl Bennett
 */
public class BailsView extends AbstractTemplateView {

    private Configuration configuration;

    private String url;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
	 * Set the URL of the resource that this view wraps.
	 * The URL must be appropriate for the concrete View implementation.
	 */
    @Override
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
        configuration = BeanFactoryUtils.beanOfTypeIncludingAncestors(
					getApplicationContext(), Configuration.class, true, false);

        System.out.println(getConfiguration() != null ? "Configuration is NOT null." : "Coniguration IS null.");

        Page page = configuration.getPage(getUrl().replace("/", ""));

        response.getWriter().write(page.render());
        response.getWriter().flush();
        response.getWriter().close();
    }
}
