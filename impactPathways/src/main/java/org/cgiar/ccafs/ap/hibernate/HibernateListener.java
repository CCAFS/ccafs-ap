/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/


package org.cgiar.ccafs.ap.hibernate;


import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.PropertiesManager;

import java.net.URL;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateListener implements ServletContextListener {

  private static Class clazz = HibernateListener.class;
  public static final String KEY_NAME = clazz.getName();
  private Configuration config;
  private SessionFactory factory;

  private String path = "/hibernate.cfg.xml";

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    //
  }

  @Override
  public void contextInitialized(ServletContextEvent event) {

    try {
      System.out.println("Entro al listener");
      URL url = HibernateListener.class.getResource(path);
      System.out.println(url.toString());
      config = new Configuration().configure(url);
      System.out.println("Entro al listener config" + config);
      PropertiesManager manager = new PropertiesManager();

      config.setProperty("hibernate.connection.username", manager.getPropertiesAsString(APConfig.MYSQL_USER));
      config.setProperty("hibernate.connection.password", manager.getPropertiesAsString(APConfig.MYSQL_PASSWORD));
      String url_mysql = "jdbc:mysql://" + manager.getPropertiesAsString(APConfig.MYSQL_HOST) + ":"
        + manager.getPropertiesAsString(APConfig.MYSQL_PORT) + "/"
        + manager.getPropertiesAsString(APConfig.MYSQL_DATABASE);
      config.setProperty("hibernate.connection.url", url_mysql);


      factory = config.buildSessionFactory();

      // save the Hibernate session factory into serlvet context
      event.getServletContext().setAttribute(KEY_NAME, factory);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}