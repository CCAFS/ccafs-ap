package org.cgiar.ccafs.ap.action.reporting.publications;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OpenAccessManager;
import org.cgiar.ccafs.ap.data.manager.PublicationManager;
import org.cgiar.ccafs.ap.data.manager.PublicationThemeManager;
import org.cgiar.ccafs.ap.data.manager.PublicationTypeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.OpenAccess;
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.data.model.PublicationTheme;
import org.cgiar.ccafs.ap.data.model.PublicationType;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
  private Map<String, String> publicationThemeList;
  // This array contains the publication types which need an access type specification.
  private int[] publicationTypeAccessNeed;
  // This array contains the publication types which need indicators description.
  private int[] publicationTypeIndicatorsNeed;
  private StringBuilder validationMessages;
  private boolean canSubmit;

  // Managers
  private PublicationManager publicationManager;
  private PublicationTypeManager publicationTypeManager;
  private OpenAccessManager openAccessManager;
  private PublicationThemeManager publicationThemeManager;
  private SubmissionManager submissionManager;

  @Inject
  public PublicationsReportingAction(APConfig config, LogframeManager logframeManager,
    PublicationManager publicationManager, PublicationTypeManager publicationTypeManager,
    OpenAccessManager openAccessManager, PublicationThemeManager publicationThemeManager,
    SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.publicationManager = publicationManager;
    this.publicationTypeManager = publicationTypeManager;
    this.openAccessManager = openAccessManager;
    this.publicationThemeManager = publicationThemeManager;
    this.submissionManager = submissionManager;

    validationMessages = new StringBuilder();
  }

  public OpenAccess[] getPublicationAccessList() {
    return publicationAccessList;
  }

  public List<Publication> getPublications() {
    return publications;
  }

  public Map<String, String> getPublicationThemeList() {
    return publicationThemeList;
  }

  public int[] getPublicationTypeAccessNeed() {
    return publicationTypeAccessNeed;
  }

  public int[] getPublicationTypeIndicatorsNeed() {
    return publicationTypeIndicatorsNeed;
  }

  public PublicationType[] getPublicationTypes() {
    return publicationTypes;
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }

  @Override
  public String next() {
    save();
    return super.next();
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

    // Publication types which need indicators description
    // ID = 1 - Journal paper
    publicationTypeIndicatorsNeed = new int[1];
    publicationTypeIndicatorsNeed[0] = publicationTypes[0].getId();

    PublicationTheme[] publicationThemeListObjects = publicationThemeManager.getPublicationThemes();
    publicationThemeList = new TreeMap<>();

    for (PublicationTheme pubTheme : publicationThemeListObjects) {
      String themeName = getText("reporting.publications.Theme") + " " + pubTheme.getCode() + ": " + pubTheme.getName();
      publicationThemeList.put(String.valueOf(pubTheme.getId()), themeName);
    }

    // Remove all publications so they can be added again in the save method.
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      LOG.debug("The publications have been deleted from the model to save it later.");
      publications.clear();
    }

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    String finalMessage;

    // Remove all publication from the database.
    boolean removed =
      publicationManager.removeAllPublications(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());
    if (removed) {
      boolean added =
        publicationManager.savePublications(publications, this.getCurrentReportingLogframe(), this.getCurrentUser()
          .getLeader());
      if (added) {
        if (validationMessages.toString().isEmpty()) {
          addActionMessage(getText("saving.success", new String[] {getText("reporting.publications")}));
        } else {
          // If there were validation messages show them in a warning message.
          finalMessage = getText("saving.success", new String[] {getText("reporting.publications")});
          finalMessage += getText("saving.missingFields", new String[] {validationMessages.toString()});

          addActionWarning(Capitalize.capitalizeString(finalMessage));
        }
        LOG.info("The user {} save the publications for the leader.", getCurrentUser().getEmail(), getCurrentUser()
          .getLeader().getId());
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
      boolean needCitation = false, needOpenAccess = false, needIdentifier = false;
      boolean needThemes = false;

      int c = 0;
      for (Publication publication : publications) {
        boolean needAccessType = false;

        if (publication.getIdentifier() == null || publication.getIdentifier().isEmpty()) {
          problem = needIdentifier = true;
        }

        if (publication.getCitation() == null || publication.getCitation().isEmpty()) {
          problem = needCitation = true;
        }

        for (int typeId : publicationTypeAccessNeed) {
          if (publication.getPublicationType() == null || publication.getPublicationType().getId() == typeId) {
            problem = needAccessType = true;
            break;
          }
        }

        if (publication.getAccess() == null) {
          publication.setAccess(new OpenAccess());
          if (needAccessType) {
            problem = needOpenAccess = true;
          }
        }
        if (publication.getRelatedThemes().length == 0) {
          problem = needThemes = true;
        }
        c++;
      }

      if (needCitation) {
        validationMessages.append(getText("reporting.publications.validation.citation") + ", ");
      }

      if (needOpenAccess) {
        validationMessages.append(getText("reporting.publications.validation.openAccess") + ", ");
      }

      if (needIdentifier) {
        validationMessages.append(getText("reporting.publications.validation.identifier") + ", ");
      }

      if (needThemes) {
        validationMessages.append(getText("reporting.publications.validation.theme") + ", ");
      }

      if (problem) {
        if (validationMessages.toString().contains(",")) {
          validationMessages.setCharAt(validationMessages.lastIndexOf(","), '.');
        }
      }
    }
  }
}
