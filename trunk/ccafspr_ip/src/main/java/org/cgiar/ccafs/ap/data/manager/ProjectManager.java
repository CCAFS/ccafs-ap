package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ProjectManagerImpl;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectManagerImpl.class)
public interface ProjectManager {

  /**
   * This method return the list of all CCAFS projects that belongs to a specific program.
   * 
   * @return a list with Project objects.
   */
  public List<Project> getAllProjects(int programId);

  /**
   * This method finds the Expected Project Leader user from a specific Project.
   * 
   * @param projectId is the project id.
   * @return a User object who represents an expected Project Leader. Or NULL if no user was found.
   */
  public User getExpectedProjectLeader(int projectId);

  /**
   * This method gets all the Project information given by a previous project selected
   * 
   * @param projectID
   * @return an Project Object.
   */
  public Project getProject(int projectId);

  /**
   * This method gets all the Projects related with a specific program determined by a given typeID
   * 
   * @param projectID - is the ID of the project
   * @param typeID - is the typeID of the program (FPL or RPL)
   * @return a List of Project with information requested or if doesn't have records an empty list
   */
  public List<Project> getProjectFocuses(int projectID, int typeID);

  /**
   * This method finds the Project Leader user from a specific Project.
   * 
   * @param projectId is the project id.
   * @return a User object who represents a Project Leader. Or NULL if no user was found.
   */
  public User getProjectLeader(int projectId);

  /**
   * This method saves or update an expected project leader possibly added in Pre-Planning step.
   * This expected project leader must belongs to a specific project.
   * 
   * @param projectId is the project identifier.
   * @param expectedLeader is the project leader to be added/updated.
   * @return true if the save process finalized successfully, false otherwise.
   */
  public boolean saveExpectedProjectLeader(int projectId, User expectedLeader);


}
