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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ProjectStatusDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectStatusManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectStatus;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectStatusManagerImpl implements ProjectStatusManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectStatusManagerImpl.class);

  // DAO
  private ProjectStatusDAO statusDAO;

  private DateFormat dateFormatter;

  @Inject
  public ProjectStatusManagerImpl(ProjectStatusDAO statusDAO) {
    this.statusDAO = statusDAO;
  }

  @Override
  public ProjectStatus getProjectStatus(Project project, String cycle) {
    Map<String, String> statusData = statusDAO.getProjectStatus(project.getId(), cycle);
    if (statusData != null && !statusData.isEmpty()) {
      ProjectStatus status = new ProjectStatus();
      status.setId(Integer.parseInt(statusData.get("id")));
      status.setCycle(statusData.get("cycle"));
      status.setSection(statusData.get("section_name"));
      status.setMissingFields(statusData.get("missing_fields"));
      return status;
    }
    return null;
  }

  @Override
  public int saveProjectStatus(ProjectStatus status, Project project) {
    Map<String, Object> statusData = new HashMap<>();
    if (status.getId() > 0) {
      statusData.put("id", status.getId());
    }
    statusData.put("cycle", status.getCycle());
    statusData.put("section_name", status.getSection());
    statusData.put("project_id", project.getId());
    statusData.put("missing_fields", status.getMissingFieldsWithPrefix());
    return statusDAO.saveProjectStatus(statusData);
  }

}
