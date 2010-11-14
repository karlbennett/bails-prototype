package org.bails;

import org.bails.element.Page;
import org.bails.test.TestPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Set;

import static junit.framework.Assert.*;

/**
 * @author Karl Bennett
 */
public class ConfigurationTest {

    private static final String TEST_PAGE_PACKAGE = "org.bails.test";

    private Configuration configuration;

    @Before
    public void initConfiguration() {
        configuration = new Configuration(TEST_PAGE_PACKAGE);
    }

    @After
    public void destroyConfiguration() {
        configuration = null;
    }

    @Test
    public void testGetPage() throws Exception {
        Page page = configuration.getPage(TestPage.class.getSimpleName());

        assertEquals("test page correct", TestPage.class.getSimpleName(), page.getBailsId());

        System.out.println(page.render());
    }

    @Test
    public void testGetPagePackages() throws Exception {
        assertEquals("test package found", 1, configuration.getPagePackages().length);
    }

    @Test
    public void testGetPages() throws Exception {
        assertEquals("test page found", 1, configuration.getPages().size());
    }
}
