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

import org.cgiar.ccafs.ap.data.dao.NextUserDAO;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.model.NextUser;
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
public class NextUserManagerImpl implements NextUserManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(NextUserManagerImpl.class);

  // DAO's
  private NextUserDAO nextUserDAO;

  // Managers


  @Inject
  public NextUserManagerImpl(NextUserDAO nextUserDAO) {
    this.nextUserDAO = nextUserDAO;
  }

  @Override
  public boolean deleteNextUserByDeliverable(int deliverableID, User user, String justification) {
    return nextUserDAO.deleteNextUsersByDeliverableId(deliverableID, user.getId(), justification);
  }

  @Override
  public boolean deleteNextUserById(int nextUserId, User user, String justification) {
    return nextUserDAO.deleteNextUserById(nextUserId, user.getId(), justification);
  }

  @Override
  public NextUser getNextUserById(int nextUserID) {
    Map<String, String> nextUserData = nextUserDAO.getNextUserById(nextUserID);
    if (!nextUserData.isEmpty()) {
      NextUser nextUser = new NextUser();
      nextUser.setId(Integer.parseInt(nextUserData.get("id")));
      nextUser.setUser(nextUserData.get("user"));
      nextUser.setExpectedChanges(nextUserData.get("expected_changes"));
      nextUser.setStrategies(nextUserData.get("strategies"));

      return nextUser;
    }
    return null;
  }

  @Override
  public List<NextUser> getNextUsersByDeliverableId(int deliverableID) {
    List<NextUser> nextUsersList = new ArrayList<>();
    List<Map<String, String>> nextUsersDataList = nextUserDAO.getNextUsersByDeliverable(deliverableID);
    for (Map<String, String> nextUserData : nextUsersDataList) {
      NextUser nextUser = new NextUser();
      nextUser.setId(Integer.parseInt(nextUserData.get("id")));
      nextUser.setUser(nextUserData.get("user"));
      nextUser.setExpectedChanges(nextUserData.get("expected_changes"));
      nextUser.setStrategies(nextUserData.get("strategies"));


      // adding information of the object to the array
      nextUsersList.add(nextUser);
    }
    return nextUsersList;
  }

  @Override
  public boolean saveNextUser(int deliverableID, NextUser nextUser, User user, String justification) {

    boolean allSaved = true;
    Map<String, Object> nextUserData = new HashMap<>();
    if (nextUser.getId() != -1) {
      nextUserData.put("id", nextUser.getId());
    } else {
      nextUserData.put("id", null);
      nextUserData.put("created_by", user.getId());
    }
    nextUserData.put("user", nextUser.getUser());
    nextUserData.put("expected_changes", nextUser.getExpectedChanges());
    nextUserData.put("strategies", nextUser.getStrategies());
    // Logs
    nextUserData.put("modified_by", user.getId());
    nextUserData.put("modification_justification", justification);

    int result = nextUserDAO.saveNextUser(deliverableID, nextUserData);

    if (result > 0) {
      LOG.debug("saveNextUser > New Next User added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveNextUser > Next User with id={} was updated", nextUser.getId());
    } else {
      LOG.error("saveNextUser > There was an error trying to save/update a Next User from deliverableId={}",
        deliverableID);
      allSaved = false;
    }

    return allSaved;

  }

  @Override
  public boolean saveNextUsers(int deliverableID, List<NextUser> nextUsers, User currentUser, String justification) {
    boolean success = true;
    boolean saved;
    for (NextUser nextUser : nextUsers) {
      saved = this.saveNextUser(deliverableID, nextUser, currentUser, justification);
      if (!saved) {
        success = false;
      }
    }
    return success;
  }

}
