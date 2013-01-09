package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;
import org.cgiar.ccafs.ap.data.dao.FileFormatDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.FileFormat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class DeliverableManagerImpl implements DeliverableManager {

  private DeliverableDAO deliverableDAO;
  private FileFormatDAO fileFormatDAO;

  @Inject
  public DeliverableManagerImpl(DeliverableDAO deliverableDAO, FileFormatDAO fileFormatDAO) {
    this.deliverableDAO = deliverableDAO;
    this.fileFormatDAO = fileFormatDAO;
  }

  @Override
  public boolean addDeliverable(Deliverable deliverable, int activityID, boolean isExpected) {
    Map<String, Object> deliverableData = new HashMap<>();
    deliverableData.put("description", deliverable.getDescription());
    deliverableData.put("year", deliverable.getYear());
    deliverableData.put("activity_id", activityID);
    deliverableData.put("deliverable_type_id", deliverable.getType().getId());
    deliverableData.put("is_expected", isExpected ? 1 : 0);
    deliverableData.put("deliverable_status_id", deliverable.getStatus().getId());

    deliverableData.put("file_format_ids", deliverable.getFileFormatsIds());

    int deliverableId = deliverableDAO.addDeliverable(deliverableData);
    if (deliverableId >= 0) {
      // Check if the deliverable has file formats
      if (!deliverable.getFileFormatsIds().isEmpty()) {
        // lets add the file format list.
        boolean fileFormatsAdded = fileFormatDAO.addFileFormats(deliverableId, deliverable.getFileFormatsIds());
        if (!fileFormatsAdded) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public List<Deliverable> getDeliverables(int activityId) {
    List<Map<String, String>> fileFormatsDB;
    List<Map<String, String>> deliverablesDB = deliverableDAO.getDeliverables(activityId);

    if (deliverablesDB != null) {
      List<Deliverable> deliverables = new ArrayList<Deliverable>();
      for (int c = 0; c < deliverablesDB.size(); c++) {

        Deliverable deliverable = new Deliverable();
        deliverable.setId(Integer.parseInt(deliverablesDB.get(c).get("id")));
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
        fileFormatsDB = fileFormatDAO.getFileFormats(deliverable.getId());

        if (fileFormatsDB != null) {
          List<FileFormat> fileFormats = new ArrayList<>();
          for (int i = 0; i < fileFormatsDB.size(); i++) {
            fileFormats.add(new FileFormat(Integer.parseInt(fileFormatsDB.get(i).get("id")), fileFormatsDB.get(i).get(
              "name")));
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

  @Override
  public boolean removeNotExpected(int activityID) {
    return deliverableDAO.removeNotExpected(activityID);
  }

}
