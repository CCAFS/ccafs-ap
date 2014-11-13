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

package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.pdfs.TestSummaryPdf;
import org.cgiar.ccafs.ap.config.APConfig;

import java.io.InputStream;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class ProjectSummariesAction extends BaseAction {

  private static final long serialVersionUID = 5140987672008315842L;
  public static Logger LOG = LoggerFactory.getLogger(ProjectSummariesAction.class);

  TestSummaryPdf testPdf;

  @Inject
  public ProjectSummariesAction(APConfig config, TestSummaryPdf testPdf) {
    super(config);
    this.testPdf = testPdf;
  }

  @Override
  public String execute() throws Exception {
    // Generate the pdf file
    testPdf.setSummaryTitle("El pdf de prueba");
    testPdf.generatePdf();
    return SUCCESS;
  }

  public int getContentLength() {
    return testPdf.getContentLength();
  }

  public String getFileName() {
    return testPdf.getFileName();
  }

  public InputStream getInputStream() {
    return testPdf.getInputStream();
  }

  @Override
  public void prepare() throws Exception {
    // Get all the information to add in the pdf file
  }


}
