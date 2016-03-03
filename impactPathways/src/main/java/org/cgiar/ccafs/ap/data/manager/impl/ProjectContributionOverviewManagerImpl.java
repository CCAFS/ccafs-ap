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
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
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
  public boolean deleteProjectContributionOverview(int outputOverviewID, User user, String justification) {
    return overviewDAO.deleteProjectContributionOverview(outputOverviewID, user.getId(), justification);
  }

  @Override
  public List<OutputOverview> getProjectContributionOverviews(Project project) {
    List<OutputOverview> outputOverviews = new ArrayList<>();
    List<Map<String, String>> overviewsData = overviewDAO.getProjectContributionOverviews(project.getId());

    for (Map<String, String> overviewData : overviewsData) {
      OutputOverview overview = new OutputOverview();
      overview.setId(Integer.parseInt(overviewData.get("id")));
      overview.setExpectedAnnualContribution(overviewData.get("annual_contribution"));
      overview.setSocialInclusionDimmension(overviewData.get("gender_contribution"));
      overview.setBriefSummary(overviewData.get("brief_summary"));
      overview.setSummaryGender(overviewData.get("summary_gender"));
      IPElement output = new IPElement();
      output.setId(Integer.parseInt(overviewData.get("output_id")));
      output.setDescription(overviewData.get("output_description"));
      overview.setOutput(output);
      overview.setYear(Integer.parseInt(overviewData.get("year")));

      outputOverviews.add(overview);
    }

    return outputOverviews;
  }


  @Override
  public List<OutputOverview> getProjectContributionOverviewsByYearAndOutput(Project project, int year, int outputID) {
    List<OutputOverview> outputOverviews = new ArrayList<>();
    List<Map<String, String>> overviewsData =
      overviewDAO.getProjectContributionOverviewsByYearAndOutput(project.getId(), year, outputID);

    for (Map<String, String> overviewData : overviewsData) {
      OutputOverview overview = new OutputOverview();
      overview.setId(Integer.parseInt(overviewData.get("id")));
      overview.setExpectedAnnualContribution(overviewData.get("annual_contribution"));
      overview.setSocialInclusionDimmension(overviewData.get("gender_contribution"));
      overview.setBriefSummary(overviewData.get("brief_summary"));
      overview.setSummaryGender(overviewData.get("summary_gender"));

      IPElement output = new IPElement();
      output.setId(Integer.parseInt(overviewData.get("output_id")));
      output.setDescription(overviewData.get("output_description"));
      overview.setOutput(output);
      overview.setYear(Integer.parseInt(overviewData.get("year")));

      outputOverviews.add(overview);
    }

    return outputOverviews;
  }

  @Override
  public List<OutputOverview> getProjectContributionOverviewsSytnhesis(int mogId, int year) {
    List<OutputOverview> outputOverviews = new ArrayList<>();
    List<Map<String, String>> overviewsData = overviewDAO.getProjectContributionOverviewsSynthesis(mogId, year);

    for (Map<String, String> overviewData : overviewsData) {
      OutputOverview overview = new OutputOverview();
      overview.setId(Integer.parseInt(overviewData.get("id")));
      overview.setExpectedAnnualContribution(overviewData.get("annual_contribution"));
      overview.setSocialInclusionDimmension(overviewData.get("gender_contribution"));
      overview.setBriefSummary(overviewData.get("brief_summary"));
      overview.setSummaryGender(overviewData.get("summary_gender"));
      IPElement output = new IPElement();
      output.setId(Integer.parseInt(overviewData.get("output_id")));

      overview.setOutput(output);
      overview.setYear(Integer.parseInt(overviewData.get("year")));
      overview.setProjectID(overviewData.get("project_id"));

      outputOverviews.add(overview);
    }

    return outputOverviews;
  }

  @Override
  public boolean saveProjectContribution(Project project, User user, String justification) {
    boolean saved = true;
    for (OutputOverview overview : project.getOutputsOverview()) {
      Map<String, Object> values = new HashMap<>();

      if (overview.getId() != -1) {
        values.put("id", overview.getId());
      } else {
        values.put("id", null);
      }

      values.put("output_id", overview.getOutput().getId());
      values.put("year", overview.getYear());
      values.put("annual_contribution", overview.getExpectedAnnualContribution());
      values.put("gender_contribution", overview.getSocialInclusionDimmension());
      values.put("brief_summary", overview.getBriefSummary());
      values.put("summary_gender", overview.getSummaryGender());

      saved = saved && overviewDAO.saveProjectContribution(project.getId(), values, user.getId(), justification);
    }

    return saved;
  }
}
