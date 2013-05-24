package org.cgiar.ccafs.ap.interceptor;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.data.model.User.UserRole;

import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AccessibleActivityInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 3723021484076686914L;

  private ActivityManager activityManager;
  private LeaderManager leaderManager;

  @Inject
  public AccessibleActivityInterceptor(ActivityManager activityManager, LeaderManager leaderManager) {
    this.activityManager = activityManager;
    this.leaderManager = leaderManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    Map<String, Object> request = invocation.getInvocationContext().getParameters();
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    String[] activityIdParam = (String[]) request.get(APConstants.ACTIVITY_REQUEST_ID);
    User user = (User) session.get(APConstants.SESSION_USER);

    if (activityIdParam != null) {
      int activityID = Integer.parseInt(activityIdParam[0]);
      // validate if activity actually exists.
      if (activityManager.isValidId(activityID)) {
        // is the user an admin?
        if (user.getRole() == UserRole.Admin) {
          return invocation.invoke();
        } else {
          // validate if current user has the enough privileges to access to this activity.
          Leader activityLeader = leaderManager.getActivityLeader(activityID);
          Leader userLeader = user.getLeader();
          if (activityLeader.equals(userLeader)) {
            return invocation.invoke();
          } else {
            return BaseAction.NOT_AUTHORIZED;
          }
        }

      }
      // activity id is not valid and was not found.
      return BaseAction.NOT_FOUND;
    }
    // activity not found.
    return BaseAction.NOT_FOUND;
  }
}