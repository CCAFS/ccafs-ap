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

import org.cgiar.ccafs.ap.data.manager.impl.StatusManagerImpl;
import org.cgiar.ccafs.ap.data.model.Status;

import com.google.inject.ImplementedBy;

@ImplementedBy(StatusManagerImpl.class)
public interface StatusManager {

  /**
   * Find the status object identified with the given id.
   * 
   * @param id - Status identifier.
   * @return a Status object identified with the given id or null if nothing found.
   */
  public Status getStatus(String id);

  /**
   * Get the status list.
   * 
   * @return an array of Status objects or null if no data found.
   */
  public Status[] getStatusList();

}
