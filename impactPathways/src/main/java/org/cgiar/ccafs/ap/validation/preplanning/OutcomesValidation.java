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
package org.cgiar.ccafs.ap.validation.preplanning;

import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.List;

/**
 * @author Hernán David Carvajal
 */
public class OutcomesValidation extends BaseValidator {

  private static final long serialVersionUID = 114728807776318498L;

  public String validateForm(List<IPElement> outcomes) {
    StringBuilder validationMessage = new StringBuilder();

    for (IPElement outcome : outcomes) {
      if (outcome.getDescription().isEmpty()) {
        validationMessage.append(getText("validation.preplanning.outcomes.description") + ", ");
      }

      for (int i = 0; i < outcome.getIndicators().size(); i++) {
        if (outcome.getIndicators().get(i).getDescription().isEmpty()) {
          outcome.getIndicators().remove(i);
        }
      }
    }

    return validationMessage.toString();
  }
}
