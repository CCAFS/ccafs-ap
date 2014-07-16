package org.cgiar.ccafs.ap.action.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Employee;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectsListAction extends BaseAction {


  private static final long serialVersionUID = 2845677913596494699L;

  // Manager
  private ProjectManager projectManager;

  private static Logger LOG = LoggerFactory.getLogger(ProjectsListAction.class);

  // Model
  List<Project> projects;

  @Inject
  public ProjectsListAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }


  public List<Project> getProjects() {
    return projects;
  }

  @Override
  public void prepare() throws Exception {
	super.prepare();
	
	// Depending on the user that is logged-in, the list of projects will be displayed.
	User fakeUser = new User();
	fakeUser.setId(100);
	fakeUser.setEmail("user@email.org");
	fakeUser.setRole("RPL");
    Employee projectLeader = new Employee(1, new User());
    
    
    // Getting project list.
    projects = projectManager.getAllProjects();
    //projects = projectManager.getProject(projectLeader);
    
    System.out.println(projects);
    
  }
}
