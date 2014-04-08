package org.cgiar.ccafs.ap.action;

import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This action aims to define procedures and objects that use several actions in order to avoid repeating code..
 * 
 * @author hftobon
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
  private boolean dataSaved;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(BaseAction.class);

  private Map<String, Object> session;
  private HttpServletRequest request;

  // Config
  protected APConfig config;

  // Managers
  protected LogframeManager logframeManager;

  protected Subject currentUser;

  @Inject
  public BaseAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager) {
    this.config = config;
    this.logframeManager = logframeManager;

    SecurityUtils.setSecurityManager(securityManager);
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
   * Get the current planning logframe based on the variable ccafsap.planning.currentYear which is in the configuration
   * file.
   * 
   * @return a Logframe object or null if no logframe is found.
   */
  public Logframe getCurrentPlanningLogframe() {
    return logframeManager.getLogframeByYear(config.getPlanningCurrentYear());
  }

  /**
   * Get the current reporting logframe based on the variable ccafsap.reporting.currentYear which is in the
   * configuration file.
   * 
   * @return a Logframe object or null if no logframe is found.
   */
  public Logframe getCurrentReportingLogframe() {
    return logframeManager.getLogframeByYear(config.getReportingCurrentYear());
  }


  /**
   * Get the user that is currently saved in the session.
   * 
   * @return a user object or null if no user was found.
   */

  public User getCurrentUser() {
    User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute(APConstants.SESSION_USER);
    return currentUser;
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

  public Session getSession() {
    return SecurityUtils.getSubject().getSession();
  }

  public Subject getSubject() {
    return SecurityUtils.getSubject();
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
    return SecurityUtils.getSubject().isAuthenticated() || SecurityUtils.getSubject().isRemembered();
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


  public void setCurrentUser(Subject currentUser) {
    this.currentUser = currentUser;
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
