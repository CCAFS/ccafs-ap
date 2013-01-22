package org.cgiar.ccafs.ap.action.reporting.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutputManager;
import org.cgiar.ccafs.ap.data.manager.OutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Output;
import org.cgiar.ccafs.ap.data.model.OutputSummary;

import java.util.ArrayList;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutputSummaryAction extends BaseAction {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(OutputSummaryAction.class);

  // Managers
  private OutputSummaryManager outputSummaryManager;
  private OutputManager outputManager;

  // Model
  private OutputSummary[] outputSummaries;
  private int activityLeaderID;

  private String activityLeaderAcronym;

  @Inject
  public OutputSummaryAction(APConfig config, LogframeManager logframeManager,
    OutputSummaryManager outputSummaryManager, OutputManager outputManager) {
    super(config, logframeManager);
    this.outputSummaryManager = outputSummaryManager;
    this.outputManager = outputManager;
  }

  public String getActivityLeaderAcronym() {
    return activityLeaderAcronym;
  }

  public int getActivityLeaderID() {
    return activityLeaderID;
  }

  public OutputSummary[] getOutputSummaries() {
    return outputSummaries;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    // Set the activity leader acronym to display in the page
    // activityLeaderAcronym = getCurrentUser().getLeader().getAcronym();

    // Temporal list to store the outputs
    Output[] outputs;

    // Get the activity leader identifier of the current user
    activityLeaderID = getCurrentUser().getLeader().getId();

    // Get the outputs of the activities that belong to the current activity leader
    outputs = outputManager.getOutputList(activityLeaderID);

    outputSummaries = new OutputSummary[outputs.length];
    for (int i = 0; i < outputs.length; i++) {
      // First, check if exists a summary for the given output id
      outputSummaries[i] = outputSummaryManager.getOutputSummary(outputs[i].getId(), activityLeaderID);
      // if not exists, create one
      if (outputSummaries[i] == null) {
        outputSummaries[i] = new OutputSummary();
      }

      // Second, set the output and leader objects
      outputSummaries[i].setOutput(outputs[i]);
      outputSummaries[i].setLeader(new Leader(activityLeaderID));
    }

  }


  @Override
  public String save() {
    boolean added = false;

    // Create lists of objects to make only one call to the manager
    ArrayList<OutputSummary> outputSummariesToSave = new ArrayList<>();
    ArrayList<OutputSummary> outputSummariesToUpdate = new ArrayList<>();

    for (OutputSummary outputSummary : outputSummaries) {
      // If the id of the field is equal to zero the summary is
      // not in the database and have to be inserted
      if (outputSummary.getId() == 0) {
        outputSummariesToSave.add(outputSummary);
      } else {
        // Another way, the summary is updated
        outputSummariesToUpdate.add(outputSummary);
      }
    }

    // TODO - Check what to do when fail the save process with the summaryToSave
    // list or the summariesToUpdate but not fail the process with the other list
    // how display that?

    if (!outputSummariesToSave.isEmpty()) {
      added = outputSummaryManager.saveOutputSummary(outputSummariesToSave);
      if (!added) {
        addActionError(getText("reporting.outputSummary.error"));
        LOG.error("There was a problem storing the output summaries to save");
        return INPUT;
      }
    }

    if (!outputSummariesToUpdate.isEmpty()) {
      added = outputSummaryManager.updateOutputSummary(outputSummariesToUpdate);
      if (!added) {
        addActionError(getText("reporting.outputSummary.error"));
        LOG.error("There was a problem storing the output summaries to update");
        return INPUT;
      }
    }

    addActionError(getText("reporting.outputSummary.saved"));
    return SUCCESS;
  }

  public void setOutputSummaries(OutputSummary[] outputSummaries) {
    this.outputSummaries = outputSummaries;
  }

  @Override
  public void validate() {
    boolean anyError = false;

    // If the page is loading dont validate
    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      for (int i = 0; i < outputSummaries.length; i++) {
        if (outputSummaries[i].getDescription().isEmpty()) {
          // Check if the summary field will be required or it will be optional
          addFieldError("outputSummaries[" + i + "].description", getText("validation.field.required"));
        }
      }
    }
  }
}
