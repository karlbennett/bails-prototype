package org.bails;

import java.util.*;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class Element {

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private boolean bailsElement = false;
    private String bailsId;
    private CharSequence openTag;
    private boolean openClose = false;
    private boolean charSequence = false;
    private CharSequence chars;
    private String name;
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private List<Element> children = new ArrayList<Element>();
    private List<Element> bailsChildren = new ArrayList<Element>();
    private CharSequence closeTag;

    public Element(IBailsStream stream) {
        if (stream.isOpenTag()) { // If the stream is currently pointing at an open tag...

            this.openTag = stream.getCharSequence(); // ...then set the open tag char sequence, ...

            this.name = stream.getName(); // ...set the name of this element, ...
            this.attributes = stream.getAttributes(); // ...get any attributes the element might have, ...

            findBailsId(this.attributes); // ...check for any bails attributes, ... NOTE: Maybe move this into the stream?

            stream.next(); // ...and after that get the next element.

            Element child = null;
            // Process any child elements until we reach the closing tag for this element.
            while (!stream.isCloseTag()) {

                child = new Element(stream); // Take the child element.

                this.children.add(child); // Add to this elements children.
                // Also if the child is a bails element add it to the bails children list.
                if (child.isBailsElement()) this.bailsChildren.add(child);

                stream.next(); // Move to the next element.
            }

            // Once we have reached the closing tag set the close tag char sequence.
            this.closeTag = stream.getCharSequence();
        } else if (stream.isOpenCloseTag()) {  // If the stream is currently pointing at an open close tag...

            this.openTag = stream.getCharSequence(); // ...then set the open tag char sequence, ...
            this.openClose = true; // ...set flag this to be an open close tag, ...

            this.name = stream.getName(); // ...set the name of this element, ...
            this.attributes = stream.getAttributes(); // ...get any attributes the element might have, ...

            findBailsId(this.attributes); // ...check for any bails attributes.

        } else if (stream.isCharacters()) { // If the stream is currently pointing at some characters...

            this.chars = stream.getCharSequence(); // ...record the characters and...
            this.charSequence = true; // ...set flag this as a character element.

            // Die on any other element type.
        } else throw new RuntimeException("Tried to initialise an element of unknown type.");
    }

    @Override
    public String toString() {
        StringBuilder elementString = new StringBuilder(0);

        if (isCharSequence()) {
            elementString.append(chars);
        } else if (isOpenClose()) {
            elementString.append(getOpenTag());
        } else {
            elementString.append(getOpenTag());
            for (Element child : children) elementString.append(child.toString());
            elementString.append(getCloseTag());
        }

        return elementString.toString();
    }

    /*
        Convenience methods.
     */

    private void findBailsId(Map<String, Object> attributes) {
        Object bailsId = attributes.get(BAILS_ID_NAME); // Try and request the Bails id attribute.

        if (bailsId != null) { // If a Bails id was found...
            this.bailsId = bailsId.toString(); // ...record it and...
            this.bailsElement = true; // ...flag this as a Bails element.
        }
    }

    /*
        Getters and Setters.
     */

    /**
     * @return true if this represents a bails element.
     */
    public boolean isBailsElement() {
        return bailsElement;
    }

    /**
     * @return The bails id for this element.
     */
    public String getBailsId() {
        return bailsId;
    }

    /**
     * @return Get the character sequence representation of this elements opening tag e.g. For <element></element> this
     * would return "<element>".
     */
    public CharSequence getOpenTag() {
        return openTag;
    }

    /**
     * @return True if this Element class represents a openClose element e.g. <element/>.
     */
    public boolean isOpenClose() {
        return openClose;
    }

    /**
     * @return True if this Element class represents a charSequence element e.g. "Some text".
     */
    public boolean isCharSequence() {
        return charSequence;
    }

    /**
     * @return The name of this element e.g. For <element/> this would return "element".
     */
    public String getName() {
        return name;
    }

    /**
     * @return The list of attributes for this element e.g. For <element one="1"/> this would return map containing a
     * key of "one" with a matching value of 1.
     */
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * @return The list of child elements within this Element class.
     */
    public List<Element> getChildren() {
        return children;
    }

    /**
     * @return The list of Bails child elements within the Element class.
     */
    public List<Element> getBailsChildren() {
        return bailsChildren;
    }

    /**
     * @return Get the character sequence representation of this elements closing tag e.g. For <element></element> this
     * would return "</element>".
     */
    public CharSequence getCloseTag() {
        return closeTag;
    }
}