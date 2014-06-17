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

import org.cgiar.ccafs.ap.data.manager.impl.MilestoneManagerImpl;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Milestone;

import com.google.inject.ImplementedBy;

@ImplementedBy(MilestoneManagerImpl.class)
public interface MilestoneManager {


  /**
   * Get a milestone object identified with the given id.
   * 
   * @param id
   * @return a Milestione object or null if no milestone was found.
   */
  public Milestone getMilestone(int id);

  /**
   * Get the complete milestone list corresponding to the logframe given
   * 
   * @param logframe - the logframe
   * @return a list of Milestone objects
   */
  public Milestone[] getMilestoneList(Logframe logframe);
}
