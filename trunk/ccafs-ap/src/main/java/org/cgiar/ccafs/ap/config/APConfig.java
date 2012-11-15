package org.cgiar.ccafs.ap.config;

import org.cgiar.ccafs.ap.util.PropertiesManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class APConfig {

  public static final String MYSQL_HOST = "mysql.host";
  public static final String MYSQL_USER = "mysql.user";
  public static final String MYSQL_PASSWORD = "mysql.password";
  public static final String MYSQL_DATABASE = "mysql.database";
  public static final String MYSQL_PORT = "mysql.port";
  public static final String BASE_URL = "ccafsap.baseUrl";

  private PropertiesManager properties;

  @Inject
  public APConfig(PropertiesManager properties) {
    this.properties = properties;
  }

  /**
   * Return the base url previously added in the configuration file.
   * 
   * @return The Base Url in the following format: http://baseurl or https://baseurl.
   */
  public String getBaseUrl() {
    String base = properties.getPropertiesAsString(BASE_URL);
    while (base != null && base.endsWith("/")) {
      base = base.substring(0, base.length() - 1);
    }
    if (!base.startsWith("https://")) {
      base = "http://" + base;
      return base;
    } else if (!base.startsWith("http://")) {
      base = "http://" + base;
      return base;
    }
    return base;
  }

}
