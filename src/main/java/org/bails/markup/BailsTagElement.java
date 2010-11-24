package org.bails.markup;

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
public class BailsTagElement extends TagElement{

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private String bailsId;

    public BailsTagElement(IBailsStream stream, String bailsId, CharSequence openTag, String name,
                           Map<String, Object> attributes) {
        super(stream, openTag, name, attributes);
        this.bailsId = bailsId;
    }

    /**
     * @return The bails id for this element.
     */
    public String getBailsId() {
        return bailsId;
    }
}