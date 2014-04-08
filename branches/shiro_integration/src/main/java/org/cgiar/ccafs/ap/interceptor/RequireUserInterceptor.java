package org.cgiar.ccafs.ap.interceptor;

import org.cgiar.ccafs.ap.action.BaseAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * This interceptor is responsible for validating if the user is actually logged or not, in order to be able to access
 * the contents of the specified page.
 * If there is no an user in the current session it will return a 401 error (Authentication Required).
 * 
 * @author hftobon
 */
public class RequireUserInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 7739155018211386527L;

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    Subject user = SecurityUtils.getSubject();

    if (user.isAuthenticated() || user.isRemembered()) {
      return invocation.invoke();
    }
    return BaseAction.NOT_LOGGED;
  }


}
