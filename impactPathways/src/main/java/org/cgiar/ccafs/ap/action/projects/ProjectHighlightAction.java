/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.action.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.HighLightManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectHighlightsType;
import org.cgiar.ccafs.ap.data.model.ProjectHighligths;
import org.cgiar.ccafs.ap.data.model.ProjectHighligthsCountry;
import org.cgiar.ccafs.ap.data.model.ProjectHighligthsTypes;
import org.cgiar.ccafs.ap.data.model.ProjectStatusEnum;
import org.cgiar.ccafs.ap.util.FileManager;
import org.cgiar.ccafs.ap.validation.projects.ProjectHighLightValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Sebastian Amariles G.
 */
public class ProjectHighlightAction extends BaseAction {

  private static final long serialVersionUID = 6921586701429004011L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectHighlightAction.class);

  // Manager
  private ProjectManager projectManager;
  private HighLightManager highLightManager;
  private LocationManager locationManager;
  private String highlightsImagesUrl;
  private File file;
  private ProjectHighLightValidator validator;
  private String fileFileName;
  private String contentType;


  // Model for the back-end
  private ProjectHighligths highlight;


  private Project project;


  // Model for the front-end
  private int highlightID;


  private Map<String, String> highlightsTypes;


  private Map<String, String> statuses;


  private List<Integer> allYears;

  private List<Country> countries;
  private List<ProjectHighlightsType> previewTypes;

  private List<ProjectHighligthsCountry> previewCountries;
  private HistoryManager historyManager;

  @Inject
  public ProjectHighlightAction(APConfig config, ProjectManager projectManager, HighLightManager highLightManager,
    LocationManager locationManager, ProjectHighLightValidator validator, HistoryManager historyManager) {
    super(config);
    this.projectManager = projectManager;
    this.highLightManager = highLightManager;

    this.locationManager = locationManager;
    this.validator = validator;
    this.historyManager = historyManager;
  }

  /**
   * This method validates if this highlight can be deleted or not.
   * Keep in mind that a highlight can be deleted if it was created in the current planning cycle.
   * 
   * @param highlightID is the highlight identifier.
   * @return true if the highlight can be deleted, false otherwise.
   */
  public boolean canDelete() {
    // Loop all the deliverables that are in the interface.
    // return highlight.getCreated() >= this.config.getCurrentPlanningStartDate().getTime();
    return true;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  private String getAnualReportRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator + "hightlihts"
      + File.separator;
  }

  public String getContentType() {
    return contentType;
  }

  public List<Country> getCountries() {
    return countries;
  }

  public int getEndYear() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy");
    return Integer.parseInt(dateFormat.format(project.getEndDate()));
  }

  public File getFile() {
    return file;
  }


  public String getFileFileName() {
    return fileFileName;
  }

  public ProjectHighligths getHighlight() {
    return highlight;
  }

  public String getHighlightsImagesUrl() {
    return config.getDownloadURL() + "/" + this.getHighlightsImagesUrlPath().replace('\\', '/');
  }


  public String getHighlightsImagesUrlPath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator + "hightlightsImage"
      + File.separator;
  }

  public Map<String, String> getHighlightsTypes() {
    return highlightsTypes;
  }

  private String getHightlightImagePath() {
    return config.getUploadsBaseFolder() + File.separator + this.getHighlightsImagesUrlPath() + File.separator;
  }


  public Project getProject() {
    return project;
  }

  public int getStartYear() {
    return config.getStartYear();
  }

  public Map<String, String> getStatuses() {
    return statuses;
  }

  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
  }

  @Override
  public String next() {
    return SUCCESS;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    previewTypes = new ArrayList<>();
    highlightID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.HIGHLIGHT_REQUEST_ID)));
    ProjectHighligths higligth = highLightManager.getHighLightById(highlightID);
    project = projectManager.getProject(Integer.parseInt(higligth.getProjectId() + ""));

    // Getting highlights Types
    highlightsTypes = new HashMap<>();
    List<ProjectHighlightsType> list = Arrays.asList(ProjectHighlightsType.values());
    for (ProjectHighlightsType ProjectHighlightsType : list) {
      highlightsTypes.put(ProjectHighlightsType.getId(), ProjectHighlightsType.getDescription());
    }

    // Getting statuses
    statuses = new HashMap<>();
    List<ProjectStatusEnum> statusesList = Arrays.asList(ProjectStatusEnum.values());
    for (ProjectStatusEnum projectStatusEnum : statusesList) {
      statuses.put(projectStatusEnum.getStatusId(), projectStatusEnum.getStatus());
    }

    // Getting all years from project
    allYears = project.getAllYears();
    List<Integer> listYears = new ArrayList<Integer>();
    for (int i = 0; i < allYears.size(); i++) {
      if ((allYears.get(i) <= this.getCurrentReportingYear())) {
        listYears.add(allYears.get(i));
      }
    }
    allYears.clear();
    allYears.addAll(listYears);

    // Getting countries list
    countries = locationManager.getAllCountries();

    // Getting the highlight information.
    highlight = highLightManager.getHighLightById(highlightID);

    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    // highlight.setStartDate(dateformatter.parse(dateformatter.format(highlight.getStartDate())));
    highlight.setStartDateText(dateformatter.format(highlight.getStartDate()));
    highlight.setEndDateText(dateformatter.format(highlight.getEndDate()));
    Iterator<ProjectHighligthsTypes> iteratorTypes = higligth.getProjectHighligthsTypeses().iterator();
    List<ProjectHighlightsType> typesids = new ArrayList<>();
    List<String> ids = new ArrayList<>();
    while (iteratorTypes.hasNext()) {
      ProjectHighligthsTypes projectHighligthsTypes = iteratorTypes.next();
      typesids.add(ProjectHighlightsType.value(projectHighligthsTypes.getIdType() + ""));
      ids.add(projectHighligthsTypes.getIdType() + "");
    }
    Iterator<ProjectHighligthsCountry> iteratorCountry = higligth.getProjectHighligthsCountries().iterator();
    List<Country> countrys = new ArrayList<>();
    List<Integer> countryids = new ArrayList<>();
    while (iteratorCountry.hasNext()) {
      ProjectHighligthsCountry projectHighligthsCountry = iteratorCountry.next();
      Country country = new Country();
      country.setId(projectHighligthsCountry.getIdCountry());
      int indexCountry = this.countries.indexOf(country);
      country.setName(this.countries.get(indexCountry).getName());
      countryids.add(projectHighligthsCountry.getIdCountry());
      countrys.add(country);
    }

    highlight.setYear(higligth.getYear());
    highlight.setCountriesIds(countryids);
    highlight.setCountries(countrys);
    highlight.setTypesIds(typesids);
    previewTypes.addAll(typesids);
    highlight.setTypesids(ids);

    super.setHistory(historyManager.getProjectHighLights(project.getId()));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());
  }

  @Override
  public String save() {

    if (this.hasProjectPermission("update", project.getId())) {
      DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
      // highlight.setStartDate(dateformatter.parse(dateformatter.format(highlight.getStartDate())));

      try {
        highlight.setStartDate(dateformatter.parse(highlight.getStartDateText()));
        highlight.setEndDate(dateformatter.parse(highlight.getEndDateText()));
      } catch (ParseException e) {
        LOG.error(e.getMessage());
      }
      List<ProjectHighligthsTypes> actualTypes = new ArrayList<>();
      for (String type : highlight.getTypesids()) {
        ProjectHighligthsTypes typeHigh = new ProjectHighligthsTypes();
        typeHigh.setIdType(Integer.parseInt(type));
        typeHigh.setProjectHighligths(highlight);
        actualTypes.add(typeHigh);


      }
      List<ProjectHighligthsCountry> actualcountries = new ArrayList<>();
      for (Integer countries : highlight.getCountriesIds()) {
        ProjectHighligthsCountry countryHigh = new ProjectHighligthsCountry();
        countryHigh.setIdCountry(countries);
        countryHigh.setProjectHighligths(highlight);
        actualcountries.add(countryHigh);
      }


      if (file != null) {
        FileManager.deleteFile(this.getHightlightImagePath() + highlight.getPhoto());
        FileManager.copyFile(file, this.getHightlightImagePath() + fileFileName);
        highlight.setPhoto(fileFileName);
      }


      highlight.setProjectId(new Long(project.getId() + ""));
      highlight.setProjectHighligthsTypeses(new HashSet<>(actualTypes));
      highlight.setProjectHighligthsCountries(new HashSet<>(actualcountries));
      highLightManager.saveHighLight(project.getId(), highlight, this.getCurrentUser(), this.getJustification());
      // Get the validation messages and append them to the save message
      Collection<String> messages = this.getActionMessages();
      if (!messages.isEmpty()) {
        String validationMessage = messages.iterator().next();
        this.setActionMessages(null);
        this.addActionWarning(this.getText("saving.saved") + validationMessage);
      } else {
        this.addActionMessage(this.getText("saving.saved"));
      }
      return SUCCESS;
    }
    return NOT_AUTHORIZED;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public void setDeliverable(ProjectHighligths deliverable) {
    this.highlight = deliverable;
  }


  public void setFile(File file) {
    this.file = file;
  }


  public void setFileFileName(String fileFileName) {
    this.fileFileName = fileFileName;
  }

  public void setHighlightsImagesUrl(String highlightsImagesUrl) {
    this.highlightsImagesUrl = highlightsImagesUrl;
  }


  @Override
  public void validate() {

    if (save) {
      validator.validate(this, project, highlight, this.getCycleName());
    }
  }
}
