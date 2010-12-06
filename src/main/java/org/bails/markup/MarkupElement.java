package org.bails.markup;

import org.bails.stream.ELEMENT_TYPE;
import org.bails.stream.IBailsStream;

import java.util.*;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class MarkupElement {

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private MarkupElement ancestor = this;
    private MarkupElement parent;
    private List children = new ArrayList();
    private Map<String, BailsTagElement> bailsMarkupChildren = new HashMap<String, BailsTagElement>();
    private Map<String, BailsTagElement> bailsChildren = new HashMap<String, BailsTagElement>();

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
                    this.add(new BailsTagElement(this, stream, stream.getBailsId(), stream.getCharSequence(),
                            stream.getName(), stream.getAttributes()));
                    break;
                }
            }

            stream.next(); // Move to the next element.
        }
    }

    public MarkupElement add(Object... childs) {
        for (Object child : childs) {
            children.add(child);
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

    /**
     * Get a bails markup child from the MarkupElement using the bails path.
     *
     * @param path the path to the BailsTagElement. This is a string of bails ids separated by  colons (:).
     * @return the child at the given path. Or null if the path is incorrect.
     */
    public BailsTagElement getBailsMarkupChild(String path) {
        return bailsMarkupChildren.get(path);
    }

    /**
     * Add a bails markup child to this element.
     *
     * @param path       the path of the bails child.
     * @param bailsChild the child bails markup element.
     * @return this element to allow for chaining.
     */
    public MarkupElement addBailsMarkupChild(String path, BailsTagElement bailsChild) {
        bailsMarkupChildren.put(path, bailsChild);

        return this;
    }

    /**
     * Get a bails child from the MarkupElement using the bails path.
     *
     * @param path the path to the BailsTagElement. This is a string of bails ids separated by  colons (:).
     * @return the child at the given path. Or null if the path is incorrect.
     */
    public BailsTagElement getBailsChild(String path) {
        return bailsChildren.get(path);
    }

    /**
     * Add a bails child to this element.
     *
     * @param path       the path of the bails child.
     * @param bailsChild the child bails element.
     * @return this element to allow for chaining.
     */
    public MarkupElement addBailsChild(String path, BailsTagElement bailsChild) {
        bailsChildren.put(path, bailsChild);

        return this;
    }

    public void setHeritage(MarkupElement parent) {
        setAncestor(parent.getAncestor());
        setParent(parent);
    }

    /*
        Getters and Setters.
     */

    public MarkupElement getAncestor() {
        return ancestor;
    }

    private void setAncestor(MarkupElement ancestor) {
        this.ancestor = ancestor;
    }

    public MarkupElement getParent() {
        return parent;
    }

    private void setParent(MarkupElement parent) {
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