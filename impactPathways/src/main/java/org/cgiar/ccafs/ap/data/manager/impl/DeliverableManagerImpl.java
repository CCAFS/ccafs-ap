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
import org.cgiar.ccafs.ap.data.dao.DeliverableRankingDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPProgram;
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

  @Inject
  public DeliverableManagerImpl(DeliverableDAO deliverableDAO, DeliverableTypeManager deliverableTypeManager,
    NextUserManager nextUserManager, DeliverablePartnerManager partnerManager, DeliverableRankingDAO rankingDao) {
    this.deliverableDAO = deliverableDAO;
    this.deliverableTypeManager = deliverableTypeManager;
    this.nextUserManager = nextUserManager;
    this.deliverablePartnerManager = partnerManager;
    this.rankingDao = rankingDao;
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
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(projectID));
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

    deliverableData.put("status", deliverable.getStatus());
    deliverableData.put("status_description", deliverable.getStatusDescription());
    // Logs
    deliverableData.put("modified_by", user.getId());
    deliverableData.put("modification_justification", justification);

    int result = deliverableDAO.saveDeliverable(deliverableData);
    if (deliverable.getRanking().getDeliverableId() == null) {
      if (result == 0) {
        deliverable.getRanking().setDeliverableId(new Long(deliverable.getId()));
      } else {
        deliverable.getRanking().setDeliverableId(new Long(result));
      }

    }

    rankingDao.save(deliverable.getRanking());
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
