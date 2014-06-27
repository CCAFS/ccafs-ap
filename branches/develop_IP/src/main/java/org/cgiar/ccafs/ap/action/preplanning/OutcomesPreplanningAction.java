package org.cgiar.ccafs.ap.action.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutcomesPreplanningAction extends BaseAction {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(OutcomesPreplanningAction.class);
  private static final long serialVersionUID = -4683268058827119069L;

  // Managers
  private IPElementManager ipElementManager;

  // Model
  private List<IPElement> outcomes;

  @Inject
  public OutcomesPreplanningAction(APConfig config, LogframeManager logframeManager, IPElementManager ipElementManager) {
    super(config, logframeManager);
    this.ipElementManager = ipElementManager;
  }

  @Override
  public String execute() throws Exception {
    return INPUT;
  }

  public List<IPElement> getOutcomes() {
    return outcomes;
  }

  @Override
  public void prepare() throws Exception {
    IPProgram program = new IPProgram();
    program.setId(1);

    // The Outcomes 2025 are stored with id 2
    IPElementType type = new IPElementType();
    type.setId(2);

    outcomes = ipElementManager.getIPElements(program, type);
    System.out.println(outcomes);
  }

  public void setOutcomes(List<IPElement> outcomes) {
    this.outcomes = outcomes;
  }
}
