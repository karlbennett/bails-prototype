package org.bails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static junit.framework.Assert.*;

/**
 * @author Karl Bennett
 */
public class BailsStreamSTAXTest {

    private static final String ROOT_START_ELEMENT = "<element xmlns:bails='http://www.bails.org/'>";
    private static final String ROOT_START_ELEMENT_NAME = "element";
    private static final String ELEMENT_ATTRIBUTE_ONE = "one";
    private static final String ELEMENT_ATTRIBUTE_TWO = "two";
    private static final String ELEMENT_ATTRIBUTE_THREE = "three";

    private IBailsStream stream;

    @Before
    public void initStream() throws FileNotFoundException {
        stream = new BailsStreamSTAX(new FileInputStream("src/test/resources/test.xml"));
    }

    @After
    public void clearStream() {
        stream.close();
        stream = null;
    }

    @Test
    public void testHasNext() throws Exception {
        assertTrue("stream has next element.", stream.hasNext());
    }

    @Test
    public void testNext() throws Exception {
        stream.next();
    }

    @Test
    public void testIsOpenTag() throws Exception {
        assertTrue("element is an opening tag.", stream.isOpenTag());
    }

    @Test
    public void testIsCloseTag() throws Exception {
        stream.next(); // <element>
        stream.next(); //   <element one="1" two="2" three="3">
        stream.next(); //       Some text one.
        stream.next(); //   </element>

        assertTrue("element is a closing tag.", stream.isCloseTag());
    }

    @Test
    public void testIsOpenCloseTag() throws Exception {
        // Not implemented.
    }

    @Test
    public void testIsCharSequence() throws Exception {
        stream.next(); // <element>
        stream.next(); //   <element one="1" two="2" three="3">
        stream.next(); //       Some text one.

        assertTrue("element is a char sequence tag.", stream.isCharSequence());
    }

    @Test
    public void testGetCurrentElement() throws Exception {
        assertEquals("element text string is correct.", ROOT_START_ELEMENT, stream.getCurrentElement());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("element name is correct.", ROOT_START_ELEMENT_NAME, stream.getName());
    }

    @Test
    public void testGetAttributes() throws Exception {
        stream.next(); // <element>
        stream.next(); //   <element one="1" two="2" three="3">

        assertEquals("element has 3 attributes.", 3, stream.getAttributes().size());
        assertNotNull("element attributes contains one.", stream.getAttributes().get(ELEMENT_ATTRIBUTE_ONE));
        assertNotNull("element attributes contains two.", stream.getAttributes().get(ELEMENT_ATTRIBUTE_TWO));
        assertNotNull("element attributes contains three.", stream.getAttributes().get(ELEMENT_ATTRIBUTE_THREE));
    }
}
