/*****************************************************************
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
 *****************************************************************/
package org.cgiar.ccafs.ap.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.data.model.IPCrossCutting;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class IPCrossCuttingThemeListConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(IPCrossCuttingThemeListConverter.class);

  // Manager
  private IPCrossCuttingManager ipCrossCuttingManager;

  @Inject
  public IPCrossCuttingThemeListConverter(IPCrossCuttingManager ipCrossCuttingManager) {
    this.ipCrossCuttingManager = ipCrossCuttingManager;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      List<IPCrossCutting> themes = new ArrayList<>();
      try {
        for (String value : values) {
          IPCrossCutting theme = ipCrossCuttingManager.getIPCrossCutting(Integer.parseInt(value));
          themes.add(theme);
          LOG.debug(">> convertFromString > id = {} ", value);
        }
      } catch (NumberFormatException e) {
        // Do Nothing
        LOG.error("Problem to convert IPCrossCutting from String (convertFromString) for values = {} ", values,
          e.getMessage());
      }
      return themes;
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public String convertToString(Map context, Object o) {
    List<IPCrossCutting> themes = (List) o;
    String[] themeIds = new String[themes.size()];
    for (int c = 0; c < themeIds.length; c++) {
      themeIds[c] = themes.get(c).getId() + "";
    }
    LOG.debug(">> convertToString > id = {} ", Arrays.toString(themeIds));
    return Arrays.toString(themeIds);
  }
}
