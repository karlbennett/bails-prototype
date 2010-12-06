package org.bails.element;

import org.bails.markup.BailsTagElement;
import org.bails.markup.MarkupElement;
import org.bails.stream.IBailsStream;

import java.util.List;

/**
 * This is the class that all Bails pages should subclass. Any user created Bails pages will be instantiated as
 * singletons and their bailsId will be set to the simple name of the subclass.
 *
 * @author Karl Bennett
 */
public abstract class Page extends MarkupElement {

    public Page() {
    }

    public Page(MarkupElement markupElement) {
        super(markupElement.getChildren());
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

    public Page add(BailsTagElement... childs) {

        for (BailsTagElement child : childs) {

            if (getBailsMarkupChild(child.getBailsPath()) == null) {
                throw new RuntimeException("No matching bails markup found for bails element: " + child.getBailsId());
            }

            child.setHeritage(this);
            addBailsChild(child.getBailsPath(), child);
        }

        return this;
    }

    /*
       Override methods.
    */
}
