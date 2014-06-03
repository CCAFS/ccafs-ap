package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.CommunicationDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLCommunicationDAO implements CommunicationDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLCommunicationDAO.class);
  DAOManager databaseManager;

  @Inject
  public MySQLCommunicationDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getCommunicationReport(int leader_id, int logframe_id) {
    LOG.debug(">> getCommunications(leader_id = {}, logframe_id = {})", leader_id, logframe_id);

    Map<String, String> communicationData = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT c.id, c.media_campaigns, c.blogs, c.websites, c.social_media_campaigns, c.newsletters, ");
    query.append("c.events, c.videos_multimedia, c.other_communications, l.id as 'leader_id', ");
    query.append("l.acronym as 'leader_acronym', lo.id as 'logframe_id', lo.year as 'logframe_year' ");
    query.append("FROM `communications` c ");
    query.append("INNER JOIN activity_leaders l ON c.activity_leader_id = l.id ");
    query.append("INNER JOIN logframes lo ON c.logframe_id = lo.id ");
    query.append("WHERE l.id = ");
    query.append(leader_id);
    query.append(" AND lo.id = ");
    query.append(logframe_id);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        communicationData.put("id", rs.getString("id"));
        communicationData.put("media_campaigns", rs.getString("media_campaigns"));
        communicationData.put("blogs", rs.getString("blogs"));
        communicationData.put("websites", rs.getString("websites"));
        communicationData.put("social_media_campaigns", rs.getString("social_media_campaigns"));
        communicationData.put("newsletters", rs.getString("newsletters"));
        communicationData.put("events", rs.getString("events"));
        communicationData.put("videos_multimedia", rs.getString("videos_multimedia"));
        communicationData.put("other_communications", rs.getString("other_communications"));
        communicationData.put("leader_id", rs.getString("leader_id"));
        communicationData.put("leader_acronym", rs.getString("leader_acronym"));
        communicationData.put("logframe_id", rs.getString("logframe_id"));
        communicationData.put("logframe_year", rs.getString("logframe_year"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getCommunications() > There was an exception trying to get the communications list.", e);
    }

    LOG.debug("<< getCommunications():communicationsDataList.size={}", communicationData);
    return communicationData;
  }

  @Override
  public boolean saveCommunicationReport(Map<String, String> communicationData) {
    LOG.debug(">> saveCommunications(communicationData={})", communicationData);

    boolean saved = false;
    String query = "INSERT INTO `communications` (`id`, `media_campaigns`, `blogs`, `websites`, ";
    query += "`social_media_campaigns`, `newsletters`, `events`, `videos_multimedia`, `other_communications`, ";
    query += "`activity_leader_id`, `logframe_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
    query += "ON DUPLICATE KEY UPDATE `media_campaigns`=VALUES(`media_campaigns`), `blogs`=VALUES(`blogs`), ";
    query += "`websites`=VALUES(`websites`), `social_media_campaigns`=VALUES(`social_media_campaigns`), ";
    query += "`newsletters`=VALUES(`newsletters`), `events`=VALUES(`events`), ";
    query += "`videos_multimedia`=VALUES(`videos_multimedia`), `other_communications`=VALUES(`other_communications`);";

    Object[] values = new Object[11];
    values[0] = communicationData.get("id");
    values[1] = communicationData.get("media_campaigns");
    values[2] = communicationData.get("blogs");
    values[3] = communicationData.get("websites");
    values[4] = communicationData.get("social_media_campaigns");
    values[5] = communicationData.get("newsletters");
    values[6] = communicationData.get("events");
    values[7] = communicationData.get("videos_multimedia");
    values[8] = communicationData.get("other_communications");
    values[9] = communicationData.get("activity_leader_id");
    values[10] = communicationData.get("logframe_id");

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG.error("There was a problem saving the communications for the leader {} and the logframe {} into the DB",
          communicationData.get("activity_leader_id"), communicationData.get("logframe_id"));
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("There was an exception saving the communications for leader {} and logframe {} into the DB", e);
    }

    LOG.debug("<< saveCommunications():{}", saved);
    return saved;
  }
}
