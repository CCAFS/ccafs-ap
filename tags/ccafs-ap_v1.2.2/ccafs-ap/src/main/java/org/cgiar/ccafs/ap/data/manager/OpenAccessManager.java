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

import org.cgiar.ccafs.ap.data.manager.impl.OpenAccessManagerImpl;
import org.cgiar.ccafs.ap.data.model.OpenAccess;

import com.google.inject.ImplementedBy;

@ImplementedBy(OpenAccessManagerImpl.class)
public interface OpenAccessManager {

  /**
   * Return the information related to the open access identified by id
   * 
   * @param id - the open access identifier
   * @return an openAccess object
   */
  public OpenAccess getOpenAccess(String id);

  /**
   * Get all the open access option
   * 
   * @return an array of openAccess objects
   */
  public OpenAccess[] getOpenAccessList();
}
