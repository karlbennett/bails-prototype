package org.bails.element;

import org.bails.markup.MarkupElement;
import org.bails.stream.IBailsStream;

/**
 * @author Karl Bennett
 */
public abstract class Page extends Element {

    public Page() {
        super();
    }

    private Page(String bailsId) {
        super(bailsId);
    }

    protected Page(IBailsStream stream) {
        super(stream);
    }

    @Override
    public String render() {
        StringBuilder renderedPage = new StringBuilder();

        renderedPage.append(getMarkupElement().getOpenTag());

        if (hasChildren()) {
            for (MarkupElement childMarkup : getMarkupElement().getChildren()) {
                if (childMarkup.isBailsElement()) {
                    renderedPage.append(nextChild().render());
                } else {
                    renderedPage.append(childMarkup.toString());
                }
            }
        }

        renderedPage.append(getMarkupElement().getCloseTag());

        return renderedPage.toString();
    }
}
