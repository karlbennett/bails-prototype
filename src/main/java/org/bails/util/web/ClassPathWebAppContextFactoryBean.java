package org.bails.util.web;

import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.beans.factory.config.AbstractFactoryBean;

/**
 * This class is used in platform-server.xml to generate a {@link WebAppContext}
 * to be used in the embedded Jetty instance that serves remote services via
 * Spring Remoting (Http Invoker).
 * <p/>
 * We do this, rather than creating {@link WebAppContext} directly in the context
 * file, because we need to parse the webApp directory from the classpath and,
 * additionally, we want to prevent the webApp directory being extracted to the
 * local filesystem.
 *
 * @author Andrew Easter
 */
public class ClassPathWebAppContextFactoryBean extends
        AbstractFactoryBean<WebAppContext> {
    private static Log log = LogFactory
            .getLog(ClassPathWebAppContextFactoryBean.class);

    /**
     * The context path.
     */
    private String contextPath = "/";

    /**
     * The classpath location of the web application root directory.
     */
    private String webAppClassPath;

    /**
     * Constructor.
     *
     * @param webAppClassPath
     * @param contextPath
     */
    public ClassPathWebAppContextFactoryBean(String webAppClassPath,
                                             String contextPath) {
        this.webAppClassPath = webAppClassPath;
        this.contextPath = contextPath;
    }

    /**
     * @see org.springframework.beans.factory.config.AbstractFactoryBean#createInstance()
     */
    @Override
    protected WebAppContext createInstance() throws Exception {
        // This is one of the important bits - webApp root should be parsed
        // from the classpath (as opposed to a filesystem path).
        URL webAppURL = getClass().getClassLoader().getResource(webAppClassPath);

        String webAppURLString = webAppURL.toExternalForm();

        log.info("Creating Jetty WebAppContext for webApp URL: "
                + webAppURLString);

        WebAppContext webAppContext = new WebAppContext(webAppURLString,
                contextPath);

        // We don't want to extract the webApp directory. Firstly, because it is unnecessary and, secondly,
        // because it breaks the application if we do!
        webAppContext.setExtractWAR(false);

        return webAppContext;
    }

    /**
     * @see org.springframework.beans.factory.config.AbstractFactoryBean#getObjectType()
     */
    @Override
    public Class<? extends WebAppContext> getObjectType() {
        return WebAppContext.class;
    }
}