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
package org.cgiar.ccafs.ap.action;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.BoardMessageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectLessonsManager;
import org.cgiar.ccafs.ap.data.manager.SectionStatusManager;
import org.cgiar.ccafs.ap.data.model.BoardMessage;
import org.cgiar.ccafs.ap.data.model.ComponentLesson;
import org.cgiar.ccafs.ap.data.model.LogHistory;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.SectionStatus;
import org.cgiar.ccafs.ap.data.model.SectionStatusEnum;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.security.APCustomRealm;
import org.cgiar.ccafs.ap.security.SecurityContext;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This action aims to define general functionalities that are going to be used by all other Actions.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 * @author Hernán David Carvajal - CIAT/CCAFS
 */
public class BaseAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware {


  private static final long serialVersionUID = -740360140511380630L;
  public static final String CANCEL = "cancel";
  public static final String NEXT = "next";
  public static final String NOT_LOGGED = "401";
  public static final String NOT_AUTHORIZED = "403";
  public static final String NOT_FOUND = "404";

  public static final String SAVED_STATUS = "savedStatus";

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(BaseAction.class);

  // button actions
  protected boolean save;
  protected boolean next;
  protected boolean delete;
  protected boolean cancel;
  protected boolean submit;
  protected boolean dataSaved;
  protected boolean add;
  // User actions
  private boolean isEditable; // If user is able to edit the form.
  private boolean canEdit; // If user is able to edit the form.
  private boolean saveable; // If user is able to see the save, cancel, delete buttons
  private boolean fullEditable; // If user is able to edit all the form.

  @SuppressWarnings("rawtypes")
  private List<LogHistory> history;

  // Justification of the changes
  private String justification;
  private ComponentLesson projectLessons;
  private ComponentLesson projectLessonsPreview;
  private Map<String, Object> session;
  private HttpServletRequest request;
  private List<SectionStatus> sectionStatuses;

  // Config
  protected APConfig config;

  // Managers
  @Inject
  protected SecurityContext securityContext;
  @Inject
  private BoardMessageManager boardMessageManager;
  @Inject
  protected ProjectLessonsManager lessonManager;
  @Inject
  private SectionStatusManager sectionStatusManager;


  private Map<String, Object> parameters;

  @Inject
  public BaseAction(APConfig config) {
    this.config = config;
    this.saveable = true;
    this.fullEditable = true;
    this.justification = "";
  }

  /* Override this method depending of the save action. */
  public String add() {
    return SUCCESS;
  }

  /**
   * This function add a flag (--warn--) to the message in order to give
   * a different style to the success message using javascript once the html is ready.
   * 
   * @param message
   */
  public void addActionWarning(String message) {
    this.addActionMessage("--warn--" + message);
  }

  /* Override this method depending of the cancel action. */
  public String cancel() {
    return CANCEL;
  }

  /**
   * This method removes all the section statuses.
   */
  public void cleanSectionStatuses() {
    if (sectionStatuses != null) {
      sectionStatuses.clear();
    }
  }

  /**
   * This method clears the cache and re-load the user permissions in the next iteration.
   */
  public void clearPermissionsCache() {
    ((APCustomRealm) securityContext.getRealm())
      .clearCachedAuthorizationInfo(securityContext.getSubject().getPrincipals());
  }

  /* Override this method depending of the delete action. */
  public String delete() {
    return SUCCESS;
  }

  @Override
  public String execute() throws Exception {
    if (save) {
      return this.save();
    } else if (delete) {
      return this.delete();
    } else if (cancel) {
      return this.cancel();
    } else if (next) {
      return this.next();
    } else if (add) {
      return this.add();
    } else if (submit) {
      return this.submit();
    }
    return INPUT;
  }

  public String getActionName() {
    return ServletActionContext.getActionMapping().getName();
  }

  public String getBaseUrl() {
    return config.getBaseUrl();
  }

  /**
   * This method gets all the board Messages
   * 
   * @return a Board Message list with the board message information
   */
  public List<BoardMessage> getBoardMessages() {
    return boardMessageManager.getAllBoardMessages();
  }

