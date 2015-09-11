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

import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.io.IOException;
import java.util.List;

import com.google.inject.Inject;
import org.apache.struts2.components.If;


/**
 * @author Jorge Leonardo Solis B.
 */

public class DeliverableSummaryCSV extends BaseCSV {

  private APConfig config;

  /**
   * Method constructor.
   */
  @Inject
  public DeliverableSummaryCSV(APConfig config) {
    this.config = config;
  }

  /**
   * Method is used for to add the deliverable
   * 
   * @param deliverables it is a list that contain the deliverables
   * @throws If an I/O error occurs.
   */
  private void addContent(List<Project> projectList) throws IOException {

    List<Deliverable> deliverables;
    StringBuilder stringBuilder;
    int counter = 0;
    Project project;
    // for (Project project : projectList) {
    try {
      for (int a = 0; a < projectList.size(); a++) {
        project = projectList.get(a);
        deliverables = project.getDeliverables();

        for (Deliverable deliverable : deliverables) {

          // if (deliverable != null && deliverable.getYear() > 2014)
          if (deliverable != null) {
            stringBuilder = new StringBuilder();

            // Project Id
            this.writeString(String.valueOf(project.getId()), true, true);

            // Title
            this.writeString(project.getTitle(), true, true);

            // Flagships
            counter = 0;
            stringBuilder = new StringBuilder();
            for (IPProgram flashig : project.getFlagships()) {
              if (counter != 0) {
                stringBuilder.append(", ");
              }
              stringBuilder.append(flashig.getAcronym());
              counter++;
            }

            this.writeString(stringBuilder.toString(), true, true);

            // Region
            counter = 0;
            stringBuilder = new StringBuilder();
            for (IPProgram region : project.getRegions()) {
              if (counter != 0) {
                stringBuilder.append(",");
              }
              stringBuilder.append(region.getAcronym());
              counter++;
            }

            this.writeString(stringBuilder.toString(), true, true);

            // deliverable Id
            this.writeString(String.valueOf(deliverable.getId()), false, true);

            // deliverable Title
            this.writeString(deliverable.getTitle(), true, true);

            // MOG
            if (deliverable.getOutput() != null) {
              this.writeString(deliverable.getOutput().getDescription(), true, true);
            } else {
              this.writeString("", false, true);
            }

            // Year
            this.writeString(String.valueOf(deliverable.getYear()), true, true);

            // Main Type
            this.writeString(deliverable.getType().getCategory().getName(), true, true);

            // Sub Type
            this.writeString(deliverable.getType().getName(), true, true);

            // Other type
            if (deliverable.getTypeOther() == null || deliverable.getTypeOther().equals("")) {
              this.writeString(this.getText("summaries.project.notapplicable"), false, true);
            } else {
              this.writeString(deliverable.getTypeOther(), false, true);
            }

            // Partner Responsible
            if (deliverable.getResponsiblePartner() != null) {
              this.writeString(deliverable.getResponsiblePartner().getPartner().getComposedName(), false, true);
            } else {
              this.writeString("", false, true);
            }

            // Others Partners
            DeliverablePartner otherPartner;
            stringBuilder = new StringBuilder();
            if (deliverable.getOtherPartners() != null && !deliverable.getOtherPartners().isEmpty()) {
              for (int b = 0; b < deliverable.getOtherPartners().size(); b++) {
                otherPartner = deliverable.getOtherPartners().get(b);
                if (otherPartner != null && otherPartner.getPartner() != null) {
                  if (b != 0) {
                    stringBuilder.append("; ");
                  }
                  stringBuilder.append(otherPartner.getPartner().getComposedName());
                }
              }
            }
            this.writeString(stringBuilder.toString(), true, true);
          }
          this.writeNewLine();
        }
      }
    } catch (IOException e) {
      // TODO To manage
      e.printStackTrace();
    }
  }

  /**
   * This method is used to generate the CSV for the deliverable report.
   * 
   * @param projectList is a list of projects with all their deliverables information.
   */

  public byte[] generateCSV(List<Project> projectList) {

    try {
      this.initializeCSV();
      String[] headers = new String[] {"Project Id", "Project title", " Flagship(s) ", "Region(s)", "Deliverable ID",
        "Deliverable title", "MOG", "Year", "Main Type", "Sub Type", "Other Type", "Partner Responsible",
      "Others Partners"};

      this.addHeaders(headers);
      this.addContent(projectList);
      this.flush();
      // TODO - We need to close the streams

      // returning the bytes that are in the output stream.
      return this.getBytes();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return null;
  }


}
