package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
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


public class MySQLActivityPartnerDAO implements ActivityPartnerDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLActivityPartnerDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLActivityPartnerDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getActivityPartnersList(int activityID) {
    LOG.debug(">> getActivityPartnersList(activityID={})", activityID);
    List<Map<String, String>> activityPartnerList = new ArrayList<>();
    String query =
      "SELECT ap.id, ap.contact_name, ap.contact_email, p.id as 'partner_id', p.acronym as 'partner_acronym', "
        + "p.name as 'partner_name', pt.id as 'partner_type_id', pt.name as 'partner_type_name', "
        + "co.iso2 as 'country_iso2', co.name as 'country_name' " + "FROM activity_partners ap "
        + "INNER JOIN partners p ON p.id = ap.partner_id "
        + "INNER JOIN partner_types pt ON p.partner_type_id = pt.id "
        + "INNER JOIN countries co ON p.country_iso2 = co.iso2 " + "WHERE ap.activity_id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> activityPartnerData = new HashMap<>();
        activityPartnerData.put("id", rs.getString("id"));
        activityPartnerData.put("contact_name", rs.getString("contact_name"));
        activityPartnerData.put("contact_email", rs.getString("contact_email"));
        activityPartnerData.put("partner_id", rs.getString("partner_id"));
        activityPartnerData.put("partner_acronym", rs.getString("partner_acronym"));
        activityPartnerData.put("partner_name", rs.getString("partner_name"));
        activityPartnerData.put("partner_type_id", rs.getString("partner_type_id"));
        activityPartnerData.put("partner_type_name", rs.getString("partner_type_name"));
        activityPartnerData.put("country_iso2", rs.getString("country_iso2"));
        activityPartnerData.put("country_name", rs.getString("country_name"));
        activityPartnerList.add(activityPartnerData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getActivityPartnersList() > There was an error getting activity partners for activity {}.",
        activityID, e);
      return null;
    }
    LOG.debug("<< getActivityPartnersList():activityPartnerList.size={}", activityPartnerList.size());
    return activityPartnerList;
  }

  @Override
  public int getPartnersCount(int activityID) {
    LOG.debug(">> getPartnersCount(activityID={})", activityID);
    int partnersCount = 0;
    String query = "SELECT COUNT(id) FROM activity_partners WHERE activity_id = " + activityID;
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, connection);
      if (rs.next()) {
        partnersCount = rs.getInt(1);
      }
    } catch (SQLException e) {
      LOG.error("-- getPartnersCount() > There was an error counting the activity partners related to the activity {}",
        activityID, e);
    }

    LOG.debug("<< getPartnersCount():{}", partnersCount);
    return partnersCount;
  }

  @Override
  public boolean removeActivityPartners(int activityID) {
    LOG.debug(">> removeActivityPartners(activityID={})", activityID);
    boolean problem = false;
    String removeQuery = "DELETE FROM activity_partners WHERE activity_id = " + activityID;
    try (Connection connection = databaseManager.getConnection()) {
      int rows = databaseManager.makeChange(removeQuery, connection);
      if (rows < 0) {
        problem = true;
      }
    } catch (SQLException e) {
      LOG.error(
        "-- removeActivityPartners > There was an error deleting the activity partners related to activity {}.",
        activityID, e);
    }
    LOG.debug("<< removeActivityPartners():{}", !problem);
    return !problem;
  }

  @Override
  public boolean saveActivityPartnerList(List<Map<String, Object>> activityPartnersData) {
    LOG.debug(">> saveActivityPartnerList(activityPartnersData={})", activityPartnersData);
    boolean problem = false;
    try (Connection connection = databaseManager.getConnection()) {
      for (Map<String, Object> cpData : activityPartnersData) {
        String preparedQuery =
          "INSERT INTO activity_partners (id, partner_id, activity_id, contact_name, contact_email) VALUES (?, ?, ?, ?, ?)";
        Object[] data = new Object[5];
        data[0] = cpData.get("id");
        data[1] = cpData.get("partner_id");
        data[2] = cpData.get("activity_id");
        data[3] = cpData.get("contact_name");
        data[4] = cpData.get("contact_email");
        int rows = databaseManager.makeChangeSecure(connection, preparedQuery, data);
        if (rows < 0) {
          problem = true;
          LOG.warn("-- saveActivityPartnerList() > There was a problem saving an activity partners list.");
        }
      }
    } catch (SQLException e) {
      LOG.error("-- saveActivityPartnerList() > There was an error saving an activity partners list.", e);
    }
    LOG.debug("<< saveActivityPartnerList():{}", !problem);
    return !problem;
  }

}
