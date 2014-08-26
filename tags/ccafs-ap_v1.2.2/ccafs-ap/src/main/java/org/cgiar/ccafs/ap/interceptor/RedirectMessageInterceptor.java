/*
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
 */

package org.cgiar.ccafs.ap.interceptor;

import org.cgiar.ccafs.ap.action.BaseAction;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.struts2.dispatcher.ServletActionRedirectResult;
import org.apache.struts2.dispatcher.ServletRedirectResult;

/**
 * An Interceptor to preserve an actions ValidationAware messages across a redirect result. It makes the assumption
 * that you always want to preserve messages across a redirect and restore them to the next action if they exist.
 * The way this works is it looks at the result type after a action has executed and if the result was a redirect
 * (ServletRedirectResult) or a redirectAction (ServletActionRedirectResult) and there were any errors, messages, or
 * fieldErrors they are stored in the session. Before the next action executes it will check if there are any messages
 * stored in the session and add them to the next action.
 */
public class RedirectMessageInterceptor extends MethodFilterInterceptor {

  private static final long serialVersionUID = -1847557437429753540L;

  public static final String FIELD_ERRORS_KEY = "RedirectMessageInterceptor_FieldErrors";
  public static final String ACTION_ERRORS_KEY = "RedirectMessageInterceptor_ActionErrors";
  public static final String ACTION_MESSAGES_KEY = "RedirectMessageInterceptor_ActionMessages";
  public static final String ACTION_WARNINGS_KEY = "RedirectMessageInterceptor_ActionWarnings";

  /**
   * If the result is a redirect then store error and messages in the session.
   */
  protected void after(ActionInvocation invocation, BaseAction action) throws Exception {
    Result result = invocation.getResult();

    if (result != null && (result instanceof ServletRedirectResult || result instanceof ServletActionRedirectResult)) {
      Map<String, Object> session = invocation.getInvocationContext().getSession();

      Collection<String> actionErrors = action.getActionErrors();
      if (actionErrors != null && !actionErrors.isEmpty()) {
        session.put(ACTION_ERRORS_KEY, actionErrors);
      }

      Collection<String> actionMessages = action.getActionMessages();
      if (actionMessages != null && !actionMessages.isEmpty()) {
        session.put(ACTION_MESSAGES_KEY, actionMessages);
      }

      Map<String, List<String>> fieldErrors = action.getFieldErrors();
      if (fieldErrors != null && !fieldErrors.isEmpty()) {
        session.put(FIELD_ERRORS_KEY, fieldErrors);
      }
    }
  }

  /**
   * Retrieve the errors and messages from the session and add them to the action.
   */
  protected void before(ActionInvocation invocation, BaseAction action) throws Exception {
    @SuppressWarnings("unchecked")
    Map<String, ?> session = invocation.getInvocationContext().getSession();

    @SuppressWarnings("unchecked")
    Collection<String> actionErrors = (Collection) session.remove(ACTION_ERRORS_KEY);
    if (actionErrors != null && !actionErrors.isEmpty()) {
      for (String error : actionErrors) {
        action.addActionError(error);
      }
    }

    @SuppressWarnings("unchecked")
    Collection<String> actionMessages = (Collection) session.remove(ACTION_MESSAGES_KEY);
    if (actionMessages != null && !actionMessages.isEmpty()) {
      for (String message : actionMessages) {
        action.addActionMessage(message);
      }
    }

    @SuppressWarnings("unchecked")
    Map<String, List<String>> fieldErrors = (Map) session.remove(FIELD_ERRORS_KEY);
    if (fieldErrors != null && !fieldErrors.isEmpty()) {
      for (Map.Entry<String, List<String>> fieldError : fieldErrors.entrySet()) {
        for (String message : fieldError.getValue()) {
          action.addFieldError(fieldError.getKey(), message);
        }
      }
    }
  }

  @Override
  public String doIntercept(ActionInvocation invocation) throws Exception {
    Object action = invocation.getAction();
    if (action instanceof BaseAction) {
      before(invocation, (BaseAction) action);
    }

    String result = invocation.invoke();

    if (action instanceof BaseAction) {
      after(invocation, (BaseAction) action);
    }
    return result;
  }
}