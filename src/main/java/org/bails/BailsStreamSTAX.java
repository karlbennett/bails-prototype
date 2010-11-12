package org.bails;

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

    private XMLInputFactory factory;
    private XMLEventReader parser;

    private XMLEvent currentEvent;

    private Map<String, Object> attributes;

    public BailsStreamSTAX(InputStream stream) {
        this.factory = XMLInputFactory.newInstance();

        try {
            this.parser = this.factory.createXMLEventReader(stream);
            next();
        } catch (XMLStreamException e) {
            System.out.println("SORT THIS OUT!: " + e.getMessage());
        }
    }

    @Override
    public boolean hasNext() {
        return currentEvent.getEventType() != END_DOCUMENT;
    }

    @Override
    public void next() {
        try {
            currentEvent = this.parser.nextEvent();

            while (currentEvent.getEventType() != START_ELEMENT
                    && currentEvent.getEventType() != CHARACTERS
                    && currentEvent.getEventType() != END_ELEMENT) {
                currentEvent = this.parser.nextEvent();
            }

            if (currentEvent.getEventType() == START_ELEMENT) {
                attributes = new HashMap<String, Object>();

                Iterator<Attribute> attributeIterator = ((StartElement) currentEvent).getAttributes();
                Attribute attribute = null;

                while (attributeIterator.hasNext()) {
                    attribute = attributeIterator.next();

                    attributes.put(attribute.getName().toString(), attribute.getValue());
                }

            } else {
                attributes = null;
            }
        } catch (XMLStreamException e) {
            System.out.println("SORT THIS OUT!: " + e.getMessage());
        }
    }

    @Override
    public void close() {
        try {
            parser.close();
        } catch (XMLStreamException e) {
            System.out.println("SORT THIS OUT!: " + e.getMessage());
        }
    }

    @Override
    public boolean isOpenTag() {
        return currentEvent.getEventType() == START_ELEMENT;
    }

    @Override
    public boolean isCloseTag() {
        return currentEvent.getEventType() == END_ELEMENT;
    }

    @Override
    public boolean isOpenCloseTag() {
        return false;
    }

    @Override
    public boolean isCharSequence() {
        return currentEvent.getEventType() == CHARACTERS;
    }

    @Override
    public CharSequence getCurrentElement() {
        return currentEvent.toString();
    }

    @Override
    public String getName() {
        StartElement startElement = null;
        EndElement endElement = null;

        String name = "";

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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
