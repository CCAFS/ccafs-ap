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
package org.cgiar.ccafs.utils;

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
  private static final String MIDOUTCOME_YEAR = "ccafsap.midOutcome.revisionYear";
  private static final String PLANNING_FUTURE_YEARS_ACTIVE = "ccafsap.planning.future.years.active";
  private static final String PLANNING_FUTURE_YEARS = "ccafsap.planning.future.years";
  private static final String PREPLANNING_ACTIVE = "ccafsap.preplanning.active";
  private static final String PLANNING_ACTIVE = "ccafsap.planning.active";
  private static final String REPORTING_ACTIVE = "ccafsap.reporting.active";
  private static final String SUMMARIES_ACTIVE = "ccafsap.summaries.active";
  private static final String GMAIL_USER = "gmail.user";
  private static final String GMAIL_PASSWORD = "gmail.password";
  private static final String MAX_CASE_STUDY_TYPES = "ccafsap.reporting.caseStudy.types.max";
  private static final String UPLOADS_BASE_FOLDER = "file.uploads.baseFolder";
  private static final String LOCATIONS_TEMPLATE_FOLDER = "file.uploads.locationsTemplateFolder";
  private static final String CASE_STUDIES_FOLDER = "file.uploads.caseStudiesImagesFolder";
  private static final String FILE_DOWNLOADS = "file.downloads";

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
    if (!base.startsWith("https://")) {
      base = "https://" + base;
      return base;
    }
    return base;
  }

  /**
   * Get the folder where the case studies images uploaded should be saved
   * 
   * @return a string with the path
   */
  public String getCaseStudiesImagesFolder() {
    try {
      return properties.getPropertiesAsString(CASE_STUDIES_FOLDER);
    } catch (Exception e) {
      LOG.error("there is not a base folder to save the uploaded files configured.");
    }
    return null;
  }

  /**
   * Get the URL where the users can download the uploaded files
   * 
   * @return a string with the path
   */
  public String getDownloadURL() {
    String downloadsURL = properties.getPropertiesAsString(FILE_DOWNLOADS);
    if (downloadsURL == null) {
      LOG.error("There is not a downloads url configured");
      return null;
    }
    while (downloadsURL != null && downloadsURL.endsWith("/")) {
      downloadsURL = downloadsURL.substring(0, downloadsURL.length() - 1);
    }
    if (!downloadsURL.startsWith("https://")) {
      downloadsURL = "https://" + downloadsURL;
      return downloadsURL;
    }
    return downloadsURL;
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

  /**
   * Get the number of future years that an user can plan.
   * 
   * @return an integer identifying the number of years.
   */
  public int getFuturePlanningYears() {
    try {
      return properties.getPropertiesAsInt(PLANNING_FUTURE_YEARS);
    } catch (Exception e) {
      LOG.error("There is not a number of future years that an user can plan.");
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
   * Get the folder where the locations templates uploaded should be saved
   * 
   * @return a string with the path
   */
  public String getLocationsTemplateFolder() {
    try {
      return properties.getPropertiesAsString(LOCATIONS_TEMPLATE_FOLDER);
    } catch (Exception e) {
      LOG.error("there is not a base folder to save the uploaded files configured.");
    }
    return null;
  }

  /**
   * Get the number maximum of types that can have a case study
   * 
   * @return
   */
  public int getMaxCaseStudyTypes() {
    try {
      return properties.getPropertiesAsInt(MAX_CASE_STUDY_TYPES);
    } catch (Exception e) {
      LOG.error("there is not a number maximum of types that can have a case study configured.");
    }
    return -1;
  }

  /**
   * Get the year of the midOutcome revision.
   * 
   * @return an integer identifying the midOutcome year.
   */
  public int getMidOutcomeYear() {
    try {
      return properties.getPropertiesAsInt(MIDOUTCOME_YEAR);
    } catch (Exception e) {
      LOG.error("There is not a midoutcome revision year configured.");
    }
    return -1;
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
   * Get the base folder where the uploaded files should be saved
   * 
   * @return a string with the path
   */
  public String getUploadsBaseFolder() {
    try {
      return properties.getPropertiesAsString(UPLOADS_BASE_FOLDER);
    } catch (Exception e) {
      LOG.error("there is not a base folder to save the uploaded files configured.");
    }
    return null;
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
   * Get the flag that indicate if planing for future years is active, that value is in the configuration file.
   * 
   * @return a boolean indicating if it is active or not.
   */
  public boolean isPlanningForFutureYearsActive() {
    String planningFutureActive = properties.getPropertiesAsString(PLANNING_FUTURE_YEARS_ACTIVE);
    if (planningFutureActive == null) {
      LOG.error("There is not a planning for future years active configured");
      return false;
    }

    return planningFutureActive.equals("true");
  }

  /**
   * Get the flag that indicate if the preplanning stage is active
   * according to the variable in the configuration file.
   * 
   * @return a boolean indicating if it is active.
   */
  public boolean isPrePlanningActive() {
    String prePlanningActive = properties.getPropertiesAsString(PREPLANNING_ACTIVE);
    if (prePlanningActive == null) {
      LOG.error("There is not a preplanning active configured");
      return false;
    }

    return prePlanningActive.equals("true");
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

  /**
   * Get the flag that indicate if summaries stage is active that is in the configuration file.
   * 
   * @return a boolean indicating if it is active.
   */
  public boolean isSummariesActive() {
    String summariesActive = properties.getPropertiesAsString(SUMMARIES_ACTIVE);
    if (summariesActive == null) {
      LOG.error("There is not a summaries active configured");
      return false;
    }

    return summariesActive.equals("true");
  }

}