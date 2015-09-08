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
import org.cgiar.ccafs.ap.action.summaries.csv.PartnersSummaryCSV;
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
public class PartnersSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(PartnersSummaryAction.class);
  private static final long serialVersionUID = 5110987672008315842L;
  private NextUserManager nextUserManager;
  private DeliverablePartnerManager deliverablePartnerManager;
  private PartnersSummaryCSV partnersCSV;
  private ProjectPartnerManager projectPartnerManager;
  private InstitutionManager institutionManager;
  private ProjectManager projectManager;
  List<InputStream> streams;
  List<ProjectPartner> partners;
  List<Institution> projectPartnerInstitutions;
  String[] projectList;
  int projectID;
  Project project;

  @Inject
  public PartnersSummaryAction(APConfig config, ProjectPartnerManager projectPartnerManager,
    NextUserManager nextUserManager, DeliverablePartnerManager deliverablePartnerManager,
    PartnersSummaryCSV partnersCSV, InstitutionManager institutionManager, ProjectManager projectManager) {
    super(config);
    this.nextUserManager = nextUserManager;
    this.deliverablePartnerManager = deliverablePartnerManager;
    this.partnersCSV = partnersCSV;
    this.projectPartnerManager = projectPartnerManager;
    this.institutionManager = institutionManager;
    this.projectManager = projectManager;

  }

  @Override
  public String execute() throws Exception {

    // Generate the csv file
    partnersCSV.generateCSV(projectPartnerInstitutions, projectList);
    streams = new ArrayList<>();
    streams.add(partnersCSV.getInputStream());

    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return partnersCSV.getContentLength();
  }

  @Override
  public String getFileName() {
    return partnersCSV.getFileName();
  }


  @Override
  public InputStream getInputStream() {
    return partnersCSV.getInputStream();
  }


  @Override
  public void prepare() {

    projectPartnerInstitutions = institutionManager.getProjectPartnerInstitutions();
    projectList = new String[projectPartnerInstitutions.size()];
    for (int p = 0; p < projectList.length; p++) {
      projectList[p] = "";
    }

    for (int k = 0; k < projectPartnerInstitutions.size(); k++) {
      for (int l = projectPartnerInstitutions.size() - 1; l > k; l--) {
        if (projectPartnerInstitutions.get(k).getId() == projectPartnerInstitutions.get(l).getId()) {
          projectPartnerInstitutions.remove(l);
        }
      }
    }
    int count = 0;
    for (int i = 0; i < projectPartnerInstitutions.size(); i++) {
      int stop = projectManager.getProjectsByInstitution(projectPartnerInstitutions.get(i).getId()).size();
      for (int j = 0; j < stop; j++) {
        if (j == stop - 1) {
          projectList[count] +=
            projectManager.getProjectsByInstitution(projectPartnerInstitutions.get(i).getId()).get(j).getId();
        } else {
          projectList[count] +=
            (projectManager.getProjectsByInstitution(projectPartnerInstitutions.get(i).getId()).get(j).getId() + " - ");
        }
      }
      count++;
    }

  }
}
