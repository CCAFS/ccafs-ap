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

package org.cgiar.ccafs.ap.action.summaries.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.summaries.projects.xlsx.LeadInstitutionPartnersSummaryXLS;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.summaries.Summary;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Alberto Martínez M.
 */
public class LeadInstitutionPartnersSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(LeadInstitutionPartnersSummaryAction.class);
  private static final long serialVersionUID = 5110987672008315842L;
  private LeadInstitutionPartnersSummaryXLS leadInstitutionPartnersSummaryXLS;
  private InstitutionManager institutionManager;
  List<ProjectPartner> partners;
  List<Map<String, Object>> projectLeadingInstitutions;
  String[] projectList;
  // CSV bytes
  private byte[] bytesXLS;

  // Streams
  InputStream inputStream;

  @Inject
  public LeadInstitutionPartnersSummaryAction(APConfig config,
    LeadInstitutionPartnersSummaryXLS leadInstitutionPartnersSummaryXLS, InstitutionManager institutionManager,
    ProjectManager projectManager) {
    super(config);
    this.leadInstitutionPartnersSummaryXLS = leadInstitutionPartnersSummaryXLS;
    this.institutionManager = institutionManager;

  }

  @Override
  public String execute() throws Exception {
    // Generate the xls file
    bytesXLS = leadInstitutionPartnersSummaryXLS.generateXLS(projectLeadingInstitutions);

    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return bytesXLS.length;
  }

  @Override
  public String getContentType() {
    if (this.getFileName().endsWith("xlsx")) {
      return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    } else {
      return "application/vnd.ms-excel";
    }
  }

  @Override
  public String getFileName() {
    String date = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());
    StringBuffer fileName = new StringBuffer();
    fileName.append("ProjectLeading-Institutions_");
    fileName.append(date);
    fileName.append(".xlsx");
    return fileName.toString();
  }


  @Override
  public InputStream getInputStream() {
    if (inputStream == null) {
      inputStream = new ByteArrayInputStream(bytesXLS);
    }
    return inputStream;
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
        if (projectLeadingInstitutions.get(k).get("id").equals(projectLeadingInstitutions.get(l).get("id"))) {
          projectLeadingInstitutions.remove(l);
        }
      }
    }

  }
}
