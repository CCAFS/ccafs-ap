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

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This action aims to define general functionalities that are going to be used by all other Actions.
 * 
 * @author Héctor Tobón - CIAT/CCAFS
 * @author Hernán Carvajal - CIAT/CCAFS
 */
public class BaseAction extends ActionSupport implements Preparable, SessionAware, ServletRequestAware {

  private static final long serialVersionUID = -740360140511380630L;

  public static final String CANCEL = "cancel";
  public static final String NEXT = "next";
  public static final String NOT_LOGGED = "401";
  public static final String NOT_AUTHORIZED = "403";
  public static final String NOT_FOUND = "404";
  public static final String SAVED_STATUS = "savedStatus";

  // button actions
  protected boolean save;
  protected boolean next;
  protected boolean delete;
  protected boolean cancel;
  protected boolean dataSaved;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(BaseAction.class);

  private Map<String, Object> session;
  private HttpServletRequest request;

  // Config
  protected APConfig config;

  @Inject
  public BaseAction(APConfig config) {
    this.config = config;
  }

  /**
   * This function add a flag (--warn--) to the message in order to give
   * a different style to the success message using javascript once the html is ready.
   * 
   * @param message
   */
  public void addActionWarning(String message) {
    addActionMessage("--warn--" + message);
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
      return save();
    } else if (delete) {
      return delete();
    } else if (cancel) {
      return cancel();
    } else if (next) {
      return next();
    }
    return INPUT;
  }

  public String getActivityIdParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public String getBaseUrl() {
    return config.getBaseUrl();
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

  /**
   * Define default locale while we decide to support other languages in the future.
   */
  @Override
  public Locale getLocale() {
    return Locale.ENGLISH;
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public Map<String, Object> getSession() {
    return session;
  }

  public boolean isDataSaved() {
    return dataSaved;
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

  public boolean isReportingActive() {
    return config.isReportingActive();
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

  public void setCancel(boolean cancel) {
    this.cancel = true;
  }


  public void setDataSaved(boolean dataSaved) {
    this.dataSaved = dataSaved;
  }

  public void setDelete(boolean delete) {
    this.delete = delete;
  }

  public void setNext(boolean next) {
    this.next = true;
  }


  public void setSave(boolean save) {
    this.save = true;
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
