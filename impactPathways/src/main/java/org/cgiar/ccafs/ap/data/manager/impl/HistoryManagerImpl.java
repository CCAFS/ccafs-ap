/*****************************************************************
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
 *****************************************************************/

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.HistoryDAO;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.model.LogHistory;
import org.cgiar.ccafs.ap.data.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Carlos Alberto Martínez M - CIAT/CCAFS
 */

public class HistoryManagerImpl implements HistoryManager {

  private static Logger LOG = LoggerFactory.getLogger(HistoryManagerImpl.class);
  private HistoryDAO historyDAO;

  @Inject
  public HistoryManagerImpl(HistoryDAO historyDAO) {
    this.historyDAO = historyDAO;
  }


  @Override
  public List<LogHistory> getActivitiesHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getActivitiesHistory(projectID);
    return this.getData(logHistoryData);
  }

  @Override
  public List<LogHistory> getCCAFSOutcomesHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getCCAFSOutcomesHistory(projectID);
    return this.getData(logHistoryData);
  }

  @SuppressWarnings("rawtypes")
  private List<LogHistory> getData(List<Map<String, String>> logHistoryData) {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT + " HH:mm:ss");
    List historyList = new ArrayList<>();

    for (Map<String, String> historyData : logHistoryData) {
      LogHistory history = new LogHistory();
      history.setJustification(historyData.get("justification"));
      history.setAction(historyData.get("action"));

      User user = new User();
      user.setId(Integer.parseInt(historyData.get("user_id")));
      user.setFirstName(historyData.get("first_name"));
      user.setLastName(historyData.get("last_name"));
      user.setEmail(historyData.get("email"));
      history.setUser(user);

      Date date;
      try {
        date = dateformatter.parse(historyData.get("date"));
        history.setDate(date);
      } catch (ParseException e) {
        LOG.error("Exception raised trying to convert the history date");
      }

      historyList.add(history);
    }
    return historyList;
  }

  @Override
  public List<LogHistory> getProjectBudgetByMogHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getProjectBudgetByMogHistory(projectID);
    return this.getData(logHistoryData);
  }

  @Override
  public List<LogHistory> getProjectBudgetHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getProjectBudgetHistory(projectID);
    return this.getData(logHistoryData);
  }

  @Override
  public List<LogHistory> getProjectDeliverablesHistory(int deliverableID) {
    List<Map<String, String>> logHistoryData = historyDAO.getProjectDeliverablesHistory(deliverableID);
    return this.getData(logHistoryData);
  }

  @Override
  public List<LogHistory> getProjectDescriptionHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getProjectDescriptionHistory(projectID);
    return this.getData(logHistoryData);
  }

  @Override
  public List<LogHistory> getProjectIPOtherContributionHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getProjectIPOtherContributionHistory(projectID);
    return this.getData(logHistoryData);
  }

  @Override
  public List<LogHistory> getProjectLocationsHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getProjectLocationsHistory(projectID);
    return this.getData(logHistoryData);
  }

  @Override
  public List<LogHistory> getProjectOutcomeHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getProjectOutcomeHistory(projectID);
    return this.getData(logHistoryData);
  }


  @Override
  public List<LogHistory> getProjectOutputsHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getProjectOutputsHistory(projectID);
    return this.getData(logHistoryData);
  }

  @Override
  public List<LogHistory> getProjectPartnersHistory(int projectID) {
    List<Map<String, String>> logHistoryData = historyDAO.getProjectPartnerHistory(projectID);
    return this.getData(logHistoryData);
  }
}
