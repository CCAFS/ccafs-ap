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

package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.csv.LeadInstitutionPartnersSummaryCSV;
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.utils.APConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Alberto Martínez M.
 */
public class LeadInstitutionPartnersSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(LeadInstitutionPartnersSummaryAction.class);
  private static final long serialVersionUID = 5110987672008315842L;
  private NextUserManager nextUserManager;
  private DeliverablePartnerManager deliverablePartnerManager;
  private LeadInstitutionPartnersSummaryCSV leadInstitutionPartnersSummaryCSV;
  private ProjectPartnerManager projectPartnerManager;
  private InstitutionManager institutionManager;
  private ProjectManager projectManager;
  List<InputStream> streams;
  List<ProjectPartner> partners;
  List<Institution> projectLeadingInstitutions;
  String[] projectList;
  int projectID;
  Project project;

  @Inject
  public LeadInstitutionPartnersSummaryAction(APConfig config, ProjectPartnerManager projectPartnerManager,
    NextUserManager nextUserManager, DeliverablePartnerManager deliverablePartnerManager,
    LeadInstitutionPartnersSummaryCSV leadInstitutionPartnersSummaryCSV, InstitutionManager institutionManager,
    ProjectManager projectManager) {
    super(config);
    this.nextUserManager = nextUserManager;
    this.deliverablePartnerManager = deliverablePartnerManager;
    this.leadInstitutionPartnersSummaryCSV = leadInstitutionPartnersSummaryCSV;
    this.projectPartnerManager = projectPartnerManager;
    this.institutionManager = institutionManager;
    this.projectManager = projectManager;

  }

  @Override
  public String execute() throws Exception {

    // Generate the csv file
    leadInstitutionPartnersSummaryCSV.generateCSV(projectLeadingInstitutions, projectList);
    streams = new ArrayList<>();
    streams.add(leadInstitutionPartnersSummaryCSV.getInputStream());

    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return leadInstitutionPartnersSummaryCSV.getContentLength();
  }

  @Override
  public String getFileName() {
    return leadInstitutionPartnersSummaryCSV.getFileName();
  }


  @Override
  public InputStream getInputStream() {
    return leadInstitutionPartnersSummaryCSV.getInputStream();
  }


  @Override
  public void prepare() {

    projectLeadingInstitutions = institutionManager.getProjectLeadingInstitutions();
    projectList = new String[projectLeadingInstitutions.size()];
    // Fill in projectList with blanks to be worked on later
    for (int p = 0; p < projectList.length; p++) {
      projectList[p] = "";
    }
    // Remove repeated institutions
    for (int k = 0; k < projectLeadingInstitutions.size(); k++) {
      for (int l = projectLeadingInstitutions.size() - 1; l > k; l--) {
        if (projectLeadingInstitutions.get(k).getId() == projectLeadingInstitutions.get(l).getId()) {
          projectLeadingInstitutions.remove(l);
        }
      }
    }
    // Generate the list with projectIDs for each institution
    int count = 0;
    for (int i = 0; i < projectLeadingInstitutions.size(); i++) {
      int stop = projectManager.getProjectsByInstitution(projectLeadingInstitutions.get(i).getId()).size();
      for (int j = 0; j < stop; j++) {
        if (j == stop - 1) {
          projectList[count] +=
            projectManager.getProjectsByInstitution(projectLeadingInstitutions.get(i).getId()).get(j).getId();
        } else {
          projectList[count] +=
            (projectManager.getProjectsByInstitution(projectLeadingInstitutions.get(i).getId()).get(j).getId() + ", ");
        }
      }
      count++;
    }

  }
}
