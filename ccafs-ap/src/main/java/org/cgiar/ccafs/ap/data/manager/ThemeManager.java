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

import org.cgiar.ccafs.ap.data.manager.impl.ThemeManagerImpl;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.Theme;

import com.google.inject.ImplementedBy;

@ImplementedBy(ThemeManagerImpl.class)
public interface ThemeManager {

  /**
   * Get the theme object corresponding to the given id
   * 
   * @param id - Theme identifier
   * @return Theme objects
   */
  public Theme getTheme(String id);

  /**
   * Get the theme list.
   * 
   * @return an array of Theme objects or null if no data found
   */
  public Theme[] getThemes();

  /**
   * Get all the themes that exist in a given logframe.
   * 
   * @param logframe - the logframe that belong the theme array.
   * @return an array of themes that belong to the given logframe
   */
  public Theme[] getThemes(Logframe logframe);

  /**
   * Get a list of themes object corresponding to the given array of ids
   * 
   * @param ids - Array of theme identifiers
   * @return a list of Theme objects
   */
  public Theme[] getThemes(String[] ids);

  /**
   * Get the themes on which partner works.
   * 
   * @param partner
   * @return an array of themes objects or null if there is no themes related.
   */
  public Theme[] getThemesByPartner(Partner partner);
}
