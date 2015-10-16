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

import org.cgiar.ccafs.ap.data.dao.SectionStatusDAO;
import org.cgiar.ccafs.ap.data.manager.SectionStatusManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.SectionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class SectionStatusManagerImpl implements SectionStatusManager {

  // DAO
  private SectionStatusDAO statusDAO;

  @Inject
  public SectionStatusManagerImpl(SectionStatusDAO statusDAO) {
    this.statusDAO = statusDAO;
  }

  @Override
  public SectionStatus getSectionStatus(Deliverable deliverable, String cycle, String section) {
    Map<String, String> statusData = statusDAO.getDeliverableSectionStatus(deliverable.getId(), cycle, section);
    if (statusData != null && !statusData.isEmpty()) {
      SectionStatus status = new SectionStatus();
      status.setId(Integer.parseInt(statusData.get("id")));
      status.setCycle(statusData.get("cycle"));
      status.setSection(statusData.get("section_name"));
      status.setMissingFields(statusData.get("missing_fields"));
      status.setProjectID(statusData.get("project_id") != null ? Integer.parseInt(statusData.get("project_id")) : -1);
      status.setDeliverableID(
        statusData.get("deliverable_id") != null ? Integer.parseInt(statusData.get("deliverable_id")) : -1);
      return status;
    }
    return null;
  }

  @Override
  public SectionStatus getSectionStatus(Project project, String cycle, String section) {
    Map<String, String> statusData = statusDAO.getProjectSectionStatus(project.getId(), cycle, section);
    if (statusData != null && !statusData.isEmpty()) {
      SectionStatus status = new SectionStatus();
      status.setId(Integer.parseInt(statusData.get("id")));
      status.setCycle(statusData.get("cycle"));
      status.setSection(statusData.get("section_name"));
      status.setMissingFields(statusData.get("missing_fields"));
      status.setProjectID(statusData.get("project_id") != null ? Integer.parseInt(statusData.get("project_id")) : -1);
      status.setDeliverableID(
        statusData.get("deliverable_id") != null ? Integer.parseInt(statusData.get("deliverable_id")) : -1);
      return status;
    }
    return null;
  }

  @Override
  public List<SectionStatus> getSectionStatuses(Project project, String cycle) {
    List<SectionStatus> statuses = new ArrayList<>();
    List<Map<String, String>> statusDataList = statusDAO.getProjectSectionStatuses(project.getId(), cycle);
    if (statusDataList != null) {
      for (Map<String, String> statusData : statusDataList) {
        SectionStatus status = new SectionStatus();
        status.setId(Integer.parseInt(statusData.get("id")));
        status.setCycle(statusData.get("cycle"));
        status.setSection(statusData.get("section_name"));
        status.setMissingFields(statusData.get("missing_fields"));
        status.setProjectID(statusData.get("project_id") != null ? Integer.parseInt(statusData.get("project_id")) : -1);
        status.setDeliverableID(
          statusData.get("deliverable_id") != null ? Integer.parseInt(statusData.get("deliverable_id")) : -1);
        statuses.add(status);
      }
      return statuses;
    }
    return null;
  }

  @Override
  public int saveSectionStatus(SectionStatus status, Project project) {
    Map<String, Object> statusData = new HashMap<>();
    if (status.getId() > 0) {
      statusData.put("id", status.getId());
    }
    statusData.put("cycle", status.getCycle());
    statusData.put("section_name", status.getSection());
    statusData.put("project_id", project.getId());
    statusData.put("missing_fields", status.getMissingFieldsWithPrefix());
    return statusDAO.saveProjectSectionStatus(statusData);
  }

  @Override
  public int saveSectionStatus(SectionStatus status, Project project, Deliverable deliverable) {
    Map<String, Object> statusData = new HashMap<>();
    if (status.getId() > 0) {
      statusData.put("id", status.getId());
    }
    statusData.put("cycle", status.getCycle());
    statusData.put("section_name", status.getSection());
    statusData.put("project_id", project.getId());
    statusData.put("deliverable_id", deliverable.getId());
    statusData.put("missing_fields", status.getMissingFieldsWithPrefix());
    return statusDAO.saveDeliverableSectionStatus(statusData);
  }

}
