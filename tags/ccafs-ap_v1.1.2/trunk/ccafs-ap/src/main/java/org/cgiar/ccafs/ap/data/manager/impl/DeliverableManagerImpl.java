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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeliverableManagerImpl implements DeliverableManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(DeliverableManagerImpl.class);
  private DeliverableDAO deliverableDAO;
  private FileFormatDAO fileFormatDAO;

  @Inject
  public DeliverableManagerImpl(DeliverableDAO deliverableDAO, FileFormatDAO fileFormatDAO) {
    this.deliverableDAO = deliverableDAO;
    this.fileFormatDAO = fileFormatDAO;
  }

  @Override
  public boolean addDeliverable(Deliverable deliverable, int activityID) {
    Map<String, Object> deliverableData = new HashMap<>();
    if (deliverable.getId() != -1) {
      deliverableData.put("id", deliverable.getId());
    } else {
      deliverableData.put("id", null);
    }
    deliverableData.put("description", deliverable.getDescription());
    deliverableData.put("year", deliverable.getYear());
    deliverableData.put("activity_id", activityID);
    deliverableData.put("deliverable_type_id", deliverable.getType().getId());
    deliverableData.put("is_expected", deliverable.isExpected());
    deliverableData.put("deliverable_status_id", deliverable.getStatus().getId());
    if (deliverable.getFileName() == null || deliverable.getFileName().isEmpty()) {
      deliverableData.put("filename", null);
    } else {
      deliverableData.put("filename", deliverable.getFileName());
    }
    // When deliverable is new, the attribute descriptionUpdate is null
    if (deliverable.getDescriptionUpdate() == null) {
      deliverableData.put("description_update", null);
    } else {
      if (deliverable.getDescriptionUpdate().isEmpty()) {
        deliverableData.put("description_update", null);
      } else {
        deliverableData.put("description_update", deliverable.getDescriptionUpdate());
      }
    }
    deliverableData.put("file_format_ids", deliverable.getFileFormatsIds());

    int deliverableId = deliverableDAO.addDeliverable(deliverableData);
    // If deliverable has an id the addDeliverable function return 0 as id,
    // so, the id must be set to its original value
    deliverableId = (deliverable.getId() != -1) ? deliverable.getId() : deliverableId;

    // If it is a new deliverable insert the file formats
    if (deliverable.getId() == -1) {
      // Check if the deliverable has file formats
      if (!deliverable.getFileFormatsIds().isEmpty()) {
        // lets add the file format list.
        boolean fileFormatsAdded = fileFormatDAO.addFileFormats(deliverableId, deliverable.getFileFormatsIds());
        if (!fileFormatsAdded) {
          LOG.warn("There was a problem saving the file formats for the deliverable {}.", deliverableId);
          return false;
        }
      }
    }
    // LOG.debug("The deliverable {} was successfully saved", deliverableId);
    return true;
  }

  @Override
  public List<Deliverable> getDeliverables(int activityId) {
    List<Map<String, String>> fileFormatsDB;
    List<Map<String, String>> deliverablesDB = deliverableDAO.getDeliverables(activityId);

    List<Deliverable> deliverables = new ArrayList<Deliverable>();
    if (deliverablesDB != null) {
      for (int c = 0; c < deliverablesDB.size(); c++) {

        Deliverable deliverable = new Deliverable();
        deliverable.setId(Integer.parseInt(deliverablesDB.get(c).get("id")));
        deliverable.setDescription(deliverablesDB.get(c).get("description"));
        deliverable.setYear(Integer.parseInt(deliverablesDB.get(c).get("year")));
        deliverable.setFileName(deliverablesDB.get(c).get("filename"));
        deliverable.setDescriptionUpdate(deliverablesDB.get(c).get("description_update"));
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
        // LOG.debug("Getting file formats for deliverable {}.", deliverable.getId());
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
    }

    return deliverables;
  }

  @Override
  public boolean removeExpected(int activityID) {
    return deliverableDAO.removeExpected(activityID);
  }

  @Override
  public boolean removeNotExpected(int activityID) {
    return deliverableDAO.removeNotExpected(activityID);
  }

  @Override
  public boolean saveDeliverables(List<Deliverable> deliverables, int activityID) {
    boolean problem = false;
    for (Deliverable deliverable : deliverables) {
      if (!this.addDeliverable(deliverable, activityID)) {
        problem = true;
      }
    }
    return problem;
  }

}
