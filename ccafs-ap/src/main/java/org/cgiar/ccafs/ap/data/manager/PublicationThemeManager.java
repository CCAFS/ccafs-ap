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

import org.cgiar.ccafs.ap.data.manager.impl.PublicationThemeManagerImpl;
import org.cgiar.ccafs.ap.data.model.PublicationTheme;

import com.google.inject.ImplementedBy;

@ImplementedBy(PublicationThemeManagerImpl.class)
public interface PublicationThemeManager {

  /**
   * This function gets all the publication themes available.
   * 
   * @return an array of PublicationTheme objects with the information.
   */
  public PublicationTheme[] getPublicationThemes();

  /**
   * This function gets all the publication themes identified by the ids given.
   * 
   * @return an array of PublicationTheme objects with the information.
   */
  public PublicationTheme[] getPublicationThemes(String[] ids);
}
