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

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.ProjectDAO;
import org.cgiar.ccafs.ap.data.model.BudgetType;
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
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 * @author Javier Andrés Gallego.
 * @author Hernán David Carvajal B.
 * @author Jorge Leonardo Solis B.
 */
public class MySQLProjectDAO implements ProjectDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLProjectDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteProject(int projectID, int userID, String justification) {
    LOG.debug(">> deleteProject(projectID={})", projectID);

    String query = "UPDATE projects SET is_active = 0, modified_by = ?, modification_justification = ? WHERE id = ?";

    Object[] values = new Object[3];
    values[0] = userID;
    values[1] = justification;
    values[2] = projectID;

    int rowsUpdated = databaseManager.saveData(query, values);
    if (rowsUpdated >= 0) {
      LOG.debug("<< deleteProject():{}", true);

      // Then we need delete all the tables that are referencing the projects table.
      return databaseManager.deleteOnCascade("projects", "id", projectID, userID, justification);
    }

    LOG.debug("<< deleteProject:{}", false);
    return false;
  }

  @Override
  public boolean deleteProjectIndicator(int projectID, int indicatorID, int userID, String justification) {
    LOG.debug(">> deleteProjectIndicator(projectID={}, indicatorID={})", projectID, indicatorID);

    StringBuilder query = new StringBuilder();
    query.append("UPDATE ip_project_indicators SET is_active = FALSE, ");
    query.append("modified_by = ? , modification_justification = ? ");
    query.append("WHERE project_id = ? AND id = ? ");

    Object[] values = new Object[4];
    values[0] = userID;
    values[1] = justification;
    values[2] = projectID;
    values[3] = indicatorID;

    int rowsDeleted = databaseManager.delete(query.toString(), values);
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProjectIndicator():{}", true);
      return true;
    }

    LOG.debug("<< deleteProjectIndicator:{}", false);
    return false;
  }

  @Override
  public boolean deleteProjectOutput(int projectID, int outputID, int outcomeID, int userID, String justification) {
    LOG.debug(">> deleteProjectOutput(projectID={}, outputID={})", projectID, outputID);

    StringBuilder query = new StringBuilder();
    query.append("UPDATE ip_project_contributions SET is_active = FALSE, ");
    query.append("modified_by = ?, modification_justification = ? ");
    query.append("WHERE project_id = ? AND mog_id = ? AND midOutcome_id = ?; ");

    Object[] values = new Object[5];
    values[0] = userID;
    values[1] = justification;
    values[2] = projectID;
    values[3] = outputID;
    values[4] = outcomeID;

    int rowsDeleted = databaseManager.delete(query.toString(), values);
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProjectOutput():{}", true);
      return true;
    }

    LOG.debug("<< deleteProjectOutput:{}", false);
    return false;
  }


  @Override
  public boolean existProject(int projectID) {
    LOG.debug(">> existProject projectID = {} )", projectID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT COUNT(id) FROM projects WHERE id = ");
    query.append(projectID);
    boolean exists = false;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getInt(1) > 0) {
          exists = true;
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the project id.", projectID, e);
    }
    LOG.debug("-- existProject() > Calling method executeQuery to get the results");
    return exists;
  }


  @Override
  public List<Map<String, String>> getAllProjects() {
    LOG.debug(">> getAllProjects )");
    StringBuilder query = new StringBuilder();

    query.append("SELECT p.* ");
    query.append("FROM projects as p ");
    query.append("INNER JOIN employees emp ON emp.id = p.liaison_user_id ");
    query.append("INNER JOIN institutions i ON i.id = emp.institution_id ");
    query.append("INNER JOIN ip_programs ip ON ip.id = i.program_id ");

    LOG.debug("-- getAllProjects() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getAllProjectsBasicInfo() {
    LOG.debug(">> getAllProjectsBasicInfo( )");
    List<Map<String, String>> projectList = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    String regionsSubquery =
      "SELECT GROUP_CONCAT(ipp.acronym) "
        + "FROM ip_programs ipp INNER JOIN project_focuses pf ON ipp.id = pf.program_id "
        + "WHERE pf.project_id = p.id AND ipp.type_id = " + APConstants.REGION_PROGRAM_TYPE;

    String flagshipsSubquery =
      "SELECT GROUP_CONCAT(ipp.acronym) "
        + "FROM ip_programs ipp INNER JOIN project_focuses pf ON ipp.id = pf.program_id "
        + "WHERE pf.project_id = p.id AND ipp.type_id = " + APConstants.FLAGSHIP_PROGRAM_TYPE;

    query.append("SELECT p.id, p.title, p.type, p.summary, p.active_since, SUM(pb.amount) as 'total_ccafs_amount', ");
    query.append("SUM(pb2.amount) as 'total_bilateral_amount', ");
    query.append("( " + regionsSubquery + " )  as 'regions', ");
    query.append("( " + flagshipsSubquery + " )  as 'flagships' ");
    query.append("FROM projects as p ");
    query.append("LEFT JOIN project_budgets pb ON p.id = pb.project_id AND pb.is_active= TRUE AND pb.budget_type =  ");
    query.append(BudgetType.W1_W2.getValue());
    query
      .append(" LEFT JOIN project_budgets pb2 ON p.id = pb2.project_id AND pb2.is_active=TRUE AND pb2.budget_type =  ");
    query.append(BudgetType.W3_BILATERAL.getValue());
    query.append(" WHERE p.is_active = TRUE ");
    query.append("GROUP BY p.id");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<String, String>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("summary", rs.getString("summary"));
        projectData.put("type", rs.getString("type"));
        projectData.put("total_ccafs_amount", rs.getString("total_ccafs_amount"));
        projectData.put("total_bilateral_amount", rs.getString("total_bilateral_amount"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");
        projectData.put("regions", rs.getString("regions"));
        projectData.put("flagships", rs.getString("flagships"));
        projectList.add(projectData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getAllProjectsBasicInfo() > Exception raised trying ";
      exceptionMessage += "to get the basic information of the projects " + query;

      LOG.error(exceptionMessage, e);
    }


    LOG.debug("-- getAllProjectsBasicInfo() : projectList.size={} ", projectList.size());
    return projectList;
  }

  @Override
  public List<Map<String, String>> getBilateralCofinancingProjects(int flagshipID, int regionID) {
    LOG.debug(">> getBilateralCofinancingProjects (flagshipID={}, regionID={})", flagshipID, regionID);
    List<Map<String, String>> coreProjects = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.id, p.title ");
    query.append("FROM projects as p ");
    query.append("WHERE p.type = '");
    query.append(APConstants.PROJECT_BILATERAL);
    query.append("' AND is_cofinancing = TRUE ");

    if (flagshipID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(flagshipID);
      query.append(") ");
    }

    if (regionID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(regionID);
      query.append(") ");
    }

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));

        coreProjects.add(projectData);
      }

    } catch (SQLException e) {
      LOG.error("getCoreProjects() > Exception raised trying to get the core projects.", e);
    }

    return coreProjects;
  }

  @Override
  public List<Map<String, String>> getBilateralProjects() {
    LOG.debug(">> getBilateralProjects ()");
    List<Map<String, String>> bilateralProjects = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.id, p.title ");
    query.append("FROM projects as p ");
    query.append("WHERE p.type = '");
    query.append(APConstants.PROJECT_BILATERAL);
    query.append("' ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));

        bilateralProjects.add(projectData);
      }

    } catch (SQLException e) {
      LOG.error("getBilateralProjects() > Exception raised trying to get the core projects.", e);
    }

    return bilateralProjects;
  }


  @Override
  public List<Map<String, String>> getBilateralProjectsLeaders() {
    LOG.debug(">> getBilateralProjectsLeaders ()");
    List<Map<String, String>> bilateralProjects = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.id, p.title ");
    query.append("FROM projects as p ");
    query.append("WHERE p.type = '");
    query.append(APConstants.PROJECT_BILATERAL);
    query.append("' ");

    query.append(" AND (");
    query.append("select count('x') from project_partners partner ");
    query
    .append("inner join project_partner_persons person on person.project_partner_id=partner.id and person.contact_type='PL'  ");
    query.append("where partner.project_id=p.id)>0");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));

        bilateralProjects.add(projectData);
      }

    } catch (SQLException e) {
      LOG.error("getBilateralProjectsLeaders() > Exception raised trying to get the core projects.", e);
    }

    return bilateralProjects;
  }

  @Override
  public List<Map<String, String>> getCoreProjects(int flagshipID, int regionID) {
    LOG.debug(">> getCoreProjects (flagshipID={}, regionID={})", flagshipID, regionID);
    List<Map<String, String>> coreProjects = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.id, p.title ");
    query.append("FROM projects as p ");
    query.append("WHERE p.type != '");
    query.append(APConstants.PROJECT_BILATERAL);
    query.append("' ");

    if (flagshipID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(flagshipID);
      query.append(") ");
    }

    if (regionID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(regionID);
      query.append(") ");
    }

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));

        coreProjects.add(projectData);
      }

    } catch (SQLException e) {
      LOG.error("getCoreProjects() > Exception raised trying to get the core projects.", e);
    }

    return coreProjects;
  }


  @Override
  public List<Map<String, String>> getCoreProjectsLeaders(int flagshipID, int regionID) {
    LOG.debug(">> getCoreProjectsLeaders (flagshipID={}, regionID={})", flagshipID, regionID);
    List<Map<String, String>> coreProjects = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.id, p.title ");
    query.append("FROM projects as p ");
    query.append("WHERE p.type != '");
    query.append(APConstants.PROJECT_BILATERAL);
    query.append("' ");

    if (flagshipID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(flagshipID);
      query.append(") ");
    }

    if (regionID != -1) {
      query.append(" AND p.id IN (SELECT project_id FROM project_focuses WHERE program_id = ");
      query.append(regionID);
      query.append(") ");
    }

    query.append(" AND (");
    query.append("select count('x') from project_partners partner ");
    query
    .append("inner join project_partner_persons person on person.project_partner_id=partner.id and person.contact_type='PL'  ");
    query.append("where partner.project_id=p.id)>0");


    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));

        coreProjects.add(projectData);
      }

    } catch (SQLException e) {
      LOG.error("getCoreProjectsLeaders() > Exception raised trying to get the core projects.", e);
    }

    return coreProjects;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> projectList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<String, String>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("summary", rs.getString("summary"));
        if (rs.getDate("start_date") != null) {
          projectData.put("start_date", rs.getDate("start_date").toString());
        }
        if (rs.getDate("end_date") != null) {
          projectData.put("end_date", rs.getDate("end_date").toString());
        }
        projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("liaison_institution_id", rs.getString("liaison_institution_id"));
        projectData.put("liaison_user_id", rs.getString("liaison_user_id"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");

        projectList.add(projectData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ProjectList.size={}", projectList.size());
    return projectList;
  }

  @Override
  public List<Integer> getPLProjectIds(int userID) {
    LOG.debug(">> getPLProjectIds(employeeId={})", new Object[] {userID});
    List<Integer> projectIds = new ArrayList<>();
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT project_id FROM project_partners pp WHERE (pp.partner_type = '");
      query.append(APConstants.PROJECT_PARTNER_PL);
      query.append("' or pp.partner_type = '");
      query.append(APConstants.PROJECT_PARTNER_PC);
      query.append("') AND pp.user_id = ");
      query.append(userID);
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        projectIds.add(rs.getInt(1));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getPLProjectIds() > There was an error getting the data for employeeId={}.", new Object[] {userID},
        e.getMessage());
      return null;
    }
    LOG.debug("<< getPLProjectIds():{}", projectIds);
    return projectIds;
  }

  @Override
  public Map<String, String> getProject(int projectID) {
    LOG.debug(">> getProject projectID = {} )", projectID);
    Map<String, String> projectData = new HashMap<String, String>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*, u.id as 'owner_id', i.id as 'owner_institution_id' ");
    query.append("FROM projects as p ");
    query.append("INNER JOIN liaison_users lu ON p.liaison_user_id = lu.id ");
    query.append("INNER JOIN users u ON lu.user_id = u.id ");
    query.append("INNER JOIN liaison_institutions li ON p.liaison_institution_id = li.id  ");
    query.append("INNER JOIN institutions i ON li.institution_id = i.id ");
    query.append("WHERE p.id = ");
    query.append(projectID);
    query.append(" AND p.is_active = 1");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("type", rs.getString("type"));
        projectData.put("summary", rs.getString("summary"));
        projectData.put("is_cofinancing", rs.getString("is_cofinancing"));
        projectData.put("is_global", rs.getString("is_global"));
        if (rs.getDate("start_date") != null) {
          projectData.put("start_date", rs.getDate("start_date").toString());
        }
        if (rs.getDate("end_date") != null) {
          projectData.put("end_date", rs.getDate("end_date").toString());
        }
        // projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("liaison_institution_id", rs.getString("liaison_institution_id"));
        projectData.put("liaison_user_id", rs.getString("owner_id"));
        projectData.put("requires_workplan_upload", rs.getString("requires_workplan_upload"));
        projectData.put("workplan_name", rs.getString("workplan_name"));
        projectData.put("bilateral_contract_name", rs.getString("bilateral_contract_name"));
        // projectData.put("project_owner_institution_id", rs.getString("owner_institution_id"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the project with id {}.", projectID, e);
    }
    LOG.debug("-- getProject() > Calling method executeQuery to get the results");
    return projectData;
  }

  @Override
  public Map<String, String> getProjectBasicInfo(int projectID) {
    LOG.debug(">> getProjectBasicInfo( id={} )", projectID);
    Map<String, String> projectData = new HashMap<String, String>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT p.id, p.title, p.type, p.active_since, SUM(pb.amount) as 'total_budget_amount', ");
    query.append("GROUP_CONCAT( DISTINCT ipp1.acronym ) as 'regions', ");
    query.append("GROUP_CONCAT( DISTINCT ipp2.acronym ) as 'flagships' ");
    query.append("FROM projects as p ");
    query.append("LEFT JOIN project_budgets pb ON p.id = pb.project_id ");
    query.append("LEFT JOIN project_focuses pf ON p.id = pf.project_id ");
    query.append("LEFT JOIN ip_programs ipp1 ON pf.program_id = ipp1.id AND ipp1.type_id = ");
    query.append(APConstants.REGION_PROGRAM_TYPE);
    query.append(" LEFT JOIN ip_programs ipp2 ON pf.program_id = ipp2.id AND ipp2.type_id = ");
    query.append(APConstants.FLAGSHIP_PROGRAM_TYPE);
    query.append(" WHERE p.id = " + projectID);
    query.append(" GROUP BY p.id  ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("type", rs.getString("type"));
        projectData.put("total_budget_amount", rs.getString("total_budget_amount"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");
        projectData.put("regions", rs.getString("regions"));
        projectData.put("flagships", rs.getString("flagships"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getAllProjectsBasicInfo() > Exception raised trying ";
      exceptionMessage += "to get the basic information of the projects " + query;

      LOG.error(exceptionMessage, e);
    }


    LOG.debug("-- getAllProjectsBasicInfo() : projectList.size={} ", projectData.size());
    return projectData;
  }

  @Override
  public Map<String, String> getProjectCoordinator(int projectID) {
    LOG.debug(">> getProjectCoordinator(projectID={})", projectID);
    Map<String, String> projectCoordinatorData = new HashMap<>();
    try (Connection connection = databaseManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.first_name, u.last_name, u.email, i.id as 'institution_id'");
      query.append("FROM project_partners pp ");
      query.append("INNER JOIN users u ON u.id = pp.user_id ");
      query.append("INNER JOIN institutions i ON i.id = pp.partner_id ");
      query.append("AND pp.partner_type = '" + APConstants.PROJECT_PARTNER_PC + "' ");
      query.append("AND pp.project_id = ");
      query.append(projectID);

      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        projectCoordinatorData.put("id", rs.getString("id"));
        projectCoordinatorData.put("first_name", rs.getString("first_name"));
        projectCoordinatorData.put("last_name", rs.getString("last_name"));
        projectCoordinatorData.put("email", rs.getString("email"));
        projectCoordinatorData.put("institution_id", rs.getString("institution_id"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectCoordinator() > There was an error getting the data for user {}.", projectID, e);
      return null;
    }
    LOG.debug("<< getProjectCoordinator():{}", projectCoordinatorData);
    return projectCoordinatorData;
  }

  @Override
  public int getProjectIdFromActivityId(int activityID) {
    LOG.debug(">> getProjectIdFromActivityId(activityID={})", new Object[] {activityID});
    int projectID = -1;
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT a.project_id FROM activities a WHERE a.id = ");
      query.append(activityID);
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        projectID = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectIdFromActivityId() > There was an error getting the data for activityID={}.",
        new Object[] {activityID}, e.getMessage());
    }
    LOG.debug("<< getProjectIdFromActivityId(): projectID={}", projectID);
    return projectID;
  }

  @Override
  public int getProjectIdFromDeliverableId(int deliverableID) {
    LOG.debug(">> getProjectIdFromDeliverableId(deliverableID={})", new Object[] {deliverableID});
    int projectID = -1;
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT d.project_id FROM deliverables d WHERE d.id = ");
      query.append(deliverableID);
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        projectID = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectIdFromDeliverableId() > There was an error getting the data for deliverableID={}.",
        new Object[] {deliverableID}, e.getMessage());
    }
    LOG.debug("<< getProjectIdFromDeliverableId(): projectID={}", projectID);
    return projectID;
  }

  @Override
  public int getProjectIDFromProjectPartnerID(int projectPartnerID) {
    LOG.debug(">> getProjectIDFromProjectPartnerID(projectPartnerID={})", new Object[] {projectPartnerID});
    int projectID = -1;
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT pp.project_id FROM project_partners pp WHERE pp.id = ");
      query.append(projectPartnerID);
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        projectID = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectIDFromProjectPartnerID() > There was an error getting the data for projectPartnerID={}.",
        new Object[] {projectPartnerID}, e.getMessage());
    }
    LOG.debug("<< getProjectIDFromProjectPartnerID(): projectPartnerID={}", projectPartnerID);
    return projectID;
  }

  @Override
  public List<Integer> getProjectIdsEditables(int userID) {
    LOG.debug(">> getProjectIdsEditables(projectID={}, ownerId={})", userID);
    List<Integer> projectIds = new ArrayList<>();
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT p.id FROM projects p WHERE ");
      query.append("p.liaison_user_id = (SELECT id FROM liaison_users WHERE user_id =  ");
      query.append(userID);
      query.append(") OR p.liaison_institution_id = (SELECT institution_id FROM liaison_users WHERE user_id = ");
      query.append(userID);
      query.append(") OR EXISTS (SELECT project_id FROM project_partners pp ");
      query.append("INNER JOIN project_partner_persons ppp ON pp.id = ppp.project_partner_id ");
      query.append("WHERE  ppp.contact_type = '");
      query.append(APConstants.PROJECT_PARTNER_PL);
      query.append("' AND pp.project_id = p.id AND ppp.user_id = ");
      query.append(userID);
      query.append(" AND ppp.is_active = 1) ");
      // If the project is bilateral and the user is a focal point of the lead institution
      query.append("OR ( p.type = '");
      query.append(APConstants.PROJECT_BILATERAL);
      query.append("' AND ( SELECT institution_id FROM liaison_institutions WHERE id = p.liaison_institution_id) = ");
      query.append(" ( SELECT li.institution_id FROM liaison_institutions li ");
      query.append(" INNER JOIN liaison_users lu ON lu.institution_id = li.id AND lu.user_id = ");
      query.append(userID);
      query.append(" ) ) ");
      // If the project user has the role of 'Admin'
      query.append("OR ( ");
      query.append(" 'Admin' IN ( SELECT acronym FROM user_roles ur INNER JOIN roles r ON ur.role_id = r.id ");
      query.append("WHERE ur.user_id = ");
      query.append(userID);
      query.append(" ) ) ");
      query.append("AND p.is_active = TRUE ");
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        projectIds.add(rs.getInt(1));
      }
      query.setLength(0);

      query.append("SELECT project_id FROM project_roles WHERE user_id = ");
      query.append(userID);
      rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        if (!projectIds.contains(rs.getInt(1))) {
          projectIds.add(rs.getInt(1));
        }
      }
      rs.close();
    } catch (SQLException e) {
      LOG
      .error("-- getProjectIdsEditables() > Exception raised getting the projects editables for user {}.", userID, e);

    }
    LOG.debug("<< getProjectIdsEditables():{}", projectIds);
    return projectIds;
  }

  @Override
  public Map<String, String> getProjectLeader(int projectID) {
    LOG.debug(">> getProjectLeader(projectID={})", projectID);
    Map<String, String> projectLeaderData = new HashMap<>();
    try (Connection connection = databaseManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.first_name, u.last_name, u.email, i.id as 'institution_id'");
      query.append("FROM project_partners pp ");
      query.append("INNER JOIN users u ON u.id = pp.user_id ");
      query.append("INNER JOIN institutions i ON i.id = pp.partner_id ");
      query.append("AND pp.partner_type = '" + APConstants.PROJECT_PARTNER_PL + "' ");
      query.append("AND pp.project_id = ");
      query.append(projectID);

      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        projectLeaderData.put("id", rs.getString("id"));
        projectLeaderData.put("first_name", rs.getString("first_name"));
        projectLeaderData.put("last_name", rs.getString("last_name"));
        projectLeaderData.put("email", rs.getString("email"));
        projectLeaderData.put("institution_id", rs.getString("institution_id"));
        // projectLeaderData.put("employee_id", rs.getString("employee_id")); NOT used any more.
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectLeader() > There was an error getting the data for user {}.", projectID, e);
      return null;
    }
    LOG.debug("<< getProjectLeader():{}", projectLeaderData);
    return projectLeaderData;
  }

  @Override
  public List<Map<String, String>> getProjectOwnerContact(int institutionId) {
    LOG.debug(">> getProjectOwnerContact( programID = {} )", institutionId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*   ");
    query.append("FROM `ccafs_employees` as ce ");
    // query.append("INNER JOIN users u ON u.id = ce.users_id ");
    // query.append("INNER JOIN persons p ON p.id = u.person_id ");
    query.append("INNER JOIN persons p ON p.id = u.person_id ");
    query.append("WHERE ce.institution_id='1' ");
    // query.append(institutionId);


    LOG.debug("-- getProjectOwnerContact() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectOwnerId(int programId) {
    LOG.debug(">> getProjectOwnerId( programID = {} )", programId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*   ");
    query.append("FROM `projects` as p ");
    query.append("INNER JOIN project_focuses pf ON p.id = pf.project_id ");
    query.append("INNER JOIN ip_programs ipr    ON pf.program_id=ipr.id ");
    query.append("WHERE ipr.id='1' ");
    // query.append(programID);


    LOG.debug("-- getProjectOwnerId() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectsByInstitution(Object institutionID) {
    LOG.debug(">> getProjectsByInstitution(institutionID={})", new Object[] {institutionID});
    List<Map<String, String>> projectList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT p.* ");
    query.append("FROM projects as p ");
    query.append("INNER JOIN project_partners pp ON pp.project_id = p.id ");
    query.append("INNER JOIN institutions i ON i.id = pp.institution_id ");
    query.append("WHERE i.id= ");
    query.append(institutionID);
    query.append(" ORDER BY p.id");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));

        projectList.add(projectData);
      }

    } catch (SQLException e) {
      LOG.error("getCoreProjects() > Exception raised trying to get the core projects.", e);
    }
    return projectList;
  }

  @Override
  public List<Map<String, String>> getProjectsByProgram(int programID) {
    LOG.debug(">> getProjects programID = {} )", programID);
    StringBuilder query = new StringBuilder();

    query.append("SELECT p.* ");
    query.append("FROM projects as p ");
    query.append("INNER JOIN employees emp ON emp.id = p.liaison_user_id ");
    query.append("INNER JOIN institutions i ON i.id = emp.institution_id ");
    query.append("INNER JOIN ip_programs ip ON ip.id = i.program_id ");
    query.append("WHERE ip.id = ");
    query.append(programID);

    LOG.debug("-- getProjects() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectsOwning(int institutionId, int userId) {
    List<Map<String, String>> projectList = new ArrayList<>();
    LOG.debug(">> getProjectsOwning institutionId = {} )", institutionId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*, emp.user_id as 'owner_user_id', emp.institution_id as 'owner_institution_id' ");
    query.append("FROM projects p ");
    query.append("INNER JOIN employees emp ON p.liaison_user_id=emp.id ");
    query.append("WHERE emp.user_id= ");
    query.append(userId);
    query.append(" AND emp.institution_id= ");
    query.append(institutionId);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<String, String>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("summary", rs.getString("summary"));
        if (rs.getDate("start_date") != null) {
          projectData.put("start_date", rs.getDate("start_date").toString());
        }
        if (rs.getDate("end_date") != null) {
          projectData.put("end_date", rs.getDate("end_date").toString());
        }
        projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("liaison_institution_id", rs.getString("liaison_institution_id"));
        projectData.put("liaison_user_id", rs.getString("liaison_user_id"));
        projectData.put("project_owner_user_id", rs.getString("owner_user_id"));
        projectData.put("project_owner_institution_id", rs.getString("owner_institution_id"));
        projectData.put("created", rs.getTimestamp("active_since").getTime() + "");

        projectList.add(projectData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ProjectList.size={}", projectList.size());
    return projectList;
  }

  @Override
  public int saveExpectedProjectLeader(int projectId, Map<String, Object> expectedProjectLeaderData) {
    LOG.debug(">> saveExpectedProjectLeader(projectData={})", projectId);
    StringBuilder query = new StringBuilder();
    int result = -1;
    int newId = -1;
    if (expectedProjectLeaderData.get("id") == null) {
      // Add the record into the database and assign it to the projects table (column expected_project_leader_id).
      query
      .append("INSERT INTO expected_project_leaders (contact_first_name, contact_last_name, contact_email, institution_id) ");


      query.append("VALUES (?, ?, ?, ?) ");
      Object[] values = new Object[4];
      values[0] = expectedProjectLeaderData.get("contact_first_name");
      values[1] = expectedProjectLeaderData.get("contact_last_name");
      values[2] = expectedProjectLeaderData.get("contact_email");
      values[3] = expectedProjectLeaderData.get("institution_id");
      newId = databaseManager.saveData(query.toString(), values);
      if (newId <= 0) {
        LOG.error("A problem happened trying to add a new expected project leader in project with id={}", projectId);
        return -1;
      } else {
        // Now we need to assign the new id in the table projects (column expected_project_leader_id).
        query.setLength(0); // Clearing query.
        query.append("UPDATE projects SET expected_project_leader_id = ");
        query.append(newId);
        query.append(" WHERE id = ");
        query.append(projectId);

        try (Connection conn = databaseManager.getConnection()) {
          result = databaseManager.makeChange(query.toString(), conn);
          if (result == 0) {
            // Great!, record was updated.
            result = newId;
          }
        } catch (SQLException e) {
          LOG.error("error trying to create a connection to the database. ", e);
          return -1;
        }
      }
    } else {
      // UPDATE the record into the database.
      query.append("UPDATE expected_project_leaders SET contact_first_name = ?, contact_last_name = ?, ");
      query.append("contact_email = ?, institution_id = ? WHERE id = ?");
      Object[] values = new Object[5];
      values[0] = expectedProjectLeaderData.get("contact_first_name");
      values[1] = expectedProjectLeaderData.get("contact_last_name");
      values[2] = expectedProjectLeaderData.get("contact_email");
      values[3] = expectedProjectLeaderData.get("institution_id");
      values[4] = expectedProjectLeaderData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update an expected project leader identified with the id = {}",
          expectedProjectLeaderData.get("id"));
        return -1;
      }
    }
    return result;
  }

  @Override
  public int saveProject(Map<String, Object> projectData) {
    LOG.debug(">> saveProject(projectData={})", projectData);
    int result = -1;
    StringBuilder query = new StringBuilder();
    if (projectData.get("id") == null) {
      // Insert a new project record.
      query.append("INSERT INTO projects ");
      query.append("(liaison_user_id, liaison_institution_id, created_by, ");
      query.append(" modified_by, modification_justification, type) ");
      query.append("VALUES ((SELECT id FROM liaison_users WHERE user_id = ?), ?, ?, ?, ?, ?) ");
      Object[] values = new Object[6];
      values[0] = projectData.get("user_id");
      values[1] = projectData.get("liaison_institution_id");
      values[2] = projectData.get("created_by");
      values[3] = projectData.get("created_by");
      values[4] = " ";
      values[5] = projectData.get("type");
      result = databaseManager.saveData(query.toString(), values);
      LOG.debug("<< saveProject():{}", result);

    } else {
      // Update project.
      query.append("UPDATE projects SET title = ?, summary = ?, start_date = ?, end_date = ?, ");
      query.append("liaison_user_id = (SELECT id FROM liaison_users WHERE user_id = ?), is_cofinancing = ?, ");
      query.append("requires_workplan_upload = ?, liaison_institution_id = ?, type = ?, workplan_name = ?, ");
      query.append("bilateral_contract_name = ?, modified_by = ?, modification_justification = ? ");
      query.append("WHERE id = ?");

      Object[] values = new Object[14];
      values[0] = projectData.get("title");
      values[1] = projectData.get("summary");
      values[2] = projectData.get("start_date");
      values[3] = projectData.get("end_date");
      values[4] = projectData.get("user_id");
      values[5] = projectData.get("is_cofinancing");
      values[6] = projectData.get("requires_workplan_upload");
      values[7] = projectData.get("liaison_institution_id");
      values[8] = projectData.get("type");
      values[9] = projectData.get("workplan_name");
      values[10] = projectData.get("bilateral_contract_name");
      values[11] = projectData.get("modified_by");
      values[12] = projectData.get("justification");
      values[13] = projectData.get("id");
      result = databaseManager.saveData(query.toString(), values);
    }
    LOG.debug(">> saveProject(projectData={})", projectData);
    return result;
  }

  @Override
  public int saveProjectOutput(Map<String, String> outputData) {
    LOG.debug(">> saveProjectOutput(outputData={})", outputData);
    StringBuilder query = new StringBuilder();

    Object[] values;
    // Insert new activity indicator record
    query.append("INSERT INTO ip_project_contributions ");
    query.append("(project_id, mog_id, midOutcome_id, created_by, modified_by, modification_justification) ");
    query.append("VALUES (?, ?, ?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE is_active = TRUE, ");
    query.append("project_id = VALUES(project_id), mog_id = VALUES(mog_id), midOutcome_id = VALUES(midOutcome_id), ");
    query.append("modified_by = VALUES(modified_by), modification_justification = VALUES(modification_justification)");

    values = new Object[6];
    values[0] = outputData.get("project_id");
    values[1] = outputData.get("mog_id");
    values[2] = outputData.get("midOutcome_id");
    values[3] = outputData.get("user_id");
    values[4] = outputData.get("user_id");
    values[5] = outputData.get("justification");

    int newId = databaseManager.saveData(query.toString(), values);
    if (newId == -1) {
      LOG.warn(
        "-- saveProjectOutput() > A problem happened trying to add a new project output. Data tried to save was: {}",
        outputData);
      LOG.debug("<< saveProjectOutput(): {}", -1);
      return -1;
    }

    LOG.debug("<< saveProjectOutput(): {}", newId);
    return newId;
  }


  @Override
  public List<Map<String, Object>> summaryGetAllActivitiesWithGenderContribution() {
    LOG.debug(">> getAllActivitiesGenderContribution ");

    List<Map<String, Object>> csvRecords = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    // Formatted query:
    query.append("SELECT p.id as 'project_id', ");
    query.append("p.title as 'project_title', ");
    query.append("a.id as 'activity_id', ");
    query.append("a.title as 'activity_title', ");
    query.append("a.description as 'activity_description', ");
    query.append("a.startDate as 'activity_startDate', ");
    query.append("a.endDate as 'activity_endDate', ");
    query.append("IFNULL");
    query.append("( ");
    query.append("CONCAT(i.acronym, ' - ', i.name), i.name ");
    query.append(") ");
    query.append("as 'institution', ");
    query.append("CONCAT(u.last_name, ', ',u.first_name, ' <', u.email, '>') ");
    query.append("as 'activity_leader' ");
    query.append("FROM activities a ");
    query.append("INNER JOIN projects p ON ");
    query.append("a.project_id = p.id ");
    query.append("INNER JOIN project_partner_persons ppp ON ");
    query.append("a.leader_id = ppp.id ");
    query.append("INNER JOIN project_partners pp ON ");
    query.append("ppp.project_partner_id = pp.id ");
    query.append("LEFT JOIN institutions i ON pp.institution_id = i.id ");
    query.append("INNER JOIN users u ON ppp.user_id = u.id ");
    query.append("WHERE a.is_active = 1 AND p.is_active = 1 ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, Object> csvData = new HashMap<>();
        csvData.put("project_id", rs.getInt("project_id"));
        csvData.put("project_title", rs.getString("project_title"));
        csvData.put("activity_id", rs.getInt("activity_id"));
        csvData.put("activity_title", rs.getString("activity_title"));
        csvData.put("activity_description", rs.getString("activity_description"));
        csvData.put("activity_startDate", rs.getString("activity_startDate"));
        csvData.put("activity_endDate", rs.getString("activity_endDate"));
        csvData.put("institution", rs.getString("institution"));
        csvData.put("activity_leader", rs.getString("activity_leader"));
        csvRecords.add(csvData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getAllActivitiesGenderContribution() > Exception raised trying ";
      exceptionMessage += "to get the summary report for activitiesGenderContribution: " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getAllActivitiesGenderContribution ");
    return csvRecords;
  }

  @Override
  public List<Map<String, Object>> summaryGetAllDeliverablesWithGenderContribution() {

    List<Map<String, Object>> csvRecords = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    LOG.debug(">> summaryGetAllDeliverablesWithGenderContribution ");
    query.append("SELECT p.id as 'project_id', ");
    query.append("p.title as 'project_title', ");
    query.append("d.id as 'deliverable_id', ");
    query.append("d.title as 'deliverable_title', ");
    query.append("(SELECT dts.name FROM deliverable_types dts WHERE dt.parent_id = dts.id) as 'deliverable_type', ");
    query.append("dt.name as 'deliverable_subtype', ");
    query.append("nu.user as 'next_user', ");
    query.append("nu.expected_changes as 'expected_changes', ");
    query.append("nu.strategies as 'strategies', ");
    query.append("IFNULL(CONCAT(i.acronym, ' - ', i.name), i.name) AS 'institution', ");
    query.append("CONCAT( u.last_name, ', ', u.first_name, ' <', u.email, '>') AS 'deliverable_responsible' ");
    query.append("FROM projects p INNER JOIN deliverables d ON p.id  = d.project_id ");
    query.append("INNER JOIN deliverable_types dt ON d.type_id = dt.id ");
    query.append("INNER JOIN next_users nu ON d.id = nu.deliverable_id ");
    query.append("LEFT JOIN deliverable_partnerships dp ON d.id = dp.deliverable_id ");
    query.append("LEFT JOIN project_partner_persons ppp ON dp.partner_person_id = ppp.id ");
    query.append("LEFT JOIN project_partners pp ON ppp.project_partner_id = pp.id ");
    query.append("LEFT JOIN users u ON ppp.user_id  = u.id ");
    query.append("LEFT JOIN institutions i ON pp.institution_id = i.id ");
    query.append("WHERE (dp.partner_type = 'Resp' OR dp.partner_type is null ) AND p.is_active = 1 ");
    query.append("AND d.is_active = 1 AND nu.is_active = 1 ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      Map<String, Object> csvData;
      while (rs.next()) {
        csvData = new HashMap<>();
        csvData.put("project_id", rs.getInt("project_id"));
        csvData.put("project_title", rs.getString("project_title"));
        csvData.put("deliverable_id", rs.getInt("deliverable_id"));
        csvData.put("deliverable_title", rs.getString("deliverable_title"));
        csvData.put("deliverable_type", rs.getString("deliverable_type"));
        csvData.put("deliverable_subtype", rs.getString("deliverable_subtype"));
        csvData.put("next_user", rs.getString("next_user"));
        csvData.put("expected_changes", rs.getString("expected_changes"));
        csvData.put("strategies", rs.getString("strategies"));
        csvData.put("institution", rs.getString("institution"));
        csvData.put("deliverable_responsible", rs.getString("deliverable_responsible"));
        csvRecords.add(csvData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- summaryGetAllDeliverablesWithGenderContribution() > Exception raised trying ";
      exceptionMessage += "to get the summary report for gender summary deliverables: " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< summaryGetAllDeliverablesWithGenderContribution ");
    return csvRecords;
  }

  @Override
  public List<Map<String, Object>> summaryGetAllProjectPartnerLeaders() {
    LOG.debug(">> getAllProjectPartnerLeaders ");

    List<Map<String, Object>> csvRecords = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    // Formatted query:
    query.append("SELECT p.id as 'project_id', ");
    query.append("p.type as 'project_type', ");
    query.append("p.title as 'project_title', ");
    query.append("p.summary as 'project_summary', ");
    query.append("( ");
    query.append("SELECT GROUP_CONCAT(DISTINCT CONCAT(' ',pro.acronym)) ");
    query.append("FROM ip_programs pro ");
    query.append("INNER JOIN project_focuses pf ON pro.id = pf.program_id ");
    query.append("WHERE pf.project_id = p.id ");
    query.append("AND pro.region_id IS NULL ");
    query.append(") as 'flagships', ");
    query.append("( ");
    query.append("SELECT GROUP_CONCAT(DISTINCT CONCAT(' ',pro.acronym)) ");
    query.append("FROM ip_programs pro ");
    query.append("INNER JOIN project_focuses pf ON pro.id = pf.program_id  ");
    query.append("WHERE pf.project_id = p.id ");
    query.append("AND pro.region_id IS NOT NULL ");
    query.append(") as 'regions', ");
    query.append("IFNULL ");
    query.append("( ");
    query.append("( ");
    query.append("SELECT CONCAT(i.acronym, ' - ', i.name) ");
    query.append("), ");
    query.append("i.name");
    query.append(") as 'Lead_institution', ");
    query.append("( ");
    query.append("SELECT CONCAT( u.last_name, ', ', u.first_name, ' <', u.email, '>') ");
    query.append("FROM users u ");
    query.append("INNER JOIN project_partner_persons ppp ON u.id = ppp.user_id ");
    query.append("INNER JOIN project_partners pp ON ppp.project_partner_id = pp.id ");
    query.append("WHERE pp.project_id = p.id AND ppp.contact_type = 'PL' ");
    query.append(") as 'project_leader', ");
    query.append("( ");
    query.append("SELECT CONCAT( u.last_name, ', ', u.first_name, ' <', u.email, '>') ");
    query.append("FROM users u ");
    query.append("INNER JOIN project_partner_persons ppp ON u.id = ppp.user_id ");
    query.append("INNER JOIN project_partners pp ON ppp.project_partner_id = pp.id ");
    query.append("WHERE pp.project_id = p.id AND ppp.contact_type = 'PC' ");
    query.append(") as 'project_coordinator' ");
    query.append("FROM projects p ");
    query.append("LEFT JOIN project_partners pp ON p.id = pp.project_id ");
    query.append("LEFT JOIN project_partner_persons ppp ON ppp.project_partner_id = pp.id ");
    query.append("LEFT JOIN institutions i ON pp.institution_id = i.id ");
    query.append("WHERE ppp.contact_type = 'PL' ");
    query.append("ORDER BY p.id ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, Object> csvData = new HashMap<>();
        csvData.put("project_id", rs.getInt("project_id"));
        csvData.put("project_type", rs.getString("project_type"));
        csvData.put("project_title", rs.getString("project_title"));
        csvData.put("project_summary", rs.getString("project_summary"));
        csvData.put("lead_institution", rs.getString("lead_institution"));
        csvData.put("project_leader", rs.getString("project_leader"));
        csvData.put("project_coordinator", rs.getString("project_coordinator"));
        csvData.put("flagships", rs.getString("flagships"));
        csvData.put("regions", rs.getString("regions"));
        csvRecords.add(csvData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getAllProjectPartnerLeaders() > Exception raised trying ";
      exceptionMessage += "to get the summary report for projectPartnerLeaders: " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getAllProjectsPartnerLeaders ");
    return csvRecords;
  }

  @Override
  public List<Map<String, Object>> summaryGetAllProjectsWithDeliverables() {
    LOG.debug(">> getAllProjectsWithDeliverables ");
    List<Map<String, Object>> csvRecords = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    // Formatted query:
    query.append("SELECT p.id as 'project_id', ");
    query.append("p.title as 'project_title', ");
    query.append("( ");
    query.append("SELECT GROUP_CONCAT(ip1.acronym SEPARATOR ', ') ");
    query.append("FROM project_focuses pf1 ");
    query.append("INNER JOIN ip_programs ip1 ON ip1.id = pf1.program_id ");
    query.append("WHERE pf1.project_id = p.id AND ip1.id < 5 ");
    query.append("GROUP BY d.id ");
    query.append(") as 'flagships', ");
    query.append("( ");
    query.append("SELECT GROUP_CONCAT(ip2.acronym SEPARATOR ', ') ");
    query.append("FROM project_focuses pf2 ");
    query.append("INNER JOIN ip_programs ip2 ON ip2.id = pf2.program_id ");
    query.append("WHERE pf2.project_id = p.id AND ip2.id >= 5 ");
    query.append("GROUP BY d.id ");
    query.append(") as 'regions', ");
    query.append("d.id as 'deliverable_id', ");
    query.append("d.title as 'deliverable_title', ");
    query.append("ip.description as 'mog', ");
    query.append("d.type_id  as 'deliverable_type_id', ");
    query.append("d.year as 'deliverable_year', ");
    query.append("dtype.name as 'deliverable_type', ");
    query.append("dsubtype.name as 'deliverable_sub_type', ");
    query.append("d.type_other as 'other_type', ");
    query.append("( ");
    query
    .append("SELECT group_concat(concat(u.first_name, ' ', u.last_name, ' <', u.email, '> - ', ifnull(i.acronym, i.name)) SEPARATOR '; ') ");
    query.append("FROM deliverable_partnerships dp_resp ");
    query.append("INNER JOIN project_partner_persons ppp ON ppp.id = dp_resp.partner_person_id ");
    query.append("INNER JOIN users u ON u.id = ppp.user_id ");
    query.append("WHERE dp_resp.deliverable_id = d.id ");
    query.append("AND dp_resp.partner_type = 'Resp' ");
    query.append(" GROUP BY d.id ");
    query.append(") as 'partner_responsible', ");
    query.append("( ");
    query
    .append("SELECT group_concat(concat(u.first_name, ' ', u.last_name, ' <', u.email, '> - ', ifnull(i.acronym, i.name)) SEPARATOR '; ') ");
    query.append("FROM deliverable_partnerships dp_resp ");
    query.append("INNER JOIN project_partner_persons ppp ON ppp.id = dp_resp.partner_person_id ");
    query.append("INNER JOIN users u ON u.id = ppp.user_id ");
    query.append("WHERE dp_resp.deliverable_id = d.id ");
    query.append("AND dp_resp.partner_type = 'Other' ");
    query.append("GROUP BY d.id ");
    query.append(") as 'other_responsibles' ");
    query.append("FROM deliverables d ");
    query.append("INNER JOIN projects p ON p.id = d.project_id ");
    query.append("INNER JOIN project_partners pp ON pp.project_id = p.id ");
    query.append("INNER JOIN project_partner_persons persons ON persons.project_partner_id = pp.id ");
    query.append("INNER JOIN institutions i ON i.id = pp.institution_id ");
    query.append("INNER JOIN users u ON u.id = persons.user_id ");
    query.append("INNER JOIN ip_deliverable_contributions ipd ON ipd.deliverable_id = d.id ");
    query.append("INNER JOIN ip_project_contributions ipp ON ipp.id = ipd.project_contribution_id ");
    query.append("INNER JOIN ip_elements ip ON ip.id = ipp.mog_id ");
    query.append("INNER JOIN deliverable_types dsubtype ON dsubtype.id = d.type_id ");
    query.append("INNER JOIN deliverable_types dtype ON dtype.id = dsubtype.parent_id ");
    query.append("WHERE p.is_active = 1 ");
    query.append("AND d.is_active = 1 ");
    query.append("AND pp.is_active = 1 ");
    query.append("AND persons.is_active = 1 ");
    query.append("GROUP BY d.id ");


    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, Object> csvData = new HashMap<>();
        csvData.put("project_id", rs.getInt("project_id"));
        csvData.put("project_title", rs.getString("project_title"));
        csvData.put("flagships", rs.getString("flagships"));
        csvData.put("regions", rs.getString("regions"));
        csvData.put("deliverable_id", rs.getInt("deliverable_id"));
        csvData.put("deliverable_title", rs.getString("deliverable_title"));
        csvData.put("year", rs.getInt("deliverable_year"));
        csvData.put("mog", rs.getString("mog"));
        csvData.put("deliverable_type_id", rs.getInt("deliverable_type_id"));
        csvData.put("deliverable_type", rs.getString("deliverable_type"));
        csvData.put("deliverable_sub_type", rs.getString("deliverable_sub_type"));
        csvData.put("other_type", rs.getString("other_type"));
        csvData.put("partner_responsible", rs.getString("partner_responsible"));
        csvData.put("other_responsibles", rs.getString("other_responsibles"));
        csvRecords.add(csvData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getAllProjectsWithDeliverables() > Exception raised trying ";
      exceptionMessage += "to get the summary report for expected deliverables: " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getAllProjectsWithDeliverables ");
    return csvRecords;
  }

  @Override
  public List<Map<String, Object>> summaryGetAllProjectsWithGenderContribution() {
    LOG.debug(">> getAllProjectsGenderContribution ");

    List<Map<String, Object>> csvRecords = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    // Formatted query:
    query.append("SELECT p.id as 'project_id', ");
    query.append("p.title as 'project_title', ");
    query.append("p.summary as 'project_summary', ");
    query.append("( ");
    query.append("SELECT po.statement ");
    query.append("FROM project_outcomes po ");
    query.append("WHERE p.id = po.project_id AND po.year = 2016 AND  po.is_active = 1 ");
    query.append(") as 'outcome_statement', ");
    query.append("p.start_date as 'start_date', ");
    query.append("p.end_date as 'end_date', ");
    query.append("( ");
    query.append("SELECT GROUP_CONCAT(DISTINCT CONCAT(' ',pro.acronym)) ");
    query.append("FROM ip_programs pro ");
    query.append("INNER JOIN project_focuses pf ON pro.id = pf.program_id ");
    query.append("WHERE pf.project_id = p.id  ");
    query.append("AND pro.region_id IS NULL AND  pf.is_active = 1");
    query.append(") as 'flagships', ");
    query.append("( ");
    query.append("SELECT GROUP_CONCAT(DISTINCT CONCAT(' ',pro.acronym)) ");
    query.append("FROM ip_programs pro ");
    query.append("INNER JOIN project_focuses pf ON pro.id = pf.program_id  ");
    query.append("WHERE pf.project_id = p.id ");
    query.append("AND pro.region_id IS NOT NULL AND  pf.is_active = 1 ");
    query.append(") as 'regions', ");
    query.append("IFNULL");
    query.append("( ");
    query.append("( ");
    query.append("SELECT CONCAT(i.acronym, ' - ', i.name) ");
    query.append("), ");
    query.append("i.name");
    query.append(") as 'Lead_institution', ");
    query.append("( ");
    query.append("SELECT CONCAT( u.last_name, ', ', u.first_name, ' <', u.email, '>') ");
    query.append("FROM users u ");
    query.append("INNER JOIN project_partner_persons ppp ON u.id = ppp.user_id ");
    query.append("INNER JOIN project_partners pp ON ppp.project_partner_id = pp.id ");
    query.append("WHERE pp.project_id = p.id AND ppp.contact_type = 'PL' ");
    query.append(" AND  u.is_active = 1 AND pp.is_active = 1 AND ppp.is_active = 1 ");
    query.append(") as 'project_leader', ");
    query.append("( ");
    query.append("SELECT CONCAT( u.last_name, ', ', u.first_name, ' <', u.email, '>') ");
    query.append("FROM users u ");
    query.append("INNER JOIN project_partner_persons ppp ON u.id = ppp.user_id ");
    query.append("INNER JOIN project_partners pp ON ppp.project_partner_id = pp.id ");
    query.append("WHERE pp.project_id = p.id AND ppp.contact_type = 'PC' ");
    query.append(" AND  u.is_active = 1 AND pp.is_active = 1 AND ppp.is_active = 1 ");
    query.append(") as 'project_coordinator', ");
    query.append("( ");
    query.append("SELECT SUM(pb.amount) ");
    query.append("FROM project_budgets pb ");
    query.append("WHERE p.id = pb.project_id AND pb.year = 2016 and pb.budget_type = 1 AND pb.is_active = 1 ");
    query.append("GROUP BY p.id ");
    query.append(") as 'budget_w1w2', ");
    query.append("( ");
    query.append("SELECT SUM(pb.amount) ");
    query.append("FROM project_budgets pb ");
    query.append("WHERE p.id = pb.project_id AND pb.year = 2016 and pb.budget_type = 2 AND pb.is_active = 1 ");
    query.append("GROUP BY p.id ");
    query.append(") as 'budget_w3bilateral', ");
    query.append("( ");
    query.append("SELECT SUM(pb.amount * pb.gender_percentage *  0.01) ");
    query.append("FROM project_budgets pb ");
    query.append("WHERE p.id = pb.project_id AND pb.year = 2016 and pb.budget_type = 1 AND pb.is_active = 1 ");
    query.append("GROUP BY p.id ");
    query.append(") as 'gender_w1w2', ");
    query.append("( ");
    query.append("SELECT SUM(pb.amount * pb.gender_percentage *  0.01) ");
    query.append("FROM project_budgets pb ");
    query.append("WHERE p.id = pb.project_id AND pb.year = 2016 and pb.budget_type = 2 AND pb.is_active = 1 ");
    query.append("GROUP BY p.id ");
    query.append(") as 'gender_w3bilateral' ");
    query.append("FROM projects p ");
    query.append("LEFT JOIN project_partners pp ON p.id = pp.project_id ");
    query.append("LEFT JOIN project_partner_persons ppp ON ppp.project_partner_id = pp.id ");
    query.append("LEFT JOIN institutions i ON pp.institution_id = i.id ");
    query.append("WHERE ppp.contact_type = 'PL' AND p.is_active = 1 ");
    query.append("ORDER BY p.id ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, Object> csvData = new HashMap<>();
        csvData.put("project_id", rs.getInt("project_id"));
        csvData.put("project_title", rs.getString("project_title"));
        csvData.put("project_summary", rs.getString("project_summary"));
        csvData.put("outcome_statement", rs.getString("outcome_statement"));
        csvData.put("start_date", rs.getString("start_date"));
        csvData.put("end_date", rs.getString("end_date"));
        csvData.put("lead_institution", rs.getString("lead_institution"));
        csvData.put("project_leader", rs.getString("project_leader"));
        csvData.put("project_coordinator", rs.getString("project_coordinator"));
        csvData.put("flagships", rs.getString("flagships"));
        csvData.put("regions", rs.getString("regions"));
        csvData.put("budget_w1w2", rs.getDouble("budget_w1w2"));
        csvData.put("budget_w3bilateral", rs.getDouble("budget_w3bilateral"));
        csvData.put("gender_w1w2", rs.getDouble("gender_w1w2"));
        csvData.put("gender_w3bilateral", rs.getDouble("gender_w3bilateral"));
        csvRecords.add(csvData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getAllProjectsGenderContribution() > Exception raised trying ";
      exceptionMessage += "to get the summary report for projectsGenderContribution: " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getAllProjectsGenderContribution ");
    return csvRecords;
  }


  @Override
  public List<Map<String, Object>> summaryGetInformationDetailPOWB(int year) {
    LOG.debug(">> getBudgetByMogAndByYear ");

    List<Map<String, Object>> csvRecords = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    // Formatted query:
    query.append("SELECT p.id as 'project_id', ");
    query.append("p.title as 'project_title', ");
    query.append("ipr.acronym as 'flagship', ");
    query.append("ipe.description AS 'mog_description', ");
    query.append("ipco.anual_contribution as 'anual_contribution', ");
    query.append("ipco.gender_contribution as 'gender_contribution', ");

    // Sum of contribution budget W1_W2 of the project for the MOG
    query.append("IF(pmb.budget_type = 1 , (SELECT SUM(pb.amount) FROM project_budgets pb WHERE pb.project_id = p.id ");
    query.append("AND pb.year = " + year + " AND pb.budget_type = 1 AND pb.is_active = 1 ) ");
    query.append("* IFNULL(pmb.total_contribution, 0) * 0.01 , 0 ) ");
    query.append(" AS 'budget_W1_W2' , ");

    // Sum of contribution gender W1_W2 of the project for the MOG
    query.append("IF(pmb.budget_type = 1 , (SELECT SUM(pb.amount * pb.gender_percentage * 0.01) FROM project_budgets ");
    query.append("pb WHERE pb.project_id = p.id AND pb.year = " + year);
    query.append(" AND pb.budget_type = 1 AND pb.is_active = 1) ");
    query.append("* IFNULL(pmb.gender_contribution,0) * 0.01 , 0 ) AS gender_W1_W2,");

    // Sum of contribution budget W3_Bilateral of the project for the MOG
    query.append("IF(pmb.budget_type = 2 , (SELECT SUM(pb.amount) FROM project_budgets pb WHERE pb.project_id = p.id ");
    query.append("AND pb.year =  " + year);
    query.append(" AND pb.budget_type = 2  AND pb.is_active = 1) * IFNULL(pmb.total_contribution, 0) * 0.01 , 0 ) ");
    query.append("AS 'budget_W3_Bilateral' , ");

    // Sum of contribution gender W3_Bilateral of the project for the MOG
    query.append("IF(pmb.budget_type = 2 , (SELECT SUM(pb.amount * pb.gender_percentage * 0.01) FROM project_budgets ");
    query.append("pb WHERE pb.project_id = p.id AND pb.year = " + year + " AND pb.budget_type = 2    ");
    query.append("AND pb.is_active = 1 ) * IFNULL(pmb.gender_contribution,0) * 0.01 , 0 )AS gender_W3_Bilateral ");

    query.append("FROM projects p ");
    query.append("LEFT JOIN ip_project_contributions ipc ON p.id = ipc.project_id AND ipc.is_active = 1 ");
    query.append("LEFT JOIN ip_elements ipe ON ipc.mog_id = ipe.id AND  ipe.is_active = 1  ");
    query.append("LEFT JOIN ip_programs ipr ON ipe.ip_program_id = ipr.id ");
    query.append("LEFT JOIN ip_project_contribution_overviews ipco ON ipco.output_id = ipe.id ");
    query.append("AND p.id = ipco.project_id AND ipco.is_active = 1  ");
    query.append("LEFT JOIN project_mog_budgets pmb ON p.id = pmb.project_id AND pmb.mog_id = ipe.id ");
    query.append("WHERE (ipco.year = ");
    query.append(year + " ");
    query.append("OR ipco.year IS NULL) AND (pmb.year = ");
    query.append(year + " ");
    query.append("OR pmb.year IS NULL) AND p.is_active = 1 ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, Object> csvData = new HashMap<>();
        csvData.put("project_id", rs.getInt("project_id"));
        csvData.put("project_title", rs.getString("project_title"));
        csvData.put("flagship", rs.getString("flagship"));
        csvData.put("mog_description", rs.getString("mog_description"));
        csvData.put("anual_contribution", rs.getString("anual_contribution"));
        csvData.put("gender_contribution", rs.getString("gender_contribution"));
        csvData.put("budget_W1_W2", rs.getDouble("budget_W1_W2"));
        csvData.put("gender_W1_W2", rs.getDouble("gender_W1_W2"));
        csvData.put("budget_W3_Bilateral", rs.getDouble("budget_W3_Bilateral"));
        csvData.put("gender_W3_Bilateral", rs.getDouble("gender_W3_Bilateral"));
        csvRecords.add(csvData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getAllProjectsWithDeliverables() > Exception raised trying ";
      exceptionMessage += "to get the summary report budget By MOG POWB Report: " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getBudgetByMogAndByYear ");
    return csvRecords;
  }

  @Override
  public List<Map<String, Object>> summaryGetInformationPOWB(int year) {

    LOG.debug(">> summaryGetInformationPOWB ");
    List<Map<String, Object>> csvRecords = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    // Formatted query:
    query.append("SELECT ipr.acronym as 'outcome_flagship', ");
    query.append("ipe.description as 'outcome_description' , ");
    query.append("iprm.acronym AS 'mog_flagship'  ,");
    query.append("ipem.description as  'mog_description', ");

    // Sum of contribution budget W1_W2 of the project for the MOG
    query.append(" (SELECT  (SUM(IFNULL(pb.amount,0)) * pmb.total_contribution * 0.01) FROM project_mog_budgets pmb  ");
    query.append(" INNER JOIN  project_budgets pb  ON pmb.project_id = pb.project_id  ");
    query.append(" WHERE pmb.mog_id = ipem.id AND pb.year = " + year + " AND pmb.year = " + year);
    query.append(" AND pb.budget_type = 1  AND  pmb.budget_type = 1 AND pb.is_active = 1 AND pmb.is_active = 1) "
      + "AS 'budget_W1_W2'  ,");

    // Sum of contribution gender W1_W2 of the project for the MOG
    query
    .append(" (SELECT (SUM(IFNULL(pb.amount,0) * IFNULL(pb.gender_percentage,0) * 0.01) * pmb.gender_contribution * 0.01) ");
    query.append(" FROM project_mog_budgets pmb INNER JOIN project_budgets pb ON pmb.project_id = pb.project_id  ");
    query.append(" WHERE pmb.mog_id = ipem.id AND pb.year = " + year + " AND pmb.year = " + year + " ");
    query.append(" AND  pb.budget_type = 1  AND  pmb.budget_type = 1 AND pb.is_active = 1 AND pmb.is_active = 1)  ");
    query.append(" AS  'gender_W1_W2' ,");

    // Sum of contribution budget W3_Bilateral of the project for the MOG
    query.append(" (SELECT  (SUM(IFNULL(pb.amount,0)) * pmb.total_contribution * 0.01) FROM project_mog_budgets pmb  ");
    query.append(" INNER JOIN  project_budgets pb  ON pmb.project_id = pb.project_id  ");
    query.append(" WHERE pmb.mog_id = ipem.id AND pb.year = " + year + " AND pmb.year = " + year);
    query.append(" AND pb.budget_type = 2  AND  pmb.budget_type = 2 AND pb.is_active = 1"
      + " AND pmb.is_active = 1) AS 'budget_W3_Bilateral'  ,");

    // Sum of contribution gender W3_Bilateral of the project for the MOG
    query.append(" (SELECT (SUM(IFNULL(pb.amount,0)* IFNULL(pb.gender_percentage,0) * 0.01) * "
      + "pmb.gender_contribution * 0.01) ");

    query.append(" FROM project_mog_budgets pmb INNER JOIN project_budgets pb ON pmb.project_id = pb.project_id  ");
    query.append(" WHERE pmb.mog_id = ipem.id AND pb.year = " + year + " AND pmb.year = " + year + " ");
    query.append(" AND  pb.budget_type = 2  AND  pmb.budget_type = 2 AND pb.is_active = 1 AND pmb.is_active = 1)  ");
    query.append(" AS  'gender_W3_Bilateral' ");

    query.append("FROM ip_elements ipe ");
    query.append("INNER JOIN ip_project_contributions ipco ON ipe.id = ipco.midOutcome_id ");
    query.append("INNER JOIN ip_programs ipr ON ipe.ip_program_id = ipr.id ");
    query.append("INNER JOIN ip_elements ipem ON ipco.mog_id = ipem.id ");
    query.append("INNER JOIN ip_programs iprm ON ipem.ip_program_id = iprm.id ");
    query.append("WHERE ipe.element_type_id = 3 AND ipe.is_active = 1 AND ipe.is_active = 1 AND ipco.is_active = 1 ");
    query.append("AND ipem.is_active = 1 ");
    query.append("GROUP BY ipem.id");


    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      Map<String, Object> csvData;
      while (rs.next()) {
        csvData = new HashMap<>();
        csvData.put("outcome_flagship", rs.getString("outcome_flagship"));
        csvData.put("outcome_description", rs.getString("outcome_description"));
        csvData.put("mog_flagship", rs.getString("mog_flagship"));
        csvData.put("mog_description", rs.getString("mog_description"));
        csvData.put("budget_W1_W2", rs.getDouble("budget_W1_W2"));
        csvData.put("gender_W1_W2", rs.getDouble("gender_W1_W2"));
        csvData.put("budget_W3_Bilateral", rs.getDouble("budget_W3_Bilateral"));
        csvData.put("gender_W3_Bilateral", rs.getDouble("gender_W3_Bilateral"));
        csvRecords.add(csvData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- summaryGetInformationPOWB() > Exception raised trying ";
      exceptionMessage += "to get the summary report budget By MOG POWB Report: " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< summaryGetInformationPOWB ");
    return csvRecords;

  }

  @Override
  public List<Map<String, Object>> summaryGetProjectBudgetPerPartners(int year) {
    LOG.debug("<< summaryGetProjectBudgetPerPartners ");
    List<Map<String, Object>> csvRecords = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    // Formatted query:
    query.append("SELECT p.id AS 'project_id', ");
    query.append("p.title AS 'project_title' , ");
    query.append("(SELECT IFNULL(CONCAT(i.acronym, ' - ', i.name) , i.name)) AS 'partner' ,");
    query.append("IFNULL((SELECT SUM(IFNULL(pb.amount,0)) FROM project_budgets pb WHERE i.id = pb.institution_id ");
    query.append("AND p.id =  pb.project_id AND pb.is_active= 1 AND pb.budget_type = 1 AND ");
    query.append("pb.year =  " + year + " ),0) AS 'budget_w1_w2', ");

    query.append("IFNULL((SELECT SUM(IFNULL(pb.amount,0) * pb.gender_percentage * 0.01) FROM project_budgets pb ");
    query.append("WHERE i.id =  pb.institution_id AND p.id = pb.project_id AND pb.is_active= 1 ");
    query.append(" AND pb.budget_type = 1 AND ");
    query.append("pb.year =  " + year + " ),0) AS 'gender_w1_w2', ");

    query.append("IFNULL((SELECT SUM(IFNULL(pb.amount, 0)) FROM project_budgets pb WHERE i.id = pb.institution_id ");
    query.append("AND p.id = pb.project_id AND pb.is_active= 1 AND pb.budget_type = 2 AND ");
    query.append("pb.year =  " + year + " ),0) AS 'budget_w3_bilateral', ");

    query.append("IFNULL((SELECT SUM(IFNULL(pb.amount,0) * pb.gender_percentage * 0.01 ) FROM project_budgets pb ");
    query.append("WHERE i.id = pb.institution_id AND p.id = pb.project_id AND pb.is_active= 1  ");
    query.append("AND pb.budget_type = 2 AND ");
    query.append("pb.year =  " + year + " ),0) AS 'gender_w3_bilateral' ");

    query.append("FROM projects p  ");
    query.append("INNER JOIN project_partners pp  ON pp.project_id = p.id ");
    query.append("INNER JOIN project_partner_persons ppp ON pp.id = ppp.project_partner_id ");
    query.append("INNER JOIN institutions i ON ");
    query.append(" IF(p.type = 'BILATERAL', (pp.institution_id = i.id AND ppp.contact_type = 'PL'), ");
    query.append("(pp.institution_id = i.id AND i.is_ppa = 1))   ");
    query.append("GROUP BY p.id, i.id ");
    query.append("ORDER BY p.id ");


    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      Map<String, Object> csvData;
      while (rs.next()) {
        csvData = new HashMap<>();
        csvData.put("project_id", rs.getInt("project_id"));
        csvData.put("project_title", rs.getString("project_title"));
        csvData.put("partner", rs.getString("partner"));
        csvData.put("budget_W1_W2", rs.getDouble("budget_W1_W2"));
        csvData.put("gender_W1_W2", rs.getDouble("gender_W1_W2"));
        csvData.put("budget_W3_Bilateral", rs.getDouble("budget_W3_Bilateral"));
        csvData.put("gender_W3_Bilateral", rs.getDouble("gender_W3_Bilateral"));
        csvRecords.add(csvData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- summaryGetProjectBudgetPerPartners() > Exception raised trying ";
      exceptionMessage += "to get the summary report budget By MOG POWB Report: " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< summaryGetProjectBudgetPerPartners ");
    return csvRecords;
  }

  @Override
  public boolean updateProjectCofinancing(int projectID, boolean cofinancing) {
    int result =
      databaseManager.saveData("UPDATE projects SET is_cofinancing = ? WHERE id = ?", new Object[] {projectID,
        cofinancing});
    return !(result == -1);
  }


  @Override
  public boolean updateProjectType(int projectID, String type) {
    int result = databaseManager.saveData("UPDATE projects SET type = ? WHERE id = ?", new Object[] {projectID, type});
    return !(result == -1);
  }

  @Override
  public boolean updateProjectTypes() {

    int result =
      databaseManager.saveData("UPDATE projects SET type = ? WHERE type = ?", new Object[] {APConstants.PROJECT_CORE,
        APConstants.PROJECT_CCAFS_COFUNDED});

    if (result != -1) {
      StringBuilder query = new StringBuilder();
      query.append("UPDATE projects p ");
      query
        .append("INNER JOIN project_cofinancing_linkages pcl ON p.id = pcl.core_project_id AND pcl.is_active =TRUE ");
      query.append("SET p.type = ?");
      result = databaseManager.saveData(query.toString(), new Object[] {APConstants.PROJECT_CCAFS_COFUNDED});
    }

    return result != -1;
  }
}
