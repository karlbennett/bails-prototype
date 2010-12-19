package org.bails.visitor;

import org.bails.markup.Document;
import org.bails.stream.BailsStreamSTAX;
import org.bails.stream.IBailsStream;
import org.bails.test.BailsTestUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;

import static junit.framework.Assert.*;

/**
 * User: pyr0
 * Date: 18/12/10
 */
public class VisitableElementTest {

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
    public void testVisitChildren() throws Exception {

        Document document = new Document(stream);

        document.visitChildren(new IVisitor() {
            @Override
            public void element(IVisitable element) {
                assertNotNull("element exists", element);
                assertTrue("element is visitable", element instanceof IVisitable);
            }
        });
    }
}
