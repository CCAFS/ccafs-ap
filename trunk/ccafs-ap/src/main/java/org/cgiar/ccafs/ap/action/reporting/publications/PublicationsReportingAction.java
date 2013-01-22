package org.cgiar.ccafs.ap.action.reporting.publications;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PublicationManager;
import org.cgiar.ccafs.ap.data.manager.PublicationTypeManager;
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.data.model.PublicationType;

import java.util.List;

import com.google.inject.Inject;


public class PublicationsReportingAction extends BaseAction {

  private static final long serialVersionUID = -7843902991180158797L;

  // Models
  private List<Publication> publications;
  private PublicationType[] publicationTypes;

  // Managers
  private PublicationManager publicationManager;
  private PublicationTypeManager publicationTypeManager;

  @Inject
  public PublicationsReportingAction(APConfig config, LogframeManager logframeManager,
    PublicationManager publicationManager, PublicationTypeManager publicationTypeManager) {
    super(config, logframeManager);
    this.publicationManager = publicationManager;
    this.publicationTypeManager = publicationTypeManager;
  }

  public List<Publication> getPublications() {
    return publications;
  }

  public PublicationType[] getPublicationTypes() {
    return publicationTypes;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    publications = publicationManager.getPublications(this.getCurrentUser().getLeader(), this.getCurrentLogframe());
    publicationTypes = publicationTypeManager.getPublicationTypes();

    // Remove all publications so they can be added again in the save method.
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      publications.clear();
    }

  }

  @Override
  public String save() {
    if (publications.size() > 0) {
      // Remove all activity partners from the database.
      boolean removed =
        publicationManager.removeAllPublications(this.getCurrentUser().getLeader(), this.getCurrentLogframe());
      if (removed) {
        boolean added =
          publicationManager.savePublications(publications, this.getCurrentLogframe(), this.getCurrentUser()
            .getLeader());
        if (added) {
          addActionMessage(getText("saving.success", new String[] {getText("reporting.publications.publication")}));
          return SUCCESS;
        }
      }
    }
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
    // If the page is loading dont validate
    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      boolean problem = false;
      int c = 0;
      for (Publication publication : publications) {
        if (publication.getIdentifier().isEmpty()) {
          problem = true;
          addFieldError("publications[" + c + "].identifier",
            getText("validation.required", new String[] {getText("reporting.publications.identifier")}));
        }

        if (publication.getCitation().isEmpty()) {
          problem = true;
          addFieldError("publications[" + c + "].citation",
            getText("validation.required", new String[] {getText("reporting.publications.citation")}));
        }
        c++;
      }

      if (problem) {
        addActionError(getText("saving.fields.required"));
      }
    }
  }

}
