package org.cgiar.ccafs.ap.action;

import org.cgiar.ccafs.ap.config.APConfig;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;

/**
 * This action aims to define procedures and objects that use several actions in order to avoid repeating code..
 * 
 * @author hftobon
 */
public class BaseAction extends ActionSupport {

  private static final long serialVersionUID = -740360140511380630L;

  private APConfig config;

  @Inject
  public BaseAction(APConfig config) {
    this.config = config;
  }

  public String getBaseUrl() {
    return config.getBaseUrl();
  }

}
