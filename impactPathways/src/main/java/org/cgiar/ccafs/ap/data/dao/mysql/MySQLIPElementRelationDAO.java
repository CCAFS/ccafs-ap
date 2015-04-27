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
package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.IPElementRelationDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hernán David Carvajal.
 */
public class MySQLIPElementRelationDAO implements IPElementRelationDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLIPElementRelationDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLIPElementRelationDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }


  @Override
  public boolean deleteIpElementsRelation(int parentElementID, int childElementID) {
    LOG.debug(">> deleteIpElementsRelation(parentElementID={}, childElementID={})", parentElementID, childElementID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM ip_relationships ");
    query.append("WHERE parent_id = ? AND child_id = ?");

    int rowsDeleted = daoManager.delete(query.toString(), new Object[] {parentElementID, childElementID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteIpElementsRelation():{}", true);
      return true;
    }
    LOG.debug("<< deleteIpElementsRelation():{}", false);
    return false;
  }


  @Override
  public boolean deleteRelationsByChildElement(int childElementID) {
    LOG.debug(">> removeRelationsByChildElement(childElementID={})", childElementID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM ip_relationships ");
    query.append("WHERE child_id = ?");

    int rowsDeleted = daoManager.delete(query.toString(), new Object[] {childElementID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< removeRelationsByChildElement():{}", true);
      return true;
    }
    LOG.debug("<< removeRelationsByChildElement():{}", false);
    return false;
  }


}
