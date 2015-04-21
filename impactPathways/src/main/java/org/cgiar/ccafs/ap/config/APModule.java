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
package org.cgiar.ccafs.ap.config;

import org.apache.commons.lang3.builder.ToStringStyle;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.google.inject.AbstractModule;


public class APModule extends AbstractModule {

  @Override
  protected void configure() {
    // We are configuring google guice using annotation. However you can do it here if you want.

    // In addition, we are using this place to configure other stuffs.
    ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
  }

}
