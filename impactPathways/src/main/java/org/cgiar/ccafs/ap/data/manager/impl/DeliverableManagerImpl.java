/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;


import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;
import org.cgiar.ccafs.ap.data.dao.DeliverableDisseminationDAO;
import org.cgiar.ccafs.ap.data.dao.DeliverablePublicationMetadataDAO;
import org.cgiar.ccafs.ap.data.dao.DeliverableRankingDAO;
import org.cgiar.ccafs.ap.data.dao.mysqlhiberate.DeliverableSharingFileMySQLDAO;
import org.cgiar.ccafs.ap.data.dao.mysqlhiberate.DeliverableSharingMySQLDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableDataSharingFile;
import org.cgiar.ccafs.ap.data.model.DeliverableFile;
import org.cgiar.ccafs.ap.data.model.DeliverableMetadataElements;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.MetadataElements;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class DeliverableManagerImpl implements DeliverableManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(DeliverableManagerImpl.class);

  // DAO's
  private DeliverableDAO deliverableDAO;
  // Managers
  private DeliverableTypeManager deliverableTypeManager;
  private NextUserManager nextUserManager;
  private DeliverablePartnerManager deliverablePartnerManager;
  private DeliverableRankingDAO rankingDao;
  private DeliverableDisseminationDAO disseminationDao;
  private DeliverableSharingMySQLDAO sharingDao;
  private DeliverableSharingFileMySQLDAO sharingFileDao;
  private IPProgramManager ipProgramManager;
  private DeliverablePublicationMetadataDAO publicationMetadataDao;

  @Inject
  public DeliverableManagerImpl(DeliverableDAO deliverableDAO, DeliverableTypeManager deliverableTypeManager,
    NextUserManager nextUserManager, DeliverablePartnerManager partnerManager, DeliverableRankingDAO rankingDao,
    DeliverableDisseminationDAO disseminationDao, DeliverableSharingMySQLDAO sharingDao,
    DeliverableSharingFileMySQLDAO sharingFileDao, DeliverablePublicationMetadataDAO publicationMetadataDao,
    IPProgramManager ipProgramManager) {
    this.deliverableDAO = deliverableDAO;
    this.deliverableTypeManager = deliverableTypeManager;
    this.nextUserManager = nextUserManager;
    this.deliverablePartnerManager = partnerManager;
    this.rankingDao = rankingDao;
    this.disseminationDao = disseminationDao;
    this.sharingDao = sharingDao;
    this.sharingFileDao = sharingFileDao;
    this.publicationMetadataDao = publicationMetadataDao;
    this.ipProgramManager = ipProgramManager;
  }

  @Override
  public boolean deleteDeliverable(int deliverableID, User user, String justification) {
    boolean problem = false;
    // Deleting deliverable.
    boolean deleted = deliverableDAO.deleteDeliverable(deliverableID, user.getId(), justification);
    if (!deleted) {
      problem = true;
    }

    // Deleting next users.
    deleted = nextUserManager.deleteNextUserByDeliverable(deliverableID, user, justification);
    if (!deleted) {
      problem = true;
    }

    // Deleting partners contribution
    deleted = deliverablePartnerManager.deleteDeliverablePartnerByDeliverable(deliverableID, user, justification);

    if (!deleted) {
      problem = true;
    }

    return !problem;
  }

  @Override
  public boolean deleteDeliverableOutput(int deliverableID) {
    return deliverableDAO.deleteDeliverableOutput(deliverableID);
  }

  @Override
  public boolean deleteDeliverablesByProject(int projectID) {
    return deliverableDAO.deleteDeliverablesByProject(projectID);
  }


  @Override
  public boolean existDeliverable(int deliverableID) {
    return deliverableDAO.existDeliverable(deliverableID);
  }

  @Override
  public Deliverable getDeliverableById(int deliverableID) {
    Map<String, String> deliverableData = deliverableDAO.getDeliverableById(deliverableID);
    if (!deliverableData.isEmpty()) {
      Deliverable deliverable = new Deliverable();
      // Basic information
      deliverable.setId(deliverableID);
      deliverable.setTitle(deliverableData.get("title"));
      deliverable.setYear(Integer.parseInt(deliverableData.get("year")));
      if (deliverableData.get("status") != null) {
        deliverable.setStatus(Integer.parseInt(deliverableData.get("status")));
      }

      deliverable.setStatusDescription(deliverableData.get("status_description"));

      deliverable.setCreated(Long.parseLong(deliverableData.get("active_since")));
      if (deliverableData.get("type_id") != null) {
        deliverable
          .setType(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableData.get("type_id"))));
      }
      deliverable.setTypeOther(deliverableData.get("type_other"));
      // Next Users
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverableID));
      // MOG
      deliverable.setOutput(this.getDeliverableOutput(deliverableID));
      // Partner Person Responsible
      List<DeliverablePartner> deliverablePartners =
        deliverablePartnerManager.getDeliverablePartners(deliverable.getId(), APConstants.DELIVERABLE_PARTNER_RESP);
      if (deliverablePartners.size() > 0) {
        deliverable.setResponsiblePartner(deliverablePartners.get(0));
      }
      // Other Partner Persons
      deliverable.setOtherPartners(
        deliverablePartnerManager.getDeliverablePartners(deliverable.getId(), APConstants.DELIVERABLE_PARTNER_OTHER));


      deliverable.setRanking(rankingDao.findDeliverableRanking(deliverableID));
      deliverable.setDissemination(disseminationDao.findDeliverableDissemination(deliverableID));
      if (deliverable.getDissemination() != null) {
        if (deliverable.getDissemination().getIntellectualProperty() != null
          && deliverable.getDissemination().getIntellectualProperty()) {
          deliverable.getDissemination().setType("intellectualProperty");
        }
        if (deliverable.getDissemination().getLimitedExclusivity() != null
          && deliverable.getDissemination().getLimitedExclusivity()) {
          deliverable.getDissemination().setType("limitedExclusivity");
        }
        if (deliverable.getDissemination().getRestrictedUseAgreement() != null
          && deliverable.getDissemination().getRestrictedUseAgreement()) {
          deliverable.getDissemination().setType("restrictedAccess");
        }
        if (deliverable.getDissemination().getEffectiveDateRestriction() != null
          && deliverable.getDissemination().getEffectiveDateRestriction()) {
          deliverable.getDissemination().setType("embargoedPeriods");
        }
      }

      // deliverable.setDataSharing(sharingDao.findDeliverableDataSharing(deliverableID));
      deliverable.setDataSharingFile(sharingFileDao.findDeliverableDataSharingFile(deliverableID));
      deliverable.setMetadataElements(disseminationDao.findDeliverableElements(deliverableID));
      deliverable.setMetadata(disseminationDao.findMetadataFields(deliverableID));
      for (MetadataElements field : deliverable.getMetadata()) {
        DeliverableMetadataElements metadata = disseminationDao.findDeliverableMetadata(deliverableID, field.getId());
        field.setElementValueId(metadata.getId());
        field.setValue(metadata.getElementValue());

      }
      List<DeliverableFile> deliverableFile = new ArrayList<>();
      DeliverableFile file;
      if (deliverable.getDataSharingFile() != null) {
        for (DeliverableDataSharingFile dataFile : deliverable.getDataSharingFile()) {
          file = new DeliverableFile();
          file.setHosted(dataFile.getType());
          file.setId(dataFile.getId());
          file.setLink(dataFile.getFile());
          file.setName(dataFile.getFile());
          deliverableFile.add(file);
        }
        deliverable.setFiles(deliverableFile);
      }


      deliverable.setPublicationMetadata(publicationMetadataDao.findDeliverablePublicationMetadata(deliverableID));
      List<IPProgram> ipFlashigps = new ArrayList<>();
      List<String> ipFlashigpsIds = new ArrayList<>();

      if (deliverable.getPublicationMetadata() != null) {
        if (deliverable.getPublicationMetadata().getFp1()) {
          ipFlashigps.add(ipProgramManager.getIPProgramById(1));
          ipFlashigpsIds.add("1");
        }
        if (deliverable.getPublicationMetadata().getFp2()) {
          ipFlashigps.add(ipProgramManager.getIPProgramById(2));
          ipFlashigpsIds.add("2");
        }
        if (deliverable.getPublicationMetadata().getFp3()) {
          ipFlashigps.add(ipProgramManager.getIPProgramById(3));
          ipFlashigpsIds.add("3");
        }
        if (deliverable.getPublicationMetadata().getFp4()) {
          ipFlashigps.add(ipProgramManager.getIPProgramById(4));
          ipFlashigpsIds.add("4");
        }
        deliverable.getPublicationMetadata().setRelatedFlagships(ipFlashigps);
        deliverable.getPublicationMetadata().setRelatedFlagshipsIds(ipFlashigpsIds);
      }


      return deliverable;
    }
    return null;
  }

  @Override
  public IPElement getDeliverableOutput(int deliverableID) {
    IPElement deliverableOutput = new IPElement();
    Map<String, String> deliverableOutputData = deliverableDAO.getDeliverableOutput(deliverableID);
    if (!deliverableOutputData.isEmpty()) {
      deliverableOutput.setId(Integer.parseInt(deliverableOutputData.get("id")));
      deliverableOutput.setDescription(deliverableOutputData.get("description"));

      IPProgram program = new IPProgram(Integer.parseInt(deliverableOutputData.get("ip_program_id")));
      deliverableOutput.setProgram(program);

      IPElementType type = new IPElementType(Integer.parseInt(deliverableOutputData.get("element_type_id")));

      deliverableOutput.setType(type);

      return deliverableOutput;
    }
    return null;
  }

  @Override
  public List<Deliverable> getDeliverablesByProject(int projectID) {
    List<Deliverable> deliverableList = new ArrayList<>();
    List<Map<String, String>> deliverableDataList = deliverableDAO.getDeliverablesByProject(projectID);
    for (Map<String, String> deliverableData : deliverableDataList) {
      Deliverable deliverable = new Deliverable();
      // Deliverable basic information
      deliverable.setId(Integer.parseInt(deliverableData.get("id")));
      deliverable.setTitle(deliverableData.get("title"));
      deliverable.setYear(Integer.parseInt(deliverableData.get("year")));
      deliverable.setCreated(Long.parseLong(deliverableData.get("active_since")));
      if (deliverableData.get("status") != null) {
        deliverable.setStatus(Integer.parseInt(deliverableData.get("status")));
      }

      deliverable.setStatusDescription(deliverableData.get("status_description"));
      // Type
      if (deliverableData.get("type_id") != null) {
        deliverable
          .setType(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableData.get("type_id"))));
      }
      deliverable.setTypeOther(deliverableData.get("type_other"));
      // Next users
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverable.getId()));
      // MOG
      deliverable.setOutput(this.getDeliverableOutput(Integer.parseInt(deliverableData.get("id"))));
      // Partner Person Responsible
      List<DeliverablePartner> deliverablePartners =
        deliverablePartnerManager.getDeliverablePartners(deliverable.getId(), APConstants.DELIVERABLE_PARTNER_RESP);
      if (deliverablePartners.size() > 0) {
        deliverable.setResponsiblePartner(deliverablePartners.get(0));
      }
      // Other Partner Persons
      deliverable.setOtherPartners(
        deliverablePartnerManager.getDeliverablePartners(deliverable.getId(), APConstants.DELIVERABLE_PARTNER_OTHER));

      // adding information of the object to the array
      deliverableList.add(deliverable);
    }
    return deliverableList;
  }

  @Override
  public List<Deliverable> getDeliverablesByProjectPartnerID(int projectPartnerID) {
    List<Deliverable> deliverableList = new ArrayList<>();
    List<Map<String, String>> deliverableDataList = deliverableDAO.getDeliverablesByProjectPartnerID(projectPartnerID);
    for (Map<String, String> deliverableData : deliverableDataList) {
      Deliverable deliverable = new Deliverable();
      // Basic information
      deliverable.setId(Integer.parseInt(deliverableData.get("id")));
      deliverable.setTitle(deliverableData.get("title"));
      deliverable.setYear(Integer.parseInt(deliverableData.get("year")));
      deliverable.setCreated(Long.parseLong(deliverableData.get("active_since")));
      deliverable.setStatus(Integer.parseInt(deliverableData.get("status")));
      deliverable.setStatusDescription(deliverableData.get("status_description"));
      // Type
      if (deliverableData.get("type_id") != null) {
        deliverable
          .setType(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableData.get("type_id"))));
      }
      deliverable.setTypeOther(deliverableData.get("type_other"));
      // adding information of the object to the array
      deliverableList.add(deliverable);
    }
    return deliverableList;
  }

  @Override
  public List<Map<String, String>> getDeliverablesCountByType() {
    return deliverableDAO.getDeliverablesCountByType();
  }

  @Override
  public List<Map<String, String>> getExpectedDeliverablesCountByYear() {
    return deliverableDAO.getExpectedDeliverablesCountByYear();
  }

  @Override
  public List<Deliverable> getProjectDeliverablesLedByUser(int projectID, int userID) {
    List<Deliverable> deliverables = new ArrayList<>();
    List<Map<String, String>> deliverablesData = deliverableDAO.getProjectDeliverablesLedByUser(projectID, userID);

    for (Map<String, String> deliverableData : deliverablesData) {
      Deliverable deliverable = new Deliverable();
      deliverable.setId(Integer.parseInt(deliverableData.get("id")));
      deliverable.setTitle(deliverableData.get("title"));

      deliverables.add(deliverable);
    }

    return deliverables;
  }

  @Override
  public String getStandardIdentifier(Project project, Deliverable deliverable, boolean useComposedCodification) {
    StringBuilder result = new StringBuilder();
    if (useComposedCodification) {
      result.append(APConstants.CCAFS_ORGANIZATION_IDENTIFIER);
      result.append("-P");
      result.append(project.getId());
      result.append("-D");
      result.append(deliverable.getId());
    } else {
      result.append("P");
      result.append(project.getId());
      result.append("-D");
      result.append(deliverable.getId());
    }
    return result.toString();
  }

  @Override
  public int saveDeliverable(int projectID, Deliverable deliverable, User user, String justification) {
    Map<String, Object> deliverableData = new HashMap<>();
    if (deliverable.getId() != -1) {
      deliverableData.put("id", deliverable.getId());
    } else {
      deliverableData.put("id", null);
      deliverableData.put("created_by", user.getId());
    }
    deliverableData.put("project_id", projectID);
    deliverableData.put("title", deliverable.getTitle());
    deliverableData.put("type_id", deliverable.getType() != null ? deliverable.getType().getId() : null);
    deliverableData.put("type_other", deliverable.getTypeOther());
    deliverableData.put("year", deliverable.getYear());
    if (deliverable.getStatus() != -1) {
      deliverableData.put("status", deliverable.getStatus());
      deliverableData.put("status_description", deliverable.getStatusDescription());
    }


    // Logs
    deliverableData.put("modified_by", user.getId());
    deliverableData.put("modification_justification", justification);
    // for (iterable_type iterable_element : iterable) {

    // }
    int deliverableId = 0;
    int result = deliverableDAO.saveDeliverable(deliverableData);
    // if (deliverable.getRanking().getDeliverableId() == null) {
    if (result == 0) {
      deliverable.getRanking().setDeliverableId(new Long(deliverable.getId()));
      deliverable.getDissemination().setDeliverableId((deliverable.getId()));
      // deliverable.getDataSharing().setDeliverableId((deliverable.getId()));
      deliverable.getPublicationMetadata().setDeliverableId((deliverable.getId()));
      deliverableId = deliverable.getId();
    } else {
      if (deliverable.getDissemination() != null) {
        deliverable.getDissemination().setDeliverableId((result));

      }
      if (deliverable.getRanking() != null) {
        deliverable.getRanking().setDeliverableId(new Long(result));
      }

      if (deliverable.getPublicationMetadata() != null) {
        deliverable.getPublicationMetadata().setDeliverableId((result));
      }
      deliverableId = result;
      // deliverable.getDataSharing().setDeliverableId((result));


      // }


    }
    if (deliverable.getPublicationMetadata() != null
      && deliverable.getPublicationMetadata().getRelatedFlagships() != null) {
      deliverable.getPublicationMetadata().setFp1(false);
      deliverable.getPublicationMetadata().setFp2(false);
      deliverable.getPublicationMetadata().setFp3(false);
      deliverable.getPublicationMetadata().setFp4(false);
      for (String flagship : deliverable.getPublicationMetadata().getRelatedFlagshipsIds()) {
        switch (flagship) {
          case "1":
            deliverable.getPublicationMetadata().setFp1(true);
            break;
          case "2":
            deliverable.getPublicationMetadata().setFp2(true);
            break;
          case "3":
            deliverable.getPublicationMetadata().setFp3(true);
            break;
          case "4":
            deliverable.getPublicationMetadata().setFp4(true);
            break;

        }
      }
    }

    // Save Deliverable Ranking
    if (deliverable.getRanking() != null) {
      rankingDao.save(deliverable.getRanking());
    }


    // Save Dissemination
    if (deliverable.getDissemination() != null) {
      // Stablish the type of Dissemination
      if (deliverable.getDissemination().getType() != null) {
        switch (deliverable.getDissemination().getType()) {
          case "intellectualProperty":
            deliverable.getDissemination().setIntellectualProperty(true);
            deliverable.getDissemination().setLimitedExclusivity(false);
            deliverable.getDissemination().setRestrictedUseAgreement(false);
            deliverable.getDissemination().setEffectiveDateRestriction(false);

            break;
          case "limitedExclusivity":
            deliverable.getDissemination().setIntellectualProperty(false);
            deliverable.getDissemination().setLimitedExclusivity(true);
            deliverable.getDissemination().setRestrictedUseAgreement(false);
            deliverable.getDissemination().setEffectiveDateRestriction(false);

            break;
          case "restrictedAccess":
            deliverable.getDissemination().setIntellectualProperty(false);
            deliverable.getDissemination().setLimitedExclusivity(false);
            deliverable.getDissemination().setEffectiveDateRestriction(false);
            deliverable.getDissemination().setRestrictedUseAgreement(true);

            break;
          case "embargoedPeriods":
            deliverable.getDissemination().setIntellectualProperty(false);
            deliverable.getDissemination().setLimitedExclusivity(false);
            deliverable.getDissemination().setEffectiveDateRestriction(true);
            deliverable.getDissemination().setRestrictedUseAgreement(false);

            break;


        }
      }
      disseminationDao.save(deliverable.getDissemination());
    }


    if (deliverable.getMetadataElements() != null) {
      for (DeliverableMetadataElements metadata_elemenet : deliverable.getMetadataElements()) {
        metadata_elemenet.setDeliverableId(deliverableId);
        disseminationDao.saveMetadataElement(metadata_elemenet);
      }
    }

    /// save publication metadata
    if (deliverable.getPublicationMetadata() != null) {
      publicationMetadataDao.save(deliverable.getPublicationMetadata());
    }


    /// Preview Files to remove
    if (deliverable.getDataSharingFile() != null) {
      List<DeliverableDataSharingFile> listPreview = sharingFileDao.findDeliverableDataSharingFile(deliverable.getId());
      if (listPreview != null) {
        for (DeliverableDataSharingFile deliverableDataSharingFile : listPreview) {
          if (!deliverable.getDataSharingFile().contains(deliverableDataSharingFile)) {
            sharingFileDao.delete(deliverableDataSharingFile);
          }
        }
      }

      /// Saving Files
      if (deliverable.getDataSharingFile() != null) {
        for (DeliverableDataSharingFile dataFile : deliverable.getDataSharingFile()) {
          if (result == 0) {
            dataFile.setDeliverableId(deliverable.getId());
          } else {
            dataFile.setDeliverableId(result);
          }
          if (dataFile.getId() == null) {
            sharingFileDao.save(dataFile);
          }

        }
      }


      /// Saving metadata elements
      if (deliverable.getMetadata() != null) {
        for (MetadataElements metadata : deliverable.getMetadata()) {
          DeliverableMetadataElements elementMetadata = new DeliverableMetadataElements();
          elementMetadata.setId(metadata.getElementValueId());
          elementMetadata.setElementValue(metadata.getValue());
          elementMetadata.setMetadataElement(metadata);
          elementMetadata.setElementId(metadata.getId());

          if (result == 0) {
            elementMetadata.setDeliverableId(deliverable.getId());
          } else {
            elementMetadata.setDeliverableId(result);
          }

          disseminationDao.saveMetadataElement(elementMetadata);

        }
      }

    }
    if (result > 0) {
      LOG.debug("saveDeliverable > New Deliverable added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveDeliverable > Deliverable with id={} was updated", deliverable.getId());
    } else {
      LOG.error("saveDeliverable > There was an error trying to save/update a Deliverable from projectId={}",
        projectID);
    }
    return result;
  }

  @Override
  public boolean saveDeliverableOutput(int deliverableID, int outputID, User user, String justification) {
    return deliverableDAO.saveDeliverableOutput(deliverableID, outputID, user.getId(), justification);
  }
}
