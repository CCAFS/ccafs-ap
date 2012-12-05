package org.cgiar.ccafs.ap.data.manager.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableFormat;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import com.google.inject.Inject;


public class DeliverableManagerImpl implements DeliverableManager {

  private DeliverableDAO deliverableDAO;

  @Inject
  public DeliverableManagerImpl(DeliverableDAO deliverableDAO) {
    this.deliverableDAO = deliverableDAO;
  }

  @Override
  public Deliverable[] getDeliverables(int activityId) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    List<Map<String, String>> deliverablesDB = deliverableDAO.getDeliverables(activityId);

    if (deliverablesDB != null) {
      Deliverable[] deliverables = new Deliverable[deliverablesDB.size()];
      for (int c = 0; c < deliverablesDB.size(); c++) {

        Deliverable deliverable = new Deliverable();
        deliverable.setCode(Integer.parseInt(deliverablesDB.get(c).get("id")));
        deliverable.setDescription(deliverablesDB.get(c).get("description"));
        deliverable.setYear(Integer.parseInt(deliverablesDB.get(c).get("year")));
        deliverable.setExpected(Integer.parseInt(deliverablesDB.get(c).get("is_expected")) == 1);

        // DeliverableStatus
        DeliverableStatus status = new DeliverableStatus();
        status.setName(deliverablesDB.get(c).get("deliverable_status_name"));
        deliverable.setStatus(status);

        // DeliverableType
        DeliverableType type = new DeliverableType();
        type.setName(deliverablesDB.get(c).get("deliverable_type_name"));
        deliverable.setType(type);

        // Deliverable Format
        DeliverableFormat deliverableFormat = new DeliverableFormat();
        deliverableFormat.setName(deliverablesDB.get(c).get("deliverable_type_name"));
        deliverable.setDeliverableFormat(deliverableFormat);

        deliverables[c] = deliverable;
      }

      return deliverables;
    } else {
      return null;
    }
  }

}
