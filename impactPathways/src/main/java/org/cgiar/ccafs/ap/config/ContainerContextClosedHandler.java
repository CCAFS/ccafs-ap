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


package org.cgiar.ccafs.ap.config;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class ContainerContextClosedHandler implements ServletContextListener {


  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {
    Enumeration<Driver> drivers = DriverManager.getDrivers();

    Driver driver = null;

    // clear drivers
    while (drivers.hasMoreElements()) {
      try {
        driver = drivers.nextElement();
        DriverManager.deregisterDriver(driver);
        System.out.println("Delete");
      } catch (SQLException ex) {
        // deregistration failed, might want to do something, log at the very least
        ex.printStackTrace();
      }
    }

    // MySQL driver leaves around a thread. This static method cleans it up.

  }

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {
    Enumeration<Driver> drivers = DriverManager.getDrivers();

    Driver driver = null;

    // clear drivers
    while (drivers.hasMoreElements()) {
      try {
        driver = drivers.nextElement();
        DriverManager.deregisterDriver(driver);
        System.out.println("Delete");
      } catch (SQLException ex) {
        // deregistration failed, might want to do something, log at the very least
        ex.printStackTrace();
      }
    }
  }

}
