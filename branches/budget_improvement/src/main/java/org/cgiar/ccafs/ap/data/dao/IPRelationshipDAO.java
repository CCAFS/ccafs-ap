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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPRelationshipDAO;

import com.google.inject.ImplementedBy;

/**
 * @author Hernán David Carvajal.
 */
@ImplementedBy(MySQLIPRelationshipDAO.class)
public interface IPRelationshipDAO {

  /**
   * This method save the relationship existent between two ip elements
   *
   * @param parentElementID - element identifier
   * @param childElementID - element identifier
   * @param relationTypeID - relation type identifier
   * @return true if all the information was saved successfully. False otherwise
   */
  public boolean saveIPRelation(int parentElementID, int childElementID, int relationTypeID);
}
