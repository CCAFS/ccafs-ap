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

import org.cgiar.ccafs.ap.data.manager.impl.OutputSummaryManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.OutputSummary;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(OutputSummaryManagerImpl.class)
public interface OutputSummaryManager {

  /**
   * Get a list of Summaries by Output that belongs to a specific activity leader and a specific logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object.
   * @return a List of OutputSummary objects.
   */
  public OutputSummary[] getOutputSummaries(Leader activityLeader, Logframe logframe);

  /**
   * Save into the DAO the outputs summary information
   * 
   * @param outputSummary The list of objects that contains the data
   * @return true if the data was successfully saved, false otherwise
   */
  public boolean saveOutputSummary(List<OutputSummary> outputSummaries);

  /**
   * Update into the DAO the outputs summary information
   * 
   * @param outputSummaries the list of objects that contains the data
   * @return true if the data was successfully save, false otherwise
   */
  public boolean updateOutputSummary(List<OutputSummary> outputSummaries);
}
