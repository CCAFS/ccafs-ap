package org.cgiar.ccafs.ap.action.reporting.publications;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OpenAccessManager;
import org.cgiar.ccafs.ap.data.manager.PublicationManager;
import org.cgiar.ccafs.ap.data.manager.PublicationTypeManager;
import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.OpenAccess;
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.data.model.PublicationType;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PublicationsReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PublicationsReportingAction.class);
  private static final long serialVersionUID = -7843902991180158797L;

  // Models
  private List<Publication> publications;
  private PublicationType[] publicationTypes;
  private OpenAccess[] publicationAccessList;
  private Map<Integer, String> themeList;
  // This array contains the publication types which need an access type specification.
  private int[] publicationTypeAccessNeed;

  // Managers
  private PublicationManager publicationManager;
  private PublicationTypeManager publicationTypeManager;
  private OpenAccessManager openAccessManager;
  private ThemeManager themeManager;

  @Inject
  public PublicationsReportingAction(APConfig config, LogframeManager logframeManager,
    PublicationManager publicationManager, PublicationTypeManager publicationTypeManager,
    OpenAccessManager openAccessManager, ThemeManager themeManager) {
    super(config, logframeManager);
    this.publicationManager = publicationManager;
    this.publicationTypeManager = publicationTypeManager;
    this.openAccessManager = openAccessManager;
    this.themeManager = themeManager;
  }

  public OpenAccess[] getPublicationAccessList() {
    return publicationAccessList;
  }

  public List<Publication> getPublications() {
    return publications;
  }

  public int[] getPublicationTypeAccessNeed() {
    return publicationTypeAccessNeed;
  }

  public PublicationType[] getPublicationTypes() {
    return publicationTypes;
  }


  public Map<Integer, String> getThemeList() {
    return themeList;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("Ther user {} load the publication section.", getCurrentUser().getEmail());
    publications =
      publicationManager.getPublications(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());

    publicationTypes = publicationTypeManager.getPublicationTypes();
    publicationAccessList = openAccessManager.getOpenAccessList();

    // Publication types which need an access type specification
    // ID = 1 - Journal paper
    publicationTypeAccessNeed = new int[1];
    publicationTypeAccessNeed[0] = publicationTypes[0].getId();

    Theme[] themes = themeManager.getThemes(this.getCurrentReportingLogframe());
    themeList = new HashMap<>();
    for (Theme theme : themes) {
      themeList.put(theme.getId(), getText("reporting.publications.Theme") + " " + theme.getCode());
    }

    // Remove all publications so they can be added again in the save method.
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      LOG.debug("The publications have been deleted from the model to save it later.");
      publications.clear();
    }
  }

  @Override
  public String save() {
    // Remove all publication from the database.
    boolean removed =
      publicationManager.removeAllPublications(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());
    if (removed) {
      boolean added =
        publicationManager.savePublications(publications, this.getCurrentReportingLogframe(), this.getCurrentUser()
          .getLeader());
      if (added) {
        LOG.info("The user {} save the publications for the leader.", getCurrentUser().getEmail(), getCurrentUser()
          .getLeader().getId());
        addActionMessage(getText("saving.success", new String[] {getText("reporting.publications")}));
        return SUCCESS;
      }
    }

    LOG.warn("The user {} had a problem saving the publications for the leader {}", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());
    addActionError(getText("saving.problem"));
    return INPUT;
  }

  public void setPublications(List<Publication> publications) {
    this.publications = publications;
  }

  public void setPublicationTypes(PublicationType[] publicationTypes) {
    this.publicationTypes = publicationTypes;
  }

  @Override
  public void validate() {
    super.validate();
    // If the page is loading don't validate
    if (save) {
      boolean problem = false;
      int c = 0;
      for (Publication publication : publications) {
        boolean needAccessType = false;

        for (int typeId : publicationTypeAccessNeed) {
          if (publication.getType().getId() == typeId) {
            needAccessType = true;
            break;
          }
        }
        if (publication.getCitation().isEmpty()) {
          problem = true;
          addFieldError("publications[" + c + "].citation",
            getText("validation.required", new String[] {getText("reporting.publications.citation")}));
        }
        if (publication.getAccess() == null) {
          publication.setAccess(new OpenAccess());
          if (needAccessType) {
            problem = true;
            addFieldError("publications[" + c + "].access",
              getText("validation.required", new String[] {getText("reporting.publications.access")}));
          }
        }
        if (publication.getRelatedThemes().length == 0) {
          problem = true;
          addFieldError("publications[" + c + "].themeRelated",
            getText("validation.required", new String[] {getText("reporting.publications.themeRelated")}));
        }
        c++;
      }

      if (problem) {
        addActionError(getText("saving.fields.required"));
      }
    }
  }
}
