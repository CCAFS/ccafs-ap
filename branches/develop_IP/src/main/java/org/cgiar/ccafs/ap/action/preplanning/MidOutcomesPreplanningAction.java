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


public class MidOutcomesPreplanningAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(MidOutcomesPreplanningAction.class);
  private static final long serialVersionUID = 8352553096226822850L;

  // Managers
  IPElementManager ipElementManager;

  // Model
  List<IPElement> midOutcomes;
  List<IPElement> outcomes;

  @Inject
  public MidOutcomesPreplanningAction(APConfig config, LogframeManager logframeManager,
    IPElementManager ipElementManager) {
    super(config, logframeManager);
    this.ipElementManager = ipElementManager;
  }

  public List<IPElement> getMidOutcomes() {
    return midOutcomes;
  }

  public List<IPElement> getOutcomes() {
    return outcomes;
  }

  @Override
  public void prepare() throws Exception {
    IPProgram program = new IPProgram();
    program.setId(1);

    // The Outcomes 2025 type is stored with id 2
    IPElementType outcomesType = new IPElementType();
    outcomesType.setId(3);

    // The Outcomes 2019 type is stored with id 3
    IPElementType midOutcomesType = new IPElementType();
    midOutcomesType.setId(3);

    midOutcomes = ipElementManager.getIPElements(program, midOutcomesType);
    System.out.println(midOutcomesType);
    outcomes = ipElementManager.getIPElements(program, outcomesType);
  }

  public void setMidOutcomes(List<IPElement> midOutcomes) {
    this.midOutcomes = midOutcomes;
  }

  public void setOutcomes(List<IPElement> outcomes) {
    this.outcomes = outcomes;
  }
}