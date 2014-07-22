package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ProjectManagerImpl;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectManagerImpl.class)
public interface ProjectManager {

  /**
   * This method return the list of all CCAFS projects.
   * 
   * @return a list with all the Projects.
   */
  public List<Project> getAllProjects(Project projectId);

  /**
   * This method gets all the Project information given by a previous project selected
   * 
   * @param projectID
   * @return an Project Object.
   */
  public Project getProject(int projectId);


}
