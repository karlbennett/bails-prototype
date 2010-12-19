package org.bails.markup;

import org.bails.Element;
import org.bails.Page;
import org.bails.stream.IBailsStream;

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
    public String toString() {
        String returnString = chars.toString();

        Element ancestor = getAncestor();
        if (ancestor instanceof Page) {
            Element parent = getParent();
            if (parent instanceof BailsTagElement && parent.getChildren().size() == 1) {
                returnString = ((Page) ancestor).getBailsChild(
                        ((BailsTagElement) parent).getBailsPath()).getChild(0).toString();
            }
        }

        return returnString;
    }
}