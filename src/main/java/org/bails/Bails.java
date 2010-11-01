package org.bails;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This is the first step to getting Bails up and running. The main class that will start up a bear bones spring
 * environment with Jetty to test the Bails templates and view resolver.
 * @author Karl Bennett
 */
public class Bails {

    public static void main(String[] args) {
       AbstractApplicationContext context = new ClassPathXmlApplicationContext("start-up.xml");
       context.registerShutdownHook();
    }
}
