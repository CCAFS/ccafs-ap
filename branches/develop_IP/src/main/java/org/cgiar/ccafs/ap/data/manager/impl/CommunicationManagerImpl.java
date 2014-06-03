package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CommunicationDAO;
import org.cgiar.ccafs.ap.data.manager.CommunicationManager;
import org.cgiar.ccafs.ap.data.model.Communication;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;


public class CommunicationManagerImpl implements CommunicationManager {

  private CommunicationDAO communicationsDAO;

  @Inject
  public CommunicationManagerImpl(CommunicationDAO communicationsDAO) {
    this.communicationsDAO = communicationsDAO;
  }

  @Override
  public Communication getCommunicationReport(Leader leader, Logframe logframe) {
    Communication communication = new Communication();
    Map<String, String> communicationReportData =
      communicationsDAO.getCommunicationReport(leader.getId(), logframe.getId());

    if (!communicationReportData.isEmpty()) {
      communication.setId(Integer.parseInt(communicationReportData.get("id")));
      communication.setMediaCampaings(communicationReportData.get("media_campaigns"));
      communication.setBlogs(communicationReportData.get("blogs"));
      communication.setWebsites(communicationReportData.get("websites"));
      communication.setSociaMediaCampaigns(communicationReportData.get("social_media_campaigns"));
      communication.setNewsletters(communicationReportData.get("newsletters"));
      communication.setEvents(communicationReportData.get("events"));
      communication.setVideosMultimedia(communicationReportData.get("videos_multimedia"));
      communication.setOtherCommunications(communicationReportData.get("other_communications"));

      // Fake logframe
      Logframe fLogframe = new Logframe();
      fLogframe.setId(Integer.parseInt(communicationReportData.get("logframe_id")));
      fLogframe.setYear(Integer.parseInt(communicationReportData.get("logframe_year")));
      communication.setLogframe(fLogframe);

      // fake leader
      Leader fLeader = new Leader();
      fLeader.setId(Integer.parseInt(communicationReportData.get("leader_id")));
      fLeader.setAcronym(communicationReportData.get("leader_acronym"));
      communication.setLeader(fLeader);
    } else {
      communication.setId(-1);
    }

    return communication;
  }

  @Override
  public boolean saveCommunicationReport(Communication communication, Leader leader, Logframe logframe) {

    Map<String, String> communicationData = new HashMap<>();
    if (communication.getId() != -1) {
      communicationData.put("id", String.valueOf(communication.getId()));
    } else {
      communicationData.put("id", null);
    }

    communicationData.put("media_campaigns", communication.getMediaCampaings());
    communicationData.put("blogs", communication.getBlogs());
    communicationData.put("websites", communication.getWebsites());
    communicationData.put("social_media_campaigns", communication.getSociaMediaCampaigns());
    communicationData.put("newsletters", communication.getNewsletters());
    communicationData.put("events", communication.getEvents());
    communicationData.put("videos_multimedia", communication.getVideosMultimedia());
    communicationData.put("other_communications", communication.getOtherCommunications());
    communicationData.put("activity_leader_id", String.valueOf(leader.getId()));
    communicationData.put("logframe_id", String.valueOf(logframe.getId()));

    return communicationsDAO.saveCommunicationReport(communicationData);
  }
}
