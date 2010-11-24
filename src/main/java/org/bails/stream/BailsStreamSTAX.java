package org.bails.stream;

import org.bails.markup.TagElement;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static javax.xml.stream.XMLStreamConstants.*;

/**
 * @author Karl Bennett
 */
public class BailsStreamSTAX implements IBailsStream {

    // The new line char for the system.
    private static final String SYSTEM_NEW_LINE = System.getProperty("line.separator");
    // The default unix new line char.
    private static final String UNIX_NEW_LINE = "\n";

    // A regexp to check for a new line(s) surrounded by white space.
    private static final String SYSTEM_NEW_LINE_REGEXP = "(\\s*" + SYSTEM_NEW_LINE + "+\\s*)+";
    private static final String UNIX_NEW_LINE_REGEXP = "(\\s*" + UNIX_NEW_LINE + "+\\s*)+";

    private XMLEventReader parser; // The STAX parser to be used throughout this class.

    private XMLEvent currentEvent; // The xml event that is currently being pointed to by this stream.

    // The white space that should be placed before the char sequence of the current event.
    private String preWhiteSpace = "";
    // The white space that should be placed after the char sequence of the current event.
    private String postWhiteSpace = "";

    // The char sequence of the current xml event including whitespace.
    private StringBuilder currentEventString = new StringBuilder(0);

    private Map<String, Object> attributes; // The attributes for the current xml event.

    private boolean bailsTag = false;

    private ELEMENT_TYPE type;

    public BailsStreamSTAX(InputStream stream) {
        XMLInputFactory factory = XMLInputFactory.newInstance(); // Get a new STAX factory class.

        try {
            this.parser = factory.createXMLEventReader(stream); // Get a new STAX event reader.
//            next(); // Initialise the stream so that the it starts with data.
        } catch (XMLStreamException e) {
            System.out.println("SORT THIS OUT!: " + e.getMessage());
        }
    }

    /*
       Convenience methods.
    */

    /**
     * Check to see if the next even is just a new line character. If so add it to the end of the last events string.
     */
    private void checkForNewLine() {

        preWhiteSpace = "";
        postWhiteSpace = "";

        if (hasNext()) { // If there is a next event...
            try {
                XMLEvent event = parser.peek(); // ...peek at it and...
                String eventString = event.toString(); // ...get it's character representation.

                // Then if the next event is just a new ling and whitespace...
                if (eventString.matches(UNIX_NEW_LINE_REGEXP) || eventString.matches(SYSTEM_NEW_LINE_REGEXP)) {
                    // Record the whitespace that needs to be placed before the next real element.
                    preWhiteSpace = eventString.substring(eventString.indexOf('\n') + 1);
                    // Record the whitespace that needs to be placed after this element.
                    postWhiteSpace = eventString.substring(0, eventString.indexOf('\n') + 1);
                    parser.nextEvent(); // Lastly move on to the next element what ever it may be.
                }
            } catch (XMLStreamException e) {
                System.out.println("SORT THIS OUT!: " + e.getMessage());
            }
        }
    }

    /**
     * Pars the STAX attributes into the Bails attribute map.
     *
     * @param element a STAX @link javax.xml.stream.events.StartElement that may or may not contain attributes.
     * @return a map of string object key value pairs that represent the Bails attributes.
     */
    private Map<String, Object> parsAttributes(StartElement element) {
        Map<String, Object> attributes = new HashMap<String, Object>(); // Initialise the attributes map.

        // Get the attributes iterator from the STAX start element object.
        Iterator<Attribute> attributeIterator = element.getAttributes();
        Attribute attribute = null; // Place holder for each attribute.

        while (attributeIterator.hasNext()) { // Iterate over the attributes...
            attribute = attributeIterator.next(); // ...recording each one, ...

            QName qName = attribute.getName(); // ...taking the qName then...

            // ...recording the name as a string.
            // A check is done on the attribute prefix. If it exists it is added to the attributes name separated by a
            // colon (:).
            String nameString = qName.getPrefix().equals("") ? qName.getLocalPart() :
                    qName.getPrefix() + ":" + qName.getLocalPart();

            bailsTag = TagElement.BAILS_ID_NAME.equals(nameString); // Check if this is a bails id attribute and tag.

            attributes.put(nameString, attribute.getValue());
        }

        return attributes;
    }

