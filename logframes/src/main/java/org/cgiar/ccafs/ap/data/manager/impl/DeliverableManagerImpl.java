package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;
import org.cgiar.ccafs.ap.data.dao.FileFormatDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableMetadata;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.FileFormat;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Metadata;
import org.cgiar.ccafs.ap.data.model.Product;
import org.cgiar.ccafs.ap.data.model.Publication;

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
  private DeliverableTypeManager deliverableTypeManager;

  @Inject
  public DeliverableManagerImpl(DeliverableDAO deliverableDAO, FileFormatDAO fileFormatDAO,
    DeliverableTypeManager deliverableTypeManager) {
    this.deliverableDAO = deliverableDAO;
    this.fileFormatDAO = fileFormatDAO;
    this.deliverableTypeManager = deliverableTypeManager;
  }

  @Override
  public boolean addDeliverable(Deliverable deliverable, int activityID) {
    int deliverableID = createDeliverable(deliverable, activityID);
    return (deliverableID > 0);
  }

  public int createDeliverable(Deliverable deliverable, int activityID) {
    Map<String, Object> deliverableData = new HashMap<>();
    if (deliverable.getId() != -1) {
      deliverableData.put("id", deliverable.getId());
    } else {
      deliverableData.put("id", null);
    }
    deliverableData.put("description", deliverable.getDescription());
    deliverableData.put("year", deliverable.getYear());
    deliverableData.put("activity_id", activityID);
    if (deliverable.getType() != null) {
      deliverableData.put("deliverable_type_id", deliverable.getType().getId());
    } else {
      deliverableData.put("deliverable_type_id", null);
    }
    deliverableData.put("other_type", deliverable.getOtherType());
    deliverableData.put("is_expected", deliverable.isExpected());
    if (deliverable.getStatus() != null) {
      deliverableData.put("deliverable_status_id", deliverable.getStatus().getId());
    } else {
      deliverableData.put("deliverable_status_id", null);
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

    int deliverableId = deliverableDAO.addDeliverable(deliverableData);
    // If deliverable has an id the addDeliverable function return 0 as id,
    // so, the id must be set to its original value
    deliverableId = (deliverable.getId() != -1) ? deliverable.getId() : deliverableId;
    return deliverableId;
  }

  @Override
  public Deliverable getDeliverable(int deliverableID) {
    Deliverable deliverable;
    DeliverableType type;
    int typeID;

    Map<String, String> deliverableData = deliverableDAO.getDeliverable(deliverableID);

    // Create the deliverable type
    if (deliverableData.get("deliverable_type_id") != null) {
      typeID = Integer.parseInt(deliverableData.get("deliverable_type_id"));

      type = deliverableTypeManager.getDeliverableType(typeID + "");
      type.setParent(deliverableTypeManager.getDeliverableTypeBySubType(type.getId()));

      switch (type.getParent().getId()) {
        case APConstants.DELIVERABLE_TYPE_PUBLICATION:
          deliverable = new Publication();
          break;

        case APConstants.DELIVERABLE_TYPE_CASE_STUDIES:
          deliverable = new CaseStudy();
          break;

        default:
          deliverable = new Product();
          break;
      }
    } else {
      deliverable = new Product();
      type = null;
    }

    deliverable.setId(Integer.parseInt(deliverableData.get("id")));

    if (deliverableData.get("year") != null) {
      deliverable.setYear(Integer.parseInt(deliverableData.get("year")));
    }

    deliverable.setDescription(deliverableData.get("description"));
    deliverable.setDescriptionUpdate(deliverableData.get("description_update"));
    deliverable.setType(type);
    deliverable.setOtherType(deliverableData.get("other_type"));

    if (deliverableData.get("isExpected") != null) {
      deliverable.setExpected(false);
    } else {
      deliverable.setExpected(deliverableData.get("is_expected").equals("1"));
    }

    if (deliverableData.get("deliverable_status_id") != null) {
      DeliverableStatus status = new DeliverableStatus();
      status.setId(Integer.parseInt(deliverableData.get("deliverable_status_id")));
      status.setName(deliverableData.get("deliverable_status_name"));
      deliverable.setStatus(status);
    }

    return deliverable;
  }

  @Override
  public Leader getDeliverableLeader(int deliverableID) {
    Leader leader = new Leader();
    Map<String, String> leaderData = deliverableDAO.getDeliverableLeader(deliverableID);

    if (!leaderData.isEmpty()) {
      leader.setId(Integer.parseInt(leaderData.get("id")));
      leader.setAcronym(leaderData.get("acronym"));
      leader.setName(leaderData.get("name"));
    }
    return leader;
  }

  @Override
  public List<Deliverable> getDeliverablesByActivityID(int activityId) {
    List<Map<String, String>> fileFormatsDB;
    List<Map<String, String>> deliverablesDB = deliverableDAO.getDeliverablesByActivityID(activityId);

    List<Deliverable> products = new ArrayList<Deliverable>();
    if (deliverablesDB != null) {
      for (int c = 0; c < deliverablesDB.size(); c++) {

        Deliverable product = new Product();
        product.setId(Integer.parseInt(deliverablesDB.get(c).get("id")));
        product.setDescription(deliverablesDB.get(c).get("description"));
        product.setYear(Integer.parseInt(deliverablesDB.get(c).get("year")));


        /**
         * TODO - Load the filenames related to this product
         */
        // product.setFileName(deliverablesDB.get(c).get("filename"));

        product.setDescriptionUpdate(deliverablesDB.get(c).get("description_update"));
        product.setExpected(Integer.parseInt(deliverablesDB.get(c).get("is_expected")) == 1);

        // DeliverableStatus
        DeliverableStatus status = new DeliverableStatus();
        status.setId(Integer.parseInt(deliverablesDB.get(c).get("deliverable_status_id")));
        status.setName(deliverablesDB.get(c).get("deliverable_status_name"));
        product.setStatus(status);

        // DeliverableType
        DeliverableType type = new DeliverableType();
        type.setId(Integer.parseInt(deliverablesDB.get(c).get("deliverable_type_id")));
        type.setName(deliverablesDB.get(c).get("deliverable_type_name"));
        product.setType(type);

        // File Format
        // LOG.debug("Getting file formats for deliverable {}.", deliverable.getId());
        fileFormatsDB = fileFormatDAO.getFileFormats(product.getId());

        if (fileFormatsDB != null) {
          List<FileFormat> fileFormats = new ArrayList<>();
          for (int i = 0; i < fileFormatsDB.size(); i++) {
            fileFormats.add(new FileFormat(Integer.parseInt(fileFormatsDB.get(i).get("id")), fileFormatsDB.get(i).get(
              "name")));
          }
          product.setFileFormats(fileFormats);
        } else {
          product.setFileFormats(null);
        }

        // Metadata
        List<Map<String, String>> metadataList = deliverableDAO.getDeliverableMetadata(product.getId());
        List<DeliverableMetadata> deliverableMetadata = new ArrayList<>();

        for (Map<String, String> data : metadataList) {
          Metadata metadata = new Metadata();
          metadata.setId(Integer.parseInt(data.get("id")));
          metadata.setDescription(data.get("description"));
          metadata.setName(data.get("name"));

          DeliverableMetadata dm = new DeliverableMetadata();
          dm.setValue(data.get("value"));
          dm.setMetadata(metadata);
          deliverableMetadata.add(dm);
        }
        product.setMetadata(deliverableMetadata);

        products.add(product);
      }
    }

    return products;
  }

  @Override
  public List<Deliverable> getDeliverablesListByLeader(int activityLeaderID) {
    List<Deliverable> deliverables = new ArrayList<>();
    List<Map<String, String>> deliverablesData = deliverableDAO.getDeliverablesListByLeader(activityLeaderID);

    for (Map<String, String> dData : deliverablesData) {
      Deliverable deliverable = new Product();
      deliverable.setId(Integer.parseInt(dData.get("id")));
      deliverable.setDescription(dData.get("description"));
      deliverable.setExpected(dData.get("is_expected").equals("1"));

      if (dData.get("year") != null) {
        deliverable.setYear(Integer.parseInt(dData.get("year")));
      }

      // DeliverableStatus
      DeliverableStatus status = new DeliverableStatus();
      status.setId(Integer.parseInt(dData.get("deliverable_status_id")));
      status.setName(dData.get("deliverable_status_name"));
      deliverable.setStatus(status);

      // DeliverableType
      DeliverableType type = new DeliverableType();
      type.setId(Integer.parseInt(dData.get("deliverable_subtype_id")));
      type.setName(dData.get("deliverable_subtype_name"));

      DeliverableType parent = new DeliverableType();
      parent.setId(Integer.parseInt(dData.get("deliverable_type_id")));
      type.setParent(parent);

      deliverable.setType(type);
      deliverables.add(deliverable);
    }

    return deliverables;
  }

  @Override
  public List<Deliverable> getDeliverablesListByLeader(int activityLeaderID, int year) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Deliverable> getDeliverablesListByTheme(int themeCode) {
    List<Deliverable> deliverables = new ArrayList<>();
    List<Map<String, String>> deliverablesData = deliverableDAO.getDeliverablesListByTheme(themeCode);

    for (Map<String, String> dData : deliverablesData) {
      Deliverable deliverable = new Product();
      deliverable.setId(Integer.parseInt(dData.get("id")));
      deliverable.setDescription(dData.get("description"));
      deliverable.setExpected(dData.get("is_expected").equals("1"));

      if (dData.get("year") != null) {
        deliverable.setYear(Integer.parseInt(dData.get("year")));
      }

      // DeliverableStatus
      DeliverableStatus status = new DeliverableStatus();
      status.setId(Integer.parseInt(dData.get("deliverable_status_id")));
      status.setName(dData.get("deliverable_status_name"));
      deliverable.setStatus(status);

      // DeliverableType
      DeliverableType type = new DeliverableType();
      type.setId(Integer.parseInt(dData.get("deliverable_subtype_id")));
      type.setName(dData.get("deliverable_subtype_name"));

      DeliverableType parent = new DeliverableType();
      parent.setId(Integer.parseInt(dData.get("deliverable_type_id")));
      type.setParent(parent);

      deliverable.setType(type);
      deliverables.add(deliverable);
    }

    return deliverables;
  }

  @Override
  public List<Deliverable> getDeliverablesListByTheme(int themeCode, int year) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Leader getDeliverableThemeLeader(int deliverableID) {
    Leader leader = new Leader();
    Map<String, String> leaderData = deliverableDAO.getDeliverableTheme(deliverableID);

    leader.setId(Integer.parseInt(leaderData.get("id")));
    leader.setAcronym(leaderData.get("acronym"));
    leader.setName(leaderData.get("name"));

    return leader;
  }

  @Override
  public boolean removeDeliverable(int deliverableID) {
    return deliverableDAO.removeDeliverable(deliverableID);
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
  public boolean saveDeliverable(List<Deliverable> products, int activityID) {
    boolean problem = false;
    for (Deliverable product : products) {
      if (!this.addDeliverable(product, activityID)) {
        problem = true;
      }
    }
    return problem;
  }

}
