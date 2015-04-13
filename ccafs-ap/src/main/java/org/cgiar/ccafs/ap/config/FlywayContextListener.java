package org.cgiar.ccafs.ap.config;

import org.cgiar.ccafs.ap.util.PropertiesManager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlywayContextListener implements ServletContextListener {

  private final String MIGRATIONS_PATH = "database/migrations";

  Logger LOG = LoggerFactory.getLogger(FlywayContextListener.class);
  private PropertiesManager properties;

  public void contextDestroyed(ServletContextEvent sce) {
  }

  public void contextInitialized(ServletContextEvent sce) {
    Flyway flyway = new Flyway();
    properties = new PropertiesManager();

    flyway.setDataSource(getDataSource());
    flyway.setLocations(MIGRATIONS_PATH);

    // Placeholders configuration
    Map<String, String> placeHolders = new HashMap<>();
    placeHolders.put("database", properties.getPropertiesAsString("mysql.database"));
    placeHolders.put("user", properties.getPropertiesAsString("mysql.user"));

    flyway.setPlaceholders(placeHolders);
    flyway.setPlaceholderPrefix("$[");
    flyway.setPlaceholderSuffix("]");
    flyway.setPlaceholderReplacement(true);

    // Show the changes to be applied
    LOG.info("-------------------------------------------------------------");
    for (MigrationInfo i : flyway.info().all()) {
      LOG.info("migrate task: " + i.getVersion() + " : " + i.getDescription() + " from file: " + i.getScript());
    }
    LOG.info("-------------------------------------------------------------");

    // Migrate
    // flyway.clean();
    flyway.migrate();
  }

  private DataSource getDataSource() {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setUrl(properties.getPropertiesAsString("mysql.url"));
    dataSource.setUser(properties.getPropertiesAsString("mysql.user"));
    dataSource.setPassword(properties.getPropertiesAsString("mysql.password"));

    return dataSource;
  }


}
