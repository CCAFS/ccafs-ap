/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLCaseStudyCountriesDAO implements CaseStudyCountriesDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLCaseStudyCountriesDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLCaseStudyCountriesDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getCaseStudyCountries(int caseStudyId) {
    LOG.debug(">> getCaseStudyCountries(caseStudyId={})", caseStudyId);

    List<Map<String, String>> caseStudyCountriesDataList = new ArrayList<>();
    String query =
      "SELECT csc.country_iso2 id, co.name "
        + "FROM case_study_countries csc INNER JOIN countries co ON csc.country_iso2 = co.iso2 "
        + "WHERE csc.case_study_id=" + caseStudyId;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> caseStudyCountriesData = new HashMap<>();
        caseStudyCountriesData.put("id", rs.getString("id"));
        caseStudyCountriesData.put("name", rs.getString("name"));
        caseStudyCountriesDataList.add(caseStudyCountriesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG
        .error(
          "-- getCaseStudyCountries() > There was an error getting the countries related to the case study identified by {}.",
          caseStudyId, e);
    }

    LOG.debug("<< getCaseStudyCountries():caseStudyCountriesDataList.size={}", caseStudyCountriesDataList.size());
    return caseStudyCountriesDataList;
  }

  @Override
  public boolean saveCaseStudyCountries(int caseStudyId, ArrayList<String> countriesIds) {
    LOG.debug(">> saveCaseStudyCountries(caseStudyId={}, countriesIds={})", caseStudyId, countriesIds);

    boolean problem = false;
    try (Connection con = databaseManager.getConnection()) {
      String addQuery = "INSERT INTO case_study_countries (case_study_id, country_iso2) VALUES ";
      for (int c = 0; c < countriesIds.size(); c++) {
        if (c != 0) {
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
      LOG
        .error(
          "-- saveCaseStudyCountries() > There was an error saving records into 'case_study_countries' table for case study {}",
          caseStudyId, e);
    }

    LOG.debug("<< saveCaseStudyCountries():{}", !problem);
    return !problem;
  }
}
