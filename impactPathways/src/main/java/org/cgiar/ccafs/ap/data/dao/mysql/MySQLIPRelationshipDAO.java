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

import org.cgiar.ccafs.ap.data.dao.IPRelationshipDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hernán david Carvajal.
 */
public class MySQLIPRelationshipDAO implements IPRelationshipDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLIPRelationshipDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLIPRelationshipDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean saveIPRelation(int parentElementID, int childElementID, int relationTypeID) {
    LOG.debug(">> saveIPRelation(parentElementID={}, childElementID={}, relationTypeID = {})", new int[] {
      parentElementID, childElementID, relationTypeID});

    StringBuilder query = new StringBuilder();
    boolean result = false;

    query.append("INSERT INTO ip_relationships (parent_id, child_id, relation_type_id) VALUES (?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE parent_id = parent_id ");
    Object[] values = new Object[3];
    values[0] = parentElementID;
    values[1] = childElementID;
    values[2] = relationTypeID;

    // TODO HC - I can confirm this is working, however is not well implemented. Hernán, Could you please update the
// following
// line to reflect the kind of return from the saveData method? thks.
    int ipRelationAdded = databaseManager.saveData(query.toString(), values);
    if (ipRelationAdded > 0) {
      result = true;
    }

    LOG.debug("<< saveIPRelation():{}", result);
    return result;
  }

}
