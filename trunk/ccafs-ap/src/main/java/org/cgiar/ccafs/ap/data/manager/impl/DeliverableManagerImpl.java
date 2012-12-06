package org.cgiar.ccafs.ap.data.manager.impl;

import java.util.ArrayList;
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
  public List<Deliverable> getDeliverables(int activityId) {
    List<Map<String, String>> deliverablesDB = deliverableDAO.getDeliverables(activityId);

    if (deliverablesDB != null) {
      List<Deliverable> deliverables = new ArrayList<Deliverable>();
      for (int c = 0; c < deliverablesDB.size(); c++) {

        Deliverable deliverable = new Deliverable();
        deliverable.setCode(Integer.parseInt(deliverablesDB.get(c).get("id")));
        deliverable.setDescription(deliverablesDB.get(c).get("description"));
        deliverable.setYear(Integer.parseInt(deliverablesDB.get(c).get("year")));
        deliverable.setExpected(Integer.parseInt(deliverablesDB.get(c).get("is_expected")) == 1);

        // DeliverableStatus
        DeliverableStatus status = new DeliverableStatus();
        status.setId(Integer.parseInt(deliverablesDB.get(c).get("deliverable_status_id")));
        status.setName(deliverablesDB.get(c).get("deliverable_status_name"));
        deliverable.setStatus(status);

        // DeliverableType
        DeliverableType type = new DeliverableType();
        type.setId(Integer.parseInt(deliverablesDB.get(c).get("deliverable_type_id")));
        type.setName(deliverablesDB.get(c).get("deliverable_type_name"));
        deliverable.setType(type);

        // Deliverable Format
        DeliverableFormat deliverableFormat = new DeliverableFormat();

        if (deliverablesDB.get(c).get("deliverable_type_id") != null
          && deliverablesDB.get(c).get("deliverable_type_name") != null) {

          deliverableFormat.setCode(Integer.parseInt(deliverablesDB.get(c).get("deliverable_type_id")));
          deliverableFormat.setName(deliverablesDB.get(c).get("deliverable_type_name"));
          deliverable.setDeliverableFormat(deliverableFormat);
        }

        deliverables.add(deliverable);
      }

      return deliverables;
    } else {
      return null;
    }
  }

}
