package org.bails.element;

import org.bails.stream.BailsStreamSTAX;
import org.bails.stream.IBailsStream;
import org.bails.test.TestBailsTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

import static junit.framework.Assert.*;

/**
 * @author Karl Bennett
 */
public class OldPageTest {

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
    public void testRender() throws Exception {
        OldPage testOldPage = new TestOldPage(stream);

        assertEquals("page bails is correct", TestOldPage.class.getSimpleName(), testOldPage.getBailsId());

        OldPage otherTestOldPage = new TestOldPage();

        assertEquals("test pages equal", testOldPage, otherTestOldPage);

        OldPage stubOldPage = new StubOldPage();

        assertFalse("stub page not equal to test page", testOldPage.equals(stubOldPage));

        System.out.println(testOldPage.render());
    }

    private static class TestOldPage extends OldPage {

        public TestOldPage() {}

        public TestOldPage(IBailsStream stream) {
            super(stream);
        }

        @Override
        public void initialise() {
            add(new TestElement(TestBailsTestUtil.TEST_BAILS_ID_TWO));
        }
    }

    private static class StubOldPage extends OldPage {

        @Override
        public void initialise() {
        }
    }

    private static class TestElement extends Element {

        public TestElement(String bailsId) {
            super(bailsId);
        }

        @Override
        public String render() {
            return getElement().toString();
        }
    }
}
