package org.bails;

import org.junit.Test;

import static junit.framework.Assert.*;
import static org.bails.test.BailsTestUtil.*;

/**
 * User: Karl Bennett
 * Date: 19/12/10
 */
public class PageTest {

    @Test
    public void testRender() throws Exception {
        Page page = new TestPage();

        page.design();

        assertTrue("page contains children", 0 < page.getChildren().size());
        assertEquals("child bailsId correct", TEST_BAILS_ID_ONE, ((BailsElement)page.getChild(0)).getBailsId());
        assertEquals("child bailsPath correct", TEST_BAILS_ID_ONE, ((BailsElement)page.getChild(0)).getBailsPath());
        assertEquals("child child's bailsId correct", TEST_BAILS_ID_TWO,
                ((BailsElement)((Element)page.getChild(0)).getChild(0)).getBailsId());
        assertEquals("child child's bailsPath correct", TEST_BAILS_ID_ONE + ":" + TEST_BAILS_ID_TWO,
                ((BailsElement)((Element)page.getChild(0)).getChild(0)).getBailsPath());
    }

    @Test
    public void testGetBailsChild() throws Exception {

    }
}

class TestPage extends Page {

    @Override
    public void design() {
        Element element = new BailsElement(TEST_BAILS_ID_ONE);
        element.add(new BailsElement(TEST_BAILS_ID_TWO, "Something."));
        add(element);
        element.add(new BailsElement(TEST_BAILS_ID_THREE, "Something else."));
    }
}