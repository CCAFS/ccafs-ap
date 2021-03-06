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


package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysqlhiberate.ProjectLeverageMySQLDAO;
import org.cgiar.ccafs.ap.data.model.ProjectLeverage;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectLeverageMySQLDAO.class)
public interface ProjectLevarageDAO {

  public boolean deleteLeverage(int leverageId, int userID, String justification);

  public boolean deleteLeveragesByProject(int projectID);

  public boolean existLeverage(int leverageID);

  public ProjectLeverage find(int id);

  public List<ProjectLeverage> getLeveragesByProject(int projectID);

  public int save(ProjectLeverage projectHighlihts);
}
