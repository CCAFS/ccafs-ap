package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ProjectManagerImpl;
import org.cgiar.ccafs.ap.data.model.Employee;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectManagerImpl.class)
public interface ProjectManager {

  /**
   * This method gets all the Projects related to a specific IP program
   * given
   * 
   * @param object - TODO
   * @return a list with Projects.
   */
  public List<Project> getProject(Object object);

  /**
   * This method return the list of all CCAFS projects.
   * @return a list with all the Projects.
   */
  public List<Project> getAllProjects();


}
