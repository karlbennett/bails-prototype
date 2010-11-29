package org.bails.markup;

import org.bails.stream.ELEMENT_TYPE;
import org.bails.stream.IBailsStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class MarkupElement {

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private MarkupElement parent;
    private List children = new ArrayList();

    public MarkupElement() {
    }

    public MarkupElement(IBailsStream stream) {
        addChildren(stream);
    }

    public MarkupElement(MarkupElement... childs) {
        this(Arrays.asList(childs));
    }

    public MarkupElement(List<MarkupElement> children) {
        this.children.addAll(children);
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
                    child.setParent(this);
                    break;
                }
                case OPENCLOSE: {
                    child = new TagElement(stream, stream.getCharSequence(), stream.getName(), stream.getAttributes(),
                            true);
                    child.setParent(this);
                    break;
                }
                case CHARACTERS: {
                    child = new CharactersElement(stream);
                    child.setParent(this);
                    break;
                }
                case BAILS: {
                    child = new BailsTagElement(stream, stream.getBailsId(), stream.getCharSequence(), stream.getName(),
                            stream.getAttributes());
                    child.setParent(this);
                    break;
                }
            }

            if (child != null) this.children.add(child); // Add to this elements children.

            stream.next(); // Move to the next element.
        }
    }

    public MarkupElement add(Object... childs) {
        for (Object child : childs) {
            children.add(child);
            if (child instanceof MarkupElement) ((MarkupElement) child).setParent(this);
        }

        return this;
    }

    /**
     * Get a child from the MarkupElement.
     *
     * @param i the index of the child.
     * @return the child at the given index.
     */
    public Object getChild(int i) {
        return children == null ? null : children.get(i);
    }

    /*
        Getters and Setters.
     */

    public MarkupElement getParent() {
        return parent;
    }

    protected void setParent(MarkupElement parent) {
        this.parent = parent;
    }

    /**
     * @return The list of child elements within this MarkupElement class.
     */
    public List getChildren() {
        return children;
    }

    protected void setChildren(List children) {
        this.children = children;
    }

    /*
       Override methods.
    */

    @Override
    public String toString() {
        StringBuilder elementString = new StringBuilder(0);

        for (Object child : children) elementString.append(child.toString()); // Run to string across all the children.

        return elementString.toString(); // Return the complete string representation of the element.
    }
}