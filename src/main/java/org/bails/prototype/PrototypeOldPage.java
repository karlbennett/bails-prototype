package org.bails.prototype;

import org.bails.element.Element;
import org.bails.element.HTMLElement;
import org.bails.element.OldPage;

/**
 * @author Karl Bennett
 */
public class PrototypeOldPage extends OldPage {

    private String message;

    @Override
    public void initialise() {
        Element html = new HTMLElement("html");
        Element body = new HTMLElement("body");
        body.add(new HTMLElement("message", message));
        html.add(body);
        add(html);
    }
}
