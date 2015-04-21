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

import java.util.Map;

import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;

import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 * @author Héctor Fabio Tobón R.
 */
public class DeliverableTypeConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(DeliverableTypeConverter.class);

  // Manager
  private DeliverableTypeManager typeManager;

  @Inject
  public DeliverableTypeConverter(DeliverableTypeManager typeManager) {
    this.typeManager = typeManager;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == DeliverableType.class) {
      String id = values[0];
      try {
        LOG.debug(">> convertFromString > id = {} ", id);
        return typeManager.getDeliverableTypeById(Integer.parseInt(id));
      } catch (NumberFormatException e) {
        // Do Nothing
        LOG.error("Problem to convert Deliverable Type from String (convertFromString) for code = {} ", id,
          e.getMessage());
      }
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public String convertToString(Map context, Object o) {
    DeliverableType type = (DeliverableType) o;
    LOG.debug(">> convertToString > id = {} ", type.getId());
    return type.getId() + "";
  }

}
