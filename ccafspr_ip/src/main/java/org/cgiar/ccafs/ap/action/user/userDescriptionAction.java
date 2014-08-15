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
package org.cgiar.ccafs.ap.action.user;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.RoleManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Role;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 */
public class userDescriptionAction extends BaseAction {

  private static final long serialVersionUID = 2389437672158262423L;

  // Manager
  private UserManager userManager;
  private RoleManager roleManager;
  private InstitutionManager institutionManager;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(userDescriptionAction.class);

  // Model for the Back-End
  private User user;
  private List<Institution> institutions;
  private List<Role> roles;
  private int employeeID;


  @Inject
  public userDescriptionAction(APConfig config, UserManager userManager, RoleManager roleManager,
    InstitutionManager institutionManager) {
    super(config);
    this.userManager = userManager;
    this.roleManager = roleManager;
    this.institutionManager = institutionManager;
  }


  public int getEmployeeID() {
    return employeeID;
  }


  public String getEmployeeRequest() {
    return APConstants.EMPLOYEE_REQUEST_ID;
  }


  public List<Institution> getInstitutions() {
    return institutions;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public User getUser() {
    return user;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    try {
      employeeID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.EMPLOYEE_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", employeeID, e);
      employeeID = -1;
      return; // Stop here and go to execute method.
    }

    // Get the Institutions
    institutions = institutionManager.getAllInstitutions();
    System.out.println(institutions);
    // Get the Roles
    roles = roleManager.getAllRoles();
    System.out.println(roles);

    // Get the Employee Information
    if (employeeID != -1) {
      user = userManager.getOwner(employeeID);
    }


  }


  @Override
  public String save() {
    System.out.println("SAVE...");

    return INPUT;

  }


  public void setEmployeeID(int employeeID) {
    this.employeeID = employeeID;
  }


  public void setInstitutions(List<Institution> instititution) {
    this.institutions = instititution;
  }


  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
