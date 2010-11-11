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
    private static final String TEST_OPEN_TAG = TestBailsStreamFactory.TEST_OPEN_TAG;
    private static final String TEST_OPEN_CLOSE_TAG = "<element/>";
    private static final String TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES = "<element one=\"1\" two=\"2\" three=\"3\"/>";
    private static final String TEST_CLOSE_TAG = TestBailsStreamFactory.TEST_CLOSE_TAG;
    private static final Map<String, Object> TEST_ATTRIBUTES = TestBailsStreamFactory.TEST_ATTRIBUTES;

    @Test
    public void testTestCharSequenceElement() throws Exception {
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
    public void testTestOpenCloseElement() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.isCharSequence()).thenReturn(false);
        when(stream.isOpenTag()).thenReturn(false);
        when(stream.isOpenCloseTag()).thenReturn(true);
        when(stream.isCloseTag()).thenReturn(false);

        when(stream.getCurrentElement()).thenReturn(TEST_OPEN_CLOSE_TAG);

        Element element = new Element(stream);

        verify(stream, times(1)).getCurrentElement();
        verify(stream, times(1)).getAttributes();
        verify(stream, never()).next();

        assertEquals("char sequence equals", TEST_OPEN_CLOSE_TAG, element.toString());
    }

    @Test
    public void testTestOpenCloseElementWithAttributes() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.isCharSequence()).thenReturn(false);
        when(stream.isOpenTag()).thenReturn(false);
        when(stream.isOpenCloseTag()).thenReturn(true);
        when(stream.isCloseTag()).thenReturn(false);

        when(stream.getCurrentElement()).thenReturn(TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES);
        when(stream.getAttributes()).thenReturn(TEST_ATTRIBUTES);

        Element element = new Element(stream);

        verify(stream, times(1)).getCurrentElement();
        verify(stream, times(1)).getAttributes();
        verify(stream, never()).next();

        assertEquals("char sequence equals", TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES, element.toString());
        assertEquals("attributes equals", TEST_ATTRIBUTES, element.getAttributes());
    }

    @Test
    public void testTestOpenTagWithCharSequenceChild() throws Exception {
        Element element = new Element(TestBailsStreamFactory.getNewBailsStream(CHAR_SEQUENCE_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, element.getOpenTag());
        assertEquals("element has a child", 1, element.getChildren().size());
        Element child = element.getChildren().get(0);
        assertTrue("element child is char sequence", child.isCharSequence());
        assertEquals("element char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        assertEquals("element close tag correct", TEST_CLOSE_TAG, element.getCloseTag());
    }

    @Test
    public void testTestOpenTagWithSingleElementChild() throws Exception {
        Element element = new Element(TestBailsStreamFactory.getNewBailsStream(SINGLE_ELEMENT_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, element.getOpenTag());
        assertEquals("element has a child", 1, element.getChildren().size());
        Element child = element.getChildren().get(0);
        assertFalse("element child is tag", child.isCharSequence());
        assertEquals("element child open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child's child is char sequence", child.isCharSequence());
        assertEquals("element child's child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        assertEquals("element close tag correct", TEST_CLOSE_TAG, element.getCloseTag());
    }

    @Test
    public void testTestOpenTagWithDoubleElementChild() throws Exception {
        Element element = new Element(TestBailsStreamFactory.getNewBailsStream(DOUBLE_ELEMENT_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, element.getOpenTag());
        assertEquals("element has two children", 2, element.getChildren().size());
        Element child = element.getChildren().get(0);
        assertFalse("element child 1 is tag", child.isCharSequence());
        assertEquals("element child 1 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 1 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 1 child is char sequence", child.isCharSequence());
        assertEquals("element child 1 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        child = element.getChildren().get(1);
        assertFalse("element child 2 is tag", child.isCharSequence());
        assertEquals("element child 2 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 2 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 2 child is char sequence", child.isCharSequence());
        assertEquals("element child 2 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        assertEquals("element close tag correct", TEST_CLOSE_TAG, element.getCloseTag());
    }
}
