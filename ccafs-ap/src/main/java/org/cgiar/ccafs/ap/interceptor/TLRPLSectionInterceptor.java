package org.cgiar.ccafs.ap.interceptor;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * This interceptor is responsible for validating if the role of user is actually TL or RPL,
 * in order to be able to access the contents of the specified page.
 * If there is no an authorized user in the current session it will return
 * a 401 error (Authentication Required).
 */
public class TLRPLSectionInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -1979150748457796470L;

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);
    if (user.isTL() || user.isRPL() || user.isAdmin()) {
      return invocation.invoke();
    }

    return BaseAction.NOT_LOGGED;
  }


}
