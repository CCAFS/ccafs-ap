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

import org.cgiar.ccafs.ap.data.dao.BudgetDAO;
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
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R.
 */
public class MySQLBudgetDAO implements BudgetDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLBudgetDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLBudgetDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public double calculateGenderBudgetByTypeAndYear(int projectID, int budgetTypeID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(gender_percentage * amount * 0.01) as total ");
    query.append("FROM project_budgets b ");
    query.append("WHERE project_id = " + projectID);
    query.append(" AND  budget_type = " + budgetTypeID);
    query.append(" AND  year = " + year);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateProjectBudgetByTypeAndYear(int projectID, int budgetTypeID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(amount) as total ");
    query.append("FROM project_budgets b ");
    query.append("WHERE project_id = " + projectID);
    query.append(" AND  budget_type = " + budgetTypeID);
    query.append(" AND  year = " + year);
    query.append(" AND  is_active = TRUE");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }


  @Override
  public double calculateTotalBudget(int projectID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(amount) as total ");
    query.append("FROM project_budgets ");
    query.append(" WHERE project_id = ");
    query.append(projectID);
    query.append(" AND is_active = TRUE");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }


  @Override
  public double calculateTotalBudgetByYear(int projectID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(amount) as total ");
    query.append("FROM project_budgets ");
    query.append(" WHERE project_id = ");
    query.append(projectID);
    query.append(" AND year =  ");
    query.append(year);
    query.append(" AND is_active = TRUE");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }


  @Override
  public double calculateTotalCCAFSBudgetByInstitution(int projectID, int institutionID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(amount) as total ");
    query.append("FROM project_budgets ");
    query.append(" WHERE project_id = ");
    query.append(projectID);
    query.append(" AND institution_id = ");
    query.append(institutionID);
    query.append(" AND is_active = TRUE");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateTotalCCAFSBudgetByInstitutionAndType(int projectID, int institutionID, int budgetTypeID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(amount) as total ");
    query.append("FROM project_budgets ");
    query.append(" WHERE project_id = ");
    query.append(projectID);
    query.append(" AND institution_id = ");
    query.append(institutionID);
    query.append(" AND budget_type = ");
    query.append(budgetTypeID);
    query.append(" AND is_active = TRUE");


    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateTotalCCAFSBudgetByType(int projectID, int budgetTypeID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(amount) as total ");
    query.append("FROM project_budgets ");
    query.append(" WHERE project_id = ");
    query.append(projectID);
    query.append(" AND budget_type = ");
    query.append(budgetTypeID);
    query.append(" AND is_active = TRUE");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }


  @Override
  public double calculateTotalGenderBudget(int projectID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(gender_percentage) as total ");
    query.append("FROM project_budgets ");
    query.append(" WHERE project_id = ");
    query.append(projectID);
    query.append(" AND is_active = TRUE ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateTotalGenderBudgetByInstitutionAndType(int projectID, int institutionID, int budgetTypeID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(amount * gender_percentage * 0.01) as total ");
    query.append("FROM project_budgets ");
    query.append(" WHERE project_id = ");
    query.append(projectID);
    query.append(" AND institution_id = ");
    query.append(institutionID);
    query.append(" AND budget_type = ");
    query.append(budgetTypeID);
    query.append(" AND is_active = TRUE");


    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }


  @Override
  public double calculateTotalGenderBudgetByYear(int projectID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(gender_percentage * amount * 0.01) as total ");
    query.append("FROM project_budgets ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND year = ");
    query.append(year);
    query.append(" AND is_active= TRUE");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised calculating the total project budget W1+W2 {}.", projectID, e.getMessage());
      total = -1.0;
    }
    return total;
  }


  @Override
  public double calculateTotalGenderPercentageByType(int projectID, int budgetTypeID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(gender_percentage * amount * 0.01 ) as total ");
    query.append("FROM project_budgets ");
    query.append(" WHERE project_id = ");
    query.append(projectID);
    query.append(" AND budget_type = ");
    query.append(budgetTypeID);
    query.append(" AND is_active = TRUE ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateTotalGenderPercentageByYearAndType(int projectID, int year, int budgetTypeID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();


    switch (budgetTypeID) {
      case 1:
        query.append("SELECT SUM(gender_percentage * amount * 0.01) as total ");
        query.append("FROM project_budgets ");
        query.append(" WHERE project_id = ");
        query.append(projectID);
        query.append(" AND budget_type = ");
        query.append(budgetTypeID);
        query.append(" AND year = ");
        query.append(year);
        query.append(" AND is_active = TRUE");

        break;

      case 2:


        query.append("SELECT SUM((");

        query.append(" SELECT b2.gender_percentage FROM project_budgets b2 ");
        query.append(" WHERE b2.project_id =  b.cofinance_project_id ");

        query.append(" AND b2.budget_type = ");
        query.append(budgetTypeID);
        query.append(" AND b2.year = ");
        query.append(year);
        query.append(" AND b2.is_active = TRUE )");


        query.append(" * amount * 0.01) as total ");
        query.append("FROM project_budgets b ");
        query.append(" WHERE b.project_id = ");
        query.append(projectID);
        query.append(" AND b.budget_type = ");
        query.append(budgetTypeID);
        query.append(" AND b.year = ");
        query.append(year);
        query.append(" AND b.is_active = TRUE");


        break;

      default:
        break;
    }

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateTotalProjectBudgetByType(int projectID, int budgetTypeID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(amount) as total ");
    query.append("FROM project_budgets ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND budget_type = ");
    query.append(budgetTypeID);
    query.append(" AND is_active = 1");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised calculating the total project budget W1+W2 {}.", projectID, e.getMessage());
      total = -1.0;
    }
    return total;
  }


  @Override
  public double calculateTotalProjectBudgetByTypeYear(int projectID, int budgetTypeID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(amount) as total ");
    query.append("FROM project_budgets ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND budget_type = ");
    query.append(budgetTypeID);
    query.append(" AND is_active = 1");
    query.append(" AND year = ");
    query.append(year);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised calculating the total project budget W1+W2 {}.", projectID, e.getMessage());
      total = -1.0;
    }
    return total;
  }


  @Override
  public boolean deleteBudget(int budgetId, int userId, String justification) {
    LOG.debug(">> deleteBudget(id={})", budgetId);

    String query =
      "UPDATE project_budgets SET is_active = 0, modified_by = ?, modification_justification = ? WHERE id = ?";
    Object[] values = new Object[3];
    values[0] = userId;
    values[1] = justification;
    values[2] = budgetId;
    int result = databaseManager.saveData(query, values);
    if (result >= 0) {
      LOG.debug("<< deleteBudget():{}", true);
      return true;
    }
    LOG.debug("<< deleteBudget:{}", false);
    return false;
  }

  @Override
  public boolean deleteBudgetsByInstitution(int projectID, int institutionID, int userID, String justification) {
    LOG.debug(">> deleteBudgetsByInstitution(projectId={}, institutionId={})", projectID, institutionID);

    StringBuilder query = new StringBuilder();
    query.append("UPDATE project_budgets SET is_active = 0, modified_by = ?, modification_justification = ? ");
    query.append("WHERE project_id = ? AND institution_id = ?");

    Object[] values = new Object[4];
    values[0] = userID;
    values[1] = justification;
    values[2] = projectID;
    values[3] = institutionID;

    int result = databaseManager.saveData(query.toString(), values);
    if (result >= 0) {
      LOG.debug("<< deleteBudgetsByInstitution():{}", true);
      return true;
    }
    LOG.debug("<< deleteBudgetsByInstitution():{}", false);
    return false;
  }

  @Override
  public boolean deleteBudgetsByYear(int projectID, int year, int userID, String justification) {
    LOG.debug(">> deleteBudgetsByYear(projectId={}, eyar={})", projectID, year);

    StringBuilder query = new StringBuilder();
    query.append("UPDATE project_budgets SET is_active = 0, modified_by = ?, modification_justification = ? ");
    query.append("WHERE project_id = ? AND year = ?");
    Object[] values = new Object[4];
    values[0] = userID;
    values[1] = justification;
    values[2] = projectID;
    values[3] = year;
    int result = databaseManager.saveData(query.toString(), values);
    if (result >= 0) {
      LOG.debug("<< deleteBudgetsByYear():{}", true);
      return true;
    }
    LOG.debug("<< deleteBudgetsByYear():{}", false);
    return false;
  }

  @Override
  public boolean deleteBudgetsFromUnexistentYears(int projectID) {
    StringBuilder query = new StringBuilder();
    query.append("UPDATE project_budgets pb ");
    query.append("INNER JOIN projects p ON pb.project_id = p.id ");
    query.append("SET pb.is_active = FALSE ");
    query.append("WHERE pb.year < YEAR(p.start_date) OR pb.year > YEAR(p.end_date) ");
    query.append("AND pb.project_id =  ");
    query.append(projectID);

    int result = databaseManager.saveData(query.toString(), new Object[] {});
    return (result != -1);
  }

  @Override
  public boolean deleteBudgetsWithNoLinkToInstitutions(int projectID, int currentYear) {
    StringBuilder query = new StringBuilder();
    query.append("UPDATE project_budgets pb ");
    query.append("SET pb.is_active = FALSE ");
    query.append("WHERE pb.institution_id NOT IN ");
    query.append("( SELECT  institution_id FROM project_partners pp ");
    query.append("  INNER JOIN institutions i ON pp.institution_id = i.id ");
    query.append("  WHERE pp.project_id = pb.project_id AND pp.is_active = TRUE AND i.is_ppa = TRUE ");
    query.append("  GROUP BY pp.institution_id, pp.is_active ");
    query.append(") AND pb.project_id = ");
    query.append(projectID);
    query.append(" AND pb.year >= ");
    query.append(currentYear);

    int result = databaseManager.saveData(query.toString(), new Object[] {});
    return (result != -1);
  }

  @Override
  public boolean deleteCofoundedBudgetsWithNoLink(int projectID) {
    StringBuilder query = new StringBuilder();
    query.append("UPDATE project_budgets pb ");
    query.append("INNER JOIN project_cofinancing_linkages pcl ON pb.project_id = pcl.core_project_id  ");
    query.append("  AND pb.cofinance_project_id = pcl.bilateral_project_id ");
    query.append("SET pb.is_active = 0 ");
    query.append("WHERE pcl.is_active = 0 ");
    query.append("AND project_id = ");
    query.append(projectID);

    int result = databaseManager.saveData(query.toString(), new Object[] {});
    return (result != -1);
  }

  @Override
  public List<Map<String, String>> getBudgetsByProject(int projectID) {
    LOG.debug(">> getBudgetsByProject projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT pb.*   ");
    query.append("FROM project_budgets as pb ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("INNER JOIN institutions i ON b.institution_id = i.id ");
    query.append("WHERE pb.project_id=  ");
    query.append(projectID);


    LOG.debug("-- getBudgetsByProject() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getBudgetsByYear(int projectID, int year) {
    LOG.debug(">> getBudgetsByYear projectID = {}, year={} )", new Object[] {projectID, year});
    List<Map<String, String>> budgetList = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT pb.*, p.id as 'cofinancing_project_id', p.title as 'cofinancing_project_title'");
    query.append("FROM project_budgets as pb ");
    query.append("INNER JOIN budget_types bt ON pb.budget_type = bt.id ");
    query.append("INNER JOIN institutions i ON pb.institution_id = i.id ");
    query.append("LEFT JOIN projects p ON pb.cofinance_project_id = p.id ");
    query.append("WHERE ( pb.project_id=  ");
    query.append(projectID);
    query.append(" OR pb.cofinance_project_id =  ");
    query.append(projectID);
    query.append(" ) AND pb.year=  ");
    query.append(year);
    query.append(" AND pb.is_active = TRUE ");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> budgetData = new HashMap<String, String>();
        budgetData.put("id", rs.getString("id"));
        budgetData.put("project_id", rs.getString("project_id"));
        budgetData.put("year", rs.getString("year"));
        budgetData.put("budget_type", rs.getString("budget_type"));
        budgetData.put("institution_id", rs.getString("institution_id"));
        budgetData.put("amount", rs.getString("amount"));
        budgetData.put("gender_percentage", rs.getString("gender_percentage"));
        budgetData.put("cofinancing_project_id", rs.getString("cofinancing_project_id"));
        budgetData.put("cofinancing_project_title", rs.getString("cofinancing_project_title"));

        budgetList.add(budgetData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getBudgetsByYear() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getBudgetsByYear():budgetList.size={}", budgetList.size());
    return budgetList;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> budgetList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> budgetData = new HashMap<String, String>();
        budgetData.put("id", rs.getString("id"));
        budgetData.put("year", rs.getString("year"));
        budgetData.put("budget_type", rs.getString("budget_type"));
        budgetData.put("institution_id", rs.getString("institution_id"));
        budgetData.put("amount", rs.getString("amount"));

        budgetList.add(budgetData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():budgetList.size={}", budgetList.size());
    return budgetList;
  }

  @Override
  public int saveBudget(int projectID, Map<String, Object> budgetData) {
    LOG.debug(">> saveBudget(budgetData={})", budgetData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    int recordID = -1;

    if (budgetData.get("id") == null) {
      // Before adding the new record, let's check that there is a budget with the same year, type and institution. If
      // so, we should update it, otherwise add the new record.
      query.append("SELECT id FROM project_budgets pb");
      query.append(" WHERE pb.budget_type = ");
      query.append(budgetData.get("budget_type"));
      query.append(" AND pb.institution_id = ");
      query.append(budgetData.get("institution_id"));
      query.append(" AND pb.year = ");
      query.append(budgetData.get("year"));
      query.append(" AND pb.project_id = ");
      query.append(projectID);
      query.append(" AND pb.cofinance_project_id = ");

      if (budgetData.get("cofinance_project_id") != null) {
        query.append(budgetData.get("cofinance_project_id"));
      } else {
        query.append("NULL");
      }

      try (Connection con = databaseManager.getConnection()) {
        ResultSet rs = databaseManager.makeQuery(query.toString(), con);
        if (rs.next()) {
          recordID = rs.getInt(1);
        }
        con.close();
      } catch (SQLException e) {
        LOG.error("Exception arised trying to validate if a budget is duplicated", e.getMessage());
      }

      if (recordID != -1) {
        budgetData.put("id", recordID);
      } else {
        // Insert new budget record
        query.setLength(0);
        query.append("INSERT INTO project_budgets (project_id, year, budget_type, institution_id, amount, ");
        query.append("gender_percentage, cofinance_project_id, created_by, modified_by, modification_justification) ");
        query.append("VALUES (?,?,?,?,?,?,?,?,?,?)  ");
        values = new Object[10];
        values[0] = projectID;
        values[1] = budgetData.get("year");
        values[2] = budgetData.get("budget_type");
        values[3] = budgetData.get("institution_id");
        values[4] = budgetData.get("amount");
        values[5] = budgetData.get("gender_percentage");
        values[6] = budgetData.get("cofinance_project_id");
        values[7] = budgetData.get("user_id");
        values[8] = budgetData.get("user_id");
        values[9] = budgetData.get("justification");
        result = databaseManager.saveData(query.toString(), values);
      }
    }

    if (budgetData.get("id") != null) {
      // update budget record
      query.setLength(0);
      query.append("UPDATE project_budgets SET year = ?, budget_type = ?, institution_id = ?, amount = ?, ");
      query.append("gender_percentage = ?, modified_by = ?, modification_justification = ? WHERE id = ? ");
      values = new Object[8];
      values[0] = budgetData.get("year");
      values[1] = budgetData.get("budget_type");
      values[2] = budgetData.get("institution_id");
      values[3] = budgetData.get("amount");
      values[4] = budgetData.get("gender_percentage");
      values[5] = budgetData.get("user_id");
      values[6] = budgetData.get("justification");
      values[7] = budgetData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update a budget identified with the id = {}", budgetData.get("id"));
        return -1;
      }
    }
    LOG.debug("<< saveBudget():{}", result);
    return result;
  }


}
