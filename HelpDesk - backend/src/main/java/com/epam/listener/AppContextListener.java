package com.epam.listener;

import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String configFile = servletContextEvent.getServletContext().getInitParameter("log4jProperties");
        PropertyConfigurator.configure(configFile);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
