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

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.LiaisonInstitutionDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

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
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class MySQLLiaisonInstitutionDAO implements LiaisonInstitutionDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLLiaisonInstitutionDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLLiaisonInstitutionDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public Map<String, String> getLiaisonInstitution(int liaisionInstitutionID) {
    Map<String, String> liaisonInstitution = new HashMap<>();
    String query = "SELECT * FROM liaison_institutions WHERE id = " + liaisionInstitutionID;

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query, con);
      if (rs.next()) {
        liaisonInstitution.put("id", rs.getString("id"));
        liaisonInstitution.put("name", rs.getString("name"));
        liaisonInstitution.put("acronym", rs.getString("acronym"));
      }
    } catch (SQLException e) {
      LOG.error("getLiaisonInstitution() > Exception raised trying to get the liaison institution {}.",
        liaisionInstitutionID, e);
    }

    return liaisonInstitution;
  }

  @Override
  public Map<String, String> getLiaisonInstitutionByUser(int userID) {
    Map<String, String> liaisonInstitution = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT li.id, li.name, li.acronym FROM liaison_institutions li ");
    query.append("INNER JOIN liaison_users lu ON li.id = lu.institution_id ");
    query.append("INNER JOIN users u ON lu.user_id = u.id ");
    query.append("WHERE u.id = ");
    query.append(userID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        liaisonInstitution.put("id", rs.getString("id"));
        liaisonInstitution.put("name", rs.getString("name"));
        liaisonInstitution.put("acronym", rs.getString("acronym"));
      }
    } catch (SQLException e) {
      LOG.error(
        "getLiaisonInstitution() > Exception raised trying to get the liaison institution linked to the user {}.",
        userID, e);
    }

    return liaisonInstitution;
  }

  @Override
  public List<Map<String, String>> getLiaisonInstitutions() {
    List<Map<String, String>> liaisonInstitutions = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * ");
    query.append("FROM liaison_institutions li ");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);

      while (rs.next()) {
        Map<String, String> liaisonInstitution = new HashMap<>();
        liaisonInstitution.put("id", rs.getString("id"));
        liaisonInstitution.put("name", rs.getString("name"));
        liaisonInstitution.put("acronym", rs.getString("acronym"));
        liaisonInstitutions.add(liaisonInstitution);
      }

    } catch (SQLException e) {
      LOG.error("getLiaisonInstitutions() > Exception raised trying to get the liaison institutions.", e);
    }

    return liaisonInstitutions;
  }


  @Override
  public List<Map<String, String>> getLiaisonInstitutionsCenter() {
    List<Map<String, String>> liaisonInstitutions = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * ");
    query.append("FROM liaison_institutions li where id not in (1,2,3,4,5,6,7,8,9,10)");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);

      while (rs.next()) {
        Map<String, String> liaisonInstitution = new HashMap<>();
        liaisonInstitution.put("id", rs.getString("id"));
        liaisonInstitution.put("name", rs.getString("name"));
        liaisonInstitution.put("acronym", rs.getString("acronym"));
        liaisonInstitutions.add(liaisonInstitution);
      }

    } catch (SQLException e) {
      LOG.error("getLiaisonInstitutions() > Exception raised trying to get the liaison institutions.", e);
    }

    return liaisonInstitutions;
  }


  @Override
  public List<Map<String, String>> getLiaisonInstitutionsCrps() {
    List<Map<String, String>> liaisonInstitutions = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * ");
    query.append("FROM liaison_institutions li where id not in(1,6,7,8,9,10) ");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);

      while (rs.next()) {
        Map<String, String> liaisonInstitution = new HashMap<>();
        liaisonInstitution.put("id", rs.getString("id"));
        liaisonInstitution.put("name", rs.getString("name"));
        liaisonInstitution.put("acronym", rs.getString("acronym"));
        liaisonInstitutions.add(liaisonInstitution);
      }

    } catch (SQLException e) {
      LOG.error("getLiaisonInstitutions() > Exception raised trying to get the liaison institutions.", e);
    }

    return liaisonInstitutions;
  }

}
