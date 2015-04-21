package org.cgiar.ccafs.ap.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import org.apache.commons.lang3.StringUtils;

/**
 * This interceptor is responsible for validating if the role of user is actually TL or RPL,
 * in order to be able to access the contents of the specified page.
 * If there is no an authorized user in the current session it will return
 * a 401 error (Authentication Required).
 */
public class TrimInterceptor extends MethodFilterInterceptor {

  private List<String> excluded = new ArrayList<>();

  @Override
  protected String doIntercept(ActionInvocation invocation) throws Exception {
    Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
    for (String param : parameters.keySet()) {
      if (isIncluded(param)) {
        String[] vals = (String[]) parameters.get(param);
        for (int i = 0; i < vals.length; i++) {
          vals[i] = vals[i].trim();
        }
      }
    }
    return invocation.invoke();
  }

  public boolean isIncluded(String param) {
    for (String exclude : excluded) {
      if (param.startsWith(exclude)) {
        return false;
      }
    }
    return true;
  }

  public void setExcludedParams(String excludedParams) {
    for (String s : StringUtils.split(excludedParams, ",")) {
      excluded.add(s.trim());
    }
  }
}
