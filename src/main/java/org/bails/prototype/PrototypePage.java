package org.bails.prototype;

import org.bails.element.Element;
import org.bails.element.HTMLElement;
import org.bails.element.Page;

/**
 * @author Karl Bennett
 */
public class PrototypePage extends Page {

    public PrototypePage() {
        Element body = new HTMLElement("body");
        body.add(new HTMLElement("message", "This text has been replaced."));
        add(body);
    }
}
