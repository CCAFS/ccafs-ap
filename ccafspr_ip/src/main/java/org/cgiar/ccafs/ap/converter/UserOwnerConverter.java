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

import org.cgiar.ccafs.ap.data.manager.InstitutionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class UserOwnerConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(UserOwnerConverter.class);

  // Manager
  private UserManager userManager;
  private InstitutionManager institutionManager;

  @Inject
  public UserOwnerConverter(UserManager userManager, InstitutionManager institutionManager) {
    this.userManager = userManager;
    this.institutionManager = institutionManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == User.class) {
      String[] ids = values[0].split("[,]");
      try {
        // This will return an user without currentInstitution defined.
        // If you want to get the current institution, you will need to use the converter XXXX
        User user = userManager.getUser(Integer.parseInt(ids[0]));
        user.setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(ids[1])));
        LOG.debug(">> convertFromString > ids = {} ", ids);
        return user;
      } catch (NumberFormatException e) {
        // Do Nothing
        LOG.error("Problem to convert User from String (convertFromString) for user_id = {} ", ids, e.getMessage());
      }
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    User user = (User) o;
    LOG.debug(">> convertToString > id = {} ", user.getComposedOwnerIDs());
    // Return should be made with simple quotes '', since the returned id is a String rather than an number.
    // Thus, this is how is going to be used by the view.
    return "'" + user.getComposedOwnerIDs() + "'";
  }

}
