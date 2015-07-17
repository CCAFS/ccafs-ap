package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class DeliverableTypeConverter extends StrutsTypeConverter {

  private DeliverableTypeManager deliverableTypeManager;

  @Inject
  public DeliverableTypeConverter(DeliverableTypeManager deliverableTypeManager) {
    this.deliverableTypeManager = deliverableTypeManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == DeliverableType.class) {
      String id = values[0];
      return deliverableTypeManager.getDeliverableType(id);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    DeliverableType type = (DeliverableType) o;
    return type.getId() + "";
  }

}
