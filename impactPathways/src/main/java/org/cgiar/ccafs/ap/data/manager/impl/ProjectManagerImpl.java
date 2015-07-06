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
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.ProjectDAO;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal.
 * @author Javier Andrés Gallego.
 */
public class ProjectManagerImpl implements ProjectManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectManagerImpl.class);

  // DAOs
  private ProjectDAO projectDAO;
  // Managers
  private UserManager userManager;
  private InstitutionManager institutionManager;
  private IPProgramManager ipProgramManager;
  private BudgetManager budgetManager;
  private LiaisonInstitutionManager liaisonInstitutionManager;


  @Inject
  public ProjectManagerImpl(ProjectDAO projectDAO, UserManager userManager, InstitutionManager institutionManager,
    IPProgramManager ipProgramManager, BudgetManager budgetManager, LiaisonInstitutionManager liaisonInstitutionManager) {
    this.projectDAO = projectDAO;
    this.userManager = userManager;
    this.institutionManager = institutionManager;
    this.ipProgramManager = ipProgramManager;
    this.budgetManager = budgetManager;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
  }

  @Override
  public boolean deleteIndicator(int projectID, int indicatorID, User user, String justification) {
    return projectDAO.deleteProjectIndicator(projectID, indicatorID, user.getId(), justification);
  }


  @Override
  public boolean deleteProject(int projectID) {
    return projectDAO.deleteProject(projectID);
  }

  @Override
  public boolean deleteProjectOutput(int projectID, int outputID, int outcomeID, int userID, String justification) {
    return projectDAO.deleteProjectOutput(projectID, outputID, outcomeID, userID, justification);
  }


  @Override
  public boolean existProject(int projectId) {
    return projectDAO.existProject(projectId);
  }

  @Override
  public List<Project> getAllProjectsBasicInfo() {
    List<Map<String, String>> projectDataList = projectDAO.getAllProjectsBasicInfo();
    List<Project> projectsList = new ArrayList<>();

    for (Map<String, String> projectData : projectDataList) {

      Project project = new Project(Integer.parseInt(projectData.get("id")));
      project.setTitle(projectData.get("title"));
      project.setType(projectData.get("type"));

      if (projectData.get("total_budget_amount") != null) {
        Budget totalBudget = new Budget();
        totalBudget.setAmount(Double.parseDouble(projectData.get("total_budget_amount")));
        List<Budget> budgets = new ArrayList<>(1);
        budgets.add(totalBudget);
        project.setBudgets(budgets);
      }

      project.setCreated(Long.parseLong(projectData.get("created")));

      // Getting Project Focuses - Regions
      if (projectData.get("regions") != null) {
        String[] regionsAcronyms = projectData.get("regions").split(",");
        List<IPProgram> regions = new ArrayList<>();
        IPProgram region;
        for (String regionAcronym : regionsAcronyms) {
          region = new IPProgram();
          region.setAcronym(regionAcronym);
          regions.add(region);
        }
        project.setRegions(regions);
      }


      // Getting Project Focuses - Flagships
      if (projectData.get("flagships") != null) {
        String[] flagshipsAcronyms = projectData.get("flagships").split(",");
        List<IPProgram> flagships = new ArrayList<>();
        IPProgram flagship;
        for (String flagshipAcronym : flagshipsAcronyms) {
          flagship = new IPProgram();
          flagship.setAcronym(flagshipAcronym);
          flagships.add(flagship);
        }
        project.setFlagships(flagships);
      }


      // Adding project to the list
      projectsList.add(project);
    }
    return projectsList;
  }

  @Override
  public List<Project> getCoreProjects(int flagshipID, int regionID) {
    List<Project> projects = new ArrayList<>();
    List<Map<String, String>> projectsData = projectDAO.getCoreProjects(flagshipID, regionID);

    for (Map<String, String> projectData : projectsData) {
      Project project = new Project();
      project.setId(Integer.parseInt(projectData.get("id")));
      project.setTitle(projectData.get("title"));

      projects.add(project);
    }

    return projects;
  }

  @Override
  public List<Integer> getPLProjectIds(User user) {
    return projectDAO.getPLProjectIds(user.getId());
  }

  @Override
  public Project getProject(int projectId) {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    Map<String, String> projectData = projectDAO.getProject(projectId);
    if (!projectData.isEmpty()) {
      Project project = new Project(Integer.parseInt(projectData.get("id")));
      project.setTitle(projectData.get("title"));
      project.setType(projectData.get("type"));
      project.setSummary(projectData.get("summary"));
      project.setWorkplanRequired(projectData.get("requires_workplan_upload").equals("1"));
      // Format to the Dates of the project
      if (projectData.get("start_date") != null) {
        try {
          Date startDate = dateformatter.parse(projectData.get("start_date"));
          project.setStartDate(startDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the date", e);
        }
      }
      if (projectData.get("end_date") != null) {
        try {
          Date endDate = dateformatter.parse(projectData.get("end_date"));
          project.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the date", e);
        }
      }

      // Getting the project Owner.
      project.setOwner(userManager.getUser(Integer.parseInt(projectData.get("liaison_user_id"))));
      // Getting the creation date timestamp.
      project.setCreated(Long.parseLong(projectData.get("created")));
      // Getting the Program creator
      if (projectData.get("liaison_institution_id") != null) {
        int institutionID = Integer.parseInt(projectData.get("liaison_institution_id"));
        project.setLiaisonInstitution(liaisonInstitutionManager.getLiaisonInstitution(institutionID));
      }

      return project;
    }
    return null;
  }

  @Override
  public Project getProjectBasicInfo(int projectID) {
    Map<String, String> projectData = projectDAO.getProjectBasicInfo(projectID);


    Project project = new Project(Integer.parseInt(projectData.get("id")));
    project.setTitle(projectData.get("title"));

    Budget totalBudget = new Budget();
    List<Budget> budgets = new ArrayList<>(1);
    if (projectData.get("total_budget_amount") != null) {
      totalBudget.setAmount(Double.parseDouble(projectData.get("total_budget_amount")));
      budgets.add(totalBudget);
    }
    project.setBudgets(budgets);

    project.setCreated(Long.parseLong(projectData.get("created")));

    // Getting Project Focuses - Regions
    List<IPProgram> regions = new ArrayList<>();
    if (projectData.get("regions") != null) {
      String[] regionsAcronyms = projectData.get("regions").split(",");
      IPProgram region;
      for (String regionAcronym : regionsAcronyms) {
        region = new IPProgram();
        region.setAcronym(regionAcronym);
        regions.add(region);
      }
    }
    project.setRegions(regions);


    // Getting Project Focuses - Flagships
    List<IPProgram> flagships = new ArrayList<>();
    if (projectData.get("flagships") != null) {
      String[] flagshipsAcronyms = projectData.get("flagships").split(",");
      IPProgram flagship;
      for (String flagshipAcronym : flagshipsAcronyms) {
        flagship = new IPProgram();
        flagship.setAcronym(flagshipAcronym);
        flagships.add(flagship);
      }
    }
    project.setFlagships(flagships);

    return project;
  }

  @Override
  public Project getProjectFromActivityId(int activityID) {
    int projectID = projectDAO.getProjectIdFromActivityId(activityID);
    if (projectID != -1) {
      return this.getProject(projectID);
    }
    return null;
  }

  @Override
  public Project getProjectFromDeliverableId(int deliverableID) {
    int projectID = projectDAO.getProjectIdFromDeliverableId(deliverableID);
    if (projectID != -1) {
      return this.getProject(projectID);
    }
    return null;
  }

  @Override
  public List<Integer> getProjectIdsEditables(User user) {
    return projectDAO.getProjectIdsEditables(user.getId());
  }

  @Override
  // TODO - Move this method to a class called projectIndicatorManager
  public List<IPIndicator> getProjectIndicators(int projectID) {
    List<IPIndicator> indicators = new ArrayList<>();
    List<Map<String, String>> indicatorsData = projectDAO.getProjectIndicators(projectID);

    for (Map<String, String> iData : indicatorsData) {
      IPIndicator indicator = new IPIndicator();
      indicator.setId(Integer.parseInt(iData.get("id")));
      indicator.setDescription(iData.get("description"));
      indicator.setTarget(iData.get("target"));
      indicator.setYear(Integer.parseInt(iData.get("year")));

      // Parent indicator
      IPIndicator parent = new IPIndicator(Integer.parseInt(iData.get("parent_id")));
      parent.setDescription(iData.get("parent_description"));
      parent.setTarget(iData.get("parent_target"));
      indicator.setParent(parent);

      // Outcome
      IPElement outcome = new IPElement(Integer.parseInt(iData.get("outcome_id")));
      outcome.setDescription(iData.get("outcome_description"));
      indicator.setOutcome(outcome);

      indicators.add(indicator);
    }

    return indicators;
  }


  @Override
  // TODO - Move this method to a class called projectOutputManager
  public List<IPElement> getProjectOutputs(int projectID) {
    List<IPElement> outputs = new ArrayList<>();
    List<Map<String, String>> outputsData = projectDAO.getProjectOutputs(projectID);

    for (Map<String, String> oData : outputsData) {
      IPElement output = new IPElement();
      output.setId(Integer.parseInt(oData.get("id")));
      output.setDescription(oData.get("description"));

      IPElement parent = new IPElement();
      parent.setId(Integer.parseInt(oData.get("parent_id")));
      parent.setDescription(oData.get("parent_description"));

      List<IPElement> parentList = new ArrayList<>();
      parentList.add(parent);
      output.setContributesTo(parentList);

      outputs.add(output);
    }

    return outputs;
  }

  @Override
  public List<Project> getProjectsByProgram(int programId) {
    List<Map<String, String>> projectDataList = projectDAO.getProjectsByProgram(programId);
    List<Project> projectsList = new ArrayList<>();
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);

    for (Map<String, String> elementData : projectDataList) {

      Project project = new Project(Integer.parseInt(elementData.get("id")));
      project.setTitle(elementData.get("title"));
      project.setSummary(elementData.get("summary"));
      // Format to the Dates of the project
      String sDate = elementData.get("start_date");
      String eDate = elementData.get("end_date");
      if (sDate != null && eDate != null) {
        try {
          Date startDate = dateformatter.parse(sDate);
          Date endDate = dateformatter.parse(eDate);
          project.setStartDate(startDate);
          project.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the dates", e);
        }
      }
      // Setting program creator.
      if (elementData.get("liaison_institution_id") != null) {
        int institutionID = Integer.parseInt(elementData.get("liaison_institution_id"));
        project.setLiaisonInstitution(liaisonInstitutionManager.getLiaisonInstitution(institutionID));
      }
      // Setting creation date.
      project.setCreated(Long.parseLong(elementData.get("created")));
      // Getting Project Focuses - IPPrograms
      project.setRegions(ipProgramManager.getProjectFocuses(Integer.parseInt(elementData.get("id")),
        APConstants.REGION_PROGRAM_TYPE));
      project.setFlagships(ipProgramManager.getProjectFocuses(Integer.parseInt(elementData.get("id")),
        APConstants.FLAGSHIP_PROGRAM_TYPE));
      // Getting Budget.
      project.setBudgets(budgetManager.getCCAFSBudgets(Integer.parseInt(elementData.get("id"))));

      // Adding project to the list
      projectsList.add(project);
    }
    return projectsList;
  }

  @Override
  public List<Project> getProjectsList(String[] values) {
    List<Project> projects = new ArrayList<>();
    List<String> ids = new ArrayList<String>(Arrays.asList(values));


    for (Project project : this.getAllProjectsBasicInfo()) {
      if (ids.contains(String.valueOf(project.getId()))) {
        projects.add(project);
      }
    }
    return projects;
  }


  @Override
  public int saveProjectDescription(Project project, User user, String justification) {
    Map<String, Object> projectData = new HashMap<>();
    if (project.getId() == -1) {
      projectData.put("user_id", user.getId());
      projectData.put("liaison_institution_id", project.getLiaisonInstitution().getId());
      projectData.put("created_by", user.getId());
      projectData.put("modified_by", user.getId());
      projectData.put("justification", justification);

      if (project.isCoreProject()) {
        projectData.put("type", APConstants.PROJECT_CORE);
      } else {
        projectData.put("type", APConstants.PROJECT_BILATERAL_STANDALONE);
      }
    } else {
      // Update project
      projectData.put("id", project.getId());
      projectData.put("title", project.getTitle());
      projectData.put("summary", project.getSummary());
      projectData.put("is_core", project.isCoreProject());
      SimpleDateFormat format = new SimpleDateFormat(APConstants.DATE_FORMAT);
      if (project.getStartDate() != null) {
        projectData.put("start_date", format.format(project.getStartDate()));
      }
      if (project.getEndDate() != null) {
        projectData.put("end_date", format.format(project.getEndDate()));
      }
      projectData.put("requires_workplan_upload", project.isWorkplanRequired());
      projectData.put("user_id", project.getOwner().getId());
      projectData.put("liaison_institution_id", project.getLiaisonInstitution().getId());
      projectData.put("modified_by", user.getId());
      projectData.put("justification", justification);
      projectData.put("type", project.getType());
    }

    return projectDAO.saveProject(projectData);
  }

  @Override
  // TODO - Move this method to a class called projectIndicatorManager
  public boolean saveProjectIndicators(List<IPIndicator> indicators, int projectID, User user, String justification) {
    Map<String, String> indicatorData;
    boolean saved = true;

    for (IPIndicator indicator : indicators) {
      if (indicator == null) {
        continue;
      }
      indicatorData = new HashMap<>();

      indicatorData.put("description", indicator.getDescription());
      indicatorData.put("target", indicator.getTarget());
      indicatorData.put("year", String.valueOf(indicator.getYear()));
      indicatorData.put("parent_id", String.valueOf(indicator.getParent().getId()));
      indicatorData.put("project_id", String.valueOf(projectID));
      indicatorData.put("outcome_id", String.valueOf(indicator.getOutcome().getId()));
      indicatorData.put("user_id", String.valueOf(user.getId()));
      indicatorData.put("justification", justification);

      if (indicator.getId() == -1) {
        indicatorData.put("id", null);
        saved = projectDAO.saveProjectIndicators(indicatorData) && saved;
      } else {
        indicatorData.put("id", String.valueOf(indicator.getId()));
        saved = projectDAO.updateProjectIndicators(indicatorData) && saved;
      }
    }

    return saved;
  }

  @Override
  // TODO - Move this method to a class called projectOutputManager
  public boolean saveProjectOutputs(List<IPElement> outputs, int projectID, User user, String justification) {
    Map<String, String> outputData;
    boolean saved = true;

    for (IPElement output : outputs) {
      if (output == null) {
        continue;
      }
      outputData = new HashMap<>();
      outputData.put("project_id", String.valueOf(projectID));
      outputData.put("mog_id", String.valueOf(output.getId()));
      outputData.put("midOutcome_id", String.valueOf(output.getContributesTo().get(0).getId()));
      outputData.put("user_id", String.valueOf(user.getId()));
      outputData.put("justification", justification);

      int relationID = projectDAO.saveProjectOutput(outputData);
      saved = (relationID != -1) && saved;
    }
    return saved;
  }
}
