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

import org.cgiar.ccafs.ap.data.manager.impl.TLOutputSummaryManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.TLOutputSummary;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(TLOutputSummaryManagerImpl.class)
public interface TLOutputSummaryManager {

  /**
   * Get a list of Summaries by Output that belong to a specific leader and a specific logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object.
   * @return a List of TLOutputSummary objects.
   */
  public List<TLOutputSummary> getTLOutputSummaries(Leader leader, Logframe logframe);

  /**
   * Save a list of Summaries by Output that belongs to a specified leader.
   * 
   * @param outputs - List of TLOutputSummary objects to be saved.
   * @param leader - Leader object
   * @return true if all the outputs were successfully saved. False if any problem appear.
   */
  public boolean saveTLOutputSummaries(List<TLOutputSummary> outputs, Leader leader);

}
