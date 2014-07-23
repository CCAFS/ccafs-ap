package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ProjectManagerImpl;
import org.cgiar.ccafs.ap.data.model.Project;

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
   * This method gets all the Project information given by a previous project selected
   *
   * @param projectID
   * @return an Project Object.
   */
  public Project getProject(int projectId);


}
