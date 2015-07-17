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

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.security.SecurityContext;

import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RPLAccessOnlyInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 5617850005062864332L;
  private static final Logger LOG = LoggerFactory.getLogger(RPLAccessOnlyInterceptor.class);

  @Inject
  protected SecurityContext securityContext;

  @Inject
  public RPLAccessOnlyInterceptor() {
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    LOG.debug("=> RPLAccessOnlyInterceptor");
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);
    if (user != null) {
      if (securityContext.isAdmin() || securityContext.isRPL()) {
        invocation.invoke();
      } else {
        LOG.info("User identify with id={}, email={}, tried to access a section only for RPLs.",
          new Object[] {user.getId(), user.getEmail()});
        return BaseAction.NOT_AUTHORIZED;
      }
    }
    return BaseAction.NOT_LOGGED;
  }


}
