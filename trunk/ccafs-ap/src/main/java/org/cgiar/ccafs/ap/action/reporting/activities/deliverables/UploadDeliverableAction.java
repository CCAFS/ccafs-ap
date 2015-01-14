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

package org.cgiar.ccafs.ap.action.reporting.activities.deliverables;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class UploadDeliverableAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(UploadDeliverableAction.class);
  private static final long serialVersionUID = 6236254742437672459L;

  // Model
  private File file;
  private String fileContentType;
  private String fileFileName;
  private int activityID;
  private int deliverableID;


  public UploadDeliverableAction(APConfig config, LogframeManager logframeManager) {
    super(config, logframeManager);
  }

  @Override
  public String save() {
    System.out.println(file.getAbsolutePath());

    return INPUT;
  }

}
