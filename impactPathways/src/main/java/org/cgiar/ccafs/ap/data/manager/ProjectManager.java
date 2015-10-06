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
package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ProjectManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectManagerImpl.class)
public interface ProjectManager {

  /**
   * This method delete the relation between the project and the indicator
   * received.
   * 
   * @param projectID - project identifier
   * @param indicatorID - indicator identifier
   * @param user - User who is deleting the indicator
   * @param justification
   * @return true if the relation was successfully removed. False otherwise.
   */
  public boolean deleteIndicator(int projectID, int indicatorID, User user, String justification);

  /**
   * This method deletes the project identified by the value received by parameter.
   * This method also deletes all the items related to the project.
   * 
   * @param projectID is the project identifier.
   * @param user is the user that is making the action.
   * @param justification is the justification example.
   * @return True if the project was deleted, false otherwise.
   */
  public boolean deleteProject(int projectID, User user, String justification);

  /**
   * This method delete the relation between the project and the output
   * received.
   * 
   * @param projectID - project identifier
   * @param outputID - output identifier
   * @param outcomeID - identifier of the outcome to which the output belongs to.
   * @param userID - identifier of the user who is deleting the output
   * @param justification
   * @return true if the relation was successfully removed. False otherwise.
   */
  public boolean deleteProjectOutput(int projectID, int outputID, int outcomeID, int userID, String justification);

  /**
   * This method validate if the system has a project identified with the given parameter.
   * 
   * @param projectId is the project identifier to be verified.
   * @return true if the project exists in the system and false otherwise.
   */
  public boolean existProject(int projectId);

  /**
   * This method returns the list of all CCAFS projects
   * but only with the basic information:
   * ID, title, the regions and flagships to which the project contributes
   * and the total budget
   * 
   * @return a list with Project objects.
   */
  public List<Project> getAllProjectsBasicInfo();

  /**
   * This method returns the bilateral projects that contributes with the flagship and the regions received by
   * parameter.
   * Only the projects marked as financing are returned.
   * If the parameters are '-1' they are not used to filter the list.
   * 
   * @return a list of maps with the information.
   */
  public List<Project> getBilateralCofinancingProjects(int flagshipID, int regionID);

  /**
   * This method returns the list of all the bilateral projects.
   * 
   * @return a list of projects that only contains the id and title.
   */
  public List<Project> getBilateralProjects();

  /**
   * This method returns the core projects that contributes with the flagship and the regions received by parameter.
   * If the parameters are '-1' they are not used to filter the list.
   * 
   * @return a list of projects that only contains the id and title.
   */
  public List<Project> getCoreProjects(int flagshipID, int regionID);

  /**
   * This method returns the list of project identifiers where the given user is assigned as Project Leader.
   * 
   * @param user is the user object.
   * @return a List of project identifiers (Integer numbers).
   */
  public List<Integer> getPLProjectIds(User user);

  /**
   * This method gets all the Project information given by a previous project selected
   * 
   * @param projectID is a project identifier.
   * @return a Project Object with the information requested.
   */
  public Project getProject(int projectID);

  /**
   * This method returns the basic information of the project identified
   * by the value received as parameter
   * ID, title, the regions and flagships to which the project contributes
   * and the total budget
   * 
   * @return a list with Project objects.
   */
  public Project getProjectBasicInfo(int projectID);

  /**
   * This method returns the Project in which the activity belongs to.
   * 
   * @param activityID is the Activity identifier.
   * @return a Project object.
   */
  public Project getProjectFromActivityId(int activityID);

  /**
   * Get the project where the given deliverable id belongs to.
   * 
   * @param deliverableID is a deliverable id.
   * @return a Project object representing the project.
   */
  public Project getProjectFromDeliverableId(int deliverableID);

  /**
   * This method gets the Project where the project partner identifier belongs to.
   * 
   * @param projectPartnerID is a project partner identifier (PL, PC, PPA or PP).
   * @return a Project object with the information requested.
   */
  public Project getProjectFromProjectPartnerID(int projectPartnerID);

  /**
   * This method returns the list of project identifiers that the given user is able to edit.
   * 
   * @param userID is the user identifier.
   * @return a List of project identifiers (Integer numbers).
   */
  public List<Integer> getProjectIdsEditables(int userID);

  /**
   * This method returns a list of projects that belongs to an institution id given as parameter.
   * 
   * @param institutionID
   * @return a list of Projects.
   */
  public List<Project> getProjectsByInstitution(int institutionID);

  /**
   * This method returns the list of all CCAFS projects that belongs to a specific program.
   * 
   * @return a list with Project objects.
   */
  public List<Project> getProjectsByProgram(int programId);

