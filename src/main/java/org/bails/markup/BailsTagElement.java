package org.bails.markup;

import org.bails.stream.IBailsStream;

import java.util.List;
import java.util.Map;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class BailsTagElement extends TagElement {

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private String bailsId;

    private String bailsPath;

    public BailsTagElement() {
    }

    public BailsTagElement(String bailsId) {
        this.bailsId = bailsId;
    }

    public BailsTagElement(String bailsId, Object value) {
        this.bailsId = bailsId;
        if (value instanceof BailsTagElement) { // Check for a BailsTagElement...
            add((BailsTagElement)value); // ...if this was initialised with a BailsTagElement then it needs to be added correctly.
        } else {
            add(value); // Otherwise just add it normal object.
        }
    }

    public BailsTagElement(MarkupElement parent, IBailsStream stream, String bailsId, CharSequence openTag, String name,
                           Map<String, Object> attributes) {
        this(parent, stream, bailsId, openTag, name, attributes, false);
    }

    public BailsTagElement(MarkupElement parent, IBailsStream stream, String bailsId, CharSequence openTag, String name, Map<String, Object> attributes,
                           boolean openClose) {
        setHeritage(parent);

        this.bailsId = bailsId;

        BailsTagElement bailsParent = findBailsParent();

        this.bailsPath = bailsParent == null ? this.bailsId : bailsParent.getBailsPath() + ":" + this.bailsId;

        getAncestor().addBailsMarkupChild(getBailsPath(), this);

        populateElement(stream, openTag, name, attributes, openClose);
    }

    public BailsTagElement(MarkupElement... childs) {
        super(childs);
    }

    public BailsTagElement(List<MarkupElement> children) {
        super(children);
    }

    /*
        Convenience methods.
     */

    private BailsTagElement findBailsParent() {
        MarkupElement parent = getParent();

        while (parent != null) {
            if (parent instanceof BailsTagElement) {
                return (BailsTagElement) parent;
            }
            parent = parent.getParent();
        }

        return null;
    }

    public BailsTagElement add(BailsTagElement... childs) {

        for (BailsTagElement child : childs) {

            if (getBailsMarkupChild(child.getBailsPath()) == null) {
                throw new RuntimeException("No matching bails markup found for bails element: " + child.getBailsId());
            }

            child.setHeritage(this);
            getAncestor().addBailsChild(child.getBailsPath(), child);
        }

        return this;
    }

    /*
        Getters and Setters.
     */

    /**
     * @return The bails id for this element.
     */
    public String getBailsId() {
        return bailsId;
    }

    protected void setBailsId(String bailsId) {
        this.bailsId = bailsId;
    }

    public String getBailsPath() {
        return bailsPath;
    }

    private void setBailsPath(String bailsPath) {
        this.bailsPath = bailsPath;
    }

    /*
       Override methods.
    */

//    @Override
//    public String toString() {
//        StringBuilder elementString = new StringBuilder(0);
//
//        elementString.append(getOpenTag()); // ...add the open tag to the output, ...
//        elementString.append(super.toString()); // ...add the child chars, ...
//        elementString.append(getCloseTag()); // ...then lastly add the close tag.
//
//        return elementString.toString(); // Return the complete string representation of the element.
//    }
}