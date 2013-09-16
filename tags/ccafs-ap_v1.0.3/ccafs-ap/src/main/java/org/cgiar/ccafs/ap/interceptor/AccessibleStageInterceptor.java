package org.cgiar.ccafs.ap.interceptor;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;


public class AccessibleStageInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 3723021484076686914L;

  private ActivityManager activityManager;
  private APConfig config;

  @Inject
  public AccessibleStageInterceptor(ActivityManager activityManager, APConfig config) {
    this.activityManager = activityManager;
    this.config = config;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    String stageName = ServletActionContext.getActionMapping().getNamespace();

    // Check what section is the user loading and
    // validate if it is active
    if (stageName.equals("/planning")) {
      if (config.isPlanningActive()) {
        return invocation.invoke();
      } else {
        return BaseAction.NOT_AUTHORIZED;
      }
    } else if (stageName.equals("/reporting")) {
      if (config.isReportingActive()) {
        return invocation.invoke();
      } else {
        return BaseAction.NOT_AUTHORIZED;
      }
    } else {
      return invocation.invoke();
    }
  }
}
