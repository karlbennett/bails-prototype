package org.bails.markup;

import org.bails.Element;
import org.bails.stream.ELEMENT_TYPE;
import org.bails.stream.IBailsStream;

/**
 * User: Karl Bennett
 * Date: 13/12/10
 */
public abstract class BuildibleElement extends Element {

    public BuildibleElement(Element parent, IBailsStream stream) {
        super(parent);
        if (stream != null) addChildren(stream);
    }

    /*
        Convenience methods.
     */

    protected void addChildren(IBailsStream stream) {
        stream.next(); // Get the next element.

        // Process any child elements until we reach the closing tag for this element.
        while (stream.hasNext() && stream.getType() != ELEMENT_TYPE.CLOSE) {

            switch (stream.getType()) {
                case OPEN: {
                    this.add(new TagElement(this, stream, stream.getCharSequence(), stream.getName(),
                            stream.getAttributes()));
                    break;
                }
                case OPENCLOSE: {
                    this.add(new TagElement(this, stream, stream.getCharSequence(), stream.getName(),
                            stream.getAttributes(),
                            true));
                    break;
                }
                case CHARACTERS: {
                    this.add(new CharactersElement(this, stream));
                    break;
                }
                case BAILS: {
                    this.add(new BailsTagElement(this, stream, stream.getBailsId(), stream.getCharSequence(),
                            stream.getName(), stream.getAttributes()));
                    break;
                }
            }

            stream.next(); // Move to the next element.
        }
    }

    /*
        Abstract methods
     */
}
