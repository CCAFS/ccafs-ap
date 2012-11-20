package org.cgiar.ccafs.ap.action;

import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APContants;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Locale;
import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This action aims to define procedures and objects that use several actions in order to avoid repeating code..
 * 
 * @author hftobon
 */
public class BaseAction extends ActionSupport implements Preparable, SessionAware {

  private static final long serialVersionUID = -740360140511380630L;
  private static final Logger LOG = LoggerFactory.getLogger(BaseAction.class);

  private Map<String, Object> sessionParams;
  private APConfig config;

  @Inject
  public BaseAction(APConfig config) {
    this.config = config;
  }

  public String getBaseUrl() {
    return config.getBaseUrl();
  }

  public User getCurrentUser() {
    User u = null;
    try {
      u = (User) sessionParams.get(APContants.SESSION_USER);
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

  @Override
  public void prepare() throws Exception {
    // TODO Auto-generated method stub

  }

  @Override
  public void setSession(Map<String, Object> session) {
    this.sessionParams = session;

  }


}
