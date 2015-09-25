package org.cgiar.ccafs.ap.validation;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.manager.SectionStatusManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.SectionStatus;
import org.cgiar.ccafs.utils.APConfig;

import javax.mail.internet.InternetAddress;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseValidator {

  private static final long serialVersionUID = -7486776113435920241L;

  private static final Logger LOG = LoggerFactory.getLogger(BaseValidator.class);

  @Inject
  protected APConfig config;
  protected StringBuilder validationMessage;
  protected StringBuilder missingFields;

  // Managers
  @Inject
  private SectionStatusManager statusManager;

  @Inject
  public BaseValidator() {
    validationMessage = new StringBuilder();
    missingFields = new StringBuilder();
  }

  protected void addMessage(String message) {
    if (validationMessage.length() != 0) {
      validationMessage.append(", ");
    }
    validationMessage.append(message);
  }

  /**
   * This method add a missing field separated by a semicolon (;).
   * 
   * @param field is the name of the field.
   */
  protected void addMissingField(String field) {
    if (missingFields.length() != 0) {
      missingFields.append(";");
    }
    missingFields.append(field);
  }

  protected boolean isValidEmail(String email) {
    if (email != null) {
      try {
        InternetAddress internetAddress = new InternetAddress(email);
        internetAddress.validate();
        return true;
      } catch (javax.mail.internet.AddressException e) {
        email = (email == null) ? "" : email;
        LOG.debug("Email address was invalid: " + email);
      }
    }
    return false;
  }

  /**
   * This method validates that the string received is not null and is not empty.
   * 
   * @param string
   * @return true if the string is valid. False otherwise.
   */
  protected boolean isValidString(String string) {
    if (string != null) {
      return !string.trim().isEmpty();
    }
    return false;
  }

  /**
   * This method saves the missing fields into the database.
   * 
   * @param project is a project.
   * @param cycle could be 'Planning' or 'Reporting'
   * @param sectionName is the name of the section (description, partners, etc.).
   */
  protected void saveMissingFields(Project project, String cycle, String sectionName) {
    // Reporting missing fields into the database.
    SectionStatus status = statusManager.getSectionStatus(project, cycle, sectionName);
    if (status == null) {
      status = new SectionStatus(cycle, sectionName);
    }
    status.setMissingFields(this.missingFields.toString());
    statusManager.saveSectionStatus(status, project);
  }

  protected void validateLessonsLearn(BaseAction action, Project project) {
    if (!project.isNew(config.getCurrentPlanningStartDate())) {
      if (action.getProjectLessons() == null || action.getProjectLessons().getLessons().isEmpty()) {
        action.addFieldError("projectLessons.lessons", action.getText("validation.field.required"));
      }
    }
  }

  /**
   * This method verify if the project was created in the current planning phase, if it was created previously the user
   * should provide a justification of the changes.
   * 
   * @param project
   */
  protected void validateProjectJustification(BaseAction action, Project project) {
    if (!project.isNew(config.getCurrentPlanningStartDate())) {
      if (action.getJustification() == null || action.getJustification().isEmpty()) {
        action.addActionError(action.getText("validation.justification"));
        action.addFieldError("justification", action.getText("validation.field.required"));
      }
    }
  }

  /**
   * This method counts the number of words in a given text.
   * 
   * @param text is some text to be validated.
   * @return the number of words.
   */
  protected int wordCount(String text) {
    text = text.trim();
    return text.isEmpty() ? 0 : text.split("\\s+").length;
  }
}
