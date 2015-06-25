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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.config.APModule;
import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * @author Javier Andrés Gallego
 */
public class DeliverableManagerImpl implements DeliverableManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(DeliverableManagerImpl.class);

  /**
   * @param args
   */
  public static void main(String[] args) {
    Injector in = Guice.createInjector(new APModule());
    DeliverableDAO deliverableDAO = in.getInstance(DeliverableDAO.class);

    // Test getDeliverableById
    // System.out.println("Deliverable by id : " + deliverableDAO.getDeliverableById(573).toString());

    // Test getDeliverablesByProject
    // List<Map<String, String>> a = deliverableDAO.getDeliverablesByProject(53);
    // System.out.println("Deliverables by project : " + a);


    // TEST saveDeliverable
    // java.util.Date date = new java.util.Date();
    // Map<String, Object> b = new HashMap<String, Object>();
    // b.put("title", "deliverable test 110");
    // b.put("id", 1311);
    // b.put("activity_id", 11);
    // b.put("created_by", 90);
    // b.put("modified_by", 90);
    // b.put("modification_justification", "");
    // b.put("year", 1880);
    // b.put("type_id", 1);
    // b.put("is_active", 1);
    // b.put("active_since", new java.sql.Timestamp(date.getTime()));
    // System.out.println("Save a deliverable : " + deliverableDAO.saveDeliverable(1, b));

    // Test deleteDeriverable
    // System.out.println("Delete a deliverable : " + deliverableDAO.deleteDeliverable(13));


    // Test deleteDeliverablesByProject
    // System.out.println("Delete a deliverable : " + deliverableDAO.deleteDeliverablesByProject(7));

    // Test delete deleteDeliverableOutput
    // System.out.println("Delete a deliverable output: " + deliverableDAO.deleteDeliverableOutput(677));

    // Test delete getDeliverableOutput
    // System.out.println("get a deliverable output: " + deliverableDAO.getDeliverableOutput(311).toString()); //
    // QUEDAMOS ACA queda pendiemnte

    // Test delete getDeliverableOutput
    System.out.println("save a deliverable output: " + deliverableDAO.saveDeliverableOutput(311, 40, 90, "prueba 1"));


    // Test
    System.out.println("test");


  }

  // DAO's
  private DeliverableDAO deliverableDAO;
  // Managers
  private DeliverableTypeManager deliverableTypeManager;

  private NextUserManager nextUserManager;

  @Inject
  public DeliverableManagerImpl(DeliverableDAO deliverableDAO, DeliverableTypeManager deliverableTypeManager,
    NextUserManager nextUserManager) {
    this.deliverableDAO = deliverableDAO;
    this.deliverableTypeManager = deliverableTypeManager;
    this.nextUserManager = nextUserManager;
  }

  @Override
  public boolean deleteDeliverable(int deliverableId) {
    return deliverableDAO.deleteDeliverable(deliverableId);
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
  public Deliverable getDeliverableById(int deliverableID) {
    Map<String, String> deliverableData = deliverableDAO.getDeliverableById(deliverableID);
    if (!deliverableData.isEmpty()) {
      Deliverable deliverable = new Deliverable();
      deliverable.setId(deliverableID);
      deliverable.setTitle(deliverableData.get("title"));
      deliverable.setYear(Integer.parseInt(deliverableData.get("year")));
      deliverable
        .setType(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableData.get("type_id"))));
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverableID));
      deliverable.setOutput(this.getDeliverableOutput(deliverableID));
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
      deliverable.setId(Integer.parseInt(deliverableData.get("id")));
      deliverable.setTitle(deliverableData.get("title"));
      deliverable.setYear(Integer.parseInt(deliverableData.get("year")));
      deliverable
        .setType(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableData.get("type_id"))));
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(projectID));
      deliverable.setOutput(this.getDeliverableOutput(Integer.parseInt(deliverableData.get("id"))));
      // adding information of the object to the array
      deliverableList.add(deliverable);
    }
    return deliverableList;
  }

  @Override
  public int saveDeliverable(int projectID, Deliverable deliverable) {
    Map<String, Object> deliverableData = new HashMap<>();
    if (deliverable.getId() != -1) {
      deliverableData.put("id", deliverable.getId());
    } else {
      deliverableData.put("id", null);
    }
    deliverableData.put("project_id", projectID);
    deliverableData.put("title", deliverable.getTitle());
    deliverableData.put("type_id", deliverable.getType().getId());
    deliverableData.put("year", deliverable.getYear());

    int result = deliverableDAO.saveDeliverable(projectID, deliverableData);

    if (result > 0) {
      LOG.debug("saveDeliverable > New Deliverable added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveDeliverable > Deliverable with id={} was updated", deliverable.getId());
    } else {
      LOG
      .error("saveDeliverable > There was an error trying to save/update a Deliverable from projectId={}", projectID);
    }

    return result;

  }

  @Override
  public boolean saveDeliverableOutput(int deliverableID, int projectID, int userID, String justification) {
    boolean saved = deliverableDAO.saveDeliverableOutput(deliverableID, projectID, userID, justification);
    return saved;
  }
}
