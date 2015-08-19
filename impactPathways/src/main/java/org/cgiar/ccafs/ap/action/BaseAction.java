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
import org.cgiar.ccafs.ap.data.dao.ProjectLessonsManager;
import org.cgiar.ccafs.ap.data.manager.BoardMessageManager;
import org.cgiar.ccafs.ap.data.model.BoardMessage;
import org.cgiar.ccafs.ap.data.model.ComponentLesson;
import org.cgiar.ccafs.ap.data.model.LogHistory;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.security.SecurityContext;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
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
  protected boolean dataSaved;
  protected boolean add;

  // User actions
  private boolean isEditable; // If user is able to edit the form.
  private boolean canEdit; // If user is able to edit the form.
  private boolean saveable; // If user is able to see the save, cancel, delete buttons

  private boolean fullEditable; // If user is able to edit all the form.

  // Justification of the changes
  private List<LogHistory> history;
  private String justification;

  private ComponentLesson projectLessons;
  private Map<String, Object> session;
  private HttpServletRequest request;

  // Config
  protected APConfig config;
  @Inject
  protected SecurityContext securityContext;

  @Inject
  private BoardMessageManager boardMessageManager;

  @Inject
  private ProjectLessonsManager lessonManager;

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


  /**
   * Get the user that is currently saved in the session.
   * 
   * @return a user object or null if no user was found.
   */
  public User getCurrentUser() {
    User u = null;
    try {
      u = (User) session.get(APConstants.SESSION_USER);
    } catch (Exception e) {
      LOG.warn("There was a problem trying to find the user in the session.");
    }
    return u;
  }


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

  public ComponentLesson getProjectLessons() {
    return projectLessons;
  }

  protected void getProjectLessons(int projectID, int year) {
    projectLessons = lessonManager.getProjectComponentLesson(projectID, this.getActionName(), year);
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

  public boolean isCanEdit() {
    return canEdit;
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

  public boolean isPreplanningActive() {
    return config.isPrePlanningActive();
  }

  public boolean isReportingActive() {
    return config.isReportingActive();
  }

  public boolean isSaveable() {
    return saveable;
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

  /* Override this method depending of the save action. */
  public String save() {
    return SUCCESS;
  }

  protected boolean saveProjectLessons(int projectID) {
    return lessonManager.saveProjectComponentLesson(projectLessons, projectID, this.getCurrentUser(), justification);
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

}
