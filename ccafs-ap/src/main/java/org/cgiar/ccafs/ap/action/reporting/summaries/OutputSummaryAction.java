package org.cgiar.ccafs.ap.action.reporting.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.OutputSummary;

import java.util.ArrayList;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutputSummaryAction extends BaseAction {

  private static final long serialVersionUID = 6422944336602787958L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(OutputSummaryAction.class);

  // Managers
  private OutputSummaryManager outputSummaryManager;

  // Model
  private OutputSummary[] outputSummaries;

  @Inject
  public OutputSummaryAction(APConfig config, LogframeManager logframeManager, OutputSummaryManager outputSummaryManager) {
    super(config, logframeManager);
    this.outputSummaryManager = outputSummaryManager;
  }

  public OutputSummary[] getOutputSummaries() {
    return outputSummaries;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    // Get all the summary outputs objects corresponding to the activity leader and current logframe
    outputSummaries = outputSummaryManager.getOutputSummaries(getCurrentUser().getLeader(), getCurrentLogframe());
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

    addActionMessage(getText("reporting.outputSummary.saved"));
    return SUCCESS;
  }

  public void setOutputSummaries(OutputSummary[] outputSummaries) {
    this.outputSummaries = outputSummaries;
  }
}
