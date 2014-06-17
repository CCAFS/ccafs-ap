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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.RPLSynthesisReportManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.RPLSynthesisReport;

import com.google.inject.ImplementedBy;

@ImplementedBy(RPLSynthesisReportManagerImpl.class)
public interface RPLSynthesisReportManager {

  /**
   * Get a Regional Program Leader Synthesis Report that belong to a specific leader and logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object.
   * @return a Map with the synthesis report information, or null if nothing found.
   */
  public RPLSynthesisReport getRPLSynthesisReport(Leader leader, Logframe logframe);

  /**
   * Save or Update the Regional Program Leader Synthesis Report into the DAO.
   * 
   * @param synthesisReport - RPLSynthesisReport object.
   * @param leader - Leader object
   * @param logframe - Logframe object
   * @return true if the save/update was successfully made, or false if any other problem occur.
   */
  public boolean saveRPLSynthesisReport(RPLSynthesisReport synthesisReport, Leader leader, Logframe logframe);
}
