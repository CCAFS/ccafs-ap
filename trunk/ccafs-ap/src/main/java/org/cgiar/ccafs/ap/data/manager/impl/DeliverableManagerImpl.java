package org.cgiar.ccafs.ap.data.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;
import org.cgiar.ccafs.ap.data.dao.FileFormatDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.FileFormat;


public class DeliverableManagerImpl implements DeliverableManager {

  private DeliverableDAO deliverableDAO;
  private FileFormatDAO fileFormatDAO;

  @Inject
  public DeliverableManagerImpl(DeliverableDAO deliverableDAO, FileFormatDAO fileFormatDAO) {
    this.deliverableDAO = deliverableDAO;
    this.fileFormatDAO = fileFormatDAO;
  }

  @Override
  public List<Deliverable> getDeliverables(int activityId) {
    List<Map<String, String>> fileFormatsDB;
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

        // File Format
        fileFormatsDB = fileFormatDAO.getFileFormats(deliverable.getCode());

        if (fileFormatsDB != null) {
          FileFormat[] fileFormats = new FileFormat[fileFormatsDB.size()];

          for (int i = 0; i < fileFormatsDB.size(); i++) {
            fileFormats[i] =
              new FileFormat(Integer.parseInt(fileFormatsDB.get(i).get("id")), fileFormatsDB.get(i).get("name"));
          }
          deliverable.setFileFormats(fileFormats);
        } else {
          deliverable.setFileFormats(null);
        }


        deliverables.add(deliverable);
      }

      return deliverables;
    } else {
      return null;
    }
  }

}
