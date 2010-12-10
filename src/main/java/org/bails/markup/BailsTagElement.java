package org.bails.markup;

import org.bails.IBailsEntity;
import org.bails.stream.IBailsStream;

import java.util.Map;

import static org.bails.util.BailsUtil.buildBailsPath;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class BailsTagElement extends TagElement implements IBailsEntity {

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private String bailsId;

    private String bailsPath;

    public BailsTagElement() {
    }

    public BailsTagElement(Element parent, IBailsStream stream, String bailsId, CharSequence openTag, String name,
                           Map<String, Object> attributes) {
        this(parent, stream, bailsId, openTag, name, attributes, false);
    }

    public BailsTagElement(Element parent, IBailsStream stream, String bailsId, CharSequence openTag, String name,
                           Map<String, Object> attributes, boolean openClose) {
        this.bailsId = bailsId;

        setHeritage(parent);

        this.bailsPath = buildBailsPath(this);

        populateElement(stream, openTag, name, attributes, openClose);
    }

    public BailsTagElement(String bailsId) {
        this.bailsId = bailsId;
    }

    /*
        Convenience methods.
     */

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


}