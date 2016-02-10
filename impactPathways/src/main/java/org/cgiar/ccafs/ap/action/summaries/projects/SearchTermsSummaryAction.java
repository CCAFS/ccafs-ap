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

package org.cgiar.ccafs.ap.action.summaries.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.summaries.planning.xlsx.SearchTermsSummaryXLS;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.summaries.Summary;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Alberto Martínez M.
 */
public class SearchTermsSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(SearchTermsSummaryAction.class);
  private static final long serialVersionUID = 5110987672008315842L;;
  private SearchTermsSummaryXLS searchTermsSummaryXLS;
  private ProjectManager projectManager;
  private String[] termsToSearch = {"Gender", "female", "male", "men", "elderly", "caste", "women", "equitable",
    "inequality", "equity", "social", "differentiation", "inclusion", "youth", "class", "children", "child"};

  private List<Map<String, Object>> projectList, deliverableList, activityList;

  // CSV bytes
  private byte[] bytesXLS;

  // Streams
  InputStream inputStream;

  @Inject
  public SearchTermsSummaryAction(APConfig config, SearchTermsSummaryXLS searchTermsSummary,
    ProjectManager projectManager) {


    super(config);
    this.searchTermsSummaryXLS = searchTermsSummary;
    this.projectManager = projectManager;

  }

  @Override
  public String execute() throws Exception {
    // Generate the xls file
    bytesXLS = searchTermsSummaryXLS.generateXLS(projectList, activityList, deliverableList, termsToSearch);

    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return bytesXLS.length;
  }

  @Override
  public String getContentType() {
    return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

  }

  @Override
  public String getFileName() {
    String date = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());
    StringBuffer fileName = new StringBuffer();
    fileName.append("SearchTermsSummary_");
    fileName.append(date);
    fileName.append(".xlsx");
    return fileName.toString();
  }


  @Override
  public InputStream getInputStream() {
    if (inputStream == null) {
      inputStream = new ByteArrayInputStream(bytesXLS);
    }
    return inputStream;
  }

  @Override
  public void prepare() {
    String string = (StringUtils.trim(this.getRequest().getParameter(APConstants.QUERY_PARAMETER)));
    if (string != null) {
      termsToSearch = StringUtils.split(string, ',');
    }
    projectList = projectManager.summaryGetAllProjectsWithGenderContribution(termsToSearch);
    activityList = projectManager.summaryGetAllActivitiesWithGenderContribution(termsToSearch);
    deliverableList = projectManager.summaryGetAllDeliverablesWithGenderContribution(termsToSearch);
  }
}
