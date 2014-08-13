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

import org.cgiar.ccafs.ap.data.manager.impl.IndicatorManagerImpl;
import org.cgiar.ccafs.ap.data.model.Indicator;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IndicatorManagerImpl.class)
public interface IndicatorManager {

  /**
   * Get the list of all the indicators stored in the database.
   * 
   * @return a list of Indicator objects with the information.
   */
  public List<Indicator> getIndicatorList();
}