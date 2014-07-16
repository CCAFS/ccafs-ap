package org.cgiar.ccafs.ap.action.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.validation.preplanning.OutcomesValidation;

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
  private StringBuilder validationMessages;

  // Validator
  private OutcomesValidation validator;

  @Inject
  public OutcomesPreplanningAction(APConfig config, IPElementManager ipElementManager, OutcomesValidation validator) {
    super(config);
    this.ipElementManager = ipElementManager;
    this.validator = validator;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  public List<IPElement> getOutcomes() {
    return outcomes;
  }

  @Override
  public void prepare() throws Exception {
	super.prepare();
    validationMessages = new StringBuilder();

    IPProgram program = new IPProgram();
    program.setId(1);

    // The Outcomes 2025 are stored with id 2
    IPElementType type = new IPElementType();
    type.setId(2);

    outcomes = ipElementManager.getIPElements(program, type);

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (outcomes != null) {
        for (IPElement outcome : outcomes) {
          outcome.getIndicators().clear();
        }
      }
    }
  }

  @Override
  public String save() {
    IPProgram program = new IPProgram();
    program.setId(1);

    IPElementType type = new IPElementType();
    type.setId(2);

    for (IPElement outcome : outcomes) {
      if (outcome.getProgram() == null) {
        outcome.setProgram(program);
      }
      if (outcome.getType() == null) {
        outcome.setType(type);
      }

      for (int i = 0; i < outcome.getIndicators().size(); i++) {
        if (outcome.getIndicators().get(i).getDescription().isEmpty()) {
          outcome.getIndicators().remove(i);
        }
      }
    }

    // Remove records already present in the database
    ipElementManager.deleteIPElements(program, type);
    ipElementManager.saveIPElements(outcomes);
    return INPUT;
  }

  public void setOutcomes(List<IPElement> outcomes) {
    this.outcomes = outcomes;
  }

  public String validateForm() {
    String messages = validator.validateForm(outcomes);

    if (messages.isEmpty()) {
      addActionMessage(getText("validation.success"));
    } else {
      String validationResult = getText("validation.fail") + messages;
      addActionWarning(validationResult);
    }
    return SUCCESS;
  }
}
