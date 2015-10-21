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

package org.cgiar.ccafs.ap.action.summaries.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.summaries.planning.xlsx.BudgetByMOGSummaryXLS;
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
 * @author Jorge Leonardo Solis B. CCAFS
 */
public class BudgetByMOGsSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(DeliverablePlanningSummaryAction.class);
  private static final long serialVersionUID = 5110987672008315842L;

  private BudgetByMOGSummaryXLS budgetSummaryByMOGXLS;
  private ProjectManager projectManager;

  // XLS bytes
  private byte[] bytesXLS;

  // Streams
  InputStream inputStream;

  // Model
  List<Project> projectsList;
  private List<Map<String, Object>> informationBudgetReportByMOGDetail;
  private List<Map<String, Object>> informationBudgetReportByMOG;

  @Inject
  public BudgetByMOGsSummaryAction(APConfig config, ProjectManager projectManager,
    BudgetByMOGSummaryXLS budgetSummaryByMOGXLS) {
    super(config);
    this.projectManager = projectManager;
    this.budgetSummaryByMOGXLS = budgetSummaryByMOGXLS;

  }

  @Override
  public String execute() throws Exception {

    // Generate the csv file
    bytesXLS = budgetSummaryByMOGXLS.generateXLS(informationBudgetReportByMOGDetail, informationBudgetReportByMOG);

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
    StringBuffer fileName = new StringBuffer();
    fileName.append("BudgetSummaryByMOGXLS-");
    fileName.append(new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date()));
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
    int year = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.YEAR_REQUEST)));
    informationBudgetReportByMOG = projectManager.summaryGetInformationPOWB(year);
    informationBudgetReportByMOGDetail = projectManager.summaryGetInformationPOWBDetail(year);
  }
}