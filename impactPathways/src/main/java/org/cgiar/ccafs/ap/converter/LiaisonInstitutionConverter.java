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

import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 */
public class LiaisonInstitutionConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(LiaisonInstitutionConverter.class);

  // Manager
  private LiaisonInstitutionManager liaisonInstitutionManager;

  @Inject
  public LiaisonInstitutionConverter(LiaisonInstitutionManager liaisonInstitutionManager) {
    this.liaisonInstitutionManager = liaisonInstitutionManager;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == LiaisonInstitution.class) {
      String id = values[0];
      try {
        LiaisonInstitution institution = liaisonInstitutionManager.getLiaisonInstitution(Integer.parseInt(id));
        LOG.debug(">> convertFromString > id = {} ", id);
        return institution;
      } catch (NumberFormatException e) {
        // Do Nothing
        LOG.error("Problem to convert User from String (convertFromString) for user_id = {} ", id, e.getMessage());
      }
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public String convertToString(Map context, Object o) {
    if (o != null) {
      LiaisonInstitution institution = (LiaisonInstitution) o;
      LOG.debug(">> convertToString > id = {} ", institution.getId());
      return institution.getId() + "";
    }
    return null;
  }

}
