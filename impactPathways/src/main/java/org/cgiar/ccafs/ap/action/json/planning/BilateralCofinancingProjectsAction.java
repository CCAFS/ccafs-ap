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

package org.cgiar.ccafs.ap.action.json.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class BilateralCofinancingProjectsAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(BilateralCofinancingProjectsAction.class);
  private static final long serialVersionUID = -9002646026540939684L;

  private ProjectManager projectManager;
  private List<Project> projects;
  private int flagshipID, regionID;

  @Inject
  public BilateralCofinancingProjectsAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }

  @Override
  public String execute() {
    projects = projectManager.getBilateralCofinancingProjects(flagshipID, regionID);
    // LOG.info("They were loaded {} core projects", projects.size());
    return SUCCESS;
  }

  public List<Project> getProjects() {
    return projects;
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a programID parameter
    String stringFlagshipID = StringUtils.trim(this.getRequest().getParameter(APConstants.PROGRAM_REQUEST_ID));
    try {
      flagshipID = (stringFlagshipID != null) ? Integer.parseInt(stringFlagshipID) : -1;
    } catch (NumberFormatException e) {
      LOG.warn("There was an exception trying to convert to int the parameter {}", stringFlagshipID);
      flagshipID = -1;
    }

    // Verify if there is a regionID parameter
    String stringRegionID = StringUtils.trim(this.getRequest().getParameter(APConstants.REGION_REQUEST_ID));
    try {
      regionID = (stringRegionID != null) ? Integer.parseInt(stringRegionID) : -1;
    } catch (NumberFormatException e) {
      LOG.warn("There was an exception trying to convert to int the parameter {}", stringRegionID);
      regionID = -1;
    }


  }
}