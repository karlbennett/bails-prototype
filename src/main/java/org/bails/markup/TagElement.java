package org.bails.markup;

import org.bails.stream.IBailsStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class TagElement extends MarkupElement {

    private CharSequence openTag;
    private String name;
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private CharSequence closeTag;

    private boolean openClose = false; // Indicates whether if this is an openClose tag or not which effects the toString method.

    public TagElement() {
    }

    public TagElement(IBailsStream stream, CharSequence openTag, String name, Map<String, Object> attributes) {
        this(stream, openTag, name, attributes, false);
    }

    public TagElement(IBailsStream stream, CharSequence openTag, String name, Map<String, Object> attributes,
                      boolean openClose) {
        this.openTag = openTag;
        this.name = name;
        this.attributes = attributes;
        this.openClose = openClose;

        addChildren(stream);

        this.closeTag = stream.getCharSequence();
    }

    public TagElement(MarkupElement... childs) {
        super(childs);
    }

    public TagElement(List<MarkupElement> children) {
        super(children);
    }

    /*
        Getters and Setters.
     */

    /**
     * @return Get the character sequence representation of this elements opening tag e.g. For <element></element> this
     *         would return "<element>".
     */
    public CharSequence getOpenTag() {

        return openClose && getChildren().size() == 0 ? ((String) openTag).replaceFirst("[^/]>$", "/>") : openTag;
    }

    protected void setOpenTag(CharSequence openTag) {
        this.openTag = openTag;
    }

    /**
     * @return The name of this element e.g. For <element/> this would return "element".
     */
    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    /**
     * @return The list of attributes for this element e.g. For <element one="1"/> this would return map containing a
     *         key of "one" with a matching value of 1.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    protected void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    /**
     * @return Get the character sequence representation of this elements closing tag e.g. For <element></element> this
     *         would return "</element>".
     */
    public CharSequence getCloseTag() {
        // If this is an open close tag with no children just return the whitespace after the close tag.
        return openClose && getChildren().size() == 0 ? ((String) closeTag).replaceFirst("</\\w*>", "") : closeTag;
    }

    protected void setCloseTag(CharSequence closeTag) {
        this.closeTag = closeTag;
    }

    /**
     * @return true if this TagElement is an openClose tag.
     */
    public boolean isOpenClose() {
        return openClose;
    }

    protected void setOpenClose(boolean openClose) {
        this.openClose = openClose;
    }

    /*
       Override methods.
    */

    @Override
    public String toString() {
        StringBuilder elementString = new StringBuilder(0);

        elementString.append(getOpenTag()); // ...add the open tag to the output, ...
        elementString.append(super.toString()); // ...add the child chars, ...
        elementString.append(getCloseTag()); // ...then lastly add the close tag.

        return elementString.toString(); // Return the complete string representation of the element.
    }
}