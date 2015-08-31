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


package org.cgiar.ccafs.ap.action.summaries.csv;


import java.io.File;
import java.io.IOException;

import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jorge Leonardo Solis Banguera
 */
public class BaseCSV {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(BaseCSV.class);
  private TextProvider textProvider;


  /**
   * Method used for to get the name of document
   * 
   * @return name of document
   */
  public String getFileName(int projectID, String name) {
    StringBuffer fileName = new StringBuffer();

    fileName.append(name);
    fileName.append("-");
    fileName.append(projectID);
    fileName.append("-");
    fileName.append(".csv");

    return fileName.toString();
  }

  public String getText(String key) {
    return textProvider.getText(key);
  }

  public void initializeCsv(File file) {
    textProvider = new DefaultTextProvider();

    file.delete();
    try {
      file.createNewFile();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

}
