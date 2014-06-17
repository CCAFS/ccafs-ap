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

import org.cgiar.ccafs.ap.data.manager.impl.CommunicationManagerImpl;
import org.cgiar.ccafs.ap.data.model.Communication;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import com.google.inject.ImplementedBy;

@ImplementedBy(CommunicationManagerImpl.class)
public interface CommunicationManager {

  /**
   * Get all the communications that belongs to the given leader and that corresponds to
   * the given logframe.
   * 
   * @param leader
   * @param logframe
   * @return a list of Communication object with the information
   */
  public Communication getCommunicationReport(Leader leader, Logframe logframe);

  /**
   * Save the list of communications given.
   * 
   * @param communication - Information to be saved
   * @param leader
   * @param logframe
   * @return true if the information was saved successfully. False otherwise.
   */
  public boolean saveCommunicationReport(Communication communication, Leader leader, Logframe logframe);
}
