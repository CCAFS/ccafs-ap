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

package org.cgiar.ccafs.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class BaseSecurityContext {

  private static Logger LOG = LoggerFactory.getLogger(BaseSecurityContext.class);

  public Subject getSubject() {
    try {
      return SecurityUtils.getSubject();
    } catch (Exception e) {
      LOG.warn("Failed to get Subject, maybe user is not login or session is lost:", e);
      return null;
    }
  }

  /**
   * Verify that the current user has all the following permissions.
   * 
   * @param permissions
   * @return
   */
  public boolean hasAllPermissions(String... permissions) {
    Subject subject = getSubject();
    return subject == null ? false : subject.isPermittedAll(permissions);
  }


  /**
   * Verify that the current user has permission to any of the following
   * 
   * @param permissions
   * @return
   */
  public boolean hasAnyPermission(String[] permissions) {
    Subject subject = getSubject();
    if (subject != null && permissions != null) {
      for (String permission : permissions) {
        if (permission != null && subject.isPermitted(permission.trim())) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Verify that the current user has the privileges.
   * 
   * @param permission
   * @return
   */
  public boolean hasPermission(String permission) {
    Subject subject = getSubject();
    return subject == null ? false : subject.isPermitted(permission);
  }

  /**
   * Validates if the user has started a session
   * 
   * @return true if the session exists, false otherwise.
   */
  public boolean isAuthenticated() {
    Subject subject = getSubject();
    if (subject == null) {
      return false;
    }
    return subject.isAuthenticated();
  }
}
