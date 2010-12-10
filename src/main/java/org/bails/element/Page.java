package org.bails.element;

import org.bails.markup.BailsElementHolder;
import org.bails.markup.BailsTagElement;
import org.bails.markup.Element;
import org.bails.stream.IBailsStream;

/**
 * This is the class that all Bails pages should subclass. Any user created Bails pages will be instantiated as
 * singletons and their bailsId will be set to the simple name of the subclass.
 *
 * @author Karl Bennett
 */
public abstract class Page extends BailsElementHolder {

    public Page() {
    }

    public Page(Element element) {
        super(element.getChildren());
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
        Convenience Methods
     */


    /*
       Override methods.
    */
}
