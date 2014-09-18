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

import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class IPElementConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(IPElementConverter.class);

  // Manager
  private IPElementManager ipElementManager;


  @Inject
  public IPElementConverter(IPElementManager ipElementManager) {
    this.ipElementManager = ipElementManager;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == IPElement.class) {
      String id = values[0];
      try {
        LOG.debug(">> convertFromString > id = {} ", id);
        return ipElementManager.getIPElement(Integer.parseInt(values[0]));
      } catch (NumberFormatException e) {
        // Do Nothing
        LOG.error("Problem to convert IPElement from String (convertFromString) for id = {} ", id, e.getMessage());
      }
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public String convertToString(Map context, Object o) {
    if (o != null) {
      IPElement ipElement = (IPElement) o;
      LOG.debug(">> convertToString > id = {} ", ipElement.getId());
      return String.valueOf(ipElement.getId());
    }
    return null;
  }
}
