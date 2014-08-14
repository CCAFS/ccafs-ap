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
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 */
public class ManageUsersAction extends BaseAction {

  private static final long serialVersionUID = 2389437672158262423L;

  // Manager
  private UserManager userManager;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ManageUsersAction.class);

  // Model for the Back-End
  private List<User> users;

  @Inject
  public ManageUsersAction(APConfig config, UserManager userManager) {
    super(config);
    this.userManager = userManager;

  }

  public List<User> getUsers() {
    return users;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    users = userManager.getAllEmployees();
  }


  @Override
  public String save() {
    System.out.println("SAVE...");

    return INPUT;

  }

  public void setUsers(List<User> users) {
    this.users = users;
  }
}
