package org.bails.markup;

import org.bails.stream.ELEMENT_TYPE;
import org.bails.stream.IBailsStream;

import java.util.*;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class Element {

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private Element ancestor;
    private Element parent;
    private List children = new ArrayList();

    public Element() {
    }

    public Element(IBailsStream stream) {
        addChildren(stream);
    }

    public Element(Element... childs) {
        this(Arrays.asList(childs));
    }

    public Element(List<Element> children) {
        this.children.addAll(children);
    }

    /*
        Convenience methods.
     */

    protected void addChildren(IBailsStream stream) {
        stream.next(); // Get the next element.

        // Process any child elements until we reach the closing tag for this element.
        while (stream.hasNext() && stream.getType() != ELEMENT_TYPE.CLOSE) {

            switch (stream.getType()) {
                case OPEN: {
                    this.add(new TagElement(this, stream, stream.getCharSequence(), stream.getName(),
                            stream.getAttributes()));
                    break;
                }
                case OPENCLOSE: {
                    this.add(new TagElement(this, stream, stream.getCharSequence(), stream.getName(),
                            stream.getAttributes(),
                            true));
                    break;
                }
                case CHARACTERS: {
                    this.add(new CharactersElement(this, stream));
                    break;
                }
                case BAILS: {
                    this.add(new TagElement(this, stream, stream.getCharSequence(), stream.getName(),
                            stream.getAttributes()));
                    break;
                }
            }

            stream.next(); // Move to the next element.
        }
    }

    /**
     * Add a object to this element.
     * @param childs the child or children to add to this element.
     * @return  this element to allow chaining.
     */
    public Element add(Object... childs) {
        for (Object child : childs) {
            addLambda(child);
            children.add(child);
        }

        return this;
    }

    /**
     *  This function can be overridden to run any logic over each child before it is added.
     * @param child the next child to be added to this element.
     */
    protected void addLambda(Object child) {
        // Add logic here.
    }

    /**
     * Get a child from the Element.
     *
     * @param i the index of the child.
     * @return the child at the given index.
     */
    public Object getChild(int i) {
        return children == null ? null : children.get(i);
    }

    public void setHeritage(Element parent) {
        setAncestor(parent.getAncestor() == null ? parent : parent.getAncestor());
        setParent(parent);
    }

    /*
        Getters and Setters.
     */

    public Element getAncestor() {
        return ancestor;
    }

    private void setAncestor(Element ancestor) {
        this.ancestor = ancestor;
    }

    public Element getParent() {
        return parent;
    }

    private void setParent(Element parent) {
        this.parent = parent;
    }

    /**
     * @return The list of child elements within this Element class.
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