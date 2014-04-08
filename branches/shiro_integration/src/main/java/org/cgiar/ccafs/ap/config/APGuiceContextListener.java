package org.cgiar.ccafs.ap.config;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.struts2.Struts2GuicePluginModule;
import org.apache.shiro.guice.web.ShiroWebModule;


public class APGuiceContextListener extends GuiceServletContextListener {

  private ServletContext servletContext;

  @Override
  public void contextInitialized(final ServletContextEvent servletContextEvent) {
    this.servletContext = servletContextEvent.getServletContext();
    super.contextInitialized(servletContextEvent);
  }

  @Override
  protected Injector getInjector() {
    return Guice.createInjector(new APShiroWebModule(this.servletContext), ShiroWebModule.guiceFilterModule(),
      new Struts2GuicePluginModule());
  }
}
