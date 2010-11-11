package org.bails;

import static org.bails.TestBailsStreamFactory.BAILS_STREAM_SELECTOR.*;

import org.junit.Test;

import java.util.Map;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author Karl Bennett
 */
public class ElementTest {

    private static final String TEST_CHAR_SEQUENCE = TestBailsStreamFactory.TEST_CHAR_SEQUENCE;
    private static final String TEST_NAME = TestBailsStreamFactory.TEST_NAME;
    private static final String TEST_OPEN_TAG = TestBailsStreamFactory.TEST_OPEN_TAG;
    private static final String TEST_OPEN_CLOSE_TAG = TestBailsStreamFactory.TEST_CLOSE_TAG;
    private static final String TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES =
            TestBailsStreamFactory.TEST_OPEN_TAG_WITH_ATTRIBUTES;
    private static final String TEST_CLOSE_TAG = TestBailsStreamFactory.TEST_CLOSE_TAG;
    private static final Map<String, Object> TEST_ATTRIBUTES = TestBailsStreamFactory.TEST_ATTRIBUTES;
    private static final Map<String, Object> TEST_ATTRIBUTES_WITH_BAILS_ID =
            TestBailsStreamFactory.TEST_ATTRIBUTES_WITH_BAILS_ID;

    @Test
    public void testCharSequenceElement() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.isCharSequence()).thenReturn(true);
        when(stream.isOpenTag()).thenReturn(false);
        when(stream.isOpenCloseTag()).thenReturn(false);
        when(stream.isCloseTag()).thenReturn(false);

        when(stream.getCurrentElement()).thenReturn(TEST_CHAR_SEQUENCE);

        Element element = new Element(stream);

        verify(stream, times(1)).getCurrentElement();
        verify(stream, never()).next();

        assertEquals("char sequence equals", TEST_CHAR_SEQUENCE, element.toString());
    }

    @Test
    public void testOpenCloseElement() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.isCharSequence()).thenReturn(false);
        when(stream.isOpenTag()).thenReturn(false);
        when(stream.isOpenCloseTag()).thenReturn(true);
        when(stream.isCloseTag()).thenReturn(false);

        when(stream.getCurrentElement()).thenReturn(TEST_OPEN_CLOSE_TAG);
        when(stream.getName()).thenReturn(TEST_NAME);

        Element element = new Element(stream);

        verify(stream, times(1)).getCurrentElement();
        verify(stream, times(1)).getName();
        verify(stream, times(1)).getAttributes();
        verify(stream, never()).next();

        assertEquals("char sequence equals", TEST_OPEN_CLOSE_TAG, element.toString());
        assertEquals("name equals", TEST_NAME, element.getName());
    }

    @Test
    public void testOpenCloseElementWithAttributes() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.isCharSequence()).thenReturn(false);
        when(stream.isOpenTag()).thenReturn(false);
        when(stream.isOpenCloseTag()).thenReturn(true);
        when(stream.isCloseTag()).thenReturn(false);

        when(stream.getCurrentElement()).thenReturn(TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES);
        when(stream.getName()).thenReturn(TEST_NAME);
        when(stream.getAttributes()).thenReturn(TEST_ATTRIBUTES);

        Element element = new Element(stream);

        verify(stream, times(1)).getCurrentElement();
        verify(stream, times(1)).getName();
        verify(stream, times(1)).getAttributes();
        verify(stream, never()).next();

        assertEquals("char sequence equals", TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES, element.toString());
        assertEquals("name equals", TEST_NAME, element.getName());
        assertEquals("attributes equals", TEST_ATTRIBUTES, element.getAttributes());
    }

    @Test
    public void testOpenTagWithCharSequenceChild() throws Exception {
        Element element = new Element(TestBailsStreamFactory.getNewBailsStream(CHAR_SEQUENCE_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, element.getOpenTag());
        assertEquals("element name correct", TEST_NAME, element.getName());
        assertEquals("element has a child", 1, element.getChildren().size());
        Element child = element.getChildren().get(0);
        assertTrue("element child is char sequence", child.isCharSequence());
        assertEquals("element char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        assertEquals("element close tag correct", TEST_CLOSE_TAG, element.getCloseTag());
    }

    @Test
    public void testOpenTagWithSingleElementChild() throws Exception {
        Element element = new Element(TestBailsStreamFactory.getNewBailsStream(SINGLE_ELEMENT_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, element.getOpenTag());
        assertEquals("element name correct", TEST_NAME, element.getName());
        assertEquals("element has a child", 1, element.getChildren().size());
        Element child = element.getChildren().get(0);
        assertFalse("element child is tag", child.isCharSequence());
        assertEquals("element child open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child name correct", TEST_NAME, child.getName());
        assertEquals("element child close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child's child is char sequence", child.isCharSequence());
        assertEquals("element child's child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        assertEquals("element close tag correct", TEST_CLOSE_TAG, element.getCloseTag());
    }

    @Test
    public void testOpenTagWithDoubleElementChild() throws Exception {
        Element element = new Element(TestBailsStreamFactory.getNewBailsStream(DOUBLE_ELEMENT_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, element.getOpenTag());
        assertEquals("element name correct", TEST_NAME, element.getName());
        assertEquals("element has two children", 2, element.getChildren().size());
        Element child = element.getChildren().get(0);
        assertFalse("element child 1 is tag", child.isCharSequence());
        assertEquals("element child 1 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 1 name correct", TEST_NAME, child.getName());
        assertEquals("element child 1 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 1 child is char sequence", child.isCharSequence());
        assertEquals("element child 1 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        child = element.getChildren().get(1);
        assertFalse("element child 2 is tag", child.isCharSequence());
        assertEquals("element child 2 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 2 name correct", TEST_NAME, child.getName());
        assertEquals("element child 2 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 2 child is char sequence", child.isCharSequence());
        assertEquals("element child 2 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        assertEquals("element close tag correct", TEST_CLOSE_TAG, element.getCloseTag());
    }

    @Test
    public void testOpenTagWithBailsElementChild() throws Exception {
        Element element = new Element(TestBailsStreamFactory.getNewBailsStream(BAILS_ELEMENT_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, element.getOpenTag());
        assertEquals("element name correct", TEST_NAME, element.getName());
        assertEquals("element has three children", 3, element.getChildren().size());
        assertEquals("element has one bails child", 1, element.getBailsChildren().size());
        Element child = element.getChildren().get(0);
        assertFalse("element child 1 is tag", child.isCharSequence());
        assertEquals("element child 1 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 1 name correct", TEST_NAME, child.getName());
        assertEquals("element child 1 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 1 child is char sequence", child.isCharSequence());
        assertEquals("element child 1 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        child = element.getChildren().get(1);
        assertFalse("element child 2 is tag", child.isCharSequence());
        assertEquals("element child 2 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 2 name correct", TEST_NAME, child.getName());
        assertEquals("element child 2 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 2 child is char sequence", child.isCharSequence());
        assertEquals("element child 2 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());

        child = element.getChildren().get(2);
        assertFalse("element child 3 is tag", child.isCharSequence());
        assertTrue("element child 3 is bails element", child.isBailsElement());
        assertEquals("element child 3 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 3 name correct", TEST_NAME, child.getName());
        assertEquals("element child 3 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 3 child is char sequence", child.isCharSequence());
        assertEquals("element child 3 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());

        assertEquals("element close tag correct", TEST_CLOSE_TAG, element.getCloseTag());
    }
}
