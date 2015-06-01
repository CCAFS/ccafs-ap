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

package org.cgiar.ccafs.ap.action.json.global;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class SearchUserAction extends BaseAction {

  private static final long serialVersionUID = 281018603716118132L;
  private static Logger LOG = LoggerFactory.getLogger(SearchUserAction.class);

  private UserManager userManager;

  private String queryParameter;
  private List<User> users;

  @Inject
  public SearchUserAction(APConfig config, UserManager userManager) {
    super(config);
    this.userManager = userManager;
  }

  @Override
  public String execute() throws Exception {
    users = userManager.searchUser(queryParameter);

    LOG.info("The search of users by '{}' was made successfully.", queryParameter);
    return SUCCESS;
  }

  public List<User> getUsers() {
    return users;
  }

  @Override
  public void prepare() throws Exception {
    // If there is a country ID take its values
    queryParameter = StringUtils.trim(this.getRequest().getParameter(APConstants.QUERY_PARAMETER));
  }
}
