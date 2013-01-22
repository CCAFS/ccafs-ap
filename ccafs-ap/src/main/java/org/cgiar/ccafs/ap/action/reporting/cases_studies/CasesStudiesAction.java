package org.cgiar.ccafs.ap.action.reporting.cases_studies;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.CasesStudiesCountriesManager;
import org.cgiar.ccafs.ap.data.manager.CasesStudiesManager;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.CasesStudy;
import org.cgiar.ccafs.ap.data.model.Country;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CasesStudiesAction extends BaseAction {

  private static final long serialVersionUID = -6393409409918755065L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(CasesStudiesAction.class);

  // Managers
  private CasesStudiesManager casesStudiesManager;
  private CasesStudiesCountriesManager casesStudiesCountriesManager;
  private CountryManager countryManager;

  // Model
  private List<CasesStudy> casesStudies;
  private Country[] countriesList;
  private String photoPath;
  private Map<Integer, String> photoNameMap;

  private int activityLeaderId;
  private int logframeId;

  @Inject
  public CasesStudiesAction(APConfig config, LogframeManager logframeManager, CasesStudiesManager casesStudiesManager,
    CountryManager countryManager, CasesStudiesCountriesManager casesStudiesCountriesManager) {
    super(config, logframeManager);
    this.casesStudiesManager = casesStudiesManager;
    this.countryManager = countryManager;
    this.casesStudiesCountriesManager = casesStudiesCountriesManager;
  }

  public boolean deleteImage(String fileName) {
    // First, load the image object
    File deleteFile = new File(config.getUserImagesPath() + getPhotoPath() + fileName);
    // Make sure the file or directory exists and isn't write protected
    if (!deleteFile.exists()) {
      // TODO must be an error that the file dissapear ?
      LOG.warn("Delete: no such file or directory: " + fileName);
      // Return true because the user can save the image
      return true;
    }
    if (!deleteFile.canWrite()) {
      LOG.error("Delete: write protected: " + fileName);
      return false;
    }
    // Attempt to delete it
    boolean success = deleteFile.delete();
    return success;
  }

  private void deleteUnusedImages() {
    Collection<String> photoNames = photoNameMap.values();
    // Remove from the list of names all the images used
    // by a case study
    for (CasesStudy caseStudy : casesStudies) {
      if (photoNames.contains(caseStudy.getPhotoFileName())) {
        photoNames.remove(caseStudy.getPhotoFileName());
      }
    }
    // All the elements that still in the list
    // aren't used, then, delete them from the disk
    for (String photoName : photoNames) {
      deleteImage(photoName);
    }
  }

  public List<CasesStudy> getCasesStudies() {
    return casesStudies;
  }

  public Country[] getCountriesList() {
    return countriesList;
  }

  public String getPhotoPath() {
    return photoPath;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    activityLeaderId = getCurrentUser().getLeader().getId();
    logframeId = getCurrentLogframe().getId();

    casesStudies = casesStudiesManager.getCasesStudiesList(getCurrentUser().getLeader(), getCurrentLogframe());
    countriesList = countryManager.getCountriesList();

    // If there isn't a case study in the database create the empty list
    // that will contain an empty object
    if (casesStudies == null) {
      casesStudies = new ArrayList<>();
      casesStudies.add(new CasesStudy());
      // the photo name list must be initialized to prevent errors
      photoNameMap = new HashMap<>();
    } else {
      // If there are elements in the list, iterate it to store
      // the list of countries
      List<Country> temporalList;
      // Initialize the list of photo's names
      photoNameMap = new HashMap<>();
      for (int c = 0; c < casesStudies.size(); c++) {
        // Set the countries
        temporalList = casesStudiesCountriesManager.getCasesStudiesCountriesList(casesStudies.get(c));
        casesStudies.get(c).setCountries(temporalList);
        // If exists an photo related store it's name in the temporal array
        if (casesStudies.get(c).getPhotoFileName() != null) {
          photoNameMap.put(casesStudies.get(c).getId(), casesStudies.get(c).getPhotoFileName());
        }
      }
    }
    // Remove all caseStudies in case user clicked on submit button
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      casesStudies.clear();
    }

    // Set the path where are stored the images
    photoPath =
      "resources/caseStudies/" + getCurrentLogframe().getYear() + "/" + getCurrentUser().getLeader().getName() + "/";
  }

  @Override
  public String save() {
    boolean problem = false;

    // First, remove all the case studies
    boolean deleted = casesStudiesManager.removeAllStudyCases(activityLeaderId, logframeId);
    if (!deleted) {
      problem = true;
    } else {
      for (int c = 0; c < casesStudies.size(); c++) {
        // Secondly, add the list of case studies into the database

        // If the form don't send photo information for this caseStudy
        if (casesStudies.get(c).getPhoto() == null) {
          // Check if there is an image associated with the id, in the
          // temporal map
          if (photoNameMap.get(casesStudies.get(c).getId()) != null) {
            // If there is one, assign it to the object
            casesStudies.get(c).setPhotoFileName(photoNameMap.get(casesStudies.get(c).getId()));
          }
        } else {
          // If there is a photo create a random number to append to the file name
          String randomNumber = String.valueOf((int) (Math.random() * 9999) + 1);
          casesStudies.get(c).setPhotoFileName(randomNumber + casesStudies.get(c).getPhotoFileName());
        }

        boolean added = casesStudiesManager.storeCaseStudies(casesStudies.get(c), activityLeaderId, logframeId);
        if (!added) {
          problem = true;
          addActionError(getText("saving.problem"));
          LOG.error(getText("save.problem"));
          return INPUT;
        } else {
          // If there was not problem saving in the DAO and
          // the user gives a new photo, move the image

          // Check if the user gives a photo
          if (casesStudies.get(c).getPhoto() != null) {
            // If the user gives an photo, check if the name is the same name stored
            // in the temporal map to this case study
            if (!casesStudies.get(c).getPhotoFileName().equals(photoNameMap.get(casesStudies.get(c).getId()))) {
              // If they aren't the same, delete the previous file
              deleteImage(photoNameMap.get(casesStudies.get(c).getId()));
              // next, store the new image
              storeImage(casesStudies.get(c).getPhoto(), casesStudies.get(c).getPhotoFileName());
            }
          }
        }
      }
    }

    // Finally, delete from the disk the unused images
    deleteUnusedImages();

    if (!problem) {
      addActionMessage(getText("saving.success", new String[] {getText("reporting.casesStudies.casesStudies")}));
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setCasesStudies(List<CasesStudy> casesStudies) {
    this.casesStudies = casesStudies;
  }

  public void setCaseStudiesPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }

  public void setCountriesList(Country[] countriesList) {
    this.countriesList = countriesList;
  }

  public boolean storeImage(File file, String fileName) {
    // TODO check if is necessary put the full path to the cases studies images in the
    // configuration file

    File saveFile = new File(config.getUserImagesPath() + getPhotoPath() + fileName);
    try {
      FileUtils.copyFile(file, saveFile);
    } catch (IOException e) {
      // TODO Auto generated catch block
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public void validate() {
    boolean anyError = false;

    // Validations
    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      for (int c = 0; c < casesStudies.size(); c++) {
        // Title
        if (casesStudies.get(c).getTitle().isEmpty()) {
          addFieldError("casesStudies[" + c + "].title", getText("validation.field.required"));
          anyError = true;
        }
        // Author
        if (casesStudies.get(c).getAuthor().isEmpty()) {
          addFieldError("casesStudies[" + c + "].author", getText("validation.field.required"));
          anyError = true;
        }
        // Photo

        if (casesStudies.get(c).getPhoto() != null) {
          String type = casesStudies.get(c).getPhotoContentType().split("/")[0];

          // Check if the file type is an image
          if (!type.equalsIgnoreCase("image")) {
            addFieldError("casesStudies[" + c + "].photo",
              getText("validation.badFormatFile", new String[] {"an image"}));
            anyError = true;
          }
          // TODO - check the maximun size allowed because, the tomcat has a max size defined too
          // in my test server is 2 Mb, in that case it must be redefined in the web.xml or in the
          // struts.properties struts.multipart.maxSize=100485760

          // Check the file size
          if (casesStudies.get(c).getPhoto().length() > config.getFileMaxSize()) {
            addFieldError("casesStudies[" + c + "].photo",
              getText("validation.FileTooLarge", new String[] {config.getFileMaxSize() + ""}));
            anyError = true;
          }

        }

        // Date, if the user don't enter a value, the object is null
        if (casesStudies.get(c).getDate() == null) {
          addFieldError("casesStudies[" + c + "].date", getText("validation.field.required"));
          anyError = true;
        }
        // Keywords
        if (casesStudies.get(c).getKeywords().isEmpty()) {
          addFieldError("casesStudies[" + c + "].keywords", getText("validation.field.required"));
          anyError = true;
        }
        // Objectives
        if (casesStudies.get(c).getObjectives().isEmpty()) {
          addFieldError("casesStudies[" + c + "].objectives", getText("validation.field.required"));
          anyError = true;
        }
        // Description
        if (casesStudies.get(c).getDescription().isEmpty()) {
          addFieldError("casesStudies[" + c + "].description", getText("validation.field.required"));
          anyError = true;
        }
        // Results
        if (casesStudies.get(c).getResults().isEmpty()) {
          addFieldError("casesStudies[" + c + "].results", getText("validation.field.required"));
          anyError = true;
        }
        // Partners
        if (casesStudies.get(c).getPartners().isEmpty()) {
          addFieldError("casesStudies[" + c + "].partners", getText("validation.field.required"));
          anyError = true;
        }
        // Links
        if (casesStudies.get(c).getLinks().isEmpty()) {
          addFieldError("casesStudies[" + c + "].links", getText("validation.field.required"));
          anyError = true;
        }
      }
    }

    if (anyError) {
      addActionError(getText("saving.fields.required"));
    }
  }
}
