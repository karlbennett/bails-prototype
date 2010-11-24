package org.bails.markup;

import org.bails.stream.IBailsStream;

import java.util.HashMap;
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

    public TagElement(IBailsStream stream, CharSequence openTag, String name, Map<String, Object> attributes) {
        this.openTag = openTag;
        this.name = name;
        this.attributes = attributes;

        addChildren(stream);

        this.closeTag = stream.getCharSequence();
    }

    /*
        Getters and Setters.
     */

    /**
     * @return Get the character sequence representation of this elements opening tag e.g. For <element></element> this
     *         would return "<element>".
     */
    public CharSequence getOpenTag() {
        return openTag;
    }

    /**
     * @return The name of this element e.g. For <element/> this would return "element".
     */
    public String getName() {
        return name;
    }

    /**
     * @return The list of attributes for this element e.g. For <element one="1"/> this would return map containing a
     *         key of "one" with a matching value of 1.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * @return Get the character sequence representation of this elements closing tag e.g. For <element></element> this
     *         would return "</element>".
     */
    public CharSequence getCloseTag() {
        return closeTag;
    }

    /*
       Override methods.
    */

    @Override
    public String toString() {
        StringBuilder elementString = new StringBuilder(0);

        elementString.append(getOpenTag()); // ...add the open tag to the output, ...
        for (MarkupElement child : getChildren()) elementString.append(child.toString()); // ...add the child chars, ...
        elementString.append(getCloseTag()); // ...then lastly add the close tag.

        return elementString.toString(); // Return the complete string representation of the element.
    }
}