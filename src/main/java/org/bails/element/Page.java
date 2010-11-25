package org.bails.element;

import org.bails.markup.MarkupElement;
import org.bails.stream.IBailsStream;

/**
 * This si the class that all Bails pages should subclass. Any user created Bails pages will be instantiated as
 * singletons and their bailsId will be set to the simple name of the subclass.
 * @author Karl Bennett
 */
public abstract class Page extends Element {

    private boolean initialised = false;

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

    /*
        Abstract methods.
     */

    /**
     * Method should be overridden and all initialisation and view logic should be placed within.
     */
    public abstract void initialise();

    /*
       Override methods.
    */

    @Override
    public String render() {
        if (!initialised) {
            initialised = true;
            initialise();
        }

        clearRender();

        renderChildren();

        return getRender();
    }
}
