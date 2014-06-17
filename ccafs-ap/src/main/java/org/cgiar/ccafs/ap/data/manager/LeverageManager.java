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

import org.cgiar.ccafs.ap.data.manager.impl.LeverageManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Leverage;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(LeverageManagerImpl.class)
public interface LeverageManager {

  /**
   * Get all the leverages that belongs to the given leader and
   * corresponding to the given logframe.
   * 
   * @param leader
   * @param logframe
   * @return a list of Leverages objects with the information
   */
  public List<Leverage> getLeverages(Leader leader, Logframe logframe);

  /**
   * Remove all the leverages that bleongs to the given leader for
   * the corresponding logframe.
   * 
   * @param leader
   * @param logframe
   * @return true if the leverages were successfully removed. False otherwise.
   */
  public boolean removeLeverages(Leader leader, Logframe logframe);

  /**
   * Save the leverages reported by the leader corresponding to the logframe given.
   * 
   * @param leverages
   * @param leader
   * @param logframe
   * @return true if All the leverages were saved successfully. False otherwise.
   */
  public boolean saveLeverages(List<Leverage> leverages, Leader leader);
}
