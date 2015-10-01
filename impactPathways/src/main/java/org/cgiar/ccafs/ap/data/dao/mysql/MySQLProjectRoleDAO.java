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

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ProjectRoleDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class MySQLProjectRoleDAO implements ProjectRoleDAO {

  private Logger LOG = LoggerFactory.getLogger(MySQLProjectRoleDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLProjectRoleDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public boolean addProjectRoles(int projectID) {
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_roles (project_id, user_id, role_id) ");
    query.append("SELECT pp.project_id, ppp.user_id, (SELECT id FROM roles WHERE acronym = ppp.contact_type) ");
    query.append("FROM `project_partner_persons` ppp ");
    query.append("INNER JOIN project_partners pp ON ppp.project_partner_id = pp.id ");
    query.append("WHERE ((ppp.contact_type = 'PL' OR ppp.contact_type = 'PC') AND ppp.is_active = 1) ");
    query.append("AND pp.project_id = ?");

    int result = daoManager.saveData(query.toString(), new Object[] {projectID});
    return result != -1;
  }

  @Override
  public boolean removeProjectRoles(int projectID) {
    String query = "DELETE FROM project_roles WHERE project_id = ?;";
    int result = daoManager.delete(query, new Object[] {projectID});
    return result != -1;
  }

}
