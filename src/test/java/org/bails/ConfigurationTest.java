package org.bails;

import org.bails.element.OldPage;
import org.bails.test.TestOldPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        OldPage oldPage = configuration.getPage(TestOldPage.class.getSimpleName());

        assertEquals("test oldPage correct", TestOldPage.class.getSimpleName(), oldPage.getBailsId());

        System.out.println(oldPage.render());
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
