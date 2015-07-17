package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.PartnerTypeDAO;

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


public class MySQLPartnerTypeDAO implements PartnerTypeDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLPartnerTypeDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLPartnerTypeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getPartnerTypeList() {
    LOG.debug(">> getPartnerTypeList()");
    List<Map<String, String>> partnerTypeList = new ArrayList<>();
    String query = "SELECT * FROM partner_types";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> partnerTypeData = new HashMap<>();
        partnerTypeData.put("id", rs.getString("id"));
        partnerTypeData.put("acronym", rs.getString("acronym"));
        partnerTypeData.put("name", rs.getString("name"));
        partnerTypeData.put("description", rs.getString("description"));
        partnerTypeList.add(partnerTypeData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getPartnerTypeList() > There was an error getting the list of partner types.", e);
    }

    LOG.debug("<< getPartnerTypeList():partnerTypeList.size={}", partnerTypeList.size());
    return partnerTypeList;
  }

}
