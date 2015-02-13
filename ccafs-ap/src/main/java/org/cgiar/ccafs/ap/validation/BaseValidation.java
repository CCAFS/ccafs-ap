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

package org.cgiar.ccafs.ap.validation;

import com.google.inject.Inject;
import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;


/**
 * @author Hernán David Carvajal
 */

public class BaseValidation {

  // Text provider to read the internationalization file
  private TextProvider textProvider;


  @Inject
  public BaseValidation() {
    this.textProvider = new DefaultTextProvider();
  }

  protected String getText(String key) {
    return textProvider.getText(key);
  }

  protected String getText(String key, String[] args) {
    return textProvider.getText(key, args);
  }

  protected boolean isValidString(String string) {
    return (string != null && !string.isEmpty());
  }
}
