package org.bails;

import java.util.*;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class Element {

    public static final String BAILS_ID_NAME = "bails:id";

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
        if (stream.isOpenTag()) {

            this.openTag = stream.getCharSequence();

            this.name = stream.getName();
            this.attributes = stream.getAttributes();

            findBailsId(this.attributes);

            stream.next();

            Element child = null;
            while (!stream.isCloseTag()) {

                child = new Element(stream);

                this.children.add(child);
                if (child.isBailsElement()) this.bailsChildren.add(child);

                stream.next();
            }

            this.closeTag = stream.getCharSequence();
        } else if (stream.isOpenCloseTag()) {

            this.openTag = stream.getCharSequence();
            this.openClose = true;

            this.name = stream.getName();
            this.attributes = stream.getAttributes();

            findBailsId(this.attributes);

        } else if (stream.isCharacters()) {

            this.chars = stream.getCharSequence();
            this.charSequence = true;

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
        Object bailsId = attributes.get(BAILS_ID_NAME);

        if (bailsId != null) {
            this.bailsId = bailsId.toString();
            this.bailsElement = true;
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