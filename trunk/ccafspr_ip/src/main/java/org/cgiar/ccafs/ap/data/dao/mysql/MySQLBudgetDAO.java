package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.BudgetDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.model.BudgetType;

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

public class MySQLBudgetDAO implements BudgetDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLBudgetDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLBudgetDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
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
      query.setLength(0);
      query.append("DELETE");
      return true;
    }
    LOG.debug("<< deleteBudgetsByInstitution():{}", false);
    return false;
  }


  @Override
  public List<Map<String, String>> getBudgetsByType(int projectID, int budgetType) {
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
    query.append(budgetType);


    LOG.debug("-- getBudgetsByType() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getBudgetsByYear(int projectID, int year) {
    LOG.debug(">> getBudgetsByYear projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT b.*   ");
    query.append("FROM budgets as b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("INNER JOIN institutions i ON b.institution_id = i.id ");
    query.append("WHERE pb.project_id=  ");
    query.append(projectID);
    query.append(" AND b.year=  ");
    query.append(year);


    LOG.debug("-- getBudgetsByYear() > Calling method executeQuery to get the results");
    return getData(query.toString());
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
    LOG.debug("<< executeQuery():projectPartnerList.size={}", budgetList.size());
    return budgetList;
  }

  @Override
  public List<Map<String, String>> getLeveragedInstitutions(int projectID) {
    LOG.debug(">> getLeveragedInstitutions projectID = {} )", projectID);
    BudgetType budgetType = BudgetType.LEVERAGED;
    List<Map<String, String>> leveragedInstitutionDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT  i.*   ");
    query.append("FROM budgets as b ");
    query.append("INNER JOIN project_budgets pb ON b.id = pb.budget_id ");
    query.append("INNER JOIN budget_types bt ON b.budget_type = bt.id ");
    query.append("INNER JOIN institutions i ON b.institution_id = i.id ");
    query.append("WHERE pb.project_id=  ");
    query.append(projectID);
    query.append(" AND bt.id= ");
    query.append(budgetType.getValue());

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
      LOG.error("Exception arised getting the institutions for the user {}.", projectID, e);
    }
    return leveragedInstitutionDataList;
  }

  @Override
  public int saveBudget(int projectID, Map<String, Object> budgetData) {
    LOG.debug(">> saveBudget(budgetData={})", budgetData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    int newId = -1;
    Object[] values;
    if (budgetData.get("id") == null) {
      // Insert new budget record
      query.append("INSERT INTO budgets (year, budget_type, institution_id, amount) ");
      query.append("VALUES (?,?,?,?) ");
      values = new Object[4];
      values[0] = budgetData.get("year");
      values[1] = budgetData.get("budget_type");
      values[2] = budgetData.get("institution_id");
      values[3] = budgetData.get("amount");
      newId = databaseManager.saveData(query.toString(), values);
      if (newId <= 0) {
        LOG.error("A problem happened trying to add a new expected project leader in project with id={}", projectID);
        return -1;
      } else {
        // Now, Addition the relation with project into table project_budgets
        query.setLength(0); // Clearing query.
        query.append("INSERT INTO project_budgets (project_id,budget_id) ");
        query.append("VALUES (?,?) ");
        values = new Object[2];
        values[0] = projectID;
        values[1] = newId;
        result = databaseManager.saveData(query.toString(), values);
      }
    } else {
      // update budget record
      query.append("UPDATE budgets SET year = ?, budget_type = ?, institution_id = ?, amount = ? ");
      query.append("WHERE id = ? ");
      values = new Object[5];
      values[0] = budgetData.get("year");
      values[1] = budgetData.get("budget_type");
      values[2] = budgetData.get("institution_id");
      values[3] = budgetData.get("amount");
      values[4] = budgetData.get("id");
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
