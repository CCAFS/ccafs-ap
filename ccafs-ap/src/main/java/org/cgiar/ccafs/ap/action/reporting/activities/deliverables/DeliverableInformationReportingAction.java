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

package org.cgiar.ccafs.ap.action.reporting.activities.deliverables;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.DeliverableAccessManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableMetadataManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableStatusManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MetadataManager;
import org.cgiar.ccafs.ap.data.manager.OpenAccessManager;
import org.cgiar.ccafs.ap.data.manager.PublicationManager;
import org.cgiar.ccafs.ap.data.manager.PublicationThemeManager;
import org.cgiar.ccafs.ap.data.manager.PublicationTypeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.Metadata;
import org.cgiar.ccafs.ap.data.model.MetadataRequirement;
import org.cgiar.ccafs.ap.data.model.OpenAccess;
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.data.model.PublicationTheme;
import org.cgiar.ccafs.ap.data.model.PublicationType;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableInformationReportingAction extends BaseAction {

  // Logger
  public static Logger LOG = LoggerFactory.getLogger(DeliverableInformationReportingAction.class);
  private static final long serialVersionUID = 6180698449849134459L;

  // Managers
  private DeliverableManager deliverableManager;
  private DeliverableTypeManager deliverableTypeManager;
  private DeliverableStatusManager deliverableStatusManager;
  private DeliverableMetadataManager deliverableMetadataManager;
  private DeliverableAccessManager deliverableAccessManager;
  private MetadataManager metadataManager;
  private PublicationThemeManager publicationThemeManager;
  private OpenAccessManager openAccessManager;
  private PublicationTypeManager publicationTypeManager;
  private PublicationManager publicationManager;
  private SubmissionManager submissionManager;

  // Model
  private Deliverable deliverable;
  private Publication publication;
  private int deliverableID;
  private int activityID;
  private boolean canSubmit;
  private DeliverableType[] deliverableTypes;
  private DeliverableType[] deliverableSubTypes;
  private DeliverableStatus[] deliverableStatusList;
  private Map<Boolean, String> yesNoRadio;
  private Map<String, String> notApplicableRadio;
  private Map<String, String> accessLimitsRadio;
  private List<Metadata> metadataList;
  private Map<String, String> publicationThemeList;
  private StringBuilder validationMessage;
  // This array contains the publication types which need an access type specification.
  private int[] publicationTypeAccessNeed;
  private OpenAccess[] publicationAccessList;
  private PublicationType[] publicationTypes;
  private Map<Metadata, MetadataRequirement> metadataRequired;

  @Inject
  public DeliverableInformationReportingAction(APConfig config, LogframeManager logframeManager,
    DeliverableManager deliverableManager, DeliverableTypeManager deliverableTypeManager,
    DeliverableStatusManager deliverableStatusManager, MetadataManager metadataManager,
    DeliverableMetadataManager deliverableMetadataManager, DeliverableAccessManager deliverableAccessManager,
    PublicationThemeManager publicationThemeManager, SubmissionManager submissionManager,
    OpenAccessManager openAccessManager, PublicationTypeManager publicationTypeManager,
    PublicationManager publicationManager) {
    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.deliverableStatusManager = deliverableStatusManager;
    this.deliverableMetadataManager = deliverableMetadataManager;
    this.deliverableAccessManager = deliverableAccessManager;
    this.metadataManager = metadataManager;
    this.publicationThemeManager = publicationThemeManager;
    this.openAccessManager = openAccessManager;
    this.publicationTypeManager = publicationTypeManager;
    this.publicationManager = publicationManager;
    this.submissionManager = submissionManager;
  }

  public Map<String, String> getAccessLimitsRadio() {
    return accessLimitsRadio;
  }

  public int getActivityID() {
    return activityID;
  }

  public Deliverable getDeliverable() {
    return deliverable;
  }

  public int getDeliverableID() {
    return deliverableID;
  }

  public DeliverableStatus[] getDeliverableStatusList() {
    return deliverableStatusList;
  }

  public DeliverableType[] getDeliverableSubTypes() {
    return deliverableSubTypes;
  }

  public DeliverableType[] getDeliverableTypes() {
    return deliverableTypes;
  }

  public List<Metadata> getMetadataList() {
    return metadataList;
  }

  public Map<Metadata, MetadataRequirement> getMetadataRequired() {
    return metadataRequired;
  }

  public Map<String, String> getNotApplicableRadio() {
    return notApplicableRadio;
  }

  public Publication getPublication() {
    return publication;
  }

  public OpenAccess[] getPublicationAccessList() {
    return publicationAccessList;
  }

  public Map<String, String> getPublicationThemeList() {
    return publicationThemeList;
  }


  public int[] getPublicationTypeAccessNeed() {
    return publicationTypeAccessNeed;
  }

  public PublicationType[] getPublicationTypes() {
    return publicationTypes;
  }

  public Map<Boolean, String> getYesNoRadio() {
    return yesNoRadio;
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }

  public boolean isMetadataMandatory(String metadataName) {
    for (Entry<Metadata, MetadataRequirement> entry : metadataRequired.entrySet()) {
      if (entry.getKey().getName().equals(metadataName)) {
        return entry.getValue().isMandatory();
      }
    }
    return false;
  }

  public boolean isMetadataNotRequired(String metadataName) {
    for (Entry<Metadata, MetadataRequirement> entry : metadataRequired.entrySet()) {
      if (entry.getKey().getName().equals(metadataName)) {
        return entry.getValue().isNotRequired();
      }
    }
    return false;
  }

  @Override
  public String next() {
    save();
    return super.next();
  }


  @Override
  public void prepare() throws Exception {
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    deliverableID =
      Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_REQUEST_ID)));

    // TODO - Create an interceptor that verifies if the deliverable belongs to the activity identified by the value
    // received as parameter
    deliverable = deliverableManager.getDeliverable(deliverableID);
    deliverableTypes = deliverableTypeManager.getDeliverableTypes();
    deliverableSubTypes = deliverableTypeManager.getDeliverableSubTypes();
    deliverableStatusList = deliverableStatusManager.getDeliverableStatus();
    deliverable.setMetadata(deliverableMetadataManager.getDeliverableMetadata(deliverableID));
    deliverable.setAccessDetails(deliverableAccessManager.getDeliverableAccessData(deliverableID));

    if (deliverable.isPublication()) {
      publication = publicationManager.getPublicationByDeliverableID(deliverableID);
    }

    // Create options for the yes/no radio buttons
    yesNoRadio = new LinkedHashMap<>();
    yesNoRadio.put(true, getText("reporting.activityDeliverables.yes"));
    yesNoRadio.put(false, getText("reporting.activityDeliverables.no"));

    // Create options for the not applicable radio buttons
    notApplicableRadio = new LinkedHashMap<>();
    notApplicableRadio.put("Yes", getText("reporting.activityDeliverables.yes"));
    notApplicableRadio.put("No", getText("reporting.activityDeliverables.no"));
    notApplicableRadio.put("Not applicable", getText("reporting.activityDeliverables.notApplicable"));

    // Create options for the access limit radio buttons
    accessLimitsRadio = new LinkedHashMap<>();
    accessLimitsRadio.put("inmediate", getText("reporting.deliverables.dataAccess.accessLimits.inmediate"));
    accessLimitsRadio.put("embargued", getText("reporting.deliverables.dataAccess.accessLimits.embargoed"));
    accessLimitsRadio.put("restricted", getText("reporting.deliverables.dataAccess.accessLimits.restricted"));

    metadataList = metadataManager.getMetadataList();
    metadataRequired = metadataManager.getRequiredMetadata(deliverable.getType().getId());

    // publication = new Publication();
    PublicationTheme[] publicationThemeListObjects = publicationThemeManager.getPublicationThemes();
    publicationThemeList = new TreeMap<>();

    for (PublicationTheme pubTheme : publicationThemeListObjects) {
      String themeName = getText("reporting.publications.Theme") + " " + pubTheme.getCode() + ": " + pubTheme.getName();
      publicationThemeList.put(String.valueOf(pubTheme.getId()), themeName);
    }

    publicationTypes = publicationTypeManager.getPublicationTypes();
    publicationAccessList = openAccessManager.getOpenAccessList();

    // Publication types which need an access type specification
    // ID = 1 - Journal paper
    publicationTypeAccessNeed = new int[1];
    publicationTypeAccessNeed[0] = publicationTypes[0].getId();


    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    boolean success = true;
    System.out.println(1);
    success = success && deliverableManager.addDeliverable(deliverable, activityID);
    System.out.println(2);
    if (deliverable.isData()) {
      success =
        success && deliverableAccessManager.saveDeliverableAccessData(deliverable.getAccessDetails(), deliverableID);
    }
    System.out.println(3);
    success = success && deliverableMetadataManager.saveDeliverableMetadata(deliverable.getMetadata(), deliverableID);
    System.out.println(4);

    if (deliverable.isPublication()) {
      publication.setDeliverableID(deliverableID);
      List<Publication> publications = new ArrayList<>();
      publications.add(publication);
      // First delete publication related if any
      publicationManager.removePublicationByDeliverableID(deliverableID);
      publicationManager.savePublications(publications, getCurrentReportingLogframe(), getCurrentUser().getLeader());
    }

    if (success) {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.activityDeliverables")}));
      } else {
        String finalMessage = getText("saving.success", new String[] {getText("reporting.activityDeliverables")});
        finalMessage += getText("saving.keepInMind", new String[] {validationMessage.toString()});
        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setDeliverable(Deliverable deliverable) {
    this.deliverable = deliverable;
  }

  public void setDeliverableID(int deliverableID) {
    this.deliverableID = deliverableID;
  }


  public void setMetadataList(List<Metadata> metadataList) {
    this.metadataList = metadataList;
  }

  public void setPublication(Publication publication) {
    this.publication = publication;
  }

  public void setYesNoRadio(Map<Boolean, String> yesNoRadio) {
    this.yesNoRadio = yesNoRadio;
  }

  @Override
  public void validate() {
    validationMessage = new StringBuilder();
  }
}