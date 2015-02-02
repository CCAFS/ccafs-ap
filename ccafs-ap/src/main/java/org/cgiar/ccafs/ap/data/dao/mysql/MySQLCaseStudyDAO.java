package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.CaseStudyDAO;
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


public class MySQLCaseStudyDAO implements CaseStudyDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLCaseStudyDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLCaseStudyDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getCaseStudyList(int activityLeaderId, int logframeId) {
    LOG.debug(">> getCaseStudyList(activityLeaderId={}, logframeId={})", activityLeaderId, logframeId);
    List<Map<String, String>> caseStudyDataList = new ArrayList<>();
    String query =
      "SELECT cs.id, cs.title, cs.author, cs.start_date, cs.end_date, cs.photo, cs.objectives, "
        + "cs.description, cs.results, cs.partners, cs.links, cs.keywords, cs.logframe_id, cs.is_global, "
        + "cs.subject, cs.contributor, cs.publisher, cs.relation, cs.coverage, cs.rights " + "FROM case_studies cs "
        + "WHERE cs.activity_leader_id=" + activityLeaderId + " AND logframe_id=" + logframeId;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> caseStudyData = new HashMap<String, String>();
        caseStudyData.put("id", rs.getString("id"));
        caseStudyData.put("title", rs.getString("title"));
        caseStudyData.put("author", rs.getString("author"));
        caseStudyData.put("start_date", rs.getString("start_date"));
        caseStudyData.put("end_date", rs.getString("end_date"));
        caseStudyData.put("photo", rs.getString("photo"));
        caseStudyData.put("objectives", rs.getString("objectives"));
        caseStudyData.put("description", rs.getString("description"));
        caseStudyData.put("results", rs.getString("results"));
        caseStudyData.put("partners", rs.getString("partners"));
        caseStudyData.put("links", rs.getString("links"));
        caseStudyData.put("keywords", rs.getString("keywords"));
        caseStudyData.put("subject", rs.getString("subject"));
        caseStudyData.put("contributor", rs.getString("contributor"));
        caseStudyData.put("publisher", rs.getString("publisher"));
        caseStudyData.put("relation", rs.getString("relation"));
        caseStudyData.put("coverage", rs.getString("coverage"));
        caseStudyData.put("rights", rs.getString("rights"));
        caseStudyData.put("logframe_id", rs.getString("logframe_id"));
        caseStudyData.put("is_global", rs.getString("is_global"));
        caseStudyDataList.add(caseStudyData);
      }
      rs.close();
    } catch (SQLException e) {
      Object[] errorParam = {activityLeaderId, logframeId, e};
      LOG.error(
        "-- getCaseStudyList() > There was a problem getting the case studies for leader {} related to logframe {}",
        errorParam);
    }

    LOG.debug("<< getCaseStudyList():caseStudyDataList.size={}", caseStudyDataList.size());
    return caseStudyDataList;
  }

  @Override
  public List<Map<String, String>> getCaseStudyListForSummary(int activityLeaderId, int year, String countriesIds,
    String typesIds) {
    LOG.debug(">> getCaseStudyListForSummary(activityLeaderId={}, year={})", activityLeaderId, year);
    List<Map<String, String>> caseStudyDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT cs.id, cs.title, al.acronym as 'activity_leader_acronym', ");
    query.append("IFNULL( GROUP_CONCAT(DISTINCT cst.name SEPARATOR ',\n'), 'No type selected') as 'type', ");
    query.append("IFNULL( GROUP_CONCAT(co.name ORDER BY co.name SEPARATOR ',\n'), 'Global') as 'Countries' ");
    query.append("FROM case_studies cs ");
    query.append("INNER JOIN activity_leaders al ON cs.activity_leader_id = al.id ");
    query.append("INNER JOIN logframes l ON cs.logframe_id = l.id ");
    query.append("LEFT JOIN cs_types ct ON cs.id = ct.case_study_id ");
    query.append("LEFT JOIN case_study_types cst ON ct.case_study_type_id = cst.id ");
    query.append("LEFT JOIN case_study_countries csc ON cs.id = csc.case_study_id ");
    query.append("LEFT JOIN countries co ON csc.country_iso2 = co.iso2 ");
    query.append("WHERE 1 ");

    if (activityLeaderId != -1) {
      query.append("AND al.id = " + activityLeaderId + " ");
    }

    if (year != -1) {
      query.append("AND l.year = " + year + " ");
    }

    if (!countriesIds.isEmpty()) {
      query.append("AND co.iso2 IN (" + countriesIds + ") ");
    }

    if (!typesIds.isEmpty()) {
      query.append("AND cst.id IN (" + typesIds + ") ");
    }

    query.append("GROUP BY cs.id ");
    query.append("ORDER BY cs.id, al.acronym, cs.title; ");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> caseStudyData = new HashMap<String, String>();
        caseStudyData.put("id", rs.getString("id"));
        caseStudyData.put("title", rs.getString("title"));
        caseStudyData.put("activity_leader_acronym", rs.getString("activity_leader_acronym"));
        caseStudyData.put("type", rs.getString("type"));
        caseStudyData.put("Countries", rs.getString("Countries"));
        caseStudyDataList.add(caseStudyData);
      }
      rs.close();
    } catch (SQLException e) {
      Object[] errorParam = {activityLeaderId, year, e};
      LOG
        .error(
          "-- getCaseStudyListForSummary() > There was a problem getting the case studies for leader {} related to logframe {}",
          errorParam);
    }

    LOG.debug("<< getCaseStudyListForSummary():caseStudyDataList.size={}", caseStudyDataList.size());
    return caseStudyDataList;
  }

  @Override
  public boolean removeAllCaseStudies(int activityLeaderId, int logframeId) {
    LOG.debug("<< removeAllCaseStudies(activityLeaderId={}, logframeId={})", activityLeaderId, logframeId);

    String deleteDeliverableQuery = "DELETE FROM case_studies WHERE activity_leader_id = ? AND logframe_id = ?";
    try (Connection connection = databaseManager.getConnection()) {
      int rowsDeleted =
        databaseManager.makeChangeSecure(connection, deleteDeliverableQuery,
          new Object[] {activityLeaderId, logframeId});
      if (rowsDeleted >= 0) {
        LOG.debug("<< removeAllCaseStudies():{}", true);
        return true;
      }
    } catch (SQLException e) {
      Object[] errorParams = {activityLeaderId, logframeId, e};
      LOG
        .error(
          "-- removeAllCaseStudies() > There was a problem deleting the case studies related to the leader {} and logframe {}",
          errorParams);
    }

    LOG.debug("<< removeAllCaseStudies():{}", false);
    return false;
  }

  @Override
  public int saveCaseStudy(Map<String, Object> caseStudyData) {
    LOG.debug("saveCaseStudy(caseStudyData={})", caseStudyData);

    int generatedId = -1;
    String preparedQuery =
      "INSERT INTO case_studies (id, title, author, start_date, end_date, photo, objectives, description, "
        + "results, partners, links, keywords, subject, contributor, publisher, relation, coverage, rights, "
        + "logframe_id, activity_leader_id, is_global) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection con = databaseManager.getConnection()) {

      Object[] data = new Object[21];
      data[0] = caseStudyData.get("id");
      data[1] = caseStudyData.get("title");
      data[2] = caseStudyData.get("author");
      data[3] = caseStudyData.get("start_date");
      data[4] = caseStudyData.get("end_date");
      data[5] = caseStudyData.get("photo");
      data[6] = caseStudyData.get("objectives");
      data[7] = caseStudyData.get("description");
      data[8] = caseStudyData.get("results");
      data[9] = caseStudyData.get("partners");
      data[10] = caseStudyData.get("links");
      data[11] = caseStudyData.get("keywords");
      data[12] = caseStudyData.get("subject");
      data[13] = caseStudyData.get("contributor");
      data[14] = caseStudyData.get("publisher");
      data[15] = caseStudyData.get("relation");
      data[16] = caseStudyData.get("coverage");
      data[17] = caseStudyData.get("rights");
      data[18] = caseStudyData.get("logframe_id");
      data[19] = caseStudyData.get("activity_leader_id");
      data[20] = caseStudyData.get("is_global");
      int rows = databaseManager.makeChangeSecure(con, preparedQuery, data);
      if (rows > 0) {
        // get the id assigned to the new record
        ResultSet rs = databaseManager.makeQuery("SELECT LAST_INSERT_ID()", con);
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
        rs.close();
      }
    } catch (SQLException e) {
      LOG.error("-- saveCaseStudy() > There was an error inserting a record into 'case_studies' table. ", e);
      return -1;
    }

    LOG.debug("<< saveCaseStudy():{}", generatedId);
    return generatedId;
  }
}
