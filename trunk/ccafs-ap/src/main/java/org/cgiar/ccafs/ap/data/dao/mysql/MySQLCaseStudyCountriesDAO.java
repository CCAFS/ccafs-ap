package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.CaseStudyCountriesDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLCaseStudyCountriesDAO implements CaseStudyCountriesDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLCaseStudyCountriesDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getCaseStudyCountries(int caseStudyId) {
    List<Map<String, String>> caseStudyCountriesDataList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT csc.country_iso2 id, co.name "
          + "FROM case_study_countries csc INNER JOIN countries co ON csc.country_iso2 = co.iso2 "
          + "WHERE csc.case_study_id=" + caseStudyId;
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> caseStudyCountriesData = new HashMap<>();
        caseStudyCountriesData.put("id", rs.getString("id"));
        caseStudyCountriesData.put("name", rs.getString("name"));
        caseStudyCountriesDataList.add(caseStudyCountriesData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto generated try catch block
      e.printStackTrace();
    }
    if (caseStudyCountriesDataList.isEmpty()) {
      return null;
    }
    return caseStudyCountriesDataList;
  }

  @Override
  public boolean saveCaseStudyCountries(int caseStudyId, ArrayList<String> countriesIds) {
    boolean problem = false;
    try (Connection con = databaseManager.getConnection()) {
      String addQuery = "INSERT INTO case_study_countries (case_study_id, country_iso2) VALUES ";
      boolean isFirst = true;
      for (String countryId : countriesIds) {
        if (isFirst) {
          isFirst = false;
        } else {
          addQuery += ", ";
        }
        addQuery += "(" + caseStudyId + ", ?)";
      }

      Object[] values = new Object[countriesIds.size()];
      for (int c = 0; c < values.length; c++) {
        values[c] = countriesIds.get(c);
      }
      int rows = databaseManager.makeChangeSecure(con, addQuery, values);
      if (rows < 0) {
        problem = true;
      }
    } catch (SQLException e) {
      // TODO Auto generated catch block
      e.printStackTrace();
    }
    return !problem;
  }
}
