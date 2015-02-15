package org.cgiar.ccafs.ap.action.reporting;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyCountriesManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyTypeManager;
import org.cgiar.ccafs.ap.data.manager.CommunicationManager;
import org.cgiar.ccafs.ap.data.manager.LeverageManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneReportManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeManager;
import org.cgiar.ccafs.ap.data.manager.OutputSummaryManager;
import org.cgiar.ccafs.ap.data.manager.PublicationManager;
import org.cgiar.ccafs.ap.data.manager.PublicationTypeManager;
import org.cgiar.ccafs.ap.data.manager.RPLSynthesisReportManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.manager.TLOutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.CaseStudyType;
import org.cgiar.ccafs.ap.data.model.Communication;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Leverage;
import org.cgiar.ccafs.ap.data.model.MilestoneReport;
import org.cgiar.ccafs.ap.data.model.OpenAccess;
import org.cgiar.ccafs.ap.data.model.Outcome;
import org.cgiar.ccafs.ap.data.model.OutputSummary;
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.data.model.PublicationType;
import org.cgiar.ccafs.ap.data.model.RPLSynthesisReport;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.data.model.TLOutputSummary;
import org.cgiar.ccafs.ap.util.Capitalize;
import org.cgiar.ccafs.ap.util.SendMail;
import org.cgiar.ccafs.ap.validation.reporting.activities.deliverables.DeliverableInformationReportingActionValidation;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SubmitAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(SubmitAction.class);
  private static final long serialVersionUID = -4239607876759346097L;

  // Managers
  private ActivityManager activityManager;
  private CaseStudyManager caseStudyManager;
  private CaseStudyCountriesManager caseStudyCountriesManager;
  private CaseStudyTypeManager caseStudyTypeManager;
  private CommunicationManager communicationManager;
  private LeverageManager leverageManager;
  private OutcomeManager outcomeManager;
  private PublicationManager publicationManager;
  private PublicationTypeManager publicationTypeManager;
  private OutputSummaryManager outputSummaryManager;
  private RPLSynthesisReportManager synthesisReportManager;
  private TLOutputSummaryManager tlOutputManager;
  private SubmissionManager submissionManager;
  private MilestoneReportManager milestoneReportManager;
  private DeliverableInformationReportingActionValidation validator;

  // Model
  private List<CaseStudy> caseStudies;
  private StringBuilder validationMessage;
  private Communication communicationReport;
  private List<Leverage> leverages;
  private List<Outcome> outcomes;
  private List<Publication> publications;
  private PublicationType[] publicationTypes;
  private int[] publicationTypeAccessNeed;
  private OutputSummary[] outputSummaries;
  private RPLSynthesisReport synthesisReport;
  private List<TLOutputSummary> tlOutputSummaries;
  private MilestoneReport[] milestoneReports;
  private Activity[] activities;
  private Submission submission;

  @Inject
  public SubmitAction(APConfig config, LogframeManager logframeManager, CaseStudyManager caseStudyManager,
    CommunicationManager communicationManager, LeverageManager leverageManager, OutcomeManager outcomeManager,
    PublicationManager publicationManager, PublicationTypeManager publicationTypeManager,
    OutputSummaryManager outputSummaryManager, RPLSynthesisReportManager synthesisReportManager,
    TLOutputSummaryManager tlOutputManager, SubmissionManager submissionManager,
    MilestoneReportManager milestoneReportManager, ActivityManager activityManager,
    CaseStudyCountriesManager caseStudyCountriesManager, CaseStudyTypeManager caseStudyTypeManager,
    DeliverableInformationReportingActionValidation validator) {
    super(config, logframeManager);
    this.caseStudyManager = caseStudyManager;
    this.communicationManager = communicationManager;
    this.leverageManager = leverageManager;
    this.outcomeManager = outcomeManager;
    this.publicationManager = publicationManager;
    this.publicationTypeManager = publicationTypeManager;
    this.outputSummaryManager = outputSummaryManager;
    this.synthesisReportManager = synthesisReportManager;
    this.tlOutputManager = tlOutputManager;
    this.submissionManager = submissionManager;
    this.milestoneReportManager = milestoneReportManager;
    this.activityManager = activityManager;
    this.caseStudyCountriesManager = caseStudyCountriesManager;
    this.caseStudyTypeManager = caseStudyTypeManager;
    this.validator = validator;

    validationMessage = new StringBuilder();
  }

  private void activitiesValidation() {
    StringBuilder activityMessage;
    boolean activityMissing;

    for (Activity activity : activities) {
      int missingCounter = 1;
      activityMessage = new StringBuilder();
      activityMissing = false;

      activityMessage.append("For the activity ");
      activityMessage.append(activity.getActivityId());
      activityMessage.append(" you should fill: ");

      /* Activity Status */
      if (activity.getStatusDescription() == null || activity.getStatusDescription().isEmpty()) {
        activityMessage.append(missingCounter);
        activityMessage.append("- The activity status; ");
        missingCounter++;
      }

      /* Deliverables */
      List<Deliverable> deliverables = activity.getDeliverables();
      if (activity.getDeliverables() != null) {
        Deliverable deliverable;
        for (int c = 0; c < deliverables.size(); c++) {
          deliverable = deliverables.get(c);
          Publication publication =
            (deliverable.isPublication()) ? publicationManager.getPublicationByDeliverableID(deliverable.getId())
              : new Publication();
          validator.validate(deliverable, publication);
          if (!validator.isValid()) {
            activityMissing = true;

            activityMessage.append(missingCounter);
            activityMessage.append("- The information of the deliverable #");
            activityMessage.append(c + 1);
            activityMessage.append(" ( ");
            activityMessage.append(validator.getValidationMessage());
            activityMessage.append(" ); ");
            missingCounter++;
          }
        }
      }

      /* Partners */
      List<ActivityPartner> activityPartners = activity.getActivityPartners();
      if (activityPartners != null) {
        ActivityPartner activityPartner;
        for (int c = 0; c < activityPartners.size(); c++) {
          boolean partnerValid = true;
          activityPartner = activityPartners.get(c);

          // Contact name
          if (activityPartner.getContactName() == null || activityPartner.getContactName().isEmpty()) {
            if (partnerValid) {
              activityMessage.append(missingCounter);
              activityMessage.append("- The information of the partner #");
              activityMessage.append(c + 1);
              activityMessage.append(" ( ");

              partnerValid = false;
            }

            activityMessage.append(" Contact name, ");
            activityMissing = true;
          }

          // Contact email
          if (activityPartner.getContactEmail() == null || activityPartner.getContactEmail().isEmpty()) {
            if (partnerValid) {
              activityMessage.append(missingCounter);
              activityMessage.append("- The information of the partner #");
              activityMessage.append(c + 1);
              activityMessage.append(" ( ");

              partnerValid = false;
            }

            activityMessage.append(" Contact email, ");
            activityMissing = true;
          }

          if (!partnerValid) {
            activityMessage.append(" ); ");
          }
        }
      }

      if (activityMissing) {
        validationMessage.append(activityMessage.toString());
        validationMessage.append("  -- / -- ");
      }
    }
  }

  private void caseStudiesValidation() {
    for (int c = 0; c < caseStudies.size(); c++) {
      StringBuilder caseStudyMesagge = new StringBuilder();

      // Title
      if (caseStudies.get(c).getTitle().isEmpty()) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.title") + ", ");
      }
      // Author
      if (caseStudies.get(c).getAuthor().isEmpty()) {
      }
      // Type
      // If a new case study don't select a type the attribute is null
      if (caseStudies.get(c).getTypes() == null) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.type") + ", ");
      } else if (caseStudies.get(c).getTypes().size() == 0) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.type") + ", ");
      }

      // Start date, if the user don't enter a value, the object is null
      if (caseStudies.get(c).getStartDate() == null) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.startDate") + ", ");
      }
      // End date, if the user don't enter a value, the object is null
      if (caseStudies.get(c).getEndDate() == null) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.endDate") + ", ");
      }
      // Countries
      // If the case study is not global check if there are countries
      if (!caseStudies.get(c).isGlobal()) {
        if (caseStudies.get(c).getCountries() == null) {
          caseStudyMesagge.append(getText("reporting.caseStudies.validation.location") + ", ");
        } else if (caseStudies.get(c).getCountries().size() == 0) {
          caseStudyMesagge.append(getText("reporting.caseStudies.validation.location") + ", ");
        }
      }
      // Keywords
      if (caseStudies.get(c).getKeywords().isEmpty()) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.keywords") + ", ");
      }
      // Objectives
      if (caseStudies.get(c).getObjectives().isEmpty()) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.objectives") + ", ");
      }
      // Description
      if (caseStudies.get(c).getDescription().isEmpty()) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.description") + ", ");
      }
      // Results
      if (caseStudies.get(c).getResults().isEmpty()) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.results") + ", ");
      }
      // Partners
      if (caseStudies.get(c).getPartners().isEmpty()) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.partners") + ", ");
      }
      // Type
      if (caseStudies.get(c).getTypes() == null || caseStudies.get(c).getTypes().size() > config.getMaxCaseStudyTypes()) {
        caseStudyMesagge.append(getText("reporting.caseStudies.validation.types") + ", ");
      }

      if (caseStudyMesagge.length() > 0) {
        validationMessage.append("There are some empty fields in the case study #" + (c + 1));
        validationMessage.append(" ( ");
        validationMessage.append(caseStudyMesagge.toString());
        validationMessage.append(" ) ");
      }
    }
  }

  public void communicationsValidation() {
    boolean problem = false;

    if (communicationReport.getMediaCampaings() == null || communicationReport.getMediaCampaings().isEmpty()) {
      problem = true;
    }

    if (communicationReport.getBlogs() == null || communicationReport.getBlogs().isEmpty()) {
      problem = true;
    }

    if (communicationReport.getWebsites() == null || communicationReport.getWebsites().isEmpty()) {
      problem = true;
    }

    if (communicationReport.getSociaMediaCampaigns() == null || communicationReport.getSociaMediaCampaigns().isEmpty()) {
      problem = true;
    }

    if (communicationReport.getNewsletters() == null || communicationReport.getNewsletters().isEmpty()) {
      problem = true;
    }

    if (communicationReport.getEvents() == null || communicationReport.getEvents().isEmpty()) {
      problem = true;
    }

    if (communicationReport.getVideosMultimedia() == null || communicationReport.getVideosMultimedia().isEmpty()) {
      problem = true;
    }

    if (communicationReport.getOtherCommunications() == null || communicationReport.getOtherCommunications().isEmpty()) {
      problem = true;
    }

    if (problem) {
      validationMessage.append("Please fill all the fields in the communications section");
    }
  }

  private void leveragesValidation() {
    int c = 1;

    for (Leverage l : leverages) {
      boolean missingTitle = false, missingPartnerName = false, missingBudget = false;
      if (l.getTitle() == null || l.getTitle().isEmpty()) {
        missingTitle = true;
      }

      if (l.getPartnerName() == null || l.getPartnerName().isEmpty()) {
        missingPartnerName = true;
      }

      if (l.getBudget() == 0) {
        missingBudget = true;


        if (missingTitle) {
          validationMessage.append("Fill the title of the levarage fund #" + c);
        }

        if (missingPartnerName) {
          validationMessage.append("Fill the partner name of the levarage fund #" + c);
        }

        if (missingBudget) {
          validationMessage.append("Fill the budget of the levarage fund #" + c);
        }
      }
    }
  }

  private void outcomesValidation() {
    int c = 1;
    for (Outcome o : outcomes) {
      boolean missingField = false;

      if (o.getTitle() == null || o.getTitle().isEmpty()) {
        missingField = true;
      }
      if (o.getOutcome() == null || o.getOutcome().isEmpty()) {
        missingField = true;
      }
      if (o.getOutputs() == null || o.getOutputs().isEmpty()) {
        missingField = true;
      }
      if (o.getPartners() == null || o.getPartners().isEmpty()) {
        missingField = true;
      }
      if (o.getOutputUser() == null || o.getOutputUser().isEmpty()) {
        missingField = true;
      }
      if (o.getHowUsed() == null || o.getHowUsed().isEmpty()) {
        missingField = true;
      }
      if (o.getEvidence() == null || o.getEvidence().isEmpty()) {
        missingField = true;
      }

      if (missingField) {
        validationMessage.append("Please fill all the fields for the outcome story #" + c);
      }
      c++;
    }

  }

  private void outputsValidation() {
    boolean missingDescription = false;

    StringBuilder message = new StringBuilder();
    for (OutputSummary os : outputSummaries) {

      if (os.getDescription() == null || os.getDescription().isEmpty()) {
        String outputCode =
          os.getOutput().getCode() + "." + os.getOutput().getObjective().getCode() + "."
            + os.getOutput().getObjective().getTheme().getCode();
        message.append("Output " + outputCode + ", ");
        missingDescription = true;
      }
    }

    if (missingDescription) {
      validationMessage.append(" Fill the summary by outputs section (" + message.toString() + ").");
      validationMessage.append(" -- / --");
    }
  }

  @Override
  public void prepare() throws Exception {

    // Activities
    activities = activityManager.getActivities(config.getReportingCurrentYear(), this.getCurrentUser());

    // Case studies
    caseStudies = caseStudyManager.getCaseStudyList(getCurrentUser().getLeader(), getCurrentReportingLogframe());

    // If there are elements in the case study list, iterate it to store
    // the corresponding list of countries and the list of types
    List<Country> temporalCountryList;
    List<CaseStudyType> temporalTypeList;
    for (int c = 0; c < caseStudies.size(); c++) {
      temporalCountryList = caseStudyCountriesManager.getCaseStudyCountriesList(caseStudies.get(c));
      caseStudies.get(c).setCountries(temporalCountryList);

      temporalTypeList = caseStudyTypeManager.getCaseStudyTypes(caseStudies.get(c));
      caseStudies.get(c).setTypes(temporalTypeList);
    }

    // Communications
    communicationReport =
      communicationManager.getCommunicationReport(getCurrentUser().getLeader(), getCurrentReportingLogframe());

    // leverages
    leverages = leverageManager.getLeverages(getCurrentUser().getLeader(), getCurrentReportingLogframe());

    // Outcomes
    outcomes = outcomeManager.getOutcomes(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());

    // publications
    publications =
      publicationManager.getPublications(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());
    publicationTypes = publicationTypeManager.getPublicationTypes();
    publicationTypeAccessNeed = new int[1];
    publicationTypeAccessNeed[0] = publicationTypes[0].getId();

    // Output summaries
    outputSummaries =
      outputSummaryManager.getOutputSummaries(getCurrentUser().getLeader(), getCurrentReportingLogframe());

    // RPL Synthesis
    if (getCurrentUser().isRPL()) {
      synthesisReport =
        synthesisReportManager.getRPLSynthesisReport(this.getCurrentUser().getLeader(),
          this.getCurrentReportingLogframe());
    }

    // TL output summaries
    if (getCurrentUser().isTL()) {
      tlOutputSummaries =
        tlOutputManager.getTLOutputSummaries(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());
    }

    // Milestone reports
    if (getCurrentUser().isTL() || getCurrentUser().isRPL()) {
      milestoneReports =
        milestoneReportManager.getMilestoneReports(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
          getCurrentUser().getRole());
    }
  }

  private void publicationsValidation() {
    boolean needCitation = false, needOpenAccess = false, needIdentifier = false;
    boolean needThemes = false;

    int c = 0;
    for (Publication publication : publications) {
      boolean needAccessType = false;

      if (publication.getIdentifier() == null || publication.getIdentifier().isEmpty()) {
        needIdentifier = true;
      }

      if (publication.getCitation().isEmpty()) {
        needCitation = true;
      }

      for (int typeId : publicationTypeAccessNeed) {
        if (publication.getPublicationType().getId() == typeId) {
          needAccessType = true;
          break;
        }
      }

      if (publication.getAccess() == null) {
        publication.setAccess(new OpenAccess());
        if (needAccessType) {
          needOpenAccess = true;
        }
      }
      if (publication.getRelatedThemes().length == 0) {
        needThemes = true;
      }
      c++;
    }

    if (needCitation) {
      validationMessage.append(getText("reporting.publications.validation.citation") + ", ");
    }

    if (needOpenAccess) {
      validationMessage.append(getText("reporting.publications.validation.openAccess") + ", ");
    }

    if (needIdentifier) {
      validationMessage.append(getText("reporting.publications.validation.identifier") + ", ");
    }

    if (needThemes) {
      validationMessage.append(getText("reporting.publications.validation.theme") + ", ");
    }

  }

  private void rplSynthesisValidation() {
    boolean missingfield = false;

    if (synthesisReport.getCcafsSites() == null || synthesisReport.getCcafsSites().isEmpty()) {
      missingfield = true;
    }

    if (synthesisReport.getCrossCenter() == null || synthesisReport.getCrossCenter().isEmpty()) {
      missingfield = true;
    }

    if (synthesisReport.getRegional() == null || synthesisReport.getRegional().isEmpty()) {
      missingfield = true;
    }

    if (synthesisReport.getDecisionSupport() == null || synthesisReport.getDecisionSupport().isEmpty()) {
      missingfield = true;
    }

    if (missingfield) {
      validationMessage.append(getText("reporting.synthesisReport.validation") + ", ");
    }
  }

  private void sendConfirmationMessage() {
    if (!config.getBaseUrl().contains("localhost") || !config.getBaseUrl().contains("/test")) {
      // Additionally, sent a confirmation message to TL/RPL
      // In this moment only to Gloria, Hector and David Abreu

      String subject;
      subject = "[CCAFS P&R] " + getCurrentUser().getLeader().getAcronym() + " has sent its technical report";

      String recipients = "d.abreu@cgiar.org h.f.tobon@cgiar.org h.d.carvajal@cgiar.org";
      // String recipients = "h.d.carvajal@cgiar.org";

      StringBuilder message = new StringBuilder();
      message.append("The user " + getCurrentUser().getName() + " ");
      message.append("has submitted the workplan for " + getCurrentUser().getLeader().getAcronym() + ". \n");
      message.append("Below you will find the list of activities that the user submits: \n\n");

      for (Activity activitie : activities) {
        message.append("http://activities.ccafs.cgiar.org/activity.do?id=" + activitie.getId());
        message.append("\n");
      }

      message.append("\nKind regards, \n");
      message.append("CCAFS P&R Team");

      SendMail sendMail = new SendMail(this.config);
      sendMail.send(recipients, subject, message.toString());
    }
  }

  public String submit() {
    boolean submitted = false;
    validateWorkplan();

    if (!validationMessage.toString().isEmpty()) {
      validationMessage.setCharAt(validationMessage.lastIndexOf(","), '.');
      addActionWarning(getText("submit.error") + " " + Capitalize.capitalizeString(validationMessage.toString()));
      return INPUT;
    }

    Submission submission = new Submission();
    submission.setLeader(getCurrentUser().getLeader());
    submission.setLogframe(getCurrentReportingLogframe());
    submission.setSection(APConstants.REPORTING_SECTION);

    submitted = submissionManager.submit(submission);
    if (submitted) {
      sendConfirmationMessage();
      addActionMessage(getText("submit.success"));
      return SUCCESS;
    } else {
      addActionError(getText("submit.error"));
      return INPUT;
    }

  }

  private void tlOutputsValidation() {
    boolean missing = false;

    for (TLOutputSummary tlo : tlOutputSummaries) {
      if (tlo.getDescription() == null || tlo.getDescription().isEmpty()) {
        missing = true;
      }
    }

    if (missing) {
      validationMessage.append(getText("reporting.tlOutputSummaries.validation") + ", ");
    }
  }

  private void tlRplMilestoneValidation() {
    boolean missing = false;

    for (MilestoneReport milestoneReport : milestoneReports) {

      if (getCurrentUser().isTL()) {
        if (milestoneReport.getThemeLeaderDescription().isEmpty()) {
          missing = true;
        }
      }

      else if (getCurrentUser().isRPL()) {
        if (milestoneReport.getRegionalLeaderDescription().isEmpty()) {
          missing = true;
        }
      }
    }

    if (missing) {
      validationMessage.append(getText("reporting.tlRplMilestoneReport.validation") + ", ");
    }
  }

  private void validateWorkplan() {
    activitiesValidation();
    outputsValidation();
    // publicationsValidation();
    communicationsValidation();
    caseStudiesValidation();
    outcomesValidation();
    leveragesValidation();

    if (getCurrentUser().isRPL()) {
      rplSynthesisValidation();
    }

    if (getCurrentUser().isTL()) {
      tlOutputsValidation();
    }

    if (getCurrentUser().isTL() || getCurrentUser().isRPL()) {
      tlRplMilestoneValidation();
    }

  }
}
