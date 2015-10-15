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

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.SubmissionDAO;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.Submission;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class SubmissionManagerImpl implements SubmissionManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(SubmissionManagerImpl.class);

  // DAO
  private SubmissionDAO submissionDAO;
  private UserManager userManager;

  private DateFormat dateFormatter;

  @Inject
  public SubmissionManagerImpl(SubmissionDAO submissionDAO, UserManager userManager) {
    this.dateFormatter = new SimpleDateFormat(APConstants.DATE_TIME_FORMAT);
    this.submissionDAO = submissionDAO;
    this.userManager = userManager;
  }

  @Override
  public List<Submission> getProjectSubmissions(Project project) {
    List<Submission> projectSubmissions = new ArrayList<>();
    List<Map<String, String>> submissionsData = submissionDAO.getProjectSubmissions(project.getId());
    for (Map<String, String> submissionData : submissionsData) {
      Submission submission = new Submission();
      submission.setId(Integer.parseInt(submissionData.get("id")));
      submission.setCycle(submissionData.get("cycle"));
      submission.setYear(Short.parseShort(submissionData.get("year")));
      try {
        submission.setDateTime(dateFormatter.parse(submissionData.get("date_time")));
      } catch (ParseException e) {
        LOG.error("There was an error formatting the date time for a Project Submission", e);
      }
      submission.setUser(userManager.getUser(Integer.parseInt(submissionData.get("id"))));
      projectSubmissions.add(submission);
    }
    return projectSubmissions;
  }

  @Override
  public int saveProjectSubmission(Project project, Submission submission) {
    Map<String, Object> submissionData = new HashMap<>();
    if (submission.getId() > 0) {
      submissionData.put("id", submission.getId());
    }
    submissionData.put("cycle", submission.getCycle());
    submissionData.put("year", submission.getYear());
    submissionData.put("project_id", project.getId());
    submissionData.put("user_id", submission.getUser().getId());
    return submissionDAO.saveProjectSubmission(submissionData);
  }

}
