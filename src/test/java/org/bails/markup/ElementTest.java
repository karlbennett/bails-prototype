package org.bails.markup;

import org.bails.stream.BailsStreamSTAX;
import org.bails.stream.ELEMENT_TYPE;
import org.bails.stream.IBailsStream;
import org.bails.test.TestBailsTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

import static junit.framework.Assert.*;
import static org.bails.test.TestBailsTestUtil.*;
import static org.mockito.Mockito.*;


/**
 * @author Karl Bennett
 */
public class ElementTest {

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
    public void initialiseMarkupElement() throws Exception {
        Element element = new Element(stream);
    }

    @Test
    public void testCharSequenceElement() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.getType()).thenReturn(ELEMENT_TYPE.CHARACTERS);
        when(stream.hasNext()).thenReturn(true).thenReturn(false);

        when(stream.getCharSequence()).thenReturn(TEST_CHAR_SEQUENCE);

        Element element = new Element(stream);

        verify(stream, times(2)).next();
        verify(stream, times(2)).hasNext();
        verify(stream, times(2)).getType();
        verify(stream, times(1)).getCharSequence();

        assertEquals("char sequence equals", TEST_CHAR_SEQUENCE, element.toString());
    }

    @Test
    public void testOpenCloseElement() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.getType()).thenReturn(ELEMENT_TYPE.OPENCLOSE);
        when(stream.hasNext()).thenReturn(true).thenReturn(false);

        when(stream.getCharSequence()).thenReturn(TEST_OPEN_CLOSE_TAG).thenReturn("");
        when(stream.getName()).thenReturn(TEST_NAME);

        Element element = new Element(stream);

        verify(stream, times(3)).next();
        verify(stream, times(3)).hasNext();
        verify(stream, times(2)).getType();
        verify(stream, times(2)).getCharSequence();
        verify(stream, times(1)).getName();
        verify(stream, times(1)).getAttributes();

        TagElement child = (TagElement) element.getChild(0);

        assertEquals("char sequence equals", TEST_OPEN_CLOSE_TAG, element.toString());
        assertEquals("name equals", TEST_NAME, child.getName());
    }

    @Test
    public void testOpenCloseElementWithAttributes() throws Exception {
        IBailsStream stream = mock(IBailsStream.class);

        when(stream.getType()).thenReturn(ELEMENT_TYPE.OPENCLOSE);
        when(stream.hasNext()).thenReturn(true).thenReturn(false);

        when(stream.getCharSequence()).thenReturn(TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES).thenReturn("");
        when(stream.getName()).thenReturn(TEST_NAME);
        when(stream.getAttributes()).thenReturn(TEST_ATTRIBUTES);

        Element element = new Element(stream);

        verify(stream, times(3)).next();
        verify(stream, times(3)).hasNext();
        verify(stream, times(2)).getType();
        verify(stream, times(2)).getCharSequence();
        verify(stream, times(1)).getName();
        verify(stream, times(1)).getAttributes();

        TagElement child = (TagElement) element.getChild(0);

        assertEquals("char sequence equals", TEST_OPEN_CLOSE_TAG_WITH_ATTRIBUTES, element.toString());
        assertEquals("name equals", TEST_NAME, child.getName());
        assertEquals("attributes equals", TEST_ATTRIBUTES, child.getAttributes());
    }
}
