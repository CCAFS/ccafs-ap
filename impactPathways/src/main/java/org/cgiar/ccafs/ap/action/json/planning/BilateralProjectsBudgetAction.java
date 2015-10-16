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
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class BilateralProjectsBudgetAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(BilateralProjectsBudgetAction.class);
  private static final long serialVersionUID = -6872327363658914875L;

  private ProjectManager projectManager;
  private List<Project> projects;

  @Inject
  public BilateralProjectsBudgetAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }

  @Override
  public String execute() {
    projects = projectManager.getBilateralProjectsLeaders();
    LOG.info("There were loaded {} bilateral projects", projects.size());
    return SUCCESS;
  }

  public List<Project> getProjects() {
    return projects;
  }

  @Override
  public void prepare() throws Exception {
  }
}
