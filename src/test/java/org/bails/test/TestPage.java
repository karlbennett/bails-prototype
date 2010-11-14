package org.bails.test;

import org.bails.element.Element;
import org.bails.element.HTMLElement;
import org.bails.element.Page;
import org.junit.Ignore;

/**
 * @author Karl Bennett
 */

@Ignore
public class TestPage extends Page {

    public TestPage() {
        Element body = new HTMLElement("body");
        body.add(new HTMLElement("message", "The text in this tag has been successfully replaced."));
        add(body);
    }
}
