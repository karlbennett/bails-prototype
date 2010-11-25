package org.bails.markup;

import org.bails.stream.ELEMENT_TYPE;
import org.bails.stream.IBailsStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class MarkupElement {

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private MarkupElement parent;
    private List<MarkupElement> children = new ArrayList<MarkupElement>();

    public MarkupElement() {
    }

    public MarkupElement(IBailsStream stream) {
        addChildren(stream);
    }

    /*
        Convenience methods.
     */

    protected void addChildren(IBailsStream stream) {
        stream.next(); // Get the next element.

        MarkupElement child = null;
        // Process any child elements until we reach the closing tag for this element.
        while (stream.hasNext() && stream.getType() != ELEMENT_TYPE.CLOSE) {

            switch (stream.getType()) {
                case OPEN: {
                    child = new TagElement(stream, stream.getCharSequence(), stream.getName(), stream.getAttributes());
                    break;
                }
                case OPENCLOSE: {
                    child = new TagElement(stream, stream.getCharSequence(), stream.getName(), stream.getAttributes(),
                            true);
                    break;
                }
                case CHARACTERS: {
                    child = new CharactersElement(stream);
                    break;
                }
                case BAILS: {
                    child = new BailsTagElement(stream, stream.getBailsId(), stream.getCharSequence(), stream.getName(),
                            stream.getAttributes());
                }
            }

            if (child != null) this.children.add(child); // Add to this elements children.

            stream.next(); // Move to the next element.
        }
    }

    /**
     * Get a child from the MarkupElement.
     *
     * @param i the index of the child.
     * @return the child at the given index.
     */
    public MarkupElement getChild(int i) {
        return children == null ? null : children.get(i);
    }

    /*
        Getters and Setters.
     */

    public MarkupElement getParent() {
        return parent;
    }

    /**
     * @return The list of child elements within this MarkupElement class.
     */
    public List<MarkupElement> getChildren() {
        return children;
    }

    /*
       Override methods.
    */

    @Override
    public String toString() {
        StringBuilder elementString = new StringBuilder(0);

        for (MarkupElement child : children)
            elementString.append(child.toString()); // Run to string across all the children.

        return elementString.toString(); // Return the complete string representation of the element.
    }
}