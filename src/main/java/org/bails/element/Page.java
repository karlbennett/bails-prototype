package org.bails.element;

import org.bails.markup.MarkupElement;
import org.bails.stream.IBailsStream;

/**
 * @author Karl Bennett
 */
public abstract class Page extends Element {

    public Page() {
    }

    protected Page(IBailsStream stream) {
        super(stream);
    }

    @Override
    public String render() {
        clearRender();

        appendToRender(getMarkupElement().getOpenTag());

        renderChildren();

        appendToRender(getMarkupElement().getCloseTag());

        return getRender();
    }
}
