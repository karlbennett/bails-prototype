package org.bails.markup;

import org.bails.stream.IBailsStream;

/**
 * This class gives an object representation of a tag within bails markup.
 *
 * @author Karl Bennett
 */
public class CharactersElement extends MarkupElement {

    private CharSequence chars;

    public CharactersElement() {
    }

    public CharactersElement(MarkupElement parent, IBailsStream stream) {
        setHeritage(parent);
        chars = stream.getCharSequence();
    }

    @Override
    public String toString() {
        StringBuilder elementString = new StringBuilder(0);

        elementString.append(chars);

        return elementString.toString();
    }
}