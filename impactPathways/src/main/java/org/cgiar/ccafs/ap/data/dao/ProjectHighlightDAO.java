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

import org.cgiar.ccafs.ap.data.dao.mysqlhiberate.ProjectHightLihgtMySQLDAO;
import org.cgiar.ccafs.ap.data.model.ProjectHighligths;
import org.cgiar.ccafs.ap.data.model.ProjectHighligthsCountry;
import org.cgiar.ccafs.ap.data.model.ProjectHighligthsTypes;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectHightLihgtMySQLDAO.class)
public interface ProjectHighlightDAO {

  public boolean deleteHighLight(int highLightId, int userID, String justification);

  public boolean deleteHighLightsByProject(int projectID);

  public boolean existHighLight(int highLightID);

  public ProjectHighligths find(int id);

  public ProjectHighligthsCountry findProjectHighligthsCountries(int highlighid, int country);

  public ProjectHighligthsTypes findProjectHighligthsTypes(int highlighid, int type);

  public List<ProjectHighligths> getHighLightsByProject(int projectID);

  public int save(ProjectHighligths projectHighlihts);
}
