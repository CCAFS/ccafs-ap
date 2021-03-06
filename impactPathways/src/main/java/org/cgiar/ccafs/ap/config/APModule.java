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

import org.cgiar.ccafs.security.authentication.Authenticator;
import org.cgiar.ccafs.security.authentication.DBAuthenticator;
import org.cgiar.ccafs.security.authentication.LDAPAuthenticator;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.PropertiesManager;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APModule implements Module {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(APModule.class);

  private PropertiesManager properties;
  private APConfig config;

  @Override
  public void configure(Binder binder) {
    // We are configuring google guice using annotation. However you can do it here if you want.
    binder.bind(Authenticator.class).annotatedWith(Names.named("LDAP")).to(LDAPAuthenticator.class);
    binder.bind(Authenticator.class).annotatedWith(Names.named("DB")).to(DBAuthenticator.class);

    // In addition, we are using this place to configure other stuffs.
    ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);

    properties = new PropertiesManager();

    config = new APConfig(properties);
    LOG.info("----- DATABASE CONNECTION -----");
    LOG.info(properties.getPropertiesAsString(config.MYSQL_USER));

    LOG.info(properties.getPropertiesAsString(config.MYSQL_HOST));
    LOG.info(properties.getPropertiesAsString(config.MYSQL_DATABASE));
    LOG.info("----- RESOURCE PATH --------");
    LOG.info(config.getResourcePath().getAbsolutePath());


  }

}
