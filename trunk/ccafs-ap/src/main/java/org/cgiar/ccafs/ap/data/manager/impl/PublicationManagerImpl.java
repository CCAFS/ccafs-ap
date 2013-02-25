package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.PublicationDAO;
import org.cgiar.ccafs.ap.data.manager.PublicationManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.data.model.PublicationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class PublicationManagerImpl implements PublicationManager {

  private PublicationDAO publicationDAO;

  @Inject
  public PublicationManagerImpl(PublicationDAO publicationDAO) {
    this.publicationDAO = publicationDAO;
  }

  @Override
  public List<Publication> getPublications(Leader leader, Logframe logframe) {
    List<Publication> publications = new ArrayList<>();
    List<Map<String, String>> pubsData = publicationDAO.getPublications(leader.getId(), logframe.getId());
    for (Map<String, String> pubData : pubsData) {
      Publication publication = new Publication();
      publication.setId(Integer.parseInt(pubData.get("id")));
      publication.setIdentifier(pubData.get("identifier"));
      publication.setCitation(pubData.get("citation"));
      publication.setFileUrl(pubData.get("file_url"));
      publication.setLogframe(logframe);
      publication.setLeader(leader);
      PublicationType publicationType = new PublicationType();
      publicationType.setId(Integer.parseInt(pubData.get("publication_type_id")));
      publicationType.setName(pubData.get("publication_type_name"));
      publication.setType(publicationType);
      publications.add(publication);
    }
    return publications;
  }

  @Override
  public boolean removeAllPublications(Leader leader, Logframe logframe) {
    return publicationDAO.removeAllPublications(leader.getId(), logframe.getId());
  }

  @Override
  public boolean savePublications(List<Publication> publications, Logframe logframe, Leader leader) {
    boolean problem = false;
    List<Map<String, String>> publicationsData = new ArrayList<>();
    for (Publication publication : publications) {
      Map<String, String> pubData = new HashMap<>();
      if (publication.getId() != -1) {
        pubData.put("id", publication.getId() + "");
      } else {
        pubData.put("id", null);
      }
      pubData.put("publication_type_id", publication.getType().getId() + "");
      if (publication.getIdentifier().isEmpty()) {
        pubData.put("identifier", null);
      } else {
        pubData.put("identifier", publication.getIdentifier());
      }
      pubData.put("citation", publication.getCitation());
      if (publication.getFileUrl().isEmpty()) {
        pubData.put("file_url", null);
      } else {
        pubData.put("file_url", publication.getFileUrl());
      }
      pubData.put("logframe_id", logframe.getId() + "");
      pubData.put("activity_leader_id", leader.getId() + "");
      publicationsData.add(pubData);
    }
    problem = !publicationDAO.savePublications(publicationsData);
    return !problem;
  }
}
