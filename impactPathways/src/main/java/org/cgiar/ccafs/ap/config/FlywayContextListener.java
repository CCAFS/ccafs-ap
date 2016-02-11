package org.cgiar.ccafs.ap.config;


import org.cgiar.ccafs.utils.PropertiesManager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlywayContextListener implements ServletContextListener {

  private final String SQL_MIGRATIONS_PATH = "database/migrations";
  private final String JAVA_MIGRATIONS_PATH = "classpath:/org/cgiar/ccafs/ap/db/migrations";

  Logger LOG = LoggerFactory.getLogger(FlywayContextListener.class);
  private PropertiesManager properties;

  private void configurePlaceholders(Flyway flyway) {
    Map<String, String> placeHolders = new HashMap<>();

    placeHolders.put("database", properties.getPropertiesAsString("mysql.database"));
    placeHolders.put("user", properties.getPropertiesAsString("mysql.user"));

    flyway.setPlaceholders(placeHolders);
    flyway.setPlaceholderPrefix("$[");
    flyway.setPlaceholderSuffix("]");
    flyway.setPlaceholderReplacement(true);
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
  }

  @Override
  public void contextInitialized(ServletContextEvent sce) {

    System.out.println("entra al flyway");
    Flyway flyway = new Flyway();
    properties = new PropertiesManager();

    flyway.setDataSource(this.getDataSource());
    flyway.setLocations(SQL_MIGRATIONS_PATH, JAVA_MIGRATIONS_PATH);

    this.configurePlaceholders(flyway);
    /*
     * if (flyway.info().current() == null) {
     * LOG.info("Setting baseline version 3.0");
     * flyway.setBaselineVersion(MigrationVersion.fromVersion("3.0"));
     * flyway.baseline();
     * }
     * // Show the changes to be applied
     * LOG.info("-------------------------------------------------------------");
     * for (MigrationInfo i : flyway.info().all()) {
     * LOG.info("migrate task: " + i.getVersion() + " : " + i.getDescription() + " from file: " + i.getScript()
     * + " with state: " + i.getState());
     * }
     * LOG.info("-------------------------------------------------------------");
     */
    // Migrate
    // flyway.clean();
    // flyway.validate();
    flyway.repair();
    // flyway.setValidateOnMigrate(false);
    flyway.setOutOfOrder(true);
    flyway.migrate();
    System.out.println("Finaliza los listenrs");

  }

  private DataSource getDataSource() {
    MysqlDataSource dataSource = new MysqlDataSource();

    StringBuilder url = new StringBuilder();
    url.append("jdbc:mysql://");
    url.append(properties.getPropertiesAsString("mysql.host"));
    url.append(":");
    url.append(properties.getPropertiesAsString("mysql.port"));
    url.append("/");
    url.append(properties.getPropertiesAsString("mysql.database"));

    dataSource.setUrl(url.toString());
    dataSource.setUser(properties.getPropertiesAsString("mysql.user"));
    dataSource.setPassword(properties.getPropertiesAsString("mysql.password"));

    return dataSource;
  }


}
