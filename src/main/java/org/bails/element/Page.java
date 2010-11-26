package org.bails.element;

import org.bails.markup.MarkupElement;
import org.bails.markup.TagElement;
import org.bails.stream.IBailsStream;

/**
 * This si the class that all Bails pages should subclass. Any user created Bails pages will be instantiated as
 * singletons and their bailsId will be set to the simple name of the subclass.
 * @author Karl Bennett
 */
public abstract class Page extends MarkupElement {

    private boolean initialised = false;

    public Page() {
    }

    public Page(MarkupElement markupElement) {
    }

    public Page(IBailsStream stream) {
        super(stream);
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


}
