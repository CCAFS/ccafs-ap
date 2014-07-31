package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectOutcomeDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLProjectOutcomeDAO.class)
public interface ProjectOutcomeDAO {


  /**
   * Deletes the information of a Project Outcome associated by a given id
   * 
   * @param projectOutcomeID - is the id of a Project Outcome
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteProjectOutcomeById(int projectOutcomeID);

  /**
   * Deletes the information of the Project Outcomes related by a given project id
   * 
   * @param projectID
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteProjectOutcomesByProject(int projectID);

  /**
   * This method gets all the Project Outcome Information by a given project ID
   * 
   * @param projectID - is the id of the project
   * @return a list of Map of the Budgets related with the budget type id and the project id
   */
  public List<Map<String, String>> getProjectOutcomesByProject(int projectID);

  /**
   * This method gets all the Project Outcome information by a given project Id and a year
   * 
   * @param projectID - is the id of the project
   * @param year - is the year of the budget
   * @return a list of Map of the Project Outcome related with the year and the project id
   */
  public List<Map<String, String>> getProjectOutcomesByYear(int projectID, int year);

  /**
   * This method saves the Project Outcome
   * 
   * @param projectOutcomeData
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveProjectOutcome(int projectID, Map<String, Object> projectOutcomeData);


}
