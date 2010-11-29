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

    public BailsTagElement() {
    }

    public BailsTagElement(IBailsStream stream, String bailsId, CharSequence openTag, String name,
                           Map<String, Object> attributes) {
        this(stream, openTag, name, attributes, false);
        this.bailsId = bailsId;
    }

    public BailsTagElement(IBailsStream stream, CharSequence openTag, String name, Map<String, Object> attributes,
                           boolean openClose) {
        super(stream, openTag, name, attributes, openClose);
    }

    public BailsTagElement(MarkupElement... childs) {
        super(childs);
    }

    public BailsTagElement(List<MarkupElement> children) {
        super(children);
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

    public void setBailsId(String bailsId) {
        this.bailsId = bailsId;
    }
}