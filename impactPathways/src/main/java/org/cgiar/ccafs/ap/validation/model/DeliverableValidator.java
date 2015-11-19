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

package org.cgiar.ccafs.ap.validation.model;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.NextUser;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.List;

import com.google.inject.Inject;


/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class DeliverableValidator extends BaseValidator {


  @Inject
  public DeliverableValidator() {
  }

  public boolean hasNextUsers(List<NextUser> nextUsers) {
    return (nextUsers != null && !nextUsers.isEmpty());
  }

  public boolean hasResponsible(DeliverablePartner responsible) {
    return (responsible != null && responsible.getPartner() != null);
  }

  public boolean isValidMOG(IPElement mog) {
    return (mog != null);
  }

  public boolean isValidNextUserExpectedChanges(String expectedChanges) {
    return (this.isValidString(expectedChanges) && this.wordCount(expectedChanges) <= 50);
  }

  public boolean isValidNextUserName(String nextUserName) {
    return (this.isValidString(nextUserName) && this.wordCount(nextUserName) <= 20);
  }

  public boolean isValidNextUserStrategies(String strategies) {
    return (this.isValidString(strategies) && this.wordCount(strategies) <= 50);
  }

  public boolean isValidTitle(String title) {
    return (this.isValidString(title) && this.wordCount(title) <= 15);
  }

  public boolean isValidType(DeliverableType type) {
    return (type != null);
  }

  public boolean isValidTypeOther(DeliverableType type, String typeOther) {
    if (type == null) {
      return true;
    }
    if (type.getId() == APConstants.DELIVERABLE_SUBTYPE_OTHER_ID) {
      return this.isValidString(typeOther); // type other has to be filled.
    }
    return true; // This is not the type, thus it is ok.
  }

  public boolean isValidYear(int year) {
    return (year != -1);
  }


}
