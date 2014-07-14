package org.cgiar.ccafs.ap.action.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Employees;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectsListAction extends BaseAction {


  /**
   * 
   */
  private static final long serialVersionUID = 2845677913596494699L;

  // Manager
  private ProjectManager projectManager;

  private static Logger LOG = LoggerFactory.getLogger(ProjectsListAction.class);

  // Model
  List<Project> projects;

  @Inject
  public ProjectsListAction(APConfig config) {
    super(config);
    this.projectManager = projectManager;
  }


  public List<Project> getProjects() {
    return projects;
  }

  @Override
  public void prepare() throws Exception {
    Employees projectLeader = new Employees(1);
    projects = projectManager.getProject(projectLeader);
    System.out.println(projects);
    super.prepare();
  }
}
