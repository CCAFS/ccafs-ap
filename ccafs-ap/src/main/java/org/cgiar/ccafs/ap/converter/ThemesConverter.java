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

package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class ThemesConverter extends StrutsTypeConverter {

  private ThemeManager themeManager;

  @Inject
  public ThemesConverter(ThemeManager themeManager) {
    this.themeManager = themeManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    Theme[] themes = themeManager.getThemes(values);
    return themes;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<Theme> themesArray = (List<Theme>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (Theme t : themesArray) {
      temp.add(String.valueOf(t.getId()));
    }
    // TODO
    return temp.toString();
  }
}
