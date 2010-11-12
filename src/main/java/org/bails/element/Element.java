package org.bails.element;

import org.bails.markup.MarkupElement;
import org.bails.stream.IBailsStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Bennett
 */
public abstract class Element {

    private MarkupElement markupElement;
    private int bailsMarkupElementIndex = 0;
    private List<Element> children = new ArrayList<Element>();
    private int childIndex = 0;
    private String bailsId;

    protected Element() {
    }

    protected Element(String bailsId) {
        this.bailsId = bailsId;
    }

    protected Element(IBailsStream stream) {
        markupElement = new MarkupElement(stream);
    }

    public void add(Element... childs) {
        if (childs.length == 1) {
            MarkupElement bailsChildMarkupElement = markupElement.getBailsChild(bailsMarkupElementIndex++);

            if (bailsChildMarkupElement.getBailsId().equals(childs[0].getBailsId())) {
                children.add(childs[0]);
                childs[0].setMarkupElement(bailsChildMarkupElement);
            } else {
                throw new RuntimeException("Bails Element id (" + childs[0].getBailsId()
                            + ") does not match the id of it's related MarkupElement ("
                            + bailsChildMarkupElement.getBailsId() + ").");
            }
        } else if (childs.length > 1) {
            MarkupElement bailsChildMarkupElement = null;

            for (Element child : children) {
                bailsChildMarkupElement = markupElement.getBailsChild(bailsMarkupElementIndex++);

                if (bailsChildMarkupElement.getBailsId().equals(childs[0].getBailsId())) {
                    children.add(child);
                    child.setMarkupElement(bailsChildMarkupElement);
                } else {
                    throw new RuntimeException("Bails Element id (" + child.getBailsId()
                            + ") does not match the id of it's related MarkupElement ("
                            + bailsChildMarkupElement.getBailsId() + ").");
                }
            }
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
     * Set the MarkupElement for this bails Element. This method should never be called publicly.
     *
     * @param markupElement the new MarkupElement.
     */
    private void setMarkupElement(MarkupElement markupElement) {
        this.markupElement = markupElement;
    }

    /**
     * @return the bails id for this Element.
     */
    public String getBailsId() {
        return bailsId;
    }
}
