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
package org.cgiar.ccafs.ap.interceptor.project;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interceptor is used to validate what kind of users are able to access to the pre-planning/projects section.
 *
 * @author Héctor Fabio Tobón R.
 */
public class PreplanningProjectAccessInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -5970028237143387666L;

  private static final Logger LOG = LoggerFactory.getLogger(PreplanningProjectAccessInterceptor.class);

  @Inject
  public PreplanningProjectAccessInterceptor() {
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    LOG.debug("=> PreplanningAccessInterceptor");
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);
    if (user != null) {
      if (user.isAdmin() || user.isFPL() || user.isRPL() || user.isCU()) {
        invocation.invoke();
      } else {
        LOG.info("User identify with id={}, email={}, role={} tried to access pre-planning section.", new Object[] {
          user.getId(), user.getEmail(), user.getRole().getName()});
        return BaseAction.NOT_AUTHORIZED;
      }
    }
    return BaseAction.NOT_LOGGED;
  }

}
