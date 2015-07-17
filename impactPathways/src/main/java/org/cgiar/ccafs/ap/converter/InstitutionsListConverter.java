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

import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.Institution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 */
public class InstitutionsListConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(InstitutionsListConverter.class);

  // Manager
  private InstitutionManager institutionManager;

  @Inject
  public InstitutionsListConverter(InstitutionManager institutionManager) {
    this.institutionManager = institutionManager;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    List<Institution> institutions = new ArrayList<Institution>();
    try {
      for (String value : values) {
        institutions.add(institutionManager.getInstitution(Integer.parseInt(value)));
        LOG.debug(">> convertFromString > id = {} ", value);
      }
    } catch (NumberFormatException e) {
      // Do Nothing
      LOG.error("Problem to convert Institutions List from String (convertFromString) for values = {} ", values,
        e.getMessage());
    }
    return institutions;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public String convertToString(Map context, Object o) {
    List<Institution> institutions = (List<Institution>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (Institution institution : institutions) {
      temp.add(institution.getId() + "");
    }
    return temp.toString();
  }

}
