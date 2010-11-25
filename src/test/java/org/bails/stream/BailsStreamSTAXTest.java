package org.bails.stream;

import junit.framework.Assert;
import org.bails.markup.MarkupElement;
import org.bails.test.TestBailsTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

import static junit.framework.Assert.*;


/**
 * @author Karl Bennett
 */
public class BailsStreamSTAXTest {

    private IBailsStream stream;

    @Before
    public void initStream() throws FileNotFoundException {
        stream = new BailsStreamSTAX(new ByteArrayInputStream(
                TestBailsTestUtil.XML_DCOUMENT.getBytes(Charset.forName("UTF8"))));
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
        stream.next(); // DOCUMENT_START
        stream.next(); //   <element xmlns:bails='http://www.bails.org/'>
        assertEquals("element is an opening tag.", ELEMENT_TYPE.BAILS, stream.getType());
    }

    @Test
    public void testIsCloseTag() throws Exception {
        stream.next(); // DOCUMENT_START
        stream.next(); //   <element xmlns:bails='http://www.bails.org/'>
        stream.next(); //   <element one="1" two="2" three="3">
        stream.next(); //       Some text one.
        stream.next(); //   </element>

        assertEquals("element is a closing tag.", ELEMENT_TYPE.CLOSE, stream.getType());
    }

    @Test
    public void testIsOpenCloseTag() throws Exception {
        // Not implemented.
    }

    @Test
    public void testIsCharSequence() throws Exception {
        stream.next(); // DOCUMENT_START
        stream.next(); //   <element xmlns:bails='http://www.bails.org/'>
        stream.next(); //   <element one="1" two="2" three="3">
        stream.next(); //       Some text one.

        assertEquals("element is a char sequence tag.", ELEMENT_TYPE.CHARACTERS, stream.getType());
    }

    @Test
    public void testGetCurrentElement() throws Exception {
        stream.next(); // DOCUMENT_START
        stream.next(); //   <element xmlns:bails='http://www.bails.org/'>
//        assertEquals("element text string is correct.", TestBailsTestUtil.XML_LINE_ONE, stream.getCharSequence());
    }

    @Test
    public void testGetName() throws Exception {
        stream.next(); // DOCUMENT_START
        stream.next(); //   <element xmlns:bails='http://www.bails.org/'>
        assertEquals("element name is correct.", TestBailsTestUtil.TEST_NAME, stream.getName());
    }

    @Test
    public void testGetAttributes() throws Exception {
        stream.next(); // DOCUMENT_START
        stream.next(); //   <element xmlns:bails='http://www.bails.org/'>
        stream.next(); //   <element one="1" two="2" three="3">

        assertEquals("element has 3 attributes.", 3, stream.getAttributes().size());
        assertNotNull("element attributes contains one.", stream.getAttributes().get(TestBailsTestUtil.ELEMENT_ATTRIBUTE_ONE));
        assertNotNull("element attributes contains two.", stream.getAttributes().get(TestBailsTestUtil.ELEMENT_ATTRIBUTE_TWO));
        assertNotNull("element attributes contains three.", stream.getAttributes().get(TestBailsTestUtil.ELEMENT_ATTRIBUTE_THREE));
    }

    @Test
    public void testExpectedCharSequences() throws Exception {
        StringBuilder testString = new StringBuilder(0);

        stream.next(); // DOCUMENT_START
        stream.next(); //   <element xmlns:bails='http://www.bails.org/'>
//        assertEquals("element one char sequence correct.", TestBailsTestUtil.XML_LINE_ONE, stream.getCharSequence());

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
        Assert.assertEquals("element three char sequence correct.", TestBailsTestUtil.XML_LINE_THREE, testString.toString());

        testString.setLength(0);
        stream.next();
        testString.append(stream.getCharSequence()); //    <element bails:id='test_element'>
        stream.next();
        testString.append(stream.getCharSequence()); //    <element bails:id='test_element'>Some text three.
        stream.next();
        testString.append(stream.getCharSequence()); //    <element bails:id='test_element'>Some text three.</element>\n
        Assert.assertEquals("element four char sequence correct.", TestBailsTestUtil.XML_LINE_FOUR, testString.toString());

        testString.setLength(0);
        stream.next();
        testString.append(stream.getCharSequence()); //    <element>
        stream.next();
        testString.append(stream.getCharSequence()); //    <element/>\n - Empty tags converted to openClose.
        Assert.assertEquals("element five char sequence correct.", TestBailsTestUtil.XML_LINE_SIX, testString.toString());

        testString.setLength(0);
        stream.next();
        testString.append(stream.getCharSequence()); //    <element>
        stream.next();
        testString.append(stream.getCharSequence()); //    <element/>\n
        Assert.assertEquals("element six char sequence correct.", TestBailsTestUtil.XML_LINE_SIX, testString.toString());


        stream.next(); // </element>
        Assert.assertEquals("element seven char sequence correct.", TestBailsTestUtil.XML_LINE_SEVEN, stream.getCharSequence());
    }

    @Test
    public void testIntegrationTest() throws Exception {
        MarkupElement markupElement = new MarkupElement(stream);

        Assert.assertEquals("element children.", TestBailsTestUtil.CHILD_NUM, markupElement.getChild(0).getChildren().size());
    }
}
