package org.cgiar.ccafs.ap.action.reporting.tlrpl;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.TLOutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.TLOutputSummary;

import java.util.List;

import com.google.inject.Inject;


public class TLOutputSummaryReportingAction extends BaseAction {

  private static final long serialVersionUID = 2184360140983552059L;

  // Models
  private List<TLOutputSummary> tlOutputSummaries;

  // Managers
  TLOutputSummaryManager tlOutputManager;

  @Inject
  public TLOutputSummaryReportingAction(APConfig config, LogframeManager logframeManager,
    TLOutputSummaryManager tlOutputManager) {
    super(config, logframeManager);
    this.tlOutputManager = tlOutputManager;
  }

  public List<TLOutputSummary> getTlOutputSummaries() {
    return tlOutputSummaries;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    tlOutputSummaries =
      tlOutputManager.getTLOutputSummaries(this.getCurrentUser().getLeader(), this.getCurrentLogframe());
  }

  @Override
  public String save() {
    boolean problem = false;
    problem = tlOutputManager.saveTLOutputSummaries(tlOutputSummaries, this.getCurrentUser().getLeader());
    if (problem) {
      addActionError(getText("saving.problem"));
      return INPUT;
    } else {
      addActionMessage(getText("saving.success", new String[] {getText("reporting.tlOutputSummaries")}));
      return SUCCESS;
    }
  }

  public void setTlOutputSummaries(List<TLOutputSummary> tlOutputSummaries) {
    this.tlOutputSummaries = tlOutputSummaries;
  }

  @Override
  public void validate() {
    super.validate();
    // If the page is loading don't validate
    if (save) {
      boolean problem = false;
      int c = 0;
      for (TLOutputSummary outputSummary : tlOutputSummaries) {
        if (outputSummary.getDescription().isEmpty()) {
          problem = true;
          addFieldError("tlOutputSummaries[" + c + "].description", getText("validation.field.required"));
        }
        c++;
      }

      if (problem) {
        addActionError(getText("saving.fields.required"));
      }
    }
  }


}
