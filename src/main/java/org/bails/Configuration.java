package org.bails;

import org.bails.element.Page;
import org.bails.markup.MarkupElement;
import org.bails.property.Property;
import org.bails.stream.BailsStreamSTAX;
import org.bails.stream.IBailsStream;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * @author Karl Bennett
 */
public class Configuration {

    private String[] pagePackages;

    private Map<String, Class> pages = new HashMap<String, Class>();

    private Map<String, MarkupElement> markupElements = new HashMap<String, MarkupElement>();

    public Configuration() {}

    public Configuration(String... pagePackages) {
        setPagePackages(pagePackages);
    }

    /*
        Convenience methods.
     */

    /**
     * Searches in the given package for all the Page classes and their related markup.
     * <p/>
     * Code taken from: http://snippets.dzone.com/posts/show/4831
     *
     * @param packageName The base package
     */
    private void populatePages(String packageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Set<File> files = new HashSet<File>();

        String path = packageName.replace('.', '/');

        Enumeration<URL> resources = null;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            System.out.println("SORT THIS OUT!: " + e.getMessage());
        }

        if (resources != null) {
            URL resource = null;
            File file = null;
            while (resources.hasMoreElements()) {
                resource = resources.nextElement();
                file = new File(resource.getFile());
                if (file.isDirectory()) {
                    for (File childFile : file.listFiles()) {
                        if (childFile.isDirectory()) {
                            populatePages(packageName + "." + childFile.getName());
                        } else setPageOrMarkup(packageName, childFile);
                    }
                } else setPageOrMarkup(packageName, file);
            }
        }
    }

    private void setPageOrMarkup(String packageName, File file) {
        if (file.getName().endsWith(".class")) {
            String className = file.getName().substring(0, file.getName().length() - 6);
            try {
                Class pageClass = Class.forName(packageName + "." + className);
                if (Page.class.isAssignableFrom(pageClass)) pages.put(className, pageClass);
            } catch (ClassNotFoundException e) {
                System.out.println("SORT THIS OUT!: " + e.getMessage());
            }
        } else if (file.getName().endsWith(".html")) {
            String viewName = file.getName().substring(0, file.getName().length() - 5);
            try {
                markupElements.put(viewName, new MarkupElement(getBailsStream(file)));
            } catch (FileNotFoundException e) {
                System.out.println("SORT THIS OUT!: " + e.getMessage());
            }
        }
    }

    private IBailsStream getBailsStream(File file) throws FileNotFoundException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(file.getName());

        return new BailsStreamSTAX(stream == null ? new FileInputStream(file) : stream);
    }

    public Page getPage(String pageName) {
        Class pageClass = pages.get(pageName);
        Page page = null;

        if (pageClass != null) {
            try {
                page = (Page) pageClass.newInstance();
                page.setMarkupElement(markupElements.get(pageClass.getSimpleName()));
            } catch (InstantiationException e) {
                System.out.println("SORT THIS OUT!: " + e.getMessage());
            } catch (IllegalAccessException e) {
                System.out.println("SORT THIS OUT!: " + e.getMessage());
            }
        }

        return page;
    }

    public static void populatePropteries(Page page, Map<String, Object> model) throws IllegalAccessException {

        Object value = null;
        for (Field field : page.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            value = model.get(field.getName());

            if (value != null) {
                field.set(page, value);
            } else throw new RuntimeException("Property " + field.getName() + " not found in the model.");
        }
    }

    /*
       Getters and Setters.
    */

    /**
     * @return a set of string contain the package names that should be scanned for pages.
     */
    public String[] getPagePackages() {
        return pagePackages;
    }

    public void setPagePackages(String... pagePackages) {
        this.pagePackages = pagePackages;
        for (String packageName : pagePackages) {
            populatePages(packageName);
        }
    }

    /**
     * @return a map of the pages with the pages bailsId as the key.
     */
    public Map<String, Class> getPages() {
        return pages;
    }
}
