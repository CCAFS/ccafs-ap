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

import org.cgiar.ccafs.ap.data.manager.impl.KeywordManagerImpl;
import org.cgiar.ccafs.ap.data.model.Keyword;

import com.google.inject.ImplementedBy;

@ImplementedBy(KeywordManagerImpl.class)
public interface KeywordManager {

  /**
   * Get the keyword object corresponding with the given id
   * 
   * @param id - keyword identifier
   * @return Keyword object with the information
   */
  public Keyword getKeyword(String id);

  /**
   * Get the list of keywords from the database.
   * 
   * @return a list of Keyword objects with the information
   */
  public Keyword[] getKeywordList();
}
