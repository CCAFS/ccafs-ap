package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.StatusManager;
import org.cgiar.ccafs.ap.data.model.Status;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class StatusTypeConverter extends StrutsTypeConverter {

  private StatusManager statusManager;

  @Inject
  public StatusTypeConverter(StatusManager statusManager) {
    this.statusManager = statusManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == Status.class) {
      String id = values[0];
      return statusManager.getStatus(id);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Status status = (Status) o;
    return status.getId() + "";
  }

}
