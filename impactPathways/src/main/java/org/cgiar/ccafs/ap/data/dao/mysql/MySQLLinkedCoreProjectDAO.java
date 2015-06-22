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

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.LinkedCoreProjectDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class MySQLLinkedCoreProjectDAO implements LinkedCoreProjectDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLLinkedCoreProjectDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLLinkedCoreProjectDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getLinkedCoreProjects(int projectID) {
    List<Map<String, String>> coreProjects = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT p.id, p.title ");
    query.append("FROM projects p ");
    query.append("INNER JOIN linked_core_projects lcp ON p.id = lcp.core_project_id ");
    query.append("WHERE lcp.bilateral_project_id = ");
    query.append(projectID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectInfo = new HashMap<>();
        projectInfo.put("id", rs.getString("id"));
        projectInfo.put("title", rs.getString("title"));

        coreProjects.add(projectInfo);
      }
    } catch (SQLException e) {
      LOG.error("getLinkedCoreProjects() > Exception raised trying to get the core projects linked to the project {} ",
        projectID, e);
    }
    return coreProjects;
  }

  @Override
  public boolean removeLinkedCoreProjects(int bilateralProjectID, List<Integer> coreProjects, int userID,
    String justification) {
    Object[] values = new Object[3 + coreProjects.size()];
    StringBuilder query = new StringBuilder();
    query.append("UPDATE linked_core_projects ");
    query.append("SET is_active = FALSE, modified_by = ?, modification_justification = ? ");
    query.append("WHERE bilateral_project_id = ? ");
    query.append("AND core_project_id IN (");

    values[0] = userID;
    values[1] = justification;
    values[2] = bilateralProjectID;

    for (int c = 0; c < coreProjects.size(); c++) {
      query.append((c == 0) ? " ?" : ", ?");
      values[3 + c] = coreProjects.get(c);
    }
    query.append("); ");

    int result = daoManager.delete(query.toString(), values);
    return (result == -1) ? false : true;
  }

  @Override
  public boolean saveLinkedCoreProjects(int bilateralProjectID, List<Integer> listCoreProjectsIDs, int userID,
    String justification) {
    boolean saved = false;
    Object[] values = new Object[listCoreProjectsIDs.size() * 5];
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO linked_core_projects ");
    query.append("(bilateral_project_id, core_project_id, created_by, modified_by, modification_justification) ");
    query.append("VALUES ");

    for (int i = 0; i < listCoreProjectsIDs.size(); i++) {
      if (i == 0) {
        query.append(" (?, ?, ?, ?, ?) ");
      } else {
        query.append(", (?, ?, ?, ?, ?) ");
      }

      int c = i * 5;
      values[c] = bilateralProjectID;
      values[c + 1] = listCoreProjectsIDs.get(i);
      values[c + 2] = userID;
      values[c + 3] = userID;
      values[c + 4] = justification;
    }
    query.append("; ");

    int result = daoManager.saveData(query.toString(), values);
    saved = (result == -1) ? false : true;
    return saved;
  }
}
