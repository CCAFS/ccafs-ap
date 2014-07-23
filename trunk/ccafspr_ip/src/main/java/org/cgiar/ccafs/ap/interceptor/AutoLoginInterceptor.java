package org.cgiar.ccafs.ap.interceptor;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.RoleManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AutoLoginInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -587313132043100255L;

  private static final Logger LOG = LoggerFactory.getLogger(AutoLoginInterceptor.class);

  private UserManager userManager;
  private InstitutionManager institutionManager;
  private RoleManager roleManager;

  @Inject
  public AutoLoginInterceptor(UserManager userManager, InstitutionManager institutionManager, RoleManager roleManager) {
    this.userManager = userManager;
    this.institutionManager = institutionManager;
    this.roleManager = roleManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);
    if (user == null) {
      user = userManager.getUser("h.d.carvajal@cgiar.org");
      // Get the institutions related to the user
      user.setInstitutions(institutionManager.getInstitutionsByUser(user));
      // Set the main institution as current institution
      user.setCurrentInstitution(institutionManager.getUserMainInstitution(user));
      user.setRole(roleManager.getRole(user));

      session.put(APConstants.SESSION_USER, user);
      LOG.debug("Auto logged as " + user.getEmail());
    }
    return invocation.invoke();
  }


}