  /**
   * This method returns a list of projects identified by the values received by parameter.
   * 
   * @param values
   * @return a list of Project objects.
   */
  public List<Project> getProjectsList(String[] values);


  /**
   * This method create or updates a project into the database.
   * 
   * @param project is the Project object to be saved.
   * @param user - The user who is creating/updating the project
   * @param justification - Why the user made the change
   * @return A number representing the new Id assigned to the new project, 0 if the project was updated or -1 if some
   *         error happened.
   */
  public int saveProjectDescription(Project project, User user, String justification);

  /**
   * This method save into the database the relation between a project and
   * the outputs
   * 
   * @param outputs - A list of ipElmenet objects
   * @param projectID - project identifier
   * @param user - the user who is making the change
   * @param justification
   * @return true if ALL the relations were saved successfully. False otherwise.
   */
  public boolean saveProjectOutputs(List<IPElement> outputs, int projectID, User user, String justification);

  /**
   * This method will return the list of all the activities with their corresponding projects
   * This method also gets all the information in one single query to the database, improving its performance and
   * letting the charge to the RAM memory.
   * This method should be used to the summary Gender Report.
   * 
   * @return a list of columns with the information summary
   */
  public List<Map<String, Object>> summaryGetAllActivitiesWithGenderContribution();

  /**
   * This method will return the list of all the deliverables with your next users.
   * This method also gets all the information in one single query to the database, improving its performance and
   * letting the charge to the RAM memory.
   * This method should be used to the summary Gender Report.
   * 
   * @return a list of columns with the information summary
   */
  public List<Map<String, Object>> summaryGetAllDeliverablesWithGenderContribution();

  /**
   * This method will return the list of all the projects and their respective leaders.
   * This method also gets all the information in one single query to the database, improving its performance and
   * letting the charge to the RAM memory.
   * This method should be used to the summary report project partner leaders.
   * 
   * @return a list of Project objects with the Project Partner Leader information.
   */
  public List<Map<String, Object>> summaryGetAllProjectPartnerLeaders();


  /**
   * This method will return the list of all the projects and all their respective deliverables.
   * This method also gets all the information in one single query to the database, improving its performance and
   * letting the charge to the RAM memory.
   * This method should be used to the summary report expected deliverables.
   * 
   * @return a list of Project objects with the Deliverable information.
   */
  public List<Map<String, Object>> summaryGetAllProjectsWithDeliverables();

  /**
   * This method will return the list of all the projects and their respective gender contribution.
   * This method also gets all the information in one single query to the database, improving its performance and
   * letting the charge to the RAM memory.
   * This method should be used to the summary report project gender contribution.
   * 
   * @return a list of Project objects with the gender contribution information.
   */
  public List<Map<String, Object>> summaryGetAllProjectsWithGenderContribution();

  /**
   * @param year - Specific year for to search in the report.
   *        This method will return the list of all the MOG of a specific year with your budgets.
   *        This method also gets all the information in one single query to the database, improving its performance and
   *        letting the charge to the RAM memory.
   *        This method should be used to the summary POWBMOGs Report.
   * @return a list of columns with the information summary
   */
  public List<Map<String, Object>> summaryGetInformationBudgetByPartners(int year);


  /**
   * @param year - Specific year for to search in the report.
   *        This method will return the list of all the MOG of a specific year with your budgets.
   *        This method also gets all the information in one single query to the database, improving its performance and
   *        letting the charge to the RAM memory.
   *        This method should be used to the summary POWBMOGs Report.
   * @return a list of columns with the information summary
   */
  public List<Map<String, Object>> summaryGetInformationPOWB(int year);


  /**
   * @param year - Specific year for to search in the report.
   *        This method will return the list of all the MOG of a specific year with your budgets.
   *        This method also gets all the information in one single query to the database, improving its performance and
   *        letting the charge to the RAM memory.
   *        This method should be used to the summary POWBMOGs Report.
   * @return a list of columns with the information summary
   */
  public List<Map<String, Object>> summaryGetInformationPOWBDetail(int year);


  /**
   * This method updates the project type into the database according to the values contained in the project received by
   * parameter.
   * 
   * @param project - Project object to update the type
   * @return true if the type was updated successfully. False otherwise.
   */
  public boolean updateProjectType(Project project);

  /**
   * This method updates the type of all the core projects following the steps below:
   * 1- Set the projects co-founded as core
   * 2- Check all the "core" projects that have at least one link with a bilateral project and update its type to
   * co-founded
   * 
   * @return true if the changes was made succesfully. False otherwise.
   */
  public boolean updateProjectTypes();
}
