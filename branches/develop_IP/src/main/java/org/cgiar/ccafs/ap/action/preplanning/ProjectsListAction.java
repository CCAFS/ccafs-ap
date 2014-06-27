package org.cgiar.ccafs.ap.action.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectsListAction extends BaseAction {

  class Project {

    int id;
    String name;

    public Project(int id, String name) {
      this.id = id;
      this.name = name;
    }
  }

  private static final long serialVersionUID = 6064621727280821945L;

  // Manager

  private static Logger LOG = LoggerFactory.getLogger(ProjectsListAction.class);

  // Model
  List<Project> projects;

  @Inject
  public ProjectsListAction(APConfig config, LogframeManager logframeManager) {
    super(config, logframeManager);
    // TODO Auto-generated constructor stub
  }


  public List<Project> getProjects() {
    return projects;
  }

  @Override
  public void prepare() throws Exception {
    projects = new ArrayList<>();
    Project project1 = new Project(1, "Project 1");
    Project project2 = new Project(2, "Project 2");
    Project project3 = new Project(3, "Project 3");
    Project project4 = new Project(4, "Project 4");

    projects.add(project1);
    projects.add(project2);
    projects.add(project3);
    projects.add(project4);
    super.prepare();
  }
}
