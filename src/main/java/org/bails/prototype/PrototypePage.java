package org.bails.prototype;

import org.bails.element.Element;
import org.bails.element.HTMLElement;
import org.bails.element.Page;
import org.bails.property.Property;

/**
 * @author Karl Bennett
 */
public class PrototypePage extends Page {

    private Property message = new Property();

    public PrototypePage() {
        Element body = new HTMLElement("body");
        body.add(new HTMLElement("message", message));
        add(body);
    }
}
