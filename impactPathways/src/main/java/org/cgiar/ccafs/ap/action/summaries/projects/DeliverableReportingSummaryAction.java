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
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.db.DAOManager;
import org.cgiar.ccafs.utils.summaries.Summary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.google.inject.Inject;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jorge Leonardo Solis B. CCAFS
 */
public class DeliverableReportingSummaryAction extends BaseAction implements Summary {

  /**
   * 
   */
  private static final long serialVersionUID = -6569401871980127876L;
  public static Logger LOG = LoggerFactory.getLogger(DeliverableReportingSummaryAction.class);

  // XLS bytes
  private byte[] bytesXLS;

  // Streams
  InputStream inputStream;
  DAOManager dao;

  @Inject
  public DeliverableReportingSummaryAction(APConfig config, DAOManager dao) {
    super(config);
    this.dao = dao;

  }

  @Override
  public String execute() throws Exception {
    File jasperFile = this.getFile("jaspers/DeliverablesInformation.jasper");
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    InputStream is = null;
    try {
      is = new FileInputStream(jasperFile);
    } catch (Exception e) {
    }
    JasperPrint jasperPrint = JasperFillManager.fillReport(is, new HashMap<String, Object>(), dao.getConnection());
    JRXlsxExporter exporter = new JRXlsxExporter();
    exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, os);
    exporter.exportReport();
    bytesXLS = os.toByteArray();
    is.close();
    os.close();
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

  private File getFile(String fileName) {


    // Get file from resources folder
    ClassLoader classLoader = this.getClass().getClassLoader();
    File file = new File(classLoader.getResource(fileName).getFile());


    return file;

  }


  @Override
  public String getFileName() {
    StringBuffer fileName = new StringBuffer();
    fileName.append("All-deliverables-");
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


  }
}
