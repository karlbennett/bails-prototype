package org.bails.markup;

import org.bails.stream.IBailsStream;

import java.util.*;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class MarkupElement {

    public static final String BAILS_ID_NAME = "bails:id"; // Bails attribute name. Quick dirty and going to change.

    private boolean bailsElement = false;
    private String bailsId;
    private CharSequence openTag;
    private boolean openClose = false;
    private boolean charSequence = false;
    private CharSequence chars;
    private String name;
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private List<MarkupElement> children = new ArrayList<MarkupElement>();
    private List<MarkupElement> bailsChildren = new ArrayList<MarkupElement>();
    private CharSequence closeTag;

    public MarkupElement(IBailsStream stream) {
        if (stream.isOpenTag()) { // If the stream is currently pointing at an open tag...

            this.openTag = stream.getCharSequence(); // ...then set the open tag char sequence, ...

            this.name = stream.getName(); // ...set the name of this element, ...
            this.attributes = stream.getAttributes(); // ...get any attributes the element might have, ...

            findBailsId(this.attributes); // ...check for any bails attributes, ... NOTE: Maybe move this into the stream?

            stream.next(); // ...and after that get the next element.

            MarkupElement child = null;
            // Process any child elements until we reach the closing tag for this element.
            while (!stream.isCloseTag()) {

                child = new MarkupElement(stream); // Take the child element.

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

        if (isCharSequence()) { // If the MarkupElement is a character element just add it's chars to the output.
            elementString.append(chars);
        } else if (isOpenClose()) { // If it an ope close tag it won't have any children so just add the open tag chars.
            elementString.append(getOpenTag());
        } else { // If this an open tag it  may have children so...
            elementString.append(getOpenTag()); // ...add the open tag to the output, ...
            for (MarkupElement child : children) elementString.append(child.toString()); // ...add the child chars, ...
            elementString.append(getCloseTag()); // ...then lastly add the close tag.
        }

        return elementString.toString(); // Return the complete string representation of the element.
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

    /**
     * Get a child from the MarkupElement.
     * @param i the index of the child.
     * @return the child at the given index.
     */
    public MarkupElement getChild(int i) {
        return children == null ? null : children.get(i);
    }

    /**
     * Get a bails child from the MarkupElement.
     * @param i the index of the bails child.
     * @return the bails child at the given index.
     */
    public MarkupElement getBailsChild(int i) {
        return bailsChildren == null ? null : bailsChildren.get(i);
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
     * @return True if this MarkupElement class represents a openClose element e.g. <element/>.
     */
    public boolean isOpenClose() {
        return openClose;
    }

    /**
     * @return True if this MarkupElement class represents a charSequence element e.g. "Some text".
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
     * @return The list of child elements within this MarkupElement class.
     */
    public List<MarkupElement> getChildren() {
        return children;
    }

    /**
     * @return The list of Bails child elements within the MarkupElement class.
     */
    public List<MarkupElement> getBailsChildren() {
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