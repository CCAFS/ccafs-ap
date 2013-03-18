package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.PublicationDAO;
import org.cgiar.ccafs.ap.data.dao.PublicationThemeDAO;
import org.cgiar.ccafs.ap.data.manager.PublicationManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.OpenAccess;
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.data.model.PublicationType;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PublicationManagerImpl implements PublicationManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PublicationManagerImpl.class);
  private PublicationDAO publicationDAO;
  private PublicationThemeDAO publicationThemeDAO;

  @Inject
  public PublicationManagerImpl(PublicationDAO publicationDAO, PublicationThemeDAO publicationThemeDAO) {
    this.publicationDAO = publicationDAO;
    this.publicationThemeDAO = publicationThemeDAO;
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
      OpenAccess publicationAccess = new OpenAccess();
      if (pubData.get("publication_access_id") != null) {
        publicationAccess.setId(Integer.parseInt(pubData.get("publication_access_id")));
      } else {
        // publicationAccess.setId(-1);
      }
      List<Map<String, String>> themes = publicationThemeDAO.getThemes(publication.getId());
      Theme[] relatedThemes = new Theme[themes.size()];
      for (int c = 0; c < themes.size(); c++) {
        Theme theme = new Theme();
        theme.setId(Integer.parseInt(themes.get(c).get("id")));
        theme.setCode(themes.get(c).get("code"));
        theme.setDescription(themes.get(c).get("description"));
        relatedThemes[c] = theme;
      }
      publication.setRelatedThemes(relatedThemes);
      publicationAccess.setName(pubData.get("publication_access_name"));
      publication.setAccess(publicationAccess);
      publications.add(publication);
    }
    LOG.debug("Publications made by leader {} for logframe {} loaded successfully", leader.getId(), logframe.getId());
    return publications;
  }

  @Override
  public boolean removeAllPublications(Leader leader, Logframe logframe) {
    LOG.debug("Sent a request to delete all publications related to leader {} and logframe.", leader.getId(),
      logframe.getId());
    return publicationDAO.removeAllPublications(leader.getId(), logframe.getId());
  }

  @Override
  public boolean savePublications(List<Publication> publications, Logframe logframe, Leader leader) {
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
      if (publication.getAccess().getId() == 0) {
        pubData.put("open_access_id", null);
      } else {
        pubData.put("open_access_id", String.valueOf(publication.getAccess().getId()));
      }
      pubData.put("citation", publication.getCitation());
      if (publication.getFileUrl().isEmpty()) {
        pubData.put("file_url", null);
      } else {
        pubData.put("file_url", publication.getFileUrl());
      }
      pubData.put("logframe_id", logframe.getId() + "");
      pubData.put("activity_leader_id", leader.getId() + "");

      LOG.debug("Sent a request to save the publications made by leader {} into the DAO", leader.getId());
      int publicationId = publicationDAO.savePublication(pubData);
      // If the publication has an id the addDeliverable function return 0 as id,
      // so, the id must be set to its original value
      publicationId = (publication.getId() != -1) ? publication.getId() : publicationId;

      // If the publications was successfully saved, save the themes related
      if (publicationId != -1) {
        // lets add the file format list.
        boolean themesRelatedAdded = publicationThemeDAO.saveThemes(publicationId, publication.getRelatedThemesIds());
        if (!themesRelatedAdded) {
          return false;
        }
      } else {
        return false;
      }
    }
    return true;
  }
}
