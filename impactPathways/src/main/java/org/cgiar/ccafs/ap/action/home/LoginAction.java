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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.RoleManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.security.APCustomRealm;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Date;

import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal
 */
public class LoginAction extends BaseAction {

  // Logging
  private static final Logger LOG = LoggerFactory.getLogger(LoginAction.class);

  private static final long serialVersionUID = -890122014241894430L;

  private User user;

  private UserManager userManager;
  private InstitutionManager institutionManager;
  private RoleManager roleManager;

  private String url;


  @Inject
  public LoginAction(APConfig config, UserManager userManager, InstitutionManager institutionManager,
    RoleManager roleManager) {
    super(config);
    this.userManager = userManager;
    this.institutionManager = institutionManager;
    this.roleManager = roleManager;

  }


  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }

  public String getUrl() {
    return url;
  }

  public User getUser() {
    return user;
  }

  public String login() {
    // attribute user is not null when the user try to login
    if (user != null) {

      // Check if is a valid user
      String userEmail = user.getEmail().trim().toLowerCase();
      User loggedUser = userManager.login(userEmail, user.getPassword());
      if (loggedUser != null) {
        loggedUser.setLastLogin(new Date());
        userManager.saveLastLogin(loggedUser);

        this.getSession().put(APConstants.SESSION_USER, loggedUser);
        LOG.info("User " + user.getEmail() + " logged in successfully.");

        /*
         * @Hjimenez
         * Save the user url with trying to enter the system to redirect after
         * loged.
         */
        String urlAction = ServletActionContext.getRequest().getHeader("Referer");
        /*
         * take the ".do" pattern in the url to differentiate the main page.
         * also discard the "logut" url beacause this action close the user session.
         */
        if (urlAction.contains(".do") && !urlAction.contains("logout")) {
          this.url = urlAction;
          return LOGIN;
        } else {
          return SUCCESS;
        }
      } else {
        LOG.info("User " + user.getEmail() + " tried to logged in but failed.");
        user.setPassword(null);
        this.addFieldError("loginMesage", this.getText("home.login.error"));
        return INPUT;
      }
    } else {
      // Check if the user exists in the session
      return (this.getCurrentUser() == null) ? INPUT : SUCCESS;
    }
  }

  public String logout() {
    User user = (User) this.getSession().get(APConstants.SESSION_USER);
    if (user != null) {
      LOG.info("User {} logout succesfully", user.getEmail());
    }
    this.getSession().clear();
    SecurityUtils.getSubject().logout();

    // Hack for cleaning cached authorization.
    for (Realm realm : ((RealmSecurityManager) SecurityUtils.getSecurityManager()).getRealms()) {
      if (realm instanceof APCustomRealm) {
        APCustomRealm customRealm = (APCustomRealm) realm;
        customRealm.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
      }
    }

    return SUCCESS;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public void validate() {
    // If is the first time the user is loading the page
    if (user != null) {
      if (user.getEmail().isEmpty()) {
        this.addFieldError("user.email", this.getText("validation.field.required"));
        user.setPassword(null);
      } /*
         * else if (!EmailValidator.isValidEmail(user.getEmail().trim())) {
         * addFieldError("user.email", getText("validation.invalid", new String[] {getText("home.login.email")}));
         * user.setPassword(null);
         * }
         */
    }

  }
}
