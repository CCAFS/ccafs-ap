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

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interceptor is responsible for validating if the user is actually logged or not, in order to be able to access
 * the contents of the specified page.
 * If there is no an user in the current session it will return a 401 error (Authentication Required).
 *
 * @author Héctor Fabio Tobón R.
 */
public class RequireUserInterceptor extends AbstractInterceptor {


  private static final Logger LOG = LoggerFactory.getLogger(RequireUserInterceptor.class);

  private static final long serialVersionUID = 7739155018211386527L;

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    LOG.debug("=> RequireUserInterceptor");
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);
    if (user != null) {
      return invocation.invoke();
    }
    /*
     * TODO HT - I tried to save the requested URL so the user can be redirected ones he is logged in.
     * else {
     * HttpServletRequest request = ServletActionContext.getRequest();
     * session.put("savedUrl",
     * request.getRequestURI() + (request.getQueryString() == null ? "" : ("?" + request.getQueryString())));
     * }
     */
    return BaseAction.NOT_LOGGED;
  }


}
