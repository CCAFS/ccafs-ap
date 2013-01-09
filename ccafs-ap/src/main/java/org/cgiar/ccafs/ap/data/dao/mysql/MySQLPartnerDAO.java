package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.PartnerDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLPartnerDAO implements PartnerDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLPartnerDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getAllPartners() {
    List<Map<String, String>> partners = new ArrayList<>();
    try (Connection connection = databaseManager.getConnection()) {
      String query =
        "SELECT p.id, p.acronym, p.name, pt.id as 'partner_type_id', pt.acronym as 'partner_type_acronym' "
          + "FROM partners p " + "INNER JOIN partner_types pt ON pt.id = p.partner_type_id " + "ORDER BY p.name";
      ResultSet rs = databaseManager.makeQuery(query, connection);
      while (rs.next()) {
        Map<String, String> partnerData = new HashMap<>();
        partnerData.put("id", rs.getString("id"));
        partnerData.put("acronym", rs.getString("acronym"));
        partnerData.put("name", rs.getString("name"));
        partnerData.put("partner_type_id", rs.getString("partner_type_id"));
        partnerData.put("partner_type_acronym", rs.getString("partner_type_acronym"));

        partners.add(partnerData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return partners;
  }

  @Override
  public Map<String, String> getPartner(int id) {
    Map<String, String> partnerData = new HashMap<>();
    try (Connection connection = databaseManager.getConnection()) {
      String query = "SELECT id, acronym, name FROM partners WHERE id = " + id;
      ResultSet rs = databaseManager.makeQuery(query, connection);
      if (rs.next()) {
        partnerData.put("id", rs.getString("id"));
        partnerData.put("acronym", rs.getString("acronym"));
        partnerData.put("name", rs.getString("name"));
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (partnerData.size() > 0) {
      return partnerData;
    }
    return null;
  }

  @Override
  public List<Map<String, String>> getPartnersList(int activityID) {
    List<Map<String, String>> statusList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT pa.id, pa.name, pa.acronym, pa.city, co.iso2 as 'country_id', co.name as 'country_name', "
          + "ap.contact_name, ap.contact_email, pt.id as 'partner_type_id', pt.name as 'partner_type_name' "
          + "FROM `partners` pa INNER JOIN countries co ON pa.country_iso2 = co.iso2 "
          + "INNER JOIN activity_partners ap ON ap.partner_id = pa.id INNER JOIN  partner_types pt ON pa.partner_type_id = pt.id "
          + "WHERE ap.activity_id=" + activityID;
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> statusData = new HashMap();
        statusData.put("id", rs.getString("id"));
        statusData.put("name", rs.getString("name"));
        statusData.put("acronym", rs.getString("acronym"));
        statusData.put("city", rs.getString("city"));
        statusData.put("country_id", rs.getString("country_id"));
        statusData.put("country_name", rs.getString("country_name"));
        statusData.put("contact_name", rs.getString("contact_name"));
        statusData.put("contact_email", rs.getString("contact_email"));
        statusData.put("partner_type_id", rs.getString("partner_type_id"));
        statusData.put("partner_type_name", rs.getString("partner_type_name"));
        statusList.add(statusData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }

    if (statusList.size() == 0) {
      return null;
    }
    return statusList;
  }

}
