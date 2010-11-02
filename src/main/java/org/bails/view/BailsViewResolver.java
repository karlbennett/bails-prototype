package org.bails.view;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

/**
 * @author Karl Bennett
 */
public class BailsViewResolver extends AbstractTemplateViewResolver {

    public BailsViewResolver() {
        setViewClass(requiredViewClass());
    }

    @Override
	protected Class requiredViewClass() {
		return BailsView.class;
	}
}
