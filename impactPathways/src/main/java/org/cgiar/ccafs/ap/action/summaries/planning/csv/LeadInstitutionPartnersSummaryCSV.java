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

package org.cgiar.ccafs.ap.action.summaries.planning.csv;

import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;


/**
 * @author Carlos Alberto Martínez M.
 */
public class LeadInstitutionPartnersSummaryCSV extends BaseCSV {

  private InputStream inputStream;
  private String COMMA_DELIMITER;
  private String NEW_LINE_SEPARATOR;

  private int contentLength;
  private APConfig config;

  @Inject
  public LeadInstitutionPartnersSummaryCSV(APConfig config) {
    COMMA_DELIMITER = ",";
    NEW_LINE_SEPARATOR = "\n";
    headers =
      new String[] {"Institution ID", "Institution name", "Institution acronym", "Web site", "Location", "Projects"};
    this.config = config;
  }

  /**
   * This method is used to add an institution being a project leader
   * 
   * @param projectLeadingInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(List<Institution> projectLeadingInstitutions, String[] projectList) {
    int i = 0;
    for (Institution institution : projectLeadingInstitutions) {
      try {

        this.addRegister(institution.getId(), fileWriter);
        fileWriter.append(COMMA_DELIMITER);

        this.addRegister(institution.getName(), fileWriter);
        fileWriter.append(COMMA_DELIMITER);

        if (institution.getAcronym() != null && !institution.getAcronym().isEmpty()) {
          this.addRegister(institution.getAcronym(), fileWriter);
        }
        fileWriter.append(COMMA_DELIMITER);

        if (institution.getWebsiteLink() != null && !institution.getWebsiteLink().isEmpty()) {
          this.addRegister(institution.getWebsiteLink(), fileWriter);
        }
        fileWriter.append(COMMA_DELIMITER);

        this.addRegister(institution.getCountry().getName(), fileWriter);
        fileWriter.append(COMMA_DELIMITER);

        // Getting the project ids
        this.addRegister(projectList[i], fileWriter);
        i++;

        fileWriter.append(this.NEW_LINE_SEPARATOR);
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
  }

  /**
   * This method is used to generate the csv file for the ProjectLeading institutions.
   * 
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  public void generateCSV(List<Institution> projectLeadingInstitutions, String[] projectList) {

    File file = new File(config.getUploadsBaseFolder() + "temporal.txt");
    fileWriter = null;
    this.initializeCsv(file);

    try {
      fileWriter = new FileWriter(file, true);
      fileWriter.write('\ufeff');

      this.addHeaders(headers, fileWriter);
      this.addContent(projectLeadingInstitutions, projectList);
      fileWriter.close();

      // *********************Created the fileName****************************
      // ProjectLeading-Institutions-fecha(yyyyMMdd-HH:mm)
      StringBuffer fileName = new StringBuffer();

      fileName.append("ProjectLeading");
      fileName.append("-");
      fileName.append("Institutions");
      fileName.append("_");
      fileName.append(new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date()));
      fileName.append(".csv");

      this.fileName = fileName.toString();
      // *****************************************************************

      inputStream = new FileInputStream(file);
      contentLength = (int) file.length();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public int getContentLength() {
    return contentLength;
  }


  /**
   * method to get the inputStream
   * 
   * @return the inputStream
   */
  @Override
  public InputStream getInputStream() {
    return inputStream;
  }


  /**
   * method to set the inputStream
   * 
   * @param inputStream the inputStream to set
   */
  @Override
  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }
}
