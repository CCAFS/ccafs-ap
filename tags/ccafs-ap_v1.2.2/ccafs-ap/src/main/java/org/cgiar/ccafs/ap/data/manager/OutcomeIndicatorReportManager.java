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

import org.cgiar.ccafs.ap.data.manager.impl.OutcomeIndicatorReportManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicatorReport;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(OutcomeIndicatorReportManagerImpl.class)
public interface OutcomeIndicatorReportManager {

  /**
   * Get the outcome indicator reports existent for the logframe given.
   * 
   * @param logframe
   * @return
   */
  public List<OutcomeIndicatorReport> getOutcomeIndicatorReports(Logframe logframe, Leader leader);

  /**
   * Save the information about the outcome indicators reports given into the database.
   * 
   * @param outcomeIndicatorReports - Information to save
   * @param leader
   * @param logframe
   * @return
   */
  public boolean saveOutcomeIndicatorReports(List<OutcomeIndicatorReport> outcomeIndicatorReports, Leader leader,
    Logframe logframe);
}
