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

package org.cgiar.ccafs.ap.action.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class HomePreplanningAction extends BaseAction {

  private static final long serialVersionUID = -4463336069370146999L;

  // Managers
  private IPProgramManager programManager;

  // Model
  List<IPProgram> flagshipPrograms;
  List<IPProgram> regionPrograms;

  @Inject
  public HomePreplanningAction(APConfig config, IPProgramManager programManager) {
    super(config);
    this.programManager = programManager;
  }

  public List<IPProgram> getFlagshipPrograms() {
    return flagshipPrograms;
  }

  public int getGlobalProgramID() {
    return APConstants.GLOBAL_PROGRAM;
  }

  public String getProgramRequest() {
    return APConstants.PROGRAM_REQUEST_ID;
  }

  public List<IPProgram> getRegionPrograms() {
    return regionPrograms;
  }

  @Override
  public void prepare() throws Exception {
    flagshipPrograms = programManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);
    regionPrograms = programManager.getProgramsByType(APConstants.REGION_PROGRAM_TYPE);
  }

  public void setPrograms(List<IPProgram> programs) {
    this.flagshipPrograms = programs;
  }
}