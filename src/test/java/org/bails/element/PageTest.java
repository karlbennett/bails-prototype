package org.bails.element;

import org.bails.markup.BailsElement;
import org.bails.stream.BailsStreamSTAX;
import org.bails.stream.IBailsStream;
import org.bails.test.TestBailsTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

/**
 * User: Karl Bennett
 * Date: 07/12/10
 */
public class PageTest {

    private static IBailsStream stream;

    @Before
    public void initStream() {
        stream = new BailsStreamSTAX(new ByteArrayInputStream(
                TestBailsTestUtil.XML_DCOUMENT.getBytes(Charset.forName("UTF8"))));
    }

    @After
    public void closeStream() {
        stream.close();
        stream = null;
    }

    @Test
    public void testInitialise() throws Exception {
        Page page = new TestPage(stream);

        page.initialise();

        System.out.println(page.toString());
    }

    @Test
    public void testAdd() throws Exception {

    }

    private static class TestPage extends Page {

        public TestPage(IBailsStream stream) {
            super(stream);
        }

        @Override
        public void initialise() {
            BailsElement element = new BailsElement("test_element_one");
            add(element);
            element.add(new BailsElement("test_element_two"));
            System.out.println("Initialised");
        }
    }
}
