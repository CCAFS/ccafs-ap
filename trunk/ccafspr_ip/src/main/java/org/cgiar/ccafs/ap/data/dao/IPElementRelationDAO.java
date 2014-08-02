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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPElementRelationDAO;

import com.google.inject.ImplementedBy;

/**
 * @author Hernán David Carvajal.
 */
@ImplementedBy(MySQLIPElementRelationDAO.class)
public interface IPElementRelationDAO {

  /**
   * This method removes from the database the relation between the parent
   * element and the child element
   *
   * @param parentElementID - parent element identifier
   * @param childElementID - child element identifier
   * @return true if the relation was successfully removed. False otherwise.
   */
  public boolean deleteIpElementsRelation(int parentElementID, int childElementID);

  /**
   * This method removes from the database all records when the childElementID
   * is the value received as parameter.
   *
   * @param childElementID - element identifier
   * @return true if the relations were removed. False otherwise.
   */
  public boolean deleteRelationsByChildElement(int childElementID);
}
