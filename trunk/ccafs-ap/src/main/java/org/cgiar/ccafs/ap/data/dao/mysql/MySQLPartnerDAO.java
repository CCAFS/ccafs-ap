package org.cgiar.ccafs.ap.data.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.PartnerDAO;


public class MySQLPartnerDAO implements PartnerDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLPartnerDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
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
