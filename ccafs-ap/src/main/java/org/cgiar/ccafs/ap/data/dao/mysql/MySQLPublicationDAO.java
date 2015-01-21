package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.PublicationDAO;

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


public class MySQLPublicationDAO implements PublicationDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLPublicationDAO.class);

  private DAOManager dbManager;

  @Inject
  public MySQLPublicationDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public Map<String, String> getPublication(int deliverableID) {
    LOG.debug(">> getPublication(deliverableID={})", deliverableID);
    Map<String, String> publicationData = new HashMap<>();
    try (Connection connection = dbManager.getConnection()) {
      String query =
        "SELECT p.id, p.identifier, p.citation, p.file_url, p.isi_publication, p.nars_coauthor, "
          + "p.earth_system_coauthor, p.ccafs_acknowledge, pt.id as 'publication_type_id', "
          + "pt.name as 'publication_type_name', oa.id as 'publication_access_id', "
          + "oa.name as 'publication_access_name' " + "FROM publications p "
          + "LEFT JOIN publication_types pt ON pt.id = p.publication_type_id "
          + "LEFT JOIN open_access oa ON p.open_access_id = oa.id " + "WHERE deliverable_id = " + deliverableID;

      ResultSet rs = dbManager.makeQuery(query, connection);
      if (rs.next()) {
        publicationData.put("id", rs.getString("id"));
        publicationData.put("identifier", rs.getString("identifier"));
        publicationData.put("citation", rs.getString("citation"));
        publicationData.put("file_url", rs.getString("file_url"));
        publicationData.put("ccafs_acknowledge", rs.getString("ccafs_acknowledge"));
        publicationData.put("isi_publication", rs.getString("isi_publication"));
        publicationData.put("nars_coauthor", rs.getString("nars_coauthor"));
        publicationData.put("earth_system_coauthor", rs.getString("earth_system_coauthor"));
        publicationData.put("publication_type_id", rs.getString("publication_type_id"));
        publicationData.put("publication_type_name", rs.getString("publication_type_name"));
        publicationData.put("publication_access_id", rs.getString("publication_access_id"));
        publicationData.put("publication_access_name", rs.getString("publication_access_name"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getPublication() > Exception rised getting the publication linked to the deliverable {}",
        deliverableID, e);
    }

    LOG.debug("<< getPublications():publication={}", publicationData);
    return publicationData;
  }

  @Override
  public List<Map<String, String>> getPublications(int leaderId, int logframeId) {
    LOG.debug(">> getPublications(leaderId={}, logframeId={})", leaderId, logframeId);
    List<Map<String, String>> publications = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {
      String query =
        "SELECT p.id, p.identifier, p.citation, p.file_url, p.isi_publication, p.nars_coauthor, "
          + "p.earth_system_coauthor, p.ccafs_acknowledge, pt.id as 'publication_type_id', "
          + "pt.name as 'publication_type_name', oa.id as 'publication_access_id', "
          + "oa.name as 'publication_access_name' " + "FROM publications p "
          + "INNER JOIN publication_types pt ON pt.id = p.publication_type_id "
          + "LEFT JOIN open_access oa ON p.open_access_id = oa.id " + "WHERE activity_leader_id = " + leaderId
          + " AND logframe_id = " + logframeId;
      ResultSet rs = dbManager.makeQuery(query, connection);
      while (rs.next()) {
        Map<String, String> publicationData = new HashMap<>();
        publicationData.put("id", rs.getString("id"));
        publicationData.put("identifier", rs.getString("identifier"));
        publicationData.put("citation", rs.getString("citation"));
        publicationData.put("file_url", rs.getString("file_url"));
        publicationData.put("ccafs_acknowledge", rs.getString("ccafs_acknowledge"));
        publicationData.put("isi_publication", rs.getString("isi_publication"));
        publicationData.put("nars_coauthor", rs.getString("nars_coauthor"));
        publicationData.put("earth_system_coauthor", rs.getString("earth_system_coauthor"));
        publicationData.put("publication_type_id", rs.getString("publication_type_id"));
        publicationData.put("publication_type_name", rs.getString("publication_type_name"));
        publicationData.put("publication_access_id", rs.getString("publication_access_id"));
        publicationData.put("publication_access_name", rs.getString("publication_access_name"));
        publications.add(publicationData);
      }
      rs.close();
    } catch (SQLException e) {
      Object[] errorParams = {leaderId, logframeId, e};
      LOG
        .error(
          "-- getPublications() > There was an error getting the publications listed related to the leader {} and the logframe {}",
          errorParams);
    }

    LOG.debug("<< getPublications():publications.size={}", publications.size());
    return publications;
  }

  @Override
  public boolean removeAllPublications(int leaderId, int logframeId) {
    LOG.debug(">> removeAllPublications(leaderId={}, logframeId={})", leaderId, logframeId);
    boolean problem = false;
    try (Connection connection = dbManager.getConnection()) {
      String removeQuery =
        "DELETE FROM publications WHERE activity_leader_id = " + leaderId + " AND logframe_id = " + logframeId;
      int rows = dbManager.makeChange(removeQuery, connection);
      if (rows < 0) {
        LOG
          .warn("-- removeAllPublications() > There was an error deleting all 'publications' for leader {}.", leaderId);
        problem = true;
      }
    } catch (SQLException e) {
      LOG.error("-- removeAllPublications() > There was an error deleting all 'publications' for leader {}.", leaderId,
        e);
      e.printStackTrace();
    }
    LOG.debug("<< removeAllPublications():{}", !problem);
    return !problem;
  }

  @Override
  public boolean removePublicationByDeliverable(int deliverableID) {
    LOG.debug(">> removePublicationByDeliverable(deliverableID={})", deliverableID);
    boolean problem = false;
    try (Connection connection = dbManager.getConnection()) {
      String removeQuery = "DELETE FROM publications WHERE deliverable_id = " + deliverableID;
      int rows = dbManager.makeChange(removeQuery, connection);
      if (rows < 0) {
        LOG.warn(
          "-- removePublicationByDeliverable() > Error deleting the publication linked with the deliverable {}.",
          deliverableID);
        problem = true;
      }
    } catch (SQLException e) {
      LOG.error("-- removePublicationByDeliverable() > Exception deleting publications linked to deliverable {}.",
        deliverableID, e);
      e.printStackTrace();
    }
    LOG.debug("<< removePublicationByDeliverable():{}", !problem);
    return !problem;
  }

  @Override
  public int savePublication(Map<String, String> publication) {
    LOG.debug(">> savePublication(publication={})");
    int generatedId = -1;
    try (Connection connection = dbManager.getConnection()) {
      String addQueryPrepared = null;
      Object[] values;
      addQueryPrepared =
        "INSERT INTO publications (id, publication_type_id, identifier, citation, file_url, logframe_id, "
          + "activity_leader_id, open_access_id, ccafs_acknowledge, isi_publication, nars_coauthor, earth_system_coauthor, "
          + "deliverable_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      values = new Object[13];
      values[0] = publication.get("id");
      values[1] = publication.get("publication_type_id");
      values[2] = publication.get("identifier");
      values[3] = publication.get("citation");
      values[4] = publication.get("file_url");
      values[5] = publication.get("logframe_id");
      values[6] = publication.get("activity_leader_id");
      values[7] = publication.get("open_access_id");
      values[8] = publication.get("ccafs_acknowledge");
      values[9] = publication.get("isi_publication");
      values[10] = publication.get("nars_coauthor");
      values[11] = publication.get("earth_system_coauthor");
      values[12] = publication.get("deliverable_id");

      int rows = dbManager.makeChangeSecure(connection, addQueryPrepared, values);
      if (rows > 0) {
        // get the id assigned to this new record.
        ResultSet rs = dbManager.makeQuery("SELECT LAST_INSERT_ID()", connection);
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
        rs.close();
      }
    } catch (SQLException e) {
      LOG.error("-- savePublication() > There was an error trying to save into 'publications' table.", e);
    }

    LOG.debug("<< savePublication():{}", generatedId);
    return generatedId;
  }
}
