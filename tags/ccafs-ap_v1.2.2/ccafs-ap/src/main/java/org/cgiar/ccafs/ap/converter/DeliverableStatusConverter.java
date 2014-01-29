package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.DeliverableStatusManager;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class DeliverableStatusConverter extends StrutsTypeConverter {

  private DeliverableStatusManager deliverableStatusManager;

  @Inject
  public DeliverableStatusConverter(DeliverableStatusManager deliverableStatusManager) {
    this.deliverableStatusManager = deliverableStatusManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == DeliverableStatus.class) {
      String id = values[0];
      return deliverableStatusManager.getDeliverableStatus(id);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    DeliverableStatus deliverableStatus = (DeliverableStatus) o;
    return deliverableStatus.getId() + "";
  }

}
