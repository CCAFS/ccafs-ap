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

package org.cgiar.ccafs.security.data.dao.mysql;

import org.cgiar.ccafs.security.data.dao.UserDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class MySQLUserDAO implements UserDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLUserDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLUserDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public Map<String, String> getUserByEmail(String email) {
    Map<String, String> userData = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM users WHERE ");
    query.append("email = '");
    query.append(email);
    query.append("'; ");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        userData.put("id", rs.getString("id"));
        userData.put("password", rs.getString("password"));
        userData.put("username", rs.getString("username"));
      }
    } catch (SQLException e) {
      LOG.error("getUserByEmail() > There was an error getting the information of the user  {}", email, e);
    }
    return userData;
  }

}
