package org.bails;

import org.bails.stream.BailsStreamSTAX;
import org.bails.stream.IBailsStream;
import org.bails.test.BailsTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.bails.test.BailsTestUtil.*;

/**
 * User: Karl Bennett
 * Date: 19/12/10
 */
public class PageTest {

    private IBailsStream stream;

    @Before
    public void initStream() throws FileNotFoundException {
        stream = new BailsStreamSTAX(new ByteArrayInputStream(
                BailsTestUtil.XML_DCOUMENT.getBytes(Charset.forName("UTF8"))));
    }

    @After
    public void clearStream() {
        stream.close();
        stream = null;
    }

    @Test
    public void testRender() throws Exception {
        Page page = new TestPage();

        page.design();

        assertTrue("page contains children", 0 < page.getBailsChildren().size());
        assertEquals("child bailsId correct", TEST_BAILS_ID_ONE, page.getBailsChild(TEST_BAILS_ID_ONE).getBailsId());
        assertEquals("child bailsPath correct", TEST_BAILS_ID_ONE, page.getBailsChild(TEST_BAILS_ID_ONE).getBailsPath());
        assertEquals("child child's bailsId correct", TEST_BAILS_ID_TWO,
                page.getBailsChild(TEST_BAILS_ID_ONE + ":" + TEST_BAILS_ID_TWO).getBailsId());
        assertEquals("child child's bailsPath correct", TEST_BAILS_ID_ONE + ":" + TEST_BAILS_ID_TWO,
                page.getBailsChild(TEST_BAILS_ID_ONE + ":" + TEST_BAILS_ID_TWO).getBailsPath());
    }

    @Test
    public void testGetBailsChild() throws Exception {

    }

    private class TestPage extends Page {

        @Override
        public void design() {
            BailsElement element1 = new BailsElement(TEST_BAILS_ID_ONE);
            element1.add(new BailsElement(TEST_BAILS_ID_TWO, "Something."));
            add(element1);
            element1.add(new BailsElement(TEST_BAILS_ID_THREE, "Something else."));

            BailsElement element2 = new BailsElement(TEST_BAILS_ID_FOUR);
            element2.add(new BailsElement(TEST_BAILS_ID_FIVE, "Something else again."));
            add(element2);
            element2.add(new BailsElement(TEST_BAILS_ID_SIX, "Something new this time."));

        }
    }
}