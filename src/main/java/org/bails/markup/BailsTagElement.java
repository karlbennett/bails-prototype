package org.bails.markup;

import org.bails.Element;
import org.bails.IBailsElement;
import org.bails.stream.IBailsStream;

import java.util.Map;

import static org.bails.util.BailsUtil.buildBailsPath;

/**
 * User: Karl Bennett
 * Date: 14/12/10
 */
public class BailsTagElement extends TagElement implements IBailsElement {

    private String bailsId;
    private String bailsPath;

    public BailsTagElement(Element parent, IBailsStream stream, String bailsId, CharSequence openTag, String name,
                           Map<String, Object> attributes) {
        this(parent, stream, bailsId, openTag, name, attributes, false);
    }

    public BailsTagElement(Element parent, IBailsStream stream, String bailsId, CharSequence openTag, String name,
                           Map<String, Object> attributes, boolean openClose) {
        super(parent, stream, openTag, name, attributes, openClose);
        this.bailsId = bailsId;
    }

    @Override
    public String getBailsId() {
        return bailsId;
    }

    protected void setBailsId(String bailsId) {
        this.bailsId = bailsId;
    }

    @Override
    public String getBailsPath() {
        if (bailsPath == null) {
            bailsPath = buildBailsPath(this);
        }

        return bailsPath;
    }

    protected void setBailsPath(String bailsPath) {
        this.bailsPath = bailsPath;
    }
}
