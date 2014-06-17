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

import org.cgiar.ccafs.ap.data.manager.impl.IndicatorReportManagerImpl;
import org.cgiar.ccafs.ap.data.model.IndicatorReport;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IndicatorReportManagerImpl.class)
public interface IndicatorReportManager {

  /**
   * Get the list of indicator's reports made by the leader and corresponding
   * to the given logframe.
   * 
   * @param leader
   * @param logframe
   * @return a list of IndicatorReport objects with the information.
   */
  public List<IndicatorReport> getIndicatorReportsList(Leader leader, Logframe logframe);

  /**
   * @param indicatorReports
   * @param leader
   * @param logframe
   * @return
   */
  public boolean saveIndicatorReportsList(List<IndicatorReport> indicatorReports, Leader leader);
}
