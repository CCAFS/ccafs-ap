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
import org.cgiar.ccafs.ap.data.manager.RoleManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.utils.APConfig;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hernán David Carvajal .
 */
public class ChangeInstitutionAction extends BaseAction {

  // Logging
  private static final Logger LOG = LoggerFactory.getLogger(ChangeInstitutionAction.class);
  private static final long serialVersionUID = 8975353090220181481L;

  // Model
  private User user;
  private int institutionID;

  // Managers
  private RoleManager roleManager;
  private UserManager userManager;

  @Inject
  public ChangeInstitutionAction(APConfig config, RoleManager roleManager, UserManager userManager) {
    super(config);
    this.roleManager = roleManager;
    this.userManager = userManager;
  }

  @Override
  public String execute() throws Exception {
    User user = (User) this.getSession().get(APConstants.SESSION_USER);

    if (user == null || institutionID == -1) {
      return NOT_FOUND;
    }

    int previousInstitution = user.getCurrentInstitution().getId();
    for (Institution inst : user.getInstitutions()) {
      if (inst.getId() == institutionID) {
        user.setCurrentInstitution(inst);
        user.setRole(roleManager.getRole(user));
        user.setEmployeeId(userManager.getEmployeeID(user));
        // Change the user in the session object
        this.getSession().put(APConstants.SESSION_USER, user);
      }
    }

    // If the institution was not changed it is because the user
    // had not permissions to change to that institution
    if (user.getCurrentInstitution().getId() == previousInstitution) {
      return NOT_AUTHORIZED;
    }

    return SUCCESS;
  }

  public User getUser() {
    return user;
  }

  @Override
  public void prepare() throws Exception {
    try {
      institutionID =
        Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.INSTITUTION_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the institution identifier '{}'.", institutionID, e);
      institutionID = -1;
      return; // Stop here and go to execute method.
    }
  }

}
