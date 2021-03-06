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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLPartnerDAO implements PartnerDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLPartnerDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLPartnerDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getAllPartners() {
    LOG.debug(">> getAllPartners()");
    List<Map<String, String>> partners = new ArrayList<>();
    String query =
      "SELECT p.id, p.acronym, CASE WHEN p.acronym IS NULL THEN CONCAT(p.name, ', ', co.name) ELSE CONCAT(p.acronym, ' - ', p.name, ', ', co.name) END as 'name', pt.id as 'partner_type_id', pt.acronym as 'partner_type_acronym' "
        + "FROM partners p "
        + "INNER JOIN partner_types pt ON pt.id = p.partner_type_id "
        + "INNER JOIN countries co ON p.country_iso2 = co.iso2 " + "ORDER BY p.name";
    try (Connection connection = databaseManager.getConnection()) {
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
      LOG.error("-- getAllPartners() > There was an error getting the list of partners.", e);
    }
    LOG.debug("<< getAllPartners():partners.size={}", partners.size());
    return partners;
  }

  @Override
  public Map<String, String> getPartner(int id) {
    LOG.debug(">> getPartner(id={})", id);
    Map<String, String> partnerData = new HashMap<>();
    String query = "SELECT id, acronym, name FROM partners WHERE id = " + id;
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, connection);
      if (rs.next()) {
        partnerData.put("id", rs.getString("id"));
        partnerData.put("acronym", rs.getString("acronym"));
        partnerData.put("name", rs.getString("name"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getPartner() > There was an error getting the information for partner {}", id, e);
    }

    if (partnerData.isEmpty()) {
      LOG.debug("<< getPartner():null");
      return null;
    }

    LOG.debug("<< getPartner():{}", partnerData);
    return partnerData;
  }

  @Override
  public List<Map<String, String>> getPartnersByFilter(String countryID, String partnerTypeID) {
    LOG.debug(">> getPartnersByFilter(countryID='{}', partnerTypeID='{}')", countryID, partnerTypeID);
    List<Map<String, String>> partners = new ArrayList<>();
    String query =
      "SELECT p.id, p.acronym, CASE WHEN p.acronym IS NULL THEN CONCAT(p.name, ', ', co.name) ELSE CONCAT(p.acronym, ' - ', p.name, ', ', co.name) END as 'name', pt.id as 'partner_type_id', pt.acronym as 'partner_type_acronym' "
        + "FROM partners p "
        + "INNER JOIN partner_types pt ON pt.id = p.partner_type_id "
        + "INNER JOIN countries co ON p.country_iso2 = co.iso2 ";

    if (!countryID.isEmpty() || !partnerTypeID.isEmpty()) {
      query += "WHERE ";

      // Add the conditions if exists
      query += (!countryID.isEmpty()) ? "p.country_iso2 = '" + countryID + "' " : " 1 ";
      query += (!partnerTypeID.isEmpty()) ? "AND p.partner_type_id = '" + partnerTypeID + "' " : "";
    }

    query += "ORDER BY p.name";
    try (Connection connection = databaseManager.getConnection()) {
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
      Object[] errorParams = {countryID, partnerTypeID, e};
      LOG.error(
        "-- getPartnersByFilter() > There was an error getting the partners list for country '{}' and type '{}'",
        errorParams);
    }

    LOG.debug("<< getPartnersByFilter():partners.size={}", partners.size());
    return partners;
  }

  @Override
  public List<Map<String, String>> getPartnersForXML() {
    LOG.debug(">> getPartnersForXML()");
    List<Map<String, String>> partners = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT p.id, p.acronym, p.name as 'name', p.city, p.website, ");
    query.append("pt.id as 'partner_type_id', pt.acronym as 'partner_type_acronym', pt.name as 'partner_type_name', ");
    query.append("co.iso2 as 'country_iso2', co.name as 'country_name', re.id as 'region_id', ");
    query.append("re.name as 'region_name'");
    query.append("FROM partners p ");
    query.append("INNER JOIN partner_types pt ON pt.id = p.partner_type_id ");
    query.append("INNER JOIN countries co ON p.country_iso2 = co.iso2 ");
    query.append("INNER JOIN regions re ON co.region_id = re.id ");
    query.append("GROUP BY p.id ");
    query.append("ORDER BY p.id ");

    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> partnerData = new HashMap<>();
        partnerData.put("id", rs.getString("id"));
        partnerData.put("acronym", rs.getString("acronym"));
        partnerData.put("name", rs.getString("name"));
        partnerData.put("city", rs.getString("city"));
        partnerData.put("website", rs.getString("website"));
        partnerData.put("partner_type_id", rs.getString("partner_type_id"));
        partnerData.put("partner_type_acronym", rs.getString("partner_type_acronym"));
        partnerData.put("partner_type_name", rs.getString("partner_type_name"));
        partnerData.put("country_iso2", rs.getString("country_iso2"));
        partnerData.put("country_name", rs.getString("country_name"));
        partnerData.put("region_id", rs.getString("region_id"));
        partnerData.put("region_name", rs.getString("region_name"));
        partners.add(partnerData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getPartnersForXML() > There was an error getting the list of partners.", e);
    }
    LOG.debug("<< getPartnersForXML():partners.size={}", partners.size());
    return partners;
  }

  @Override
  public List<Map<String, String>> getPartnersList(int activityID) {
    LOG.debug(">> getPartnersList(activityID={})", activityID);
    List<Map<String, String>> statusList = new ArrayList<>();
    String query =
      "SELECT pa.id, pa.name, pa.acronym, pa.city, co.iso2 as 'country_id', co.name as 'country_name', "
        + "ap.contact_name, ap.contact_email, pt.id as 'partner_type_id', pt.name as 'partner_type_name' "
        + "FROM `partners` pa INNER JOIN countries co ON pa.country_iso2 = co.iso2 "
        + "INNER JOIN activity_partners ap ON ap.partner_id = pa.id INNER JOIN  partner_types pt ON pa.partner_type_id = pt.id "
        + "WHERE ap.activity_id=" + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> statusData = new HashMap<>();
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
      LOG.error("-- getPartnersList() > There was an error getting the partners list for activity {}", activityID, e);
      return null;
    }

    if (statusList.size() == 0) {
      return null;
    }

    LOG.debug("<< getPartnersList():statusList.size={}", statusList.size());
    return statusList;
  }

  @Override
  public boolean isCurrentlyActive(int partnerID, int year) {
    StringBuilder query = new StringBuilder();
    boolean isActive = false;

    query.append("SELECT ap.partner_id, a.id ");
    query.append("FROM `activity_partners` ap ");
    query.append("INNER JOIN activities a ON ap.activity_id = a.id ");
    query.append("WHERE a.year = ");
    query.append(year);
    query.append(" AND ap.partner_id = ");
    query.append(partnerID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        isActive = true;
      }
    } catch (SQLException e) {
      LOG.debug("-- isCurrentlyActive(): There was an exception verifying if the partner {} is active", partnerID, e);
    }

    return isActive;
  }

}