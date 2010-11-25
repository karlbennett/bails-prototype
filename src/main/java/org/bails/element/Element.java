package org.bails.element;

import org.bails.markup.BailsTagElement;
import org.bails.markup.MarkupElement;
import org.bails.stream.IBailsStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Karl Bennett
 */
public abstract class Element {

    private MarkupElement markupElement;
    private List<Element> children = new ArrayList<Element>();
    private int childIndex = 0;
    private String bailsId;
    private StringBuilder renderedElement = new StringBuilder();

    protected Element() {
    }

    protected Element(String bailsId) {
        this.bailsId = bailsId;
    }

    protected Element(MarkupElement markupElement) {
        this.markupElement = markupElement;
    }

    protected Element(IBailsStream stream) {
        this.markupElement = new MarkupElement(stream);
    }

    protected Element(String bailsId, IBailsStream stream) {
        this.bailsId = bailsId;
        this.markupElement = new MarkupElement(stream);
    }

    public void add(Element... childs) {
        if (childs.length == 1) {
                children.add(childs[0]);
        } else if (childs.length > 1) {
                children.addAll(Arrays.asList(childs));
        }
    }

    /*
        Convenience methods.
     */

    /**
     * @return true if this Element has children.
     */
    public boolean hasChildren() {
        return children != null && children.size() > 0;
    }

    /**
     * Get a child from the Element.
     *
     * @param i the index of the child.
     * @return the child at the given index.
     */
    public Element getChild(int i) {
        return children == null ? null : children.get(i);
    }

    /**
     * @return the child Element that is being pointed to by the current index.
     */
    public Element getCurrnetChild() {
        return children == null ? null : children.get(childIndex);
    }

    /**
     * @return true if there are more children in the child list.
     */
    public boolean hasNextChild() {
        return childIndex < children.size();
    }

    /**
     * @return the next child element from the Elements list of children.
     */
    public Element nextChild() {
        return children == null ? null : children.get(childIndex++);
    }

    /**
     * @return the current child index.
     */
    public int getChildIndex() {
        return childIndex;
    }

    /**
     * Reset the child index to 0.
     */
    public void resetChildIndex() {
        childIndex = 0;
    }

    /**
     * Clear the render string for this Element.
     */
    protected void clearRender() {
        renderedElement.setLength(0);
    }

    /**
     * Append some chars to the render string for this Element
     *
     * @param chars
     */
    protected void appendToRender(CharSequence chars) {
        renderedElement.append(chars);
    }

    /**
     * Render the children of the current Element.
     */
    protected void renderChildren() {
        Element child = null;
        for (MarkupElement childMarkup : getMarkupElement().getChildren()) {
            if (childMarkup instanceof BailsTagElement) {
                child = nextChild();
                child.setMarkupElement(childMarkup);
                appendToRender(child.render());
            } else {
                appendToRender(childMarkup.toString());
            }
        }
    }

    /**
     * @return the current render string for the Element.
     */
    protected String getRender() {
        return renderedElement.toString();
    }

    /*
        Abstract methods.
     */

    /**
     * @return a string representation of the rendered page.
     */
    public abstract String render();

    /*
        Getters and Setters.
     */

    /**
     * Get the MarkupElement for this Element. This should not be called publicly.
     * But is used by subclasses render method.
     *
     * @return the MarkupElement for this Element.
     */
    protected MarkupElement getMarkupElement() {
        return markupElement;
    }

    /**
     * Set the MarkupElement for this bails Element.
     *
     * @param markupElement the new MarkupElement.
     */
    public void setMarkupElement(MarkupElement markupElement) {
        this.markupElement = markupElement;
    }

    /**
     * @return the bails id for this Element.
     */
    public String getBailsId() {
        return bailsId;
    }

    /**
     * Set the Bails Id. This should only ever be called in the constructor.
     *
     * @param bailsId for this Element.
     */
    public void setBailsId(String bailsId) {
        this.bailsId = bailsId;
    }

    /*
       Override methods.
    */

    @Override
    public String toString() {
        return getClass().getSimpleName() + " id: " + getBailsId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Element)) return false;

        Element element = (Element) o;

        return bailsId.equals(element.bailsId);
    }

    @Override
    public int hashCode() {
        return bailsId.hashCode();
    }
}
