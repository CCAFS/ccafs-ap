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

import java.util.Map;

import org.cgiar.ccafs.ap.data.manager.ActivityManager;

import org.apache.struts2.ServletActionContext;
import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.action.BaseAction;
import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interceptor is used to validate if the activity parameter is well passed through the URL.
 *
 * @author Héctor Fabio Tobón R.
 */
public class ValidateActivityParameterInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 1524902722153661399L;

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(ValidateActivityParameterInterceptor.class);

  // Managers
  private ActivityManager activityManager;

  @Inject
  public ValidateActivityParameterInterceptor(ActivityManager activityManager) {
    this.activityManager = activityManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    LOG.debug("=> ValidateActivityParameterInterceptor");
    String actionName = ServletActionContext.getActionMapping().getName();
    // if user is not in section that list the activities.
    if (!actionName.equals("activities")) {
      Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
      // Validate if project parameter exists in the URL.
      if (parameters.get(APConstants.ACTIVITY_REQUEST_ID) != null) {
        String activityParameter = ((String[]) parameters.get(APConstants.ACTIVITY_REQUEST_ID))[0];
        // Validate if the parameter is a number.
        if (StringUtils.isNumeric(activityParameter)) {
          int activityID = Integer.parseInt(activityParameter);
          // If activity doesn't exist.
          if (!activityManager.existActivity(activityID)) {
            return BaseAction.NOT_FOUND;
          } else {
            // If activity exists, continue!
            return invocation.invoke();
          }
        } else {
          // If parameter is not a number.
          return BaseAction.NOT_FOUND;
        }
      } else {
        // if parameter does not exist.
        return BaseAction.NOT_FOUND;
      }
    } else {
      // So far, if user is in the activities list section, do nothing!
      return invocation.invoke();
    }
  }

}
