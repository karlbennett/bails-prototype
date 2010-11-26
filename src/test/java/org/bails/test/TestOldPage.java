package org.bails.test;

import org.bails.element.Element;
import org.bails.element.HTMLElement;
import org.bails.element.OldPage;
import org.junit.Ignore;

/**
 * @author Karl Bennett
 */

@Ignore
public class TestOldPage extends OldPage {

    @Override
    public void initialise() {
        Element body = new HTMLElement("body");
        body.add(new HTMLElement("message", "The text in this tag has been successfully replaced."));
        add(body);
    }
}
