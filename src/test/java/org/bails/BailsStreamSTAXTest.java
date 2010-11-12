package org.bails;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

import static junit.framework.Assert.*;
import static org.bails.TestBailsStreamFactory.*;


/**
 * @author Karl Bennett
 */
public class BailsStreamSTAXTest {

    private IBailsStream stream;

    @Before
    public void initStream() throws FileNotFoundException {
        stream = new BailsStreamSTAX(new ByteArrayInputStream(XML_DCOUMENT.getBytes(Charset.forName("UTF8"))));
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
        stream.next(); //   <element one="1" two="2" three="3">
        stream.next(); //       Some text one.

        assertTrue("element is a char sequence tag.", stream.isCharacters());
    }

    @Test
    public void testGetCurrentElement() throws Exception {
        assertEquals("element text string is correct.", XML_LINE_ONE, stream.getCharSequence());
    }

    @Test
    public void testGetName() throws Exception {
        assertEquals("element name is correct.", TEST_NAME, stream.getName());
    }

    @Test
    public void testGetAttributes() throws Exception {
        stream.next(); //   <element one="1" two="2" three="3">

        assertEquals("element has 3 attributes.", 3, stream.getAttributes().size());
        assertNotNull("element attributes contains one.", stream.getAttributes().get(ELEMENT_ATTRIBUTE_ONE));
        assertNotNull("element attributes contains two.", stream.getAttributes().get(ELEMENT_ATTRIBUTE_TWO));
        assertNotNull("element attributes contains three.", stream.getAttributes().get(ELEMENT_ATTRIBUTE_THREE));
    }

    @Test
    public void testExpectedCharSequences() throws Exception {
        StringBuilder testString = new StringBuilder(0);

        // <element xmlns:bails='http://www.bails.org/'>\n
        assertEquals("element one char sequence correct.", XML_LINE_ONE, stream.getCharSequence());

        stream.next();
        testString.append(stream.getCharSequence()); //     <element one='1' two='2' three='3'>
        stream.next();
        testString.append(stream.getCharSequence()); //     <element one='1' two='2' three='3'>Some text one.
        stream.next();
        testString.append(stream.getCharSequence()); //     <element one='1' two='2' three='3'>Some text one.</element>\n
        assertTrue("element two match correct.", testString.toString().matches(
                "^\\s+<element (one='1'|two='2'|three='3') (one='1'|two='2'|three='3') (one='1'|two='2'|three='3')>Some text one.</element>\\n"));

        testString.setLength(0);
        stream.next();
        testString.append(stream.getCharSequence()); //    <element>
        stream.next();
        testString.append(stream.getCharSequence()); //    <element>Some test two.
        stream.next();
        testString.append(stream.getCharSequence()); //    <element>Some test two.</element>\n
        assertEquals("element three char sequence correct.", XML_LINE_THREE, testString.toString());

        testString.setLength(0);
        stream.next();
        testString.append(stream.getCharSequence()); //    <element bails:id='test_element'>
        stream.next();
        testString.append(stream.getCharSequence()); //    <element bails:id='test_element'>Some text three.
        stream.next();
        testString.append(stream.getCharSequence()); //    <element bails:id='test_element'>Some text three.</element>\n
        assertEquals("element four char sequence correct.", XML_LINE_FOUR, testString.toString());

        testString.setLength(0);
        stream.next();
        testString.append(stream.getCharSequence()); //    <element>
        stream.next();
        testString.append(stream.getCharSequence()); //    <element></element>\n
        assertEquals("element five char sequence correct.", XML_LINE_FIVE, testString.toString());

        // Can't yet handle open closed elements.
        testString.setLength(0);
        stream.next();
        testString.append(stream.getCharSequence()); //    <element>
        stream.next();
        testString.append(stream.getCharSequence()); //    <element></element>\n
        assertEquals("element six char sequence correct.", XML_LINE_FIVE, testString.toString());


        stream.next(); // </element>
        assertEquals("element seven char sequence correct.", XML_LINE_SEVEN, stream.getCharSequence());
    }

    @Test
    public void testIntegrationTest() throws Exception {
        Element element = new Element(stream);

        assertEquals("element children correct.", CHILD_NUM, element.getChildren().size());
        assertEquals("element bails children correct.", BAILS_CHILD_NUM, element.getBailsChildren().size());
    }
}
