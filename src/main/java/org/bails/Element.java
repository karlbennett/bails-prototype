package org.bails;

import java.util.*;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public abstract class Element {

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private Element ancestor;
    private Element parent;
    private List children = new ArrayList();

    public Element(Element parent) {
        setHeritage(parent);
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

    private void setHeritage(Element parent) {
        setAncestor(parent == null || parent.getAncestor() == null ? parent : parent.getAncestor());
        setParent(parent);
    }

    /**
     * Add a object to this element.
     * @param childs the child or children to add to this element.
     * @return  this element to allow chaining.
     */
    public Element add(Object... childs) {
        for (Object child : childs) {
            children.add(child);
        }

        return this;
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

    /*
        Getters and Setters.
     */

    public Element getAncestor() {
        return ancestor;
    }

    protected void setAncestor(Element ancestor) {
        this.ancestor = ancestor;
    }

    public Element getParent() {
        return parent;
    }

    protected void setParent(Element parent) {
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