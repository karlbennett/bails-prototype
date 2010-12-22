package org.bails.markup;

import org.bails.Element;
import org.bails.Page;
import org.bails.stream.IBailsStream;
import org.bails.visitor.IVisitor;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class CharactersElement extends BuildableElement {

    private CharSequence chars;

    public CharactersElement(Element parent, IBailsStream stream) {
        super(parent, null);
        chars = stream.getCharSequence();
    }

    @Override
    public String toString(IVisitor<String> visitor) {
        if (visitor == null) return chars.toString();
        else return visitor.element(this);
    }
}