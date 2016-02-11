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

package org.cgiar.ccafs.ap.summaries.projects.csv;

import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.utils.APConfig;

import java.io.IOException;
import java.util.List;

import com.google.inject.Inject;


/**
 * @author Carlos Alberto Martínez M.
 */
public class BudgetSummaryCSV extends BaseCSV {

  private APConfig config;

  @Inject
  public BudgetSummaryCSV(APConfig config) {
    this.config = config;
  }

  /**
   * This method is used to add an institution being a project partner
   *
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(List<Institution> projectPartnerInstitutions, String[] projectList) {
    int i = 0;
    for (Institution institution : projectPartnerInstitutions) {
      try {

        this.writeString(String.valueOf(institution.getId()), false, true);

        this.writeString(institution.getName(), false, true);

        this.writeString(institution.getAcronym(), false, true);


        this.writeString(institution.getWebsiteLink(), false, true);

        this.writeString(institution.getCountry().getName(), false, true);

        // Getting the project ids
        this.writeString(projectList[i], false, false);
        i++;

        this.writeNewLine();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
  }

  /**
   * This method is used to generate the csv file for the ProjectPartner institutions.
   *
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  public byte[] generateCSV(List<Institution> projectPartnerInstitutions, String[] projectList) {

    try {
      String[] headers =
        new String[] {"Project ID", "Project title", "Institution acronym", "Web site", "Location", "Projects"};

      this.initializeCSV();
      this.addHeaders(headers);
      this.addContent(projectPartnerInstitutions, projectList);
      this.flush();
      byte[] byteArray = this.getBytes();
      // Closing streams.
      this.closeStreams();
      return byteArray;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