  public APConfig getConfig() {
    return config;
  }

  public int getCurrentPlanningYear() {
    return config.getPlanningCurrentYear();
  }


  public int getCurrentReportingYear() {
    return config.getReportingCurrentYear();
  }


  /**
   * Get the user that is currently saved in the session.
   * 
   * @return a user object or null if no user was found.
   */
  public User getCurrentUser() {
    User u = null;
    try {
      u = session.get(APConstants.SESSION_USER) != null ? (User) session.get(APConstants.SESSION_USER) : null;
    } catch (Exception e) {
      LOG.warn("There was a problem trying to find the user in the session.");
    }
    return u;
  }

  public String getCycleName() {
    boolean isReporting = this.isReportingCycle();
    if (isReporting) {
      return APConstants.REPORTING_SECTION;
    } else {
      return APConstants.PLANNING_SECTION;
    }
  }

  /**
   * This method gets the specific section status from the sectionStatuses array for a Deliverable.
   * 
   * @param deliverableID is the deliverable ID to be identified.
   * @param section is the name of some section.
   * @return a SectionStatus object with the information requested.
   */
  public SectionStatus getDeliverableStatus(int deliverableID, String section) {
    if (this.sectionStatuses != null) {
      for (SectionStatus status : this.sectionStatuses) {
        if (status.getDeliverableID() == deliverableID && status.getSection().equals(section)) {
          return status;
        }
      }
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  public List<LogHistory> getHistory() {
    return history;
  }

  public String getJustification() {
    return justification;
  }

  /**
   * Define default locale while we decide to support other languages in the future.
   */
  @Override
  public Locale getLocale() {
    return Locale.ENGLISH;
  }

  public String getOrganizationIdentifier() {
    return APConstants.CCAFS_ORGANIZATION_IDENTIFIER;
  }

  public Map<String, Object> getParameters() {
    parameters = ActionContext.getContext().getParameters();
    return parameters;
  }


  public String getParameterValue(String param) {
    Object paramObj = this.getParameters().get(param);
    if (paramObj == null) {
      return null;
    }
    return ((String[]) paramObj)[0];
  }

  public ComponentLesson getProjectLessons() {
    return projectLessons;
  }

  public ComponentLesson getProjectLessonsPreview() {
    return projectLessonsPreview;
  }


  /**
   * This method gets the specific section status from the sectionStatuses array.
   * 
   * @param section is the name of some section.
   * @return a SectionStatus object with the information requested.
   */
  public SectionStatus getProjectSectionStatus(String section) {
    if (this.sectionStatuses != null) {
      if (section.equals("deliverablesList")) {
        boolean statusesExist = false;
        SectionStatus allDeliverablesStatuses = new SectionStatus();
        allDeliverablesStatuses.setId(-1);
        StringBuilder missingFields = new StringBuilder();
        for (SectionStatus status : this.sectionStatuses) {
          if (status.getSection().equals("deliverablesList") || status.getSection().equals("deliverable")) {
            statusesExist = true;
            missingFields.append(status.getMissingFieldsWithPrefix());
          }
        }
        allDeliverablesStatuses.setMissingFields(missingFields.toString());
        if (statusesExist) {
          return allDeliverablesStatuses;
        }
      } else {
        for (SectionStatus status : this.sectionStatuses) {
          if (status.getSection().equals(section)) {
            return status;
          }
        }
      }
    }
    return null;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public SecurityContext getSecurityContext() {
    return this.securityContext;
  }

  public Map<String, Object> getSession() {
    return session;
  }

  /**
   * This method validates if current user has permissions to edit a specified field name in the platform.
   * As this method is called in some action, it will validate automatically if the user is working in reporting or in
   * planning.
   * 
   * @param fieldName is the name of a field.
   * @param usePlanningReporting is true if you want to validate permission in a certain round. False otherwise.
   * @return true if the user has permissions to edit the specified field name, false otherwise.
   */
  public boolean hasPermission(String fieldName, boolean usePlanningReporting) {
    StringBuffer permissionString = new StringBuffer();
    if (usePlanningReporting) {
      if (this.isReportingCycle()) {
        permissionString.append("reporting:");
      } else {
        permissionString.append("planning:");
      }
    }
    permissionString.append(this.getActionName());
    permissionString.append(":");
    permissionString.append(fieldName);

    return securityContext.hasPermission(permissionString.toString());
  }

  /**
   * This method validates if current user has permissions to edit a specified field name in the platform.
   * As this method is called in some action, it will validate automatically if the user is working in reporting or in
   * planning.
   * 
   * @param fieldName is the name of a field.
   * @param projectID is some project identifier.
   * @return true if the user has permissions to edit the specified field name, false otherwise.
   */
  public boolean hasProjectPermission(String fieldName, int projectID) {
    StringBuffer permissionString = new StringBuffer();
    if (this.isReportingCycle()) {
      permissionString.append("reporting:projects:");
    } else {
      permissionString.append("planning:projects:");
    }
    permissionString.append(projectID);
    permissionString.append(":");
    permissionString.append(this.getActionName());
    permissionString.append(":");
    permissionString.append(fieldName);


    return securityContext.hasPermission(permissionString.toString());
  }

  /**
   * This method validates if current user has permissions to edit a specified field name in the platform.
   * As this method is called in some action, it will validate automatically if the user is working in reporting or in
   * planning.
   * 
   * @param fieldName is the name of a field.
   * @param projectID is some project identifier.
   * @param actionName is a specific action name.
   * @return true if the user has permissions to edit the specified field name, false otherwise.
   */
  public boolean hasProjectPermission(String fieldName, int projectID, String actionName) {
    StringBuffer permissionString = new StringBuffer();
    if (this.isReportingCycle()) {
      permissionString.append("reporting:projects:");
    } else {
      permissionString.append("planning:projects:");
    }
    permissionString.append(projectID);
    permissionString.append(":");
    permissionString.append(actionName);
    permissionString.append(":");
    permissionString.append(fieldName);

    return securityContext.hasPermission(permissionString.toString());
  }

  /**
   * This method validates if current user has permissions to edit a specified field name in the platform in SYNTHESIS.
   * As this method is called in some action, it will validate automatically if the user is working in reporting or in
   * planning.
   * 
   * @param fieldName is the name of a field.
   * @param liaisonInstitutionID is some liaison institution identifier.
   * @return true if the user has permissions to edit the specified field name, false otherwise.
   */
  public boolean hasSynthesisPermission(String fieldName, int liaisonInstitutionID) {
    StringBuffer permissionString = new StringBuffer();
    if (this.isReportingCycle()) {
      permissionString.append("reporting:synthesis:");
    } else {
      permissionString.append("planning:synthesis:");
    }
    permissionString.append(liaisonInstitutionID);
    permissionString.append(":");
    permissionString.append(this.getActionName());
    permissionString.append(":");
    permissionString.append(fieldName);


    return securityContext.hasPermission(permissionString.toString());
  }

  /**
   * This method returns the status of the given section in a specific cycle (Planning or Reporting).
   * 
   * @param project is the project that you want to look for the missing fields.
   * @param sections is an array of sections.
   * @param cycle could be 'Planning' or 'Reporting'.
   * @return a Map array with the name of the sections as keys and the status of the section as values, or null if some
   *         error occurred.
   */
  public void initializeProjectSectionStatuses(Project project, String cycle) {
    int year = 0;
    if (cycle.equals(APConstants.REPORTING_SECTION)) {
      year = config.getReportingCurrentYear();
    } else {
      year = config.getPlanningCurrentYear();
    }
    this.sectionStatuses = sectionStatusManager.getSectionStatuses(project, cycle, year);
  }

  public boolean isCanEdit() {
    return canEdit;
  }

  /**
   * This method checks that all the section status do not have missing fields.
   * If so, the process is ready to be submitted.
   * 
   * @return true if the process is complete and is ready to be submitted, false otherwise.
   */
  public boolean isComplete() {
    if (this.sectionStatuses == null || this.sectionStatuses.isEmpty()) {
      return false;
    }

    SectionStatusEnum seciones[] = SectionStatusEnum.values();
    if (this.isReportingCycle()) {
      if (seciones.length - 2 != this.realSize(this.sectionStatuses)) {
        return false;
      }
    } else {
      if (seciones.length != this.realSize(this.sectionStatuses)) {
        return false;
      }
    }

    for (SectionStatus status : this.sectionStatuses) {
      if (!status.getMissingFieldsWithPrefix().isEmpty()) {
        return false;
      }


    }
    return true;
  }

  public boolean isDataSaved() {
    return dataSaved;
  }

  public boolean isEditable() {
    return isEditable;
  }

  public boolean isFullEditable() {
    return fullEditable;
  }

  protected boolean isHttpPost() {
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      return true;
    }
    return false;
  }


  /**
   * Validate if the user is already logged in or not.
   * 
   * @return true if the user is logged in, false otherwise.
   */
  public boolean isLogged() {
    if (this.getCurrentUser() == null) {
      return false;
    }
    return true;
  }

  public boolean isPlanningActive() {
    return config.isPlanningActive();
  }

  public boolean isPlanningCycle() {
    String namespace = ServletActionContext.getActionMapping().getNamespace();
    return namespace.contains("/planning");
  }

  public boolean isPreplanningActive() {
    return config.isPrePlanningActive();
  }

  public boolean isReportingActive() {
    return config.isReportingActive();
  }

  public boolean isReportingCycle() {
    String namespace = ServletActionContext.getActionMapping().getNamespace();
    return namespace.contains("/reporting");
  }

  public boolean isSaveable() {
    return saveable;
  }

  public boolean isSubmit() {
    return submit;
  }

  public boolean isSummariesActive() {
    return config.isSummariesActive();
  }

  public String next() {
    return NEXT;
  }

  @Override
  public void prepare() throws Exception {
    // So far, do nothing here!
  }

  public int realSize(List<SectionStatus> sectionStatusList) {

    int size = 0;

    for (SectionStatus sectionStatus : sectionStatusList) {
      if (sectionStatus.getDeliverableID() == -1) {
        size++;
      }
    }
    return size;

  }

  /* Override this method depending of the save action. */
  public String save() {
    return SUCCESS;
  }

  protected boolean saveProjectLessons(int projectID) {
    return lessonManager.saveProjectComponentLesson(projectLessons, projectID, this.getCurrentUser(), justification,
      this.getCycleName());
  }

  protected boolean saveProjectLessonsSynthesis(int progamId) {
    return lessonManager.saveProjectComponentLessonSynthesis(projectLessons, progamId, this.getCurrentUser(),
      justification, this.getCycleName());
  }

  public void setAdd(boolean add) {
    this.add = true;
  }

  public void setCancel(boolean cancel) {
    this.cancel = true;
  }

  public void setCanEdit(boolean canEdit) {
    this.canEdit = canEdit;
  }

  public void setDataSaved(boolean dataSaved) {
    this.dataSaved = dataSaved;
  }

  public void setDelete(boolean delete) {
    this.delete = delete;
  }

  public void setEditableParameter(boolean isEditable) {
    this.isEditable = isEditable;
  }


  public void setFullEditable(boolean fullEditable) {
    this.fullEditable = fullEditable;
  }

  @SuppressWarnings("rawtypes")
  public void setHistory(List<LogHistory> history) {
    this.history = history;
  }

  public void setJustification(String justification) {
    this.justification = justification;
  }

  public void setNext(boolean next) {
    this.next = true;
  }

  public void setProjectLessons(ComponentLesson projectLessons) {
    this.projectLessons = projectLessons;
  }

  public void setProjectLessonsPreview(ComponentLesson projectLessonsPreview) {
    this.projectLessonsPreview = projectLessonsPreview;
  }

  public void setSave(boolean save) {
    this.save = true;
  }

  public void setSaveable(boolean saveable) {
    this.saveable = saveable;
  }

  @Override
  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  @Override
  public void setSession(Map<String, Object> session) {
    this.session = session;
  }

  public void setSubmit(boolean submit) {
    this.submit = true;
  }

  public String submit() {
    return SUCCESS;
  }

}
