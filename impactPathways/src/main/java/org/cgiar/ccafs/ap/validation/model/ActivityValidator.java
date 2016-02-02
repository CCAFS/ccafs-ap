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

import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.Date;

import com.google.inject.Inject;


/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ActivityValidator extends BaseValidator {


  @Inject
  public ActivityValidator() {
  }

  public boolean isValidDescription(String description) {
    return (this.isValidString(description) && this.wordCount(description) <= 150) ? true : false;
  }

  public boolean isValidEndDate(Date endDate) {
    return endDate != null;
  }

  public boolean isValidLeader(PartnerPerson leader) {
    return leader != null;
  }

  public boolean isValidStartDate(Date startDate) {
    return startDate != null;
  }

  public boolean isValidStatus(int status) {
    return (status != -1);
  }

  public boolean isValidTitle(String title) {
    return (this.isValidString(title) && this.wordCount(title) <= 15) ? true : false;
  }
}
