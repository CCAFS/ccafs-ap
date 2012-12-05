package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.model.Status;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;


public class StatusTypeConverter extends StrutsTypeConverter {

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == Status.class) {
      String value = values[0];
      Status status = new Status();
      status.setId(Integer.parseInt(value));
      status.setName("NONE");
      return status;
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Status status = (Status) o;
    return status.getId() + "";
  }

}
