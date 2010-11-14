package org.bails.element;

import org.bails.markup.MarkupElement;
import org.bails.stream.IBailsStream;

/**
 * This si the class that all Bails pages should subclass. Any user created Bails pages will be instantiated as
 * singletons and their bailsId will be set to the simple name of the subclass.
 * @author Karl Bennett
 */
public abstract class Page extends Element {

    public Page() {
        // Set the bailsId for the Page to the simple name of the class.
        setBailsId(getClass().getSimpleName());
    }

    public Page(MarkupElement markupElement) {
        super(markupElement);
        setBailsId(getClass().getSimpleName());
    }

    public Page(IBailsStream stream) {
        super(stream);
        setBailsId(getClass().getSimpleName());
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
