package org.cgiar.ccafs.ap.util;

import org.cgiar.ccafs.ap.data.model.Activity;

import com.opensymphony.xwork2.ActionSupport;


public class ActivityValidator extends ActionSupport {

  private static final long serialVersionUID = -3290221027694919282L;

  /**
   * This method validate if the activity is complete for the
   * planning section.
   * 
   * @param activity
   * @return A message specifying the empty fields or a empty string
   *         if the activity is complete.
   */
  public String validateActivityPlanning(Activity activity) {
    StringBuilder validationMessages = new StringBuilder();
    boolean problem = false;
    int activityID;

    // It is needed take the activity identifier before continue
    activityID = activity.getId();

    // Validate process
    // Check the exists an start date and end date
    if (activity.getStartDate() == null) {
      validationMessages.append(getText("planning.mainInformation.validation.startDate"));
      validationMessages.append(", ");
      problem = true;
    }

    if (activity.getEndDate() == null) {
      validationMessages.append(getText("planning.mainInformation.validation.endDate"));
      validationMessages.append(", ");
      problem = true;
    }

    // Check if the activity have at least one contact person
    if (activity.getContactPersons() == null || activity.getContactPersons().isEmpty()) {
      validationMessages.append(getText("planning.mainInformation.validation.contactPerson"));
      validationMessages.append(", ");
      problem = true;
    } else {
      for (int c = 0; c < activity.getContactPersons().size(); c++) {
        // Check if at least there is a contact name
        if (activity.getContactPersons().get(c).getName().isEmpty()) {
          validationMessages.append(getText("planning.mainInformation.validation.contactPerson.name"));
          validationMessages.append(", ");
          problem = true;
        }

        // If there is a contact email, check if it is valid
        if (!activity.getContactPersons().get(c).getEmail().isEmpty()) {
          if (!EmailValidator.isValidEmail(activity.getContactPersons().get(c).getEmail())) {
            validationMessages.append(getText("planning.mainInformation.validation.contactPerson.email"));
            validationMessages.append(", ");
            problem = true;
          }
        }
      }
    }

    // Validate objectives
    if (activity.getObjectives().isEmpty()) {
      validationMessages.append(getText("planning.objectives.validation.atLeastOne"));
      validationMessages.append(", ");
      problem = true;
    }

    // Validate locations
    // Activity should be global or have at least one location
    if (!activity.isGlobal() && activity.getCountries().isEmpty() && activity.getRegions().isEmpty()
      && activity.getOtherLocations().isEmpty()) {
      validationMessages.append(getText("planning.locations.validation.atLeastOneLocation"));
      validationMessages.append(", ");
      problem = true;
    }

    validationMessages.setCharAt(validationMessages.lastIndexOf(","), '.');
    System.out.println(validationMessages.toString());
    if (problem) {
      String message = getText("planning.activityList.validation.error", new String[] {String.valueOf(activityID)});
      message += validationMessages.toString();

      // Return the string with the message.
      return message;
    } else {
      // Return an empty field to indicate that wasn't any problem.
      return "";
    }
  }
}