    /*
       Override methods.
    */

    /**
     * @see IBailsStream#hashCode()
     */
    @Override
    public boolean hasNext() {
        // If the stream is not pointing to the end of the document there must be more to process.
        return currentEvent.getEventType() != END_DOCUMENT;
    }

    /**
     * @see IBailsStream#next()
     */
    @Override
    public void next() {
        try {
            currentEventString.setLength(0); // Clear the xml events char sequence.
            currentEvent = this.parser.nextEvent(); // get the next event.

            // Search for the next start/end element or a character event.
            // These are the only three events supported at the moment. This means that the stat/end document events
            // are ignored. So they will be absent from a rendered Bails page.
            while (currentEvent.getEventType() != START_ELEMENT
                    && currentEvent.getEventType() != CHARACTERS
                    && currentEvent.getEventType() != END_ELEMENT) {
                currentEvent = this.parser.nextEvent();
            }

            switch (currentEvent.getEventType()) {
                case START_ELEMENT : type = ELEMENT_TYPE.OPEN; break;
                case END_ELEMENT : type = ELEMENT_TYPE.CLOSE; break;
                case CHARACTERS : type = ELEMENT_TYPE.CHARACTERS;
            }

            // Add the white space that should be at the start of the char sequence.
            currentEventString.append(preWhiteSpace);
            currentEventString.append(currentEvent); // Add char sequence.

            // Get the white space that should be at the end of this char sequence as well as the white space that
            // should be before the next.
            checkForNewLine();

            // Add the white space that should be at the end of the char sequence.
            currentEventString.append(postWhiteSpace);

            // If this is an opening tag any attributes will need be recorded.
            if (currentEvent.getEventType() == START_ELEMENT) {

                attributes = parsAttributes((StartElement) currentEvent); // Pars the start events attributes.

                if(bailsTag) type = ELEMENT_TYPE.BAILS; // If this is a bails tag set it to the correct type.

            } else { // Otherwise there should be no attributes.
                attributes = null;
            }
        } catch (XMLStreamException e) {
            System.out.println("SORT THIS OUT!: " + e.getMessage());
        }
    }

    /**
     * @see IBailsStream#close()
     */
    @Override
    public void close() {
        try {
            parser.close(); // Close the STAX stream.
        } catch (XMLStreamException e) {
            System.out.println("SORT THIS OUT!: " + e.getMessage());
        }
    }

    @Override
    public ELEMENT_TYPE getType() {
        return type;
    }

    /**
     * @see IBailsStream#isOpenTag()
     */
    @Override
    public boolean isOpenTag() {
        return currentEvent.getEventType() == START_ELEMENT;
    }

    @Override
    public boolean isCloseTag() {
        return currentEvent.getEventType() == END_ELEMENT;
    }

    /**
     * @see IBailsStream#isOpenCloseTag()
     */
    @Override
    public boolean isOpenCloseTag() {
        return false; // No open close tag support yet.
    }

    /**
     * @see IBailsStream#isCharacters()
     */
    @Override
    public boolean isCharacters() {
        return currentEvent.getEventType() == CHARACTERS;
    }

    /**
     * @see IBailsStream#getCharSequence()
     */
    @Override
    public CharSequence getCharSequence() {
        // This should contain the tags char sequence along with any surrounding whitespace.
        return currentEventString.toString();
    }

    /**
     * @see IBailsStream#getName()
     */
    @Override
    public String getName() {
        String name = null;

        // Need to check what type of element this is because there are two that could contain a name.
        switch (currentEvent.getEventType()) {
            case START_ELEMENT:
                name = ((StartElement) currentEvent).getName().toString();
                break;
            case END_ELEMENT:
                name = ((EndElement) currentEvent).getName().toString();
                break;
        }

        return name;
    }

    /**
     * @see IBailsStream#getAttributes()
     */
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public boolean isBailsTag() {
        return bailsTag;
    }

    @Override
    public String getBailsId() {
        return (String) attributes.get(TagElement.BAILS_ID_NAME);
    }
}
