package org.cgiar.ccafs.ap.interceptor;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

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
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);
    if (user != null) {
      return invocation.invoke();
    }
    return BaseAction.NOT_LOGGED;
  }


}
