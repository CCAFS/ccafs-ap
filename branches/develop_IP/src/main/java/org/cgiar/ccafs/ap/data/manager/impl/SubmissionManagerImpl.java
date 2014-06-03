package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.SubmissionDAO;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Submission;

import java.util.Map;

import com.google.inject.Inject;


public class SubmissionManagerImpl implements SubmissionManager {

  private SubmissionDAO submissionDAO;

  @Inject
  public SubmissionManagerImpl(SubmissionDAO submissionDAO) {
    this.submissionDAO = submissionDAO;
  }

  @Override
  public Submission getSubmission(Leader _leader, Logframe _logframe, String section) {
    Map<String, String> submissionData = submissionDAO.getSubmission(_leader.getId(), _logframe.getId(), section);
    if (!submissionData.isEmpty()) {
      Submission submission = new Submission();
      // Logframe
      Logframe logframe = new Logframe();
      logframe.setId(Integer.parseInt(submissionData.get("logframe_id")));
      logframe.setYear(Integer.parseInt(submissionData.get("logframe_year")));
      logframe.setName(submissionData.get("logframe_name"));
      // Leader
      Leader leader = new Leader();
      leader.setId(Integer.parseInt(submissionData.get("leader_id")));
      leader.setAcronym(submissionData.get("leader_acronym"));

      submission.setLeader(leader);
      submission.setLogframe(logframe);
      submission.setSection(section);

      return submission;
    } else {
      return null;
    }
  }

  @Override
  public boolean submit(Submission submission) {
    return submissionDAO.submit(submission.getLeader().getId(), submission.getLogframe().getId(),
      submission.getSection());
  }

}
