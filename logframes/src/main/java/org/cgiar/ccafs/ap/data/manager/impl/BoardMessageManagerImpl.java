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

import org.cgiar.ccafs.ap.data.dao.BoardMessageDAO;
import org.cgiar.ccafs.ap.data.manager.BoardMessageManager;
import org.cgiar.ccafs.ap.data.model.BoardMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 */
public class BoardMessageManagerImpl implements BoardMessageManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(BoardMessageManagerImpl.class);

  // DAO's
  private BoardMessageDAO boardMessageDAO;

  // Managers

  @Inject
  public BoardMessageManagerImpl(BoardMessageDAO boardMessageDAO) {
    this.boardMessageDAO = boardMessageDAO;
  }

  @Override
  public List<BoardMessage> getAllBoardMessages() {
    List<BoardMessage> boardMessageList = new ArrayList<>();
    List<Map<String, String>> boardMessageDataList = boardMessageDAO.getAllBoardMessages();
    for (Map<String, String> boardMessageData : boardMessageDataList) {
      BoardMessage boardMessage = new BoardMessage();
      boardMessage.setId(Integer.parseInt(boardMessageData.get("id")));
      boardMessage.setMessage(boardMessageData.get("message"));
      boardMessage.setType(boardMessageData.get("type"));
      boardMessage.setCreated(Long.parseLong(boardMessageData.get("created")));

      boardMessageList.add(boardMessage);
    }
    // adding information to the object to the array
    return boardMessageList;
  }
}
