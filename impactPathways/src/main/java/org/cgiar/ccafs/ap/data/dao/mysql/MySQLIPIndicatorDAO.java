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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IPIndicatorDAO;

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
 * @author Hernán David Carvajal.
 */
public class MySQLIPIndicatorDAO implements IPIndicatorDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPIndicatorDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLIPIndicatorDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteIpElementIndicators(int ipElementID, int ipProgramID) {
    StringBuilder query = new StringBuilder();
    query.append("DELETE ipi.* FROM ip_indicators ipi ");
    query.append("INNER JOIN ip_program_elements ipe ON ipi.program_element_id = ipe.id ");
    query.append("WHERE ipe.program_id = ? AND ipe.element_id = ? ");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {ipProgramID, ipElementID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< removeIpElementIndicators():{}", true);
      return true;
    }


    LOG.debug("<< removeIpElementIndicators():{}", false);
    return false;
  }

  @Override
  public Map<String, String> getIndicator(int indicatorID) {
    LOG.debug(">> getIndicator(indicatorID = {})", indicatorID);
    Map<String, String> indicatorData = new HashMap<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT i.id, i.description, i.target, p.id as 'parent_id', p.description as 'parent_description' ");
    query.append("FROM ip_indicators i ");
    query.append("LEFT JOIN ip_indicators p ON i.parent_id = p.id ");
    query.append("WHERE i.id = ");
    query.append(indicatorID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        indicatorData.put("id", rs.getString("id"));
        indicatorData.put("description", rs.getString("description"));
        indicatorData.put("target", rs.getString("target"));
        indicatorData.put("parent_id", rs.getString("parent_id"));
        indicatorData.put("parent_description", rs.getString("parent_description"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getIndicator() > Exception raised trying ";
      exceptionMessage += "to get the ip indicator with id " + indicatorID;
      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< getIndicator():indicatorData={}", indicatorData);
    return indicatorData;
  }

  @Override
  public List<Map<String, String>> getIndicatorsByIpProgramElementID(int ipProgramElementID) {
    LOG.debug(">> getElementIndicators( ipProgramElementID = {} )", ipProgramElementID);
    List<Map<String, String>> indicatorsList = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT i.id, i.description, i.target, p.id as 'parent_id', p.description as 'parent_description' ");
    query.append("FROM ip_indicators i ");
    query.append("LEFT JOIN ip_indicators p ON i.parent_id = p.id ");
    query.append("WHERE i.program_element_id = ");
    query.append(ipProgramElementID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> indicatorData = new HashMap<String, String>();
        indicatorData.put("id", rs.getString("id"));
        indicatorData.put("description", rs.getString("description"));
        indicatorData.put("target", rs.getString("target"));
        indicatorData.put("parent_id", rs.getString("parent_id"));
        indicatorData.put("parent_description", rs.getString("parent_description"));
        indicatorsList.add(indicatorData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getElementIndicators() > Exception raised trying ";
      exceptionMessage += "to get the ip indicators corresponding to the ip program element " + ipProgramElementID;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< getElementIndicators():ipIndicatorList.size={}", indicatorsList.size());
    return indicatorsList;
  }

  @Override
  public List<Map<String, String>> getIndicatorsByParent(int parentIndicatorID) {
    LOG.debug(">> getIndicatorsByParent( indicatorID = {} )", parentIndicatorID);
    List<Map<String, String>> indicatorsList = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.description, i.target, p.id as 'parent_id', p.description as 'parent_description' ");
    query.append("FROM ip_indicators i ");
    query.append("INNER JOIN ip_indicators p ON i.parent_id = p.id ");
    query.append("WHERE p.id = ");
    query.append(parentIndicatorID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> indicatorData = new HashMap<String, String>();
        indicatorData.put("id", rs.getString("id"));
        indicatorData.put("description", rs.getString("description"));
        indicatorData.put("target", rs.getString("target"));
        indicatorData.put("parent_id", rs.getString("parent_id"));
        indicatorData.put("parent_description", rs.getString("parent_description"));
        indicatorsList.add(indicatorData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getIndicatorsByParent() > Exception raised trying ";
      exceptionMessage += "to get the ip indicators with parentID =  " + parentIndicatorID;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< getIndicatorsByParent():ipIndicatorList.size={}", indicatorsList.size());
    return indicatorsList;
  }

  @Override
  public List<Map<String, String>> getIndicatorsByProjectID(int projectID) {
    LOG.debug(">> getIndicatorsByProjectID( projectID = {} )", projectID);
    List<Map<String, String>> indicatorsList = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT i.id, i.description, i.target, p.id as 'parent_id', p.description as 'parent_description', ");
    query.append("ie.id as 'outcome_id', ie.description as 'outcome_description' ");
    query.append("FROM ip_indicators i ");
    query.append("LEFT JOIN ip_indicators p ON i.parent_id = p.id ");
    query.append("INNER JOIN ip_project_indicators ipi ON i.id = ipi.parent_id ");
    query.append("INNER JOIN ip_elements ie ON ipi.outcome_id = ie.id ");
    query.append("WHERE ipi.project_id = ");
    query.append(projectID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> indicatorData = new HashMap<String, String>();
        indicatorData.put("id", rs.getString("id"));
        indicatorData.put("description", rs.getString("description"));
        indicatorData.put("target", rs.getString("target"));
        indicatorData.put("parent_id", rs.getString("parent_id"));
        indicatorData.put("parent_description", rs.getString("parent_description"));
        indicatorData.put("outcome_id", rs.getString("outcome_id"));
        indicatorData.put("outcome_description", rs.getString("outcome_description"));
        indicatorsList.add(indicatorData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getIndicatorsByProjectID() > Exception raised trying ";
      exceptionMessage += "to get the ip indicators corresponding to the project " + projectID;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< getIndicatorsByProjectID():ipIndicatorList.size={}", indicatorsList.size());
    return indicatorsList;
  }

  @Override
  public List<Map<String, String>> getIndicatorsList() {
    LOG.debug(">> getIndicatorsList()");
    List<Map<String, String>> indicatorsDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM ip_indicators; ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> indicatorData = new HashMap<>();
        indicatorData.put("id", rs.getString("id"));
        indicatorData.put("description", rs.getString("description"));
        indicatorData.put("target", rs.getString("target"));
        indicatorData.put("program_element_id", rs.getString("program_element_id"));
        indicatorData.put("parent_id", rs.getString("parent_id"));

        indicatorsDataList.add(indicatorData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getIndicatorsList()> Exception raised trying to get the list of indicators.", e);
    }

    LOG.debug("<< getIndicatorsList():indicatorsDataList.size=", indicatorsDataList.size());
    return indicatorsDataList;
  }

  @Override
  public int saveIndicator(Map<String, Object> indicatorData) {
    LOG.debug(">> saveIndicator(indicatorData={})", indicatorData);

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO ip_indicators (id, description, target, program_element_id, parent_id) ");
    query.append("VALUES (?, ?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE description = VALUES(description), target = VALUES(target), ");
    query.append("parent_id = VALUES(parent_id) ");

    Object[] values = new Object[5];
    values[0] = indicatorData.get("id");
    values[1] = indicatorData.get("description");
    values[2] = indicatorData.get("target");
    values[3] = indicatorData.get("program_element_id");
    values[4] = indicatorData.get("parent_id");

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveIndicator():{}", result);
    return result;

  }
}
