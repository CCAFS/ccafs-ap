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

import org.cgiar.ccafs.ap.data.manager.impl.LogframeManagerImpl;
import org.cgiar.ccafs.ap.data.model.Logframe;

import com.google.inject.ImplementedBy;

@ImplementedBy(LogframeManagerImpl.class)
public interface LogframeManager {

  /**
   * Get a logframe object based on the given id.
   * 
   * @param id - identifier.
   * @return a Logframe object.
   */
  public Logframe getLogframe(int id);

  /**
   * Get a logframe object based on the given year or null if no exists logframe for the given year.
   * 
   * @param year of the logframe.
   * @return a Logframe object.
   */
  public Logframe getLogframeByYear(int year);
}
