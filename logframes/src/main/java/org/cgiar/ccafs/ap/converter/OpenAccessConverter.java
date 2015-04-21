package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.OpenAccessManager;
import org.cgiar.ccafs.ap.data.model.OpenAccess;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class OpenAccessConverter extends StrutsTypeConverter {

  private OpenAccessManager openAccessManager;

  @Inject
  public OpenAccessConverter(OpenAccessManager openAccessManager) {
    this.openAccessManager = openAccessManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == OpenAccess.class) {
      return openAccessManager.getOpenAccess(values[0]);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    OpenAccess openAccess = (OpenAccess) o;
    return String.valueOf(openAccess.getId());
  }
}
