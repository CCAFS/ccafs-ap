package org.cgiar.ccafs.ap.action.reporting.casestudies;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.CaseStudyCountriesManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyManager;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.util.FileManager;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CaseStudiesAction extends BaseAction {

  private static final long serialVersionUID = -6393409409918755065L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(CaseStudiesAction.class);

  // Managers
  private CaseStudyManager caseStudyManager;
  private CaseStudyCountriesManager caseStudyCountriesManager;
  private CountryManager countryManager;

  // Model
  private List<CaseStudy> caseStudies;
  private Country[] countryList;
  private Map<Integer, String> imageNameMap;

  @Inject
  public CaseStudiesAction(APConfig config, LogframeManager logframeManager, CaseStudyManager caseStudyManager,
    CountryManager countryManager, CaseStudyCountriesManager caseStudyCountriesManager) {
    super(config, logframeManager);
    this.caseStudyManager = caseStudyManager;
    this.countryManager = countryManager;
    this.caseStudyCountriesManager = caseStudyCountriesManager;
  }

  /**
   * Delete all the images stored in the hard disk
   * which are not being used
   */
  private void deleteUnusedImages() {
    Collection<String> imageNames = imageNameMap.values();
    // Remove from the list of names all the images used
    // by a case study
    for (CaseStudy caseStudy : caseStudies) {
      if (imageNames.contains(caseStudy.getImageFileName())) {
        imageNames.remove(caseStudy.getImageFileName());
      }
    }
    // All the elements that still in the list
    // aren't used, then, delete them from the disk
    for (String imageName : imageNames) {
      FileManager.deleteFile(getFolderPath(imageName));
    }
  }

  public List<CaseStudy> getCaseStudies() {
    return caseStudies;
  }

  /**
   * Join the url to the folder of cases studies images and
   * the organization folders to return the complete url where
   * are located the images
   * 
   * @return the url where images are
   */
  public String getCaseStudiesImagesUrl() {
    return config.getCaseStudiesImagesUrl() + "/" + getCurrentLogframe().getYear() + "/"
      + getCurrentUser().getLeader().getAcronym() + "/";
  }

  public Country[] getCountryList() {
    return countryList;
  }

  public int getCurrentYear() {
    return config.getCurrentYear();
  }

  public int getEndYear() {
    return config.getEndYear();
  }

  /**
   * Join the path to the folder of cases studies images, the organization
   * folders path and the image name to return the complete path
   * where is or will be stored the image
   * 
   * @param imageName
   * @return complete path where the image is stored
   */
  private String getFolderPath(String imageName) {
    return config.getCaseStudiesImagesPath() + File.separator + getCurrentLogframe().getYear() + File.separator
      + getCurrentUser().getLeader().getAcronym() + File.separator + imageName;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    caseStudies = caseStudyManager.getCaseStudyList(getCurrentUser().getLeader(), getCurrentLogframe());
    countryList = countryManager.getCountryList();

    // Initialize the map of image's names
    imageNameMap = new HashMap<>();

    // If there are elements in the case study list, iterate it to store
    // the corresponding list of countries
    List<Country> temporalCountryList;
    for (int c = 0; c < caseStudies.size(); c++) {
      temporalCountryList = caseStudyCountriesManager.getCaseStudyCountriesList(caseStudies.get(c));
      caseStudies.get(c).setCountries(temporalCountryList);
      // If the case study has an image name, store it into the image names map
      // Key -> caseStudy.id ,Value -> image name
      if (caseStudies.get(c).getImageFileName() != null) {
        imageNameMap.put(caseStudies.get(c).getId(), caseStudies.get(c).getImageFileName());
      }
    }

    // Remove all caseStudies in case user clicked on submit button
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      caseStudies.clear();
    }
  }

  @Override
  public String save() {
    boolean problem = false;

    // First, remove all the case studies from the database
    boolean deleted =
      caseStudyManager.removeAllCaseStudies(getCurrentUser().getLeader().getId(), getCurrentLogframe().getId());
    if (!deleted) {
      problem = true;
    } else {
      for (int c = 0; c < caseStudies.size(); c++) {
        // Second, add the list of case studies into the database

        // If the user don't upload an image
        if (caseStudies.get(c).getImage() == null) {
          // Check if there is an image name related to the case study in
          // image names map
          if (imageNameMap.get(caseStudies.get(c).getId()) != null) {
            // If there is one, assign it to the case study
            caseStudies.get(c).setImageFileName(imageNameMap.get(caseStudies.get(c).getId()));
          }
        } else {
          caseStudies.get(c).setImageFileName(caseStudies.get(c).getImageFileName());
        }

        boolean added =
          caseStudyManager.saveCaseStudy(caseStudies.get(c), getCurrentUser().getLeader().getId(), getCurrentLogframe()
            .getId());
        if (!added) {
          problem = true;
        } else {
          // If there was not problem saving into the DAO and
          // the user uploads an image, move the image from temporal folder to
          // the appropriate folder

          if (caseStudies.get(c).getImage() != null) {
            // If the user uploads an image, check if the name is the same name stored
            // in the image names map to this case study
            if (!caseStudies.get(c).getImageFileName().equals(imageNameMap.get(caseStudies.get(c).getId()))) {
              // If they aren't the same, delete the previous file
              FileManager.deleteFile(getFolderPath(imageNameMap.get(caseStudies.get(c).getId())));
              // next, copy the new image from temporal folder to its final folder
              FileManager.copyFile(caseStudies.get(c).getImage(), getFolderPath(caseStudies.get(c).getImageFileName()));
            }
          }
        }
      }
    }

    // Finally, delete from the disk the unused images
    deleteUnusedImages();

    if (!problem) {
      addActionMessage(getText("saving.success", new String[] {getText("reporting.caseStudies.caseStudies")}));
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setCaseStudies(List<CaseStudy> caseStudies) {
    this.caseStudies = caseStudies;
  }

  @Override
  public void validate() {
    boolean anyError = false;

    // Validations
    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      for (int c = 0; c < caseStudies.size(); c++) {
        // Title
        if (caseStudies.get(c).getTitle().isEmpty()) {
          addFieldError("caseStudies[" + c + "].title", getText("validation.field.required"));
          anyError = true;
        }
        // Author
        if (caseStudies.get(c).getAuthor().isEmpty()) {
          addFieldError("caseStudies[" + c + "].author", getText("validation.field.required"));
          anyError = true;
        }
        // Photo

        if (caseStudies.get(c).getImage() != null) {
          String type = caseStudies.get(c).getImageContentType().split("/")[0];

          // Check if the file type is an image
          if (!type.equalsIgnoreCase("image")) {
            addFieldError("caseStudies[" + c + "].image",
              getText("validation.file.badFormat", new String[] {"an image"}));
            anyError = true;
          }
          // TODO - check the maximum size allowed because, the tomcat has a max size defined too
          // in my test server is 2 Mb, in that case it must be redefined in the web.xml or in the
          // struts.properties struts.multipart.maxSize=100485760

          // Check the file size
          if (caseStudies.get(c).getImage().length() > config.getFileMaxSize()) {
            addFieldError("caseStudies[" + c + "].image",
              getText("validation.file.tooLarge", new String[] {config.getFileMaxSize() + ""}));
            anyError = true;
          }

        }

        // Start date, if the user don't enter a value, the object is null
        if (caseStudies.get(c).getStartDate() == null) {
          addFieldError("caseStudies[" + c + "].startDate", getText("validation.field.required"));
          anyError = true;
        }
        // End date, if the user don't enter a value, the object is null
        if (caseStudies.get(c).getEndDate() == null) {
          addFieldError("caseStudies[" + c + "].endDate", getText("validation.field.required"));
          anyError = true;
        }
        // Keywords
        if (caseStudies.get(c).getKeywords().isEmpty()) {
          addFieldError("caseStudies[" + c + "].keywords", getText("validation.field.required"));
          anyError = true;
        }
        // Objectives
        if (caseStudies.get(c).getObjectives().isEmpty()) {
          addFieldError("caseStudies[" + c + "].objectives", getText("validation.field.required"));
          anyError = true;
        }
        // Description
        if (caseStudies.get(c).getDescription().isEmpty()) {
          addFieldError("caseStudies[" + c + "].description", getText("validation.field.required"));
          anyError = true;
        }
        // Results
        if (caseStudies.get(c).getResults().isEmpty()) {
          addFieldError("caseStudies[" + c + "].results", getText("validation.field.required"));
          anyError = true;
        }
        // Partners
        if (caseStudies.get(c).getPartners().isEmpty()) {
          addFieldError("caseStudies[" + c + "].partners", getText("validation.field.required"));
          anyError = true;
        }
      }
    }

    if (anyError) {
      addActionError(getText("saving.fields.required"));
    }
  }
}
