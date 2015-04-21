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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.BoardMessageDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 */
public class MySQLBoardMessageDAO implements BoardMessageDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLBoardMessageDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLBoardMessageDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getAllBoardMessages() {
    List<Map<String, String>> boardMessagesDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * ");
    query.append("FROM board_messages WHERE is_active = TRUE ");
    query.append("ORDER BY created DESC ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> boardMessageData = new HashMap<String, String>();
        boardMessageData.put("id", rs.getString("id"));
        boardMessageData.put("message", rs.getString("message"));
        boardMessageData.put("type", rs.getString("type"));
        boardMessageData.put("created", rs.getTimestamp("created").getTime() + "");

        boardMessagesDataList.add(boardMessageData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the board Messages.", e);
    }
    LOG.debug("-- getAllBoardMessages() > Calling method executeQuery to get the results");
    return boardMessagesDataList;
  }

}
