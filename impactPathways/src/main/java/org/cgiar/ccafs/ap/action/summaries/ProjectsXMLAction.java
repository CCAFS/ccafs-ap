package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.summaries.Summary;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ProjectsXMLAction extends BaseAction implements Summary {

  private static final long serialVersionUID = -6383250876172016481L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ProjectsXMLAction.class);

  // Managers
  private ProjectPartnerManager partnerManager;
  private IPProgramManager programManager;
  private ProjectManager projectManager;

  // Models
  private List<Map<String, Object>> projectsData;
  private Map<String, IPProgram> ipPrograms;
  private Map<String, Project> projects;


  // Model for the front-end
  private byte[] bytesXML;
  private InputStream inputStream;


  @Inject
  public ProjectsXMLAction(APConfig config, IPProgramManager programManager, ProjectPartnerManager partnerManager,
    ProjectManager projectManager) {
    super(config);
    this.partnerManager = partnerManager;
    this.programManager = programManager;
    this.projectManager = projectManager;
  }

  private Element buildElement(Document doc, String elementName, String elementValue) {
    Element partnerID = doc.createElement(elementName);
    partnerID.appendChild(doc.createTextNode(elementValue));
    return partnerID;
  }

  private void buildXML(Document doc) {
    // root element
    Element project, location, contactPerson, institutions, partner, contribution, flagshipsLabel, regionsLabel, flagship, region, contributionsLabel, partnersLabel, outcomesLabel, locationsLabel, contactPersonsLabel, institutionsLabel;
    Element rootElement = doc.createElement("projects");
    IPProgram ipProgram;
    doc.appendChild(rootElement);
    // Project project;
    String[] ipProgramIds, projectIds;
    for (Map<String, Object> projectData : projectsData) {
      project = doc.createElement("project");
      // id
      project.appendChild(this.buildElement(doc, "id", this.convertToString(projectData.get("project_id"))));
      // type
      project.appendChild(this.buildElement(doc, "type", this.convertToString(projectData.get("project_type"))));
      // title
      project.appendChild(this.buildElement(doc, "title", this.convertToString(projectData.get("project_title"))));
      // summary
      project.appendChild(this.buildElement(doc, "summary", this.convertToString(projectData.get("project_summary"))));
      // start date
      project.appendChild(this.buildElement(doc, "startDate", this.convertToString(projectData.get("start_date"))));
      // end date
      project.appendChild(this.buildElement(doc, "endDate", this.convertToString(projectData.get("end_date"))));
      // lead institution
      institutionsLabel = doc.createElement("leader");
      institutions = doc.createElement("institution");
      // lead institution acronym
      institutions.appendChild(this.buildElement(doc, "acronym",
        this.convertToString(projectData.get("lead_institution_acronym"))));
      // lead institution name
      institutions.appendChild(this.buildElement(doc, "name",
        this.convertToString(projectData.get("lead_institution_name"))));
      // contact persons
      contactPersonsLabel = doc.createElement("contactPersons");
      contactPerson = doc.createElement("contactPerson");
      // name
      contactPerson
      .appendChild(this.buildElement(doc, "name", this.convertToString(projectData.get("contact_persons"))));
      // email
      contactPerson.appendChild(this.buildElement(doc, "email",
        this.convertToString(projectData.get("contact_persons"))));
      contactPersonsLabel.appendChild(contactPerson);
      institutions.appendChild(contactPersonsLabel);
      institutionsLabel.appendChild(institutions);
      project.appendChild(institutionsLabel);
      // partners
      partnersLabel = doc.createElement("partners");
      partner = doc.createElement("partner");
      // acronym
      partner.appendChild(this.buildElement(doc, "acronym", this.convertToString(projectData.get("partners"))));
      // name
      partner.appendChild(this.buildElement(doc, "name", this.convertToString(projectData.get("partners"))));
      partnersLabel.appendChild(partner);
      project.appendChild(partnersLabel);
      // flagships
      flagshipsLabel = doc.createElement("flagships");
      flagship = doc.createElement("flagship");
      flagship.appendChild(this.buildElement(doc, "name", this.convertToString(projectData.get("flagships"))));
      flagshipsLabel.appendChild(flagship);
      project.appendChild(flagshipsLabel);
      // regions
      regionsLabel = doc.createElement("regions");
      region = doc.createElement("region");
      region.appendChild(this.buildElement(doc, "name", this.convertToString(projectData.get("regions"))));
      regionsLabel.appendChild(region);
      project.appendChild(regionsLabel);
      // location
      locationsLabel = doc.createElement("locations");
      location = doc.createElement("location");
      // location type
      location.appendChild(this.buildElement(doc, "level", this.convertToString(projectData.get("locations"))));
      // location name
      location.appendChild(this.buildElement(doc, "name", this.convertToString(projectData.get("locations"))));
      // location latitude
      location.appendChild(this.buildElement(doc, "latitude", this.convertToString(projectData.get("locations"))));
      // location longitude
      location.appendChild(this.buildElement(doc, "longitude", this.convertToString(projectData.get("locations"))));
      locationsLabel.appendChild(location);
      project.appendChild(locationsLabel);
      // contributions
      contributionsLabel = doc.createElement("contribution");
      contribution = doc.createElement("project");
      if (this.convertToString(projectData.get("project_type")).equals("CCAFS_COFUNDED")) {
        contribution.appendChild(this.buildElement(doc, "title",
          this.convertToString(projectData.get("cofunded_contributions"))));
      } else if (this.convertToString(projectData.get("project_type")).equals("BILATERAL")) {
        contribution.appendChild(this.buildElement(doc, "title",
          this.convertToString(projectData.get("bilateral_contributions"))));
      } else {
        contribution.appendChild(this.buildElement(doc, "title", ""));
      }
      contributionsLabel.appendChild(contribution);
      project.appendChild(contributionsLabel);
      // outcomes
      outcomesLabel = doc.createElement("outcomes");
      outcomesLabel.appendChild(this.buildElement(doc, "statement",
        this.convertToString(projectData.get("outcome_statement"))));
      project.appendChild(outcomesLabel);
      rootElement.appendChild(project);
    }
  }

  private String convertToString(Object obj) {
    if (obj == null) {
      return "";
    }
    return String.valueOf(obj);
  }

  @Override
  public String execute() throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.newDocument();

    doc.setXmlVersion("1.0");
    // Lets build the XMLS into the document.
    this.buildXML(doc);

    TransformerFactory transFactory = TransformerFactory.newInstance();
    Transformer transformer = transFactory.newTransformer();
    DOMSource source = new DOMSource(doc);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    StreamResult result = new StreamResult(outputStream);
    transformer.transform(source, result);

    bytesXML = outputStream.toByteArray();
    outputStream.close();
    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return bytesXML.length;
  }

  @Override
  public String getContentType() {
    return "text/xml";
  }

  @Override
  public String getFileName() {
    return "projects.xml";
  }

  @Override
  public InputStream getInputStream() {
    if (inputStream == null) {
      inputStream = new ByteArrayInputStream(bytesXML);
    }
    return inputStream;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("Initiating the export for all the active projects in XML format for the CCAFS Website.");
    // Getting all the active projects with their corresponding information
    projectsData = projectManager.summaryGetActiveProjects();


    LOG.info("XML format for the CCAFS Website exported.");
  }
}