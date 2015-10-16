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
    LOG.debug("=> AutoLoginInterceptor");
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);
    if (user == null) {
      // User loggedUser = userManager.login("h.f.tobon@cgiar.org", "123");
      User loggedUser = userManager.login("p.laderach@cgiar.org", "123");
      // user = userManager.getUserByEmail("h.d.carvajal@cgiar.org");
      // user = userManager.getUserByEmail("h.f.tobon@cgiar.org");
      user = userManager.getUserByEmail("p.laderach@cgiar.org");
      // user = userManager.getUserByEmail("d.m.baron@cgiar.org");
      // user = userManager.getUserByEmail("d.giraldo@cgiar.org");
      // Get the institutions related to the user
      // user.setInstitutions(institutionManager.getInstitutionsByUser(user));
      // Set the main institution as current institution
      // user.setCurrentInstitution(institutionManager.getUserMainInstitution(user));
      // user.setRole(roleManager.getRole(user));

      session.put(APConstants.SESSION_USER, user);
      LOG.debug("Auto logged as " + user.getEmail());
    }
    return invocation.invoke();
  }


}
