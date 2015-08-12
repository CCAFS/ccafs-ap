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
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.CRPManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOtherContributionManager;
import org.cgiar.ccafs.ap.data.model.CRP;
import org.cgiar.ccafs.ap.data.model.OtherContribution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.planning.ProjectIPOtherContributionValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hernán David Carvajal B.
 */
public class ProjectIPOtherContributionAction extends BaseAction {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectIPOtherContributionAction.class);
  private static final long serialVersionUID = 5866456304533553208L;

  // Manager
  private ProjectOtherContributionManager ipOtherContributionManager;
  private CRPManager crpManager;
  private ProjectManager projectManager;
  private ProjectIPOtherContributionValidator otherContributionValidator;
  private HistoryManager historyManager;

  // Model for the back-end
  private OtherContribution ipOtherContribution;
  private List<CRP> previousCRPs;

  // Model for the front-end
  private int projectID;
  private Project project;
  private List<CRP> crps;

  @Inject
  public ProjectIPOtherContributionAction(APConfig config, ProjectOtherContributionManager ipOtherContributionManager,
    ProjectManager projectManager, CRPManager crpManager,
    ProjectIPOtherContributionValidator otherContributionValidator, HistoryManager historyManager) {
    super(config);
    this.ipOtherContributionManager = ipOtherContributionManager;
    this.projectManager = projectManager;
    this.crpManager = crpManager;
    this.otherContributionValidator = otherContributionValidator;
    this.historyManager = historyManager;
  }

  public List<CRP> getCrps() {
    return crps;
  }

  public OtherContribution getIpOtherContribution() {
    return ipOtherContribution;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
  }

  @Override
  public String next() {
    String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the activity information
    project = projectManager.getProject(projectID);
    crps = crpManager.getCRPsList();

    // Getting the information for the IP Other Contribution
    ipOtherContribution = ipOtherContributionManager.getIPOtherContributionByProjectId(projectID);

    project.setCrpContributions(crpManager.getCrpContributions(projectID));
    project.setIpOtherContribution(ipOtherContribution);

    previousCRPs = new ArrayList<>();
    for (CRP crp : project.getCrpContributions()) {
      previousCRPs.add(new CRP(crp.getId()));
    }

    super.setHistory(historyManager.getProjectIPOtherContributionHistory(project.getId()));

    if (this.isHttpPost()) {
      project.getCrpContributions().clear();
    }
  }

  @Override
  public String save() {
    if (securityContext.canUpdateProjectOtherContributions()) {
      // Saving Activity IP Other Contribution
      boolean saved =
        ipOtherContributionManager.saveIPOtherContribution(projectID, project.getIpOtherContribution(),
          this.getCurrentUser(), this.getJustification());


      // Delete the CRPs that were un-selected
      for (CRP crp : previousCRPs) {
        if (!project.getCrpContributions().contains(crp)) {
          saved =
            saved
              && crpManager.removeCrpContribution(project.getId(), crp.getId(), this.getCurrentUser().getId(),
                this.getJustification());
        }
      }

      saved = saved && crpManager.saveCrpContributions(project, this.getCurrentUser(), this.getJustification());

      if (!saved) {
        this.addActionError(this.getText("saving.problem"));
        return BaseAction.INPUT;
      } else {
        // Get the validation messages and append them to the save message if any
        Collection<String> messages = this.getActionMessages();
        if (!messages.isEmpty()) {
          String validationMessage = messages.iterator().next();
          this.setActionMessages(null);
          this.addActionWarning(this.getText("saving.saved") + validationMessage);
        } else {
          this.addActionMessage(this.getText("saving.success",
            new String[] {this.getText("planning.impactPathways.otherContributions.title")}));
        }
        return SUCCESS;
      }
    }
    return BaseAction.NOT_AUTHORIZED;
  }

  public void setIpOtherContribution(OtherContribution ipOtherContribution) {
    this.ipOtherContribution = ipOtherContribution;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  @Override
  public void validate() {
    if (save) {
      otherContributionValidator.validate(this, project);
    }
  }
}