package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.CaseStudyTypeDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLCaseStudyTypeDAO implements CaseStudyTypeDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLPartnerDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLCaseStudyTypeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getCaseStudyTypes() {
    LOG.debug(">> getCaseStudyTypes()");
    List<Map<String, String>> caseStudyTypesList = new ArrayList<>();
    String query = "SELECT * FROM case_study_types";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> typesData = new HashMap<>();
        typesData.put("id", rs.getString("id"));
        typesData.put("name", rs.getString("name"));
        caseStudyTypesList.add(typesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getCaseStudyTypes() > There was an error getting the data from 'case_study_types' table. \n{}", e);
    }

    LOG.debug("<< getCaseStudyTypes():caseStudyTypesList.size={}", caseStudyTypesList.size());
    return caseStudyTypesList;
  }

  @Override
  public List<Map<String, String>> getCaseStudyTypes(int caseStudyId) {
    LOG.debug(">> getCaseStudyTypes(caseStudyId={})", caseStudyId);

    List<Map<String, String>> caseStudyTypesList = new ArrayList<>();
    String query =
      "SELECT cst.id, cst.name FROM case_study_types cst "
        + "INNER JOIN cs_types cs_t ON cst.id = cs_t.case_study_type_id "
        + "INNER JOIN case_studies cs ON cs_t.case_study_id = cs.id " + "WHERE cs.id = " + caseStudyId;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> typesData = new HashMap<>();
        typesData.put("id", rs.getString("id"));
        typesData.put("name", rs.getString("name"));
        caseStudyTypesList.add(typesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getCaseStudyTypes() > There was an error getting the types of case study {}.", caseStudyId, e);
    }

    LOG.debug("<< getCaseStudyTypes():caseStudyTypesList.size={}", caseStudyTypesList.size());
    return caseStudyTypesList;
  }

  @Override
  public boolean saveCaseStudyTypes(int caseStudyId, int[] caseStudyTypeIds) {
    LOG
      .debug("saveCaseStudyTypes(caseStudyId={}, caseStudyTypeIds={})", caseStudyId, Arrays.toString(caseStudyTypeIds));
    boolean problem = false;
    try (Connection con = databaseManager.getConnection()) {
      String addQuery = "INSERT INTO cs_types (case_study_id, case_study_type_id) VALUES ";
      for (int c = 0; c < caseStudyTypeIds.length; c++) {
        if (c != 0) {
          addQuery += ", ";
        }
        addQuery += "( " + String.valueOf(caseStudyId) + ", ?)";
      }
      Object[] values = new Object[caseStudyTypeIds.length];
      for (int c = 0; c < caseStudyTypeIds.length; c++) {
        values[c] = caseStudyTypeIds[c];
      }
      int rows = databaseManager.makeChangeSecure(con, addQuery, values);
      if (rows <= 0) {
        LOG.warn("-- saveCaseStudyTypes() > There was not posible save types for case study {}.", caseStudyId);
        problem = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveCaseStudyTypes() > There was an error saving types for case study {}.", caseStudyId, e);
    }

    LOG.debug("saveCaseStudyTypes():{}", problem);
    return problem;
  }
}