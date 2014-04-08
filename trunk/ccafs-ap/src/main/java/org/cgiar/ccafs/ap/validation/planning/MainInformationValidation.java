package org.cgiar.ccafs.ap.validation.planning;

import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.validation.BaseValidator;


public class MainInformationValidation extends BaseValidator {

  private static final long serialVersionUID = 1193960335657737955L;

  /**
   * This function is in charge of validate that all fields
   * of the main information section were completely filled.
   * 
   * @param activity
   * @return
   */
  public String validateForm(Activity activity) {
    StringBuilder validationMessage = new StringBuilder();
    boolean problem = false;

    if (activity.getTitle().isEmpty()) {
      validationMessage.append(getText("planning.mainInformation.title") + ", ");
      problem = true;
    }

    if (activity.getDescription().isEmpty()) {
      validationMessage.append(getText("planning.mainInformation.descripition") + ", ");
      problem = true;
    }

    // Validate if there is at least one contact person, if there is a contact person
    // without name nor email remove it from the list.
    if (activity.getContactPersons() != null) {
      if (!activity.getContactPersons().isEmpty()) {
        boolean invalidEmail = false;
        for (int c = 0; c < activity.getContactPersons().size(); c++) {
          if (activity.getContactPersons().get(c) == null) {
            continue;
          }

          // If there is a contact email, check if it is valid
          if (!activity.getContactPersons().get(c).getEmail().isEmpty()) {
            if (!isValidEmail(activity.getContactPersons().get(c).getEmail())) {
              invalidEmail = true;
              problem = true;
            }
          } else {
            if (activity.getContactPersons().get(c).getName().isEmpty()) {
              activity.getContactPersons().remove(c);
              c--;
            }
          }
        }

        if (invalidEmail) {
          validationMessage.append(getText("planning.mainInformation.contactEmail") + ", ");
        }
      }
    }

    // The list could be empty after the last validation
    if (activity.getContactPersons().isEmpty()) {
      validationMessage.append(getText("planning.mainInformation.validation.contactPerson") + ", ");
      problem = true;
    }

    if (activity.getStartDate() == null) {
      validationMessage.append(getText("planning.mainInformation.startDate") + ", ");
      problem = true;
    }

    if (activity.getEndDate() == null) {
      validationMessage.append(getText("planning.mainInformation.endDate") + ", ");
      problem = true;
    }

    // Change the last colon by a period
    if (problem == true) {
      validationMessage.setCharAt(validationMessage.lastIndexOf(","), '.');
    }

    return validationMessage.toString();
  }
}
