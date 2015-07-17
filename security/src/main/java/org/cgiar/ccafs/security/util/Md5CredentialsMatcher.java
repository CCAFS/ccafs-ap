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

package org.cgiar.ccafs.security.util;

import org.cgiar.ccafs.utils.MD5Convert;

import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;


/**
 * @author Hernán David Carvajal
 */

public class Md5CredentialsMatcher extends SimpleCredentialsMatcher {

  @Override
  protected boolean equals(Object tokenCredentials, Object accountCredentials) {
    String tokenPassword = new String((char[]) tokenCredentials);
    String encryptPassword = MD5Convert.stringToMD5(tokenPassword);
    return ((String) accountCredentials).equals(encryptPassword);
  }
}
