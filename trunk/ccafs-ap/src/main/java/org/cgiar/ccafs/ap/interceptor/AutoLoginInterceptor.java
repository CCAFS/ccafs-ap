package org.cgiar.ccafs.ap.interceptor;

import org.cgiar.ccafs.ap.config.APContants;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.data.model.User.UserRole;

import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AutoLoginInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -587313132043100255L;

  private static final Logger LOG = LoggerFactory.getLogger(AutoLoginInterceptor.class);

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APContants.SESSION_USER);
    if (user == null) {
      user = new User();
      user.setEmail("admin@cgiar.org");
      user.setLastLogin(new Date());
      user.setRole(UserRole.Admin);
      session.put(APContants.SESSION_USER, user);
      LOG.debug("Auto logged as admin@cgiar.org");
    }
    return invocation.invoke();
  }


}
