package org.cgiar.ccafs.ap.validation.preplanning;

import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.List;


public class OutcomesValidation extends BaseValidator {

  private static final long serialVersionUID = 114728807776318498L;

  public String validateForm(List<IPElement> outcomes) {
    StringBuilder validationMessage = new StringBuilder();
    boolean problem = false;

    for (IPElement outcome : outcomes) {
      if (outcome.getDescription().isEmpty()) {
        validationMessage.append(getText("validation.preplanning.outcomes.description") + ", ");
        problem = true;
      }

      for (int i = 0; i < outcome.getIndicators().size(); i++) {
        if (outcome.getIndicators().get(i).getDescription().isEmpty()) {
          outcome.getIndicators().remove(i);
        }
      }
    }

    return validationMessage.toString();
  }
}
