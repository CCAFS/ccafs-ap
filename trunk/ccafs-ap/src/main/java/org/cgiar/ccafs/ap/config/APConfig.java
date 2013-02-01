package org.cgiar.ccafs.ap.config;

import org.cgiar.ccafs.ap.util.PropertiesManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class APConfig {

  public static final String MYSQL_HOST = "mysql.host";
  public static final String MYSQL_USER = "mysql.user";
  public static final String MYSQL_PASSWORD = "mysql.password";
  public static final String MYSQL_DATABASE = "mysql.database";
  public static final String MYSQL_PORT = "mysql.port";
  public static final String BASE_URL = "ccafsap.baseUrl";
  public static final String CURRENT_YEAR = "ccafsap.currentYear";
  public static final String END_YEAR = "ccafsap.endYear";

  // Logging.
  private static final Logger LOG = LoggerFactory.getLogger(APConfig.class);

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
    if (base == null) {
      LOG.error("There is not a base url configured");
      return null;
    }
    while (base != null && base.endsWith("/")) {
      base = base.substring(0, base.length() - 1);
    }
    /*
     * if (!base.startsWith("https://")) {
     * base = "http://" + base;
     * return base;
     * } else
     */
    if (!base.startsWith("http://")) {
      base = "http://" + base;
      return base;
    }
    return base;
  }

  /**
   * Get the path where are stored the case studies user images
   * 
   * @return a string with the path
   */
  public String getCaseStudiesImagesPath() {
    try {
      return properties.getPropertiesAsString("file.caseStudiesImagesPath");
    } catch (Exception e) {
      LOG.error("there is not a path for the user images configured.");
    }
    return null;
  }

  public String getCaseStudiesImagesUrl() {
    String url = properties.getPropertiesAsString("file.caseStudiesImagesUrl");
    if (url == null) {
      LOG.error("There is not a url for case studies images configured");
      return null;
    }
    while (url != null && url.endsWith("/")) {
      url = url.substring(0, url.length() - 1);
    }
    if (!url.startsWith("http://")) {
      url = "http://" + url;
      return url;
    }
    return url;
  }

  /**
   * Get the current year value that is in the configuration file.
   * 
   * @return an integer identifying the current year.
   */
  public int getCurrentYear() {
    try {
      return properties.getPropertiesAsInt("ccafsap.currentYear");
    } catch (Exception e) {
      LOG.error("There is not a current year configured");
    }
    return -1;
  }

  /**
   * Get the end year value that is in the configuration file.
   * 
   * @return an integer identifying the end year.
   */
  public int getEndYear() {
    try {
      return properties.getPropertiesAsInt("ccafsap.endYear");
    } catch (Exception e) {
      LOG.error("there is not a end  year configured.");
    }
    return -1;
  }

  /**
   * Get the maximun file size allowed
   * 
   * @return an integer with the value
   */
  public int getFileMaxSize() {
    try {
      return properties.getPropertiesAsInt("file.maxSizeAllowed.bytes");
    } catch (Exception e) {
      LOG.error("there is not a maximun file size configured.");
    }
    return -1;
  }

  public String getGmailPassword() {
    try {
      return properties.getPropertiesAsString("gmail.password");
    } catch (Exception e) {
      LOG.error("there is not a Gmail password configured.");
    }
    return null;
  }

  public String getGmailUsername() {
    try {
      return properties.getPropertiesAsString("gmail.user");
    } catch (Exception e) {
      LOG.error("there is not a Gmail user configured.");
    }
    return null;
  }

}
