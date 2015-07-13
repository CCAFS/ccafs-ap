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

import org.cgiar.ccafs.ap.data.dao.ProjectContributionOverivewDAO;
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

public class MySQLProjectContributionOverivewDAO implements ProjectContributionOverivewDAO {

  private DAOManager daoManager;
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectContributionOverivewDAO.class);

  @Inject
  public MySQLProjectContributionOverivewDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public boolean deleteProjectContributionOverview(int outputOverviewID, int userID, String justification) {
    String query = "UPDATE ip_project_contribution_overviews SET is_active = FALSE, ";
    query += "modified_by = ?, modification_justification = ? ";
    query += "WHERE id = ?";

    int result = daoManager.delete(query, new String[] {userID + "", justification, outputOverviewID + ""});
    return (result != -1);
  }

  @Override
  public List<Map<String, String>> getProjectContributionOverviews(int projectID) {
    List<Map<String, String>> overviewsData = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipco.id, ipco.year, ipco.anual_contribution, ipco.gender_contribution, ");
    query.append("ie.id as 'output_id', ie.description as output_description ");
    query.append("FROM ip_project_contributions ipc ");
    query.append("INNER JOIN ip_project_contribution_overviews ipco ON ipco.output_id = ipc.mog_id ");
    query.append("INNER JOIN ip_elements ie ON ipc.mog_id = ie.id ");
    query.append("WHERE ipc.project_id = ");
    query.append(projectID);
    query.append(" GROUP BY ipc.mog_id ");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> overview = new HashMap<>();
        overview.put("id", rs.getString("id"));
        overview.put("year", rs.getString("year"));
        overview.put("annual_contribution", rs.getString("anual_contribution"));
        overview.put("gender_contribution", rs.getString("gender_contribution"));
        overview.put("output_id", rs.getString("output_id"));
        overview.put("output_description", rs.getString("output_description"));

        overviewsData.add(overview);
      }

    } catch (SQLException e) {
      LOG.error("-- getProjectContributionOverviews(int projectID)( ) > Exception arised trying to get the project "
        + "contributions overview for project {}", projectID, e);
    }
    return overviewsData;
  }

}
