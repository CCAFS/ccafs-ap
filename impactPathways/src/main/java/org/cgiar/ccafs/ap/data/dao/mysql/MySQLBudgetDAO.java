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
  public double calculateActivityBudgetByType(int activityID, int budgetTypeID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as total ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN activity_budgets ab ON b.id = ab.budget_id ");
    query.append("WHERE ab.activity_id = " + activityID);
    query.append(" AND  b.budget_type = " + budgetTypeID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting activity budget amount for activity {} in the year {}.", activityID,
        budgetTypeID);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateActivityBudgetByTypeAndYear(int activityID, int budgetTypeID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as total ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN activity_budgets ab ON b.id = ab.budget_id ");
    query.append("WHERE ab.activity_id = " + activityID);
    query.append(" AND  b.budget_type = " + budgetTypeID);
    query.append(" AND  b.year = " + year);


    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting activity budget amount for activity {} in the year {}.", activityID,
        budgetTypeID);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateProjectBudgetByTypeAndYear(int projectID, int budgetTypeID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as total ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append("WHERE pb.project_id = " + projectID);
    query.append(" AND  b.budget_type = " + budgetTypeID);
    query.append(" AND  b.year = " + year);


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
  @Deprecated
  public double calculateProjectLeveragedBudgetByYear(int projectID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as total ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append(" WHERE pb.project_id = ");
    query.append(projectID);

    // TODO - This method is not valid anymore since we have not leveraged budgets anymore.
    // query.append(" AND b.budget_type = ");
    // query.append(BudgetType.LEVERAGED.getValue());
    // query.append(" AND b.year = ");
    // query.append(year);

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
  public double calculateProjectTotalLeveragedBudget(int projectID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as total ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append(" WHERE pb.project_id = ");
    query.append(projectID);
    // TODO - This method is not valid anymore since we have not leveraged budgets anymore.
    // query.append(" AND b.budget_type = ");
    // query.append(BudgetType.LEVERAGED.getValue());

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
  public double calculateProjectW1W2W3BilateralBudget(int projectID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as total ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append(" WHERE pb.project_id = ");
    query.append(projectID);
    query.append(" AND ( b.budget_type = ");
    query.append(BudgetType.W1_W2.getValue());
    query.append(" OR b.budget_type = ");
    query.append(BudgetType.W3_BILATERAL.getValue());
    query.append(" ) ");


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
  public double calculateProjectW1W2W3BilateralBudgetByYear(int projectID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as total ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append(" WHERE pb.project_id = ");
    query.append(projectID);
    query.append(" AND ( b.budget_type = ");
    query.append(BudgetType.W1_W2.getValue());
    query.append(" OR b.budget_type = ");
    query.append(BudgetType.W3_BILATERAL.getValue());
    query.append(" ) ");
    query.append(" AND b.year = ");
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
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateTotalActivityBudget(int activityID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as TOTAL ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN activity_budgets ab ON b.id = ab.budget_id ");
    query.append(" WHERE ab.activity_id = ");
    query.append(activityID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getString("total") != null) {
          total = Double.parseDouble(rs.getString("total"));
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", activityID, e);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateTotalActivityBudgetByYear(int activityID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as TOTAL ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN activity_budgets ab ON b.id = ab.budget_id ");
    query.append(" WHERE ab.activity_id = ");
    query.append(activityID);
    query.append(" AND b.year =  ");
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
      LOG.error("Exception arised getting the institutions for the user {}.", activityID, e);
      total = -1.0;
    }
    return total;
  }

  @Override
  public double calculateTotalCCAFSBudget(int projectID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as TOTAL ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append(" WHERE pb.project_id = ");
    query.append(projectID);

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
  public double calculateTotalCCAFSBudgetByYear(int projectID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as TOTAL ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append(" WHERE pb.project_id = ");
    query.append(projectID);
    query.append(" AND b.year =  ");
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
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }


  @Override
  public double calculateTotalOverallBudget(int projectID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as TOTAL ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append(" WHERE pb.project_id = ");
    query.append(projectID);

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
  public double calculateTotalOverallBudgetByYear(int projectID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as TOTAL ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append(" WHERE pb.project_id = ");
    query.append(projectID);
    query.append(" AND b.year =  ");
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
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
      total = -1.0;
    }
    return total;
  }


  @Override
  public double calculateTotalProjectW1W2(int projectID) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as TOTAL ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON pb.budget_id = b.id ");
    query.append("WHERE pb.project_id = ");
    query.append(projectID);
    query.append(" AND b.budget_type = ");
    query.append(BudgetType.W1_W2.getValue());

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
  public double calculateTotalProjectW1W2ByYear(int projectID, int year) {
    Double total = 0.0;
    StringBuilder query = new StringBuilder();
    query.append("SELECT SUM(b.amount) as TOTAL ");
    query.append("FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON pb.budget_id = b.id ");
    query.append("WHERE pb.project_id = ");
    query.append(projectID);
    query.append(" AND b.budget_type = ");
    query.append(BudgetType.W1_W2.getValue());
    query.append(" AND b.year = ");
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
  public boolean deleteActivityBudgetByYear(int activityID, int year) {
    LOG.debug(">> deleteActivityBudgetByYear(activityID={}, year={})", activityID, year);

    StringBuilder query = new StringBuilder();
    query.append("DELETE b FROM budgets b ");
    query.append("INNER JOIN activity_budgets ab ON b.id = ab.budget_id ");
    query.append("WHERE ab.activity_id = ? AND b.year = ?");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {activityID, year});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteActivityBudgetByYear():{}", true);
      return true;
    }
    LOG.debug("<< deleteActivityBudgetByYear():{}", false);
    return false;
  }


  @Override
  public boolean deleteActivityBudgetsByActivityID(int activityID) {
    LOG.debug(">> deleteActivityBudgetsByActivityID(activityID={})", activityID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE b FROM budgets b ");
    query.append("INNER JOIN activity_budgets ab ON b.id = ab.budget_id ");
    query.append("WHERE ab.activity_id = ? ");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {activityID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteActivityBudgetsByActivityID():{}", true);
      return true;
    }
    LOG.debug("<< deleteActivityBudgetsByActivityID():{}", false);
    return false;
  }


  @Override
  public boolean deleteActivityBudgetsByInstitution(int activityID, int institutionID) {
    LOG.debug(">> deleteActivityBudgetsByInstitution(activityID={}, institutionId={})", activityID, institutionID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE b FROM budgets b ");
    query.append("INNER JOIN activity_budgets ab ON b.id = ab.budget_id ");
    query.append("WHERE ab.activity_id = ? AND b.institution_id = ?");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {activityID, institutionID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteActivityBudgetsByInstitution():{}", true);
      return true;
    }
    LOG.debug("<< deleteActivityBudgetsByInstitution():{}", false);
    return false;
  }


  @Override
  public boolean deleteBudget(int budgetId) {
    LOG.debug(">> deleteBudget(id={})", budgetId);

    String query = "DELETE FROM budgets WHERE id= ?";

    int rowsDeleted = databaseManager.delete(query, new Object[] {budgetId});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteBudget():{}", true);
      return true;
    }

    LOG.debug("<< deleteBudget:{}", false);
    return false;
  }


  @Override
  public boolean deleteBudgetsByInstitution(int projectID, int institutionID) {
    LOG.debug(">> deleteBudgetsByInstitution(projectId={}, institutionId={})", projectID, institutionID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE b FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append("WHERE pb.project_id = ? AND b.institution_id = ?");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {projectID, institutionID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteBudgetsByInstitution():{}", true);
      return true;
    }
    LOG.debug("<< deleteBudgetsByInstitution():{}", false);
    return false;
  }


  @Override
  public boolean deleteBudgetsByYear(int projectID, int year) {
    LOG.debug(">> deleteBudgetsByYear(projectId={}, eyar={})", projectID, year);

    StringBuilder query = new StringBuilder();
    query.append("DELETE b FROM budgets b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append("WHERE pb.project_id = ? AND b.year = ?");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {projectID, year});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteBudgetsByYear():{}", true);
      return true;
    }
    LOG.debug("<< deleteBudgetsByYear():{}", false);
    return false;
  }


  @Override
  public List<Map<String, String>> getActivityBudgetsByType(int activityID, int BudgetTypeNew) {
    LOG.debug(">> getActivityBudgetsByType activityID = {} )", activityID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT b.*   ");
    query.append("FROM budgets as b ");
    query.append("INNER JOIN activity_budgets ab ON b.id = ab.budget_id ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("INNER JOIN institutions i ON b.institution_id = i.id ");
    query.append("WHERE ab.activity_id=  ");
    query.append(activityID);
    query.append(" AND b.budget_type=  ");
    query.append(BudgetTypeNew);


    LOG.debug("-- getActivityBudgetsByType() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }


  @Override
  public List<Map<String, String>> getActivityBudgetsByYear(int activityID, int year) {
    LOG.debug(">> getBudgetsByYear projectID = {}, year={} )", new Object[] {activityID, year});

    StringBuilder query = new StringBuilder();
    query.append("SELECT b.*   ");
    query.append("FROM budgets as b ");
    query.append("INNER JOIN activity_budgets ab ON b.id = ab.budget_id ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("INNER JOIN institutions i ON b.institution_id = i.id ");
    query.append("WHERE ab.activity_id=  ");
    query.append(activityID);
    query.append(" AND b.year=  ");
    query.append(year);


    LOG.debug("-- getBudgetsByYear() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }


  @Override
  @Deprecated
  public List<Map<String, String>> getActivityInstitutions(int activityID) {
    LOG.debug(">> getActivityInstitutions activityID = {} )", activityID);
    List<Map<String, String>> institutionDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT DISTINCT i.*   ");
    query.append("FROM institutions as i ");
    query.append("INNER JOIN budgets b ON b.institution_id = i.id ");
    query.append("INNER JOIN activity_budgets pb ON b.id = pb.budget_id ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("WHERE pb.activity_id = ");
    query.append(activityID);

    // TODO - This method is not valid anymore since we have not activity budgets.
    // query.append(" AND (b.budget_type = ");
    // query.append(BudgetType.ACTIVITY_W1_W2.getValue());
    // query.append(" OR b.budget_type = ");
    // query.append(BudgetType.ACTIVITY_W3_BILATERAL.getValue());
    // query.append(" )");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> institutionData = new HashMap<String, String>();
        institutionData.put("id", rs.getString("id"));
        institutionData.put("name", rs.getString("name"));
        institutionData.put("acronym", rs.getString("acronym"));
        institutionData.put("contact_person_name", rs.getString("contact_person_name"));
        institutionData.put("contact_person_name", rs.getString("contact_person_name"));
        institutionData.put("city", rs.getString("city"));
        institutionData.put("website_link", rs.getString("website_link"));
        institutionData.put("program_id", rs.getString("program_id"));
        institutionData.put("institution_type_id", rs.getString("institution_type_id"));
        institutionData.put("country_id", rs.getString("country_id"));

        institutionDataList.add(institutionData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the activity budget institutions for the activity {}.", activityID,
        e.getMessage());
    }
    return institutionDataList;
  }

  @Override
  public List<Map<String, String>> getBudgetsByProject(int projectID) {
    LOG.debug(">> getBudgetsByProject projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT b.*   ");
    query.append("FROM budgets as b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("INNER JOIN institutions i ON b.institution_id = i.id ");
    query.append("WHERE pb.project_id=  ");
    query.append(projectID);


    LOG.debug("-- getBudgetsByProject() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }


  @Override
  public List<Map<String, String>> getBudgetsByType(int projectID, int BudgetTypeNew) {
    LOG.debug(">> getBudgetsByType projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT b.*   ");
    query.append("FROM budgets as b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("INNER JOIN institutions i ON b.institution_id = i.id ");
    query.append("WHERE pb.project_id=  ");
    query.append(projectID);
    query.append(" AND b.budget_type=  ");
    query.append(BudgetTypeNew);


    LOG.debug("-- getBudgetsByType() > Calling method executeQuery to get the results");
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
    query.append("WHERE pb.project_id=  ");
    query.append(projectID);
    query.append(" AND pb.year=  ");
    query.append(year);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> budgetData = new HashMap<String, String>();
        budgetData.put("id", rs.getString("id"));
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

  @Override
  public List<Map<String, String>> getCCAFSBudgets(int projectID) {
    LOG.debug(">> getCCAFSBudgets projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT b.*   ");
    query.append("FROM budgets as b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("INNER JOIN institutions i ON b.institution_id = i.id ");
    query.append("WHERE pb.project_id=  ");
    query.append(projectID);

    LOG.debug("-- getCCAFSBudgets() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
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
  public List<Map<String, String>> getW1Institutions(int projectID) {
    LOG.debug(">> getW1Institutions projectID = {} )", projectID);
    List<Map<String, String>> leveragedInstitutionDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT DISTINCT i.*   ");
    query.append("FROM institutions as i ");
    query.append("INNER JOIN budgets b ON b.institution_id = i.id ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("WHERE pb.project_id = ");
    query.append(projectID);
    query.append(" AND b.budget_type = ");
    query.append(BudgetType.W1_W2.getValue());

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> leveragedInstitutionData = new HashMap<String, String>();
        leveragedInstitutionData.put("id", rs.getString("id"));
        leveragedInstitutionData.put("name", rs.getString("name"));
        leveragedInstitutionData.put("acronym", rs.getString("acronym"));
        leveragedInstitutionData.put("contact_person_name", rs.getString("contact_person_name"));
        leveragedInstitutionData.put("contact_person_name", rs.getString("contact_person_name"));
        leveragedInstitutionData.put("city", rs.getString("city"));
        leveragedInstitutionData.put("website_link", rs.getString("website_link"));
        leveragedInstitutionData.put("program_id", rs.getString("program_id"));
        leveragedInstitutionData.put("institution_type_id", rs.getString("institution_type_id"));
        leveragedInstitutionData.put("country_id", rs.getString("country_id"));

        leveragedInstitutionDataList.add(leveragedInstitutionData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the WI institutions for the project {}.", projectID, e.getNextException());
    }
    return leveragedInstitutionDataList;
  }

  @Override
  public int saveActivityBudget(int activityID, Map<String, Object> activityBudgetData) {
    LOG.debug(">> saveActivityBudget(activityBudgetData={})", activityBudgetData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    int newId = -1;
    Object[] values;
    int recordExists = 0;

    if (activityBudgetData.get("id") == null) {
      // Before adding the new record, let's check that there is a budget with the same year, type and institution. If
      // so, we should update it, otherwise add the new record.
      query.append("SELECT count(*) FROM budgets b");
      query.append(" INNER JOIN activity_budgets ab ON ab.budget_id = b.id");
      query.append(" WHERE b.budget_type = ");
      query.append(activityBudgetData.get("budget_type"));
      query.append(" AND b.institution_id = ");
      query.append(activityBudgetData.get("institution_id"));
      query.append(" AND b.year = ");
      query.append(activityBudgetData.get("year"));
      query.append(" AND ab.activity_id = ");
      query.append(activityID);

      try (Connection con = databaseManager.getConnection()) {
        ResultSet rs = databaseManager.makeQuery(query.toString(), con);
        if (rs.next()) {
          recordExists = rs.getInt(1);
        }
        con.close();
      } catch (SQLException e) {
        LOG.error("Exception arised trying to validate if a budget is duplicated", e.getMessage());
      }

      // If the record already exists, then we have to find the budget id.
      if (recordExists > 0) {
        query.setLength(0);
        query.append("SELECT b.id from budgets b");
        query.append(" INNER JOIN activity_budgets ab ON ab.budget_id = b.id ");
        query.append(" WHERE b.budget_type = ");
        query.append(activityBudgetData.get("budget_type"));
        query.append(" AND b.institution_id = ");
        query.append(activityBudgetData.get("institution_id"));
        query.append(" AND b.year = ");
        query.append(activityBudgetData.get("year"));
        query.append(" AND ab.activity_id = ");
        query.append(activityID);

        try (Connection con = databaseManager.getConnection()) {
          ResultSet rs = databaseManager.makeQuery(query.toString(), con);
          if (rs.next()) {
            activityBudgetData.put("id", rs.getInt(1));
          }
          con.close();
        } catch (SQLException e) {
          LOG.error("Exception arised trying to add the id of the budget", e.getMessage());
        }
      } else {
        // Insert new budget record
        query.setLength(0);
        query.append("INSERT INTO budgets (year, budget_type, institution_id, amount) ");
        query.append("VALUES (?,?,?,?) ");
        values = new Object[4];
        values[0] = activityBudgetData.get("year");
        values[1] = activityBudgetData.get("budget_type");
        values[2] = activityBudgetData.get("institution_id");
        values[3] = activityBudgetData.get("amount");
        newId = databaseManager.saveData(query.toString(), values);
        if (newId <= 0) {
          LOG.error("A problem happened trying to add a new budget with id={}", activityID);
          return -1;
        } else {
          // Now, Addition the relation with activity into table activity_budgets
          query.setLength(0); // Clearing query.
          query.append("INSERT INTO activity_budgets (activity_id, budget_id) ");
          query.append("VALUES (?,?) ");
          values = new Object[2];
          values[0] = activityID;
          values[1] = newId;
          result = databaseManager.saveData(query.toString(), values);
        }
      }
    }

    if (activityBudgetData.get("id") != null) {
      // update budget record
      query.setLength(0);
      query.append("UPDATE budgets SET year = ?, budget_type = ?, institution_id = ?, amount = ? ");
      query.append("WHERE id = ? ");
      values = new Object[5];
      values[0] = activityBudgetData.get("year");
      values[1] = activityBudgetData.get("budget_type");
      values[2] = activityBudgetData.get("institution_id");
      values[3] = activityBudgetData.get("amount");
      values[4] = activityBudgetData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update a budget identified with the id = {}",
          activityBudgetData.get("id"));
        return -1;
      }
    }
    LOG.debug("<< saveActivityBudget():{}", result);
    return result;
  }

  @Override
  public int saveBudget(int projectID, Map<String, Object> budgetData) {
    LOG.debug(">> saveBudget(budgetData={})", budgetData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    int newId = -1;
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
        query.append("INSERT INTO project_budgets (year, budget_type, institution_id, amount, gender_percentage, ");
        query.append("created_by, modified_by, modification_justification) VALUES (?,?,?,?,?,?,?,?) ");
        values = new Object[8];
        values[0] = budgetData.get("year");
        values[1] = budgetData.get("budget_type");
        values[2] = budgetData.get("institution_id");
        values[3] = budgetData.get("amount");
        values[4] = budgetData.get("gender_percentage");
        values[5] = budgetData.get("user_id");
        values[6] = budgetData.get("user_id");
        values[7] = budgetData.get("justification");
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
