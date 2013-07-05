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

  private static final String BASE_URL = "ccafsap.baseUrl";
  private static final String REPORTING_CURRENT_YEAR = "ccafsap.reporting.currentYear";
  private static final String PLANNING_CURRENT_YEAR = "ccafsap.planning.currentYear";
  private static final String START_YEAR = "ccafsap.startYear";
  private static final String END_YEAR = "ccafsap.endYear";
  private static final String PLANNING_ACTIVE = "ccafsap.planning.active";
  private static final String REPORTING_ACTIVE = "ccafsap.reporting.active";
  private static final String GMAIL_USER = "gmail.user";
  private static final String GMAIL_PASSWORD = "gmail.password";
  private static final String FILE_CASE_STUDIES_IMAGE_URL = "file.caseStudiesImagesUrl";
  private static final String FILE_CASE_STUDIES_IMAGE_PATH = "file.caseStudiesImagesPath";

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
      return properties.getPropertiesAsString(FILE_CASE_STUDIES_IMAGE_PATH);
    } catch (Exception e) {
      LOG.error("there is not a path for the user images configured.");
    }
    return null;
  }

  public String getCaseStudiesImagesUrl() {
    String url = properties.getPropertiesAsString(FILE_CASE_STUDIES_IMAGE_URL);
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
   * Get the end year value that is in the configuration file.
   * 
   * @return an integer identifying the end year.
   */
  public int getEndYear() {
    try {
      return properties.getPropertiesAsInt(END_YEAR);
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
      return properties.getPropertiesAsString(GMAIL_PASSWORD);
    } catch (Exception e) {
      LOG.error("there is not a Gmail password configured.");
    }
    return null;
  }

  public String getGmailUsername() {
    try {
      return properties.getPropertiesAsString(GMAIL_USER);
    } catch (Exception e) {
      LOG.error("there is not a Gmail user configured.");
    }
    return null;
  }

  /**
   * Get the current year value that is being used in the planning stage.
   * 
   * @return an integer identifying the current year.
   */
  public int getPlanningCurrentYear() {
    try {
      return properties.getPropertiesAsInt(PLANNING_CURRENT_YEAR);
    } catch (Exception e) {
      LOG.error("There is not a current year configured for the planning section.");
    }
    return -1;
  }

  /**
   * Get the current year value that is being used in the reporting stage.
   * 
   * @return an integer identifying the current year.
   */
  public int getReportingCurrentYear() {
    try {
      return properties.getPropertiesAsInt(REPORTING_CURRENT_YEAR);
    } catch (Exception e) {
      LOG.error("There is not a current year configured for the reporting section.");
    }
    return -1;
  }

  /**
   * Get the start year value that is in the configuration file.
   * 
   * @return an integer identifying the end year.
   */
  public int getStartYear() {
    try {
      return properties.getPropertiesAsInt(START_YEAR);
    } catch (Exception e) {
      LOG.error("there is not a start year configured.");
    }
    return -1;
  }

  /**
   * Get the flag that indicate is planing stage is active that is in the configuration file.
   * 
   * @return a boolean indicating if it is active.
   */
  public boolean isPlanningActive() {
    String planningActive = properties.getPropertiesAsString(PLANNING_ACTIVE);
    if (planningActive == null) {
      LOG.error("There is not a planning active configured");
      return false;
    }

    return planningActive.equals("true");
  }

  /**
   * Get the flag that indicate is planing stage is active that is in the configuration file.
   * 
   * @return a boolean indicating if it is active.
   */
  public boolean isReportingActive() {
    String reportingActive = properties.getPropertiesAsString(REPORTING_ACTIVE);
    if (reportingActive == null) {
      LOG.error("There is not a reporting active configured");
      return false;
    }

    return reportingActive.equals("true");
  }

}
