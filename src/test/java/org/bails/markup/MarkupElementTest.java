package org.bails.markup;

import static org.bails.stream.TestBailsStreamFactory.BAILS_STREAM_SELECTOR.*;

import org.bails.markup.MarkupElement;
import org.bails.stream.IBailsStream;
import org.bails.stream.TestBailsStreamFactory;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.*;
import static org.bails.stream.TestBailsStreamFactory.*;

/**
 * @author Karl Bennett
 */
public class MarkupElementTest {

    @Test
    public void testCharSequenceElement() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.isCharacters()).thenReturn(true);
        when(stream.isOpenTag()).thenReturn(false);
        when(stream.isOpenCloseTag()).thenReturn(false);
        when(stream.isCloseTag()).thenReturn(false);

        when(stream.getCharSequence()).thenReturn(TEST_CHAR_SEQUENCE);

        MarkupElement markupElement = new MarkupElement(stream);

        verify(stream, times(1)).getCharSequence();
        verify(stream, never()).next();

        assertEquals("char sequence equals", TEST_CHAR_SEQUENCE, markupElement.toString());
    }

    @Test
    public void testOpenCloseElement() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.isCharacters()).thenReturn(false);
        when(stream.isOpenTag()).thenReturn(false);
        when(stream.isOpenCloseTag()).thenReturn(true);
        when(stream.isCloseTag()).thenReturn(false);

        when(stream.getCharSequence()).thenReturn(TEST_OPEN_CLOSE_TAG);
        when(stream.getName()).thenReturn(TEST_NAME);

        MarkupElement markupElement = new MarkupElement(stream);

        verify(stream, times(1)).getCharSequence();
        verify(stream, times(1)).getName();
        verify(stream, times(1)).getAttributes();
        verify(stream, never()).next();

        assertEquals("char sequence equals", TEST_OPEN_CLOSE_TAG, markupElement.toString());
        assertEquals("name equals", TEST_NAME, markupElement.getName());
    }

    @Test
    public void testOpenCloseElementWithAttributes() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.isCharacters()).thenReturn(false);
        when(stream.isOpenTag()).thenReturn(false);
        when(stream.isOpenCloseTag()).thenReturn(true);
        when(stream.isCloseTag()).thenReturn(false);

        when(stream.getCharSequence()).thenReturn(TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES);
        when(stream.getName()).thenReturn(TEST_NAME);
        when(stream.getAttributes()).thenReturn(TEST_ATTRIBUTES);

        MarkupElement markupElement = new MarkupElement(stream);

        verify(stream, times(1)).getCharSequence();
        verify(stream, times(1)).getName();
        verify(stream, times(1)).getAttributes();
        verify(stream, never()).next();

        assertEquals("char sequence equals", TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES, markupElement.toString());
        assertEquals("name equals", TEST_NAME, markupElement.getName());
        assertEquals("attributes equals", TEST_ATTRIBUTES, markupElement.getAttributes());
    }

    @Test
    public void testOpenTagWithCharSequenceChild() throws Exception {
        MarkupElement markupElement = new MarkupElement(TestBailsStreamFactory.getNewBailsStream(CHAR_SEQUENCE_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, markupElement.getOpenTag());
        assertEquals("element name correct", TEST_NAME, markupElement.getName());
        assertEquals("element has a child", 1, markupElement.getChildren().size());
        MarkupElement child = markupElement.getChildren().get(0);
        assertTrue("element child is char sequence", child.isCharSequence());
        assertEquals("element char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        assertEquals("element close tag correct", TEST_CLOSE_TAG, markupElement.getCloseTag());
    }

    @Test
    public void testOpenTagWithSingleElementChild() throws Exception {
        MarkupElement markupElement = new MarkupElement(TestBailsStreamFactory.getNewBailsStream(SINGLE_ELEMENT_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, markupElement.getOpenTag());
        assertEquals("element name correct", TEST_NAME, markupElement.getName());
        assertEquals("element has a child", 1, markupElement.getChildren().size());
        MarkupElement child = markupElement.getChildren().get(0);
        assertFalse("element child is tag", child.isCharSequence());
        assertEquals("element child open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child name correct", TEST_NAME, child.getName());
        assertEquals("element child close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child's child is char sequence", child.isCharSequence());
        assertEquals("element child's child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        assertEquals("element close tag correct", TEST_CLOSE_TAG, markupElement.getCloseTag());
    }

    @Test
    public void testOpenTagWithDoubleElementChild() throws Exception {
        MarkupElement markupElement = new MarkupElement(TestBailsStreamFactory.getNewBailsStream(DOUBLE_ELEMENT_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, markupElement.getOpenTag());
        assertEquals("element name correct", TEST_NAME, markupElement.getName());
        assertEquals("element has two children", 2, markupElement.getChildren().size());
        MarkupElement child = markupElement.getChildren().get(0);
        assertFalse("element child 1 is tag", child.isCharSequence());
        assertEquals("element child 1 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 1 name correct", TEST_NAME, child.getName());
        assertEquals("element child 1 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 1 child is char sequence", child.isCharSequence());
        assertEquals("element child 1 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        child = markupElement.getChildren().get(1);
        assertFalse("element child 2 is tag", child.isCharSequence());
        assertEquals("element child 2 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 2 name correct", TEST_NAME, child.getName());
        assertEquals("element child 2 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 2 child is char sequence", child.isCharSequence());
        assertEquals("element child 2 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        assertEquals("element close tag correct", TEST_CLOSE_TAG, markupElement.getCloseTag());
    }

    @Test
    public void testOpenTagWithBailsElementChild() throws Exception {
        MarkupElement markupElement = new MarkupElement(TestBailsStreamFactory.getNewBailsStream(BAILS_ELEMENT_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, markupElement.getOpenTag());
        assertEquals("element name correct", TEST_NAME, markupElement.getName());
        assertEquals("element has three children", 3, markupElement.getChildren().size());
        assertEquals("element has one bails child", 1, markupElement.getBailsChildren().size());
        MarkupElement child = markupElement.getChildren().get(0);
        assertFalse("element child 1 is tag", child.isCharSequence());
        assertEquals("element child 1 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 1 name correct", TEST_NAME, child.getName());
        assertEquals("element child 1 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 1 child is char sequence", child.isCharSequence());
        assertEquals("element child 1 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());
        child = markupElement.getChildren().get(1);
        assertFalse("element child 2 is tag", child.isCharSequence());
        assertEquals("element child 2 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 2 name correct", TEST_NAME, child.getName());
        assertEquals("element child 2 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 2 child is char sequence", child.isCharSequence());
        assertEquals("element child 2 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());

        child = markupElement.getChildren().get(2);
        assertFalse("element child 3 is tag", child.isCharSequence());
        assertTrue("element child 3 is bails element", child.isBailsElement());
        assertEquals("element child 3 open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child 3 name correct", TEST_NAME, child.getName());
        assertEquals("element child 3 close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child 3 child is char sequence", child.isCharSequence());
        assertEquals("element child 3 child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());

        assertEquals("element close tag correct", TEST_CLOSE_TAG, markupElement.getCloseTag());
    }

    @Test
    public void testOpenTagWithChildElementWithChild() throws Exception {
        MarkupElement markupElement = new MarkupElement(TestBailsStreamFactory.getNewBailsStream(CHILD_WITH_ELEMENT_CHILD));

        assertEquals("element open tag correct", TEST_OPEN_TAG, markupElement.getOpenTag());
        assertEquals("element name correct", TEST_NAME, markupElement.getName());
        assertEquals("element has three children", 1, markupElement.getChildren().size());
        MarkupElement child = markupElement.getChildren().get(0);
        assertFalse("element child is tag", child.isCharSequence());
        assertEquals("element child open tag correct", TEST_OPEN_TAG, child.getOpenTag());
        assertEquals("element child name correct", TEST_NAME, child.getName());
        assertEquals("element child close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertFalse("element child child is tag", child.isCharSequence());
        assertEquals("element child child name correct", TEST_NAME, child.getName());
        assertEquals("element child child close tag correct", TEST_CLOSE_TAG, child.getCloseTag());
        child = child.getChildren().get(0);
        assertTrue("element child child child is char sequence", child.isCharSequence());
        assertEquals("element child child child char sequence correct", TEST_CHAR_SEQUENCE, child.toString());

        assertEquals("element close tag correct", TEST_CLOSE_TAG, markupElement.getCloseTag());
    }
}
