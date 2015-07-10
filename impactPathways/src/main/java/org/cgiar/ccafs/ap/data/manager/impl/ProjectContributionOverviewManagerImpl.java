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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ProjectContributionOverivewDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectContributionOverviewManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.util.DualMap;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectContributionOverviewManagerImpl implements ProjectContributionOverviewManager {

  private ProjectContributionOverivewDAO overviewDAO;

  @Inject
  public ProjectContributionOverviewManagerImpl(ProjectContributionOverivewDAO overviewDAO) {
    this.overviewDAO = overviewDAO;
  }

  @Override
  public DualMap<Integer, IPElement, OutputOverview> getProjectContributionOverviews(Project project) {
    DualMap<Integer, IPElement, OutputOverview> outputOverviews = new DualMap();
    List<Map<String, String>> overviewsData = overviewDAO.getProjectContributionOverviews(project.getId());

    for (Map<String, String> overviewData : overviewsData) {
      IPElement output = new IPElement();
      output.setId(Integer.parseInt(overviewData.get("output_id")));
      output.setDescription(overviewData.get("output_description"));


      OutputOverview overview = new OutputOverview(-1);
      if (overviewData.get("id") != null) {
        overview.setId(Integer.parseInt(overviewData.get("id")));
        overview.setExpectedAnnualContribution(overviewData.get("annual_contribution"));
        overview.setSocialInclusionDimmension(overviewData.get("gender_contribution"));
      }

      Integer year = new Integer(overviewData.get("year"));

      outputOverviews.put(year, output, overview);
    }

    return outputOverviews;
  }
}
