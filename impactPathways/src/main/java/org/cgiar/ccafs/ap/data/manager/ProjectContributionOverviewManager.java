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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ProjectContributionOverviewManagerImpl;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(ProjectContributionOverviewManagerImpl.class)
public interface ProjectContributionOverviewManager {

  /**
   * This method deletes a contribution overview made by a project to some output.
   * 
   * @param outputOverviewID - output overview identifier
   * @param user - The user who is making the change
   * @param justification - Justification of the change
   * @return True if the relation was deleted successfully. False otherwise.
   */
  public boolean deleteProjectContributionOverview(int outputOverviewID, User user, String justification);

  /**
   * This method returns the description made for each output linked to the project identified by the value received
   * by parameter.
   * 
   * @param project - Project Identifier
   * @return a DualMap with the format <year, output, overview>
   */
  public List<OutputOverview> getProjectContributionOverviews(Project project);

  /**
   * This method returns the description made for each output linked to the project identified by the value received
   * by parameter and the year.
   * 
   * @param project - Project Identifier
   * @param int year - Year
   * @return a DualMap with the format <year, output, overview>
   */
  public List<OutputOverview> getProjectContributionOverviewsByYearAndOutput(Project project, int year, int outputID);

  /**
   * TODO Falta documentar
   * 
   * @param mogId
   * @param year
   * @return
   */
  public List<OutputOverview> getProjectContributionOverviewsSytnhesis(int mogId, int year, int program);

  /**
   * TODO Falta documentar
   * 
   * @param mogId
   * @param year
   * @return
   */
  public List<OutputOverview> getProjectContributionOverviewsSytnhesisGlobal(int mogId, int year, int program);

  /**
   * This method saves the output overviews contained in the project received by parameter.
   * 
   * @param project - Project that contains the output overviews to save.
   * @param currentUser - User who is making the changes
   * @param justification
   * @return True if the information was saved successfully. False otherwise.
   */
  public boolean saveProjectContribution(Project project, User currentUser, String justification);
}
