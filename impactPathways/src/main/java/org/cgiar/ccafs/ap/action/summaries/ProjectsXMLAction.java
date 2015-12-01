package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
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
  private ProjectManager projectManager;

  // Models
  private List<Map<String, Object>> projectsData;

  // Model for the front-end
  private byte[] bytesXML;
  private InputStream inputStream;


  @Inject
  public ProjectsXMLAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }

  private Element buildElement(Document doc, String elementName, String elementValue, boolean cdata) {
    Element partnerID = doc.createElement(elementName);
    if (cdata) {
      partnerID.appendChild(doc.createCDATASection(elementValue));
    } else {
      partnerID.appendChild(doc.createTextNode(elementValue));
    }
    return partnerID;
  }

  private void buildXML(Document doc) {
    // root element
    Element project, location, contactPerson, institutions, partner, contribution, flagshipsLabel, regionsLabel, flagship, region, contributionsLabel, partnersLabel, outcomesLabel, locationsLabel, contactPersonsLabel, institutionsLabel;
    Element rootElement = doc.createElement("projects");
    doc.appendChild(rootElement);
    String[] partners, singlePartner, flagships, singleFlagship, regions, singleRegion, locations, singleLocation, contributions, singleContribution;
    for (Map<String, Object> projectData : projectsData) {
      project = doc.createElement("project");
      // id
      project.appendChild(this.buildElement(doc, "id", this.convertToString(projectData.get("project_id")), false));
      // type
      project.appendChild(this.buildElement(doc, "type", this.convertToString(projectData.get("project_type")), false));
      // title
      project
      .appendChild(this.buildElement(doc, "title", this.convertToString(projectData.get("project_title")), true));
      // summary
      project.appendChild(this.buildElement(doc, "summary", this.convertToString(projectData.get("project_summary")),
        true));
      // start date
      project.appendChild(this.buildElement(doc, "startDate", this.convertToString(projectData.get("start_date")),
        false));
      // end date
      project.appendChild(this.buildElement(doc, "endDate", this.convertToString(projectData.get("end_date")), false));
      // lead institution
      institutionsLabel = doc.createElement("leader");
      institutions = doc.createElement("institution");
      // lead institution acronym
      institutions.appendChild(this.buildElement(doc, "acronym",
        this.convertToString(projectData.get("lead_institution_acronym")), false));
      // lead institution name
      institutions.appendChild(this.buildElement(doc, "name",
        this.convertToString(projectData.get("lead_institution_name")), false));
      // contact persons
      contactPersonsLabel = doc.createElement("contactPersons");
      contactPerson = doc.createElement("contactPerson");
      // name
      contactPerson.appendChild(this.buildElement(doc, "name",
        this.convertToString(projectData.get("contact_person_name")), false));
      // email
      contactPerson.appendChild(this.buildElement(doc, "email",
        this.convertToString(projectData.get("contact_person_email")), false));
      contactPersonsLabel.appendChild(contactPerson);
      institutions.appendChild(contactPersonsLabel);
      institutionsLabel.appendChild(institutions);
      project.appendChild(institutionsLabel);
      // partners
      partnersLabel = doc.createElement("partners");
      partners = this.convertToString(projectData.get("partners")).split(";");
      for (String eachPartner : partners) {
        partner = doc.createElement("partner");
        singlePartner = eachPartner.split("@");
        // acronym
        partner.appendChild(this.buildElement(doc, "acronym", singlePartner[0], false));
        if (singlePartner.length > 1) {
          // name
          partner.appendChild(this.buildElement(doc, "name", singlePartner[1], false));
        }
        partnersLabel.appendChild(partner);
      }
      project.appendChild(partnersLabel);
      // flagships
      flagshipsLabel = doc.createElement("flagships");
      flagships = this.convertToString(projectData.get("flagships")).split(";");
      for (String eachFlagship : flagships) {
        flagship = doc.createElement("flagship");
        singleFlagship = eachFlagship.split("@");
        // acronym
        flagship.appendChild(this.buildElement(doc, "acronym", singleFlagship[0], false));
        if (singleFlagship.length > 1) {
          // name
          flagship.appendChild(this.buildElement(doc, "name", singleFlagship[1], false));
        }
        flagshipsLabel.appendChild(flagship);
      }
      project.appendChild(flagshipsLabel);
      // regions
      regionsLabel = doc.createElement("regions");
      regions = this.convertToString(projectData.get("regions")).split(";");
      for (String eachRegion : regions) {
        region = doc.createElement("region");
        singleRegion = eachRegion.split("@");
        // acronym
        region.appendChild(this.buildElement(doc, "acronym", singleRegion[0], false));
        if (singleRegion.length > 1) {
          // name
          region.appendChild(this.buildElement(doc, "name", singleRegion[1], false));
        }
        regionsLabel.appendChild(region);
      }
      project.appendChild(regionsLabel);
      // locations
      locationsLabel = doc.createElement("locations");
      locations = this.convertToString(projectData.get("locations")).split(";");
      for (String eachLocation : locations) {
        location = doc.createElement("location");
        singleLocation = eachLocation.split("@@");
        // location type
        location.appendChild(this.buildElement(doc, "level", singleLocation[0], false));
        if (singleLocation.length > 1) {
          // name
          location.appendChild(this.buildElement(doc, "name", singleLocation[1], false));
        }
        if (singleLocation.length > 2) {
          // latitude
          location.appendChild(this.buildElement(doc, "latitude", singleLocation[2], false));
        }
        if (singleLocation.length > 3) {
          // longitude
          location.appendChild(this.buildElement(doc, "longitude", singleLocation[3], false));
        }
        locationsLabel.appendChild(location);
      }
      project.appendChild(locationsLabel);
      // contributions
      contributionsLabel = doc.createElement("contributions");
      // cofunded projects
      if (this.convertToString(projectData.get("project_type")).equals("CCAFS_COFUNDED")) {
        contributions = this.convertToString(projectData.get("cofunded_contributions")).split(";");
        for (String eachContribution : contributions) {
          contribution = doc.createElement("bilateralProject");
          singleContribution = eachContribution.split("@@");
          contribution.appendChild(this.buildElement(doc, "id", singleContribution[0], false));
          if (singleContribution.length > 1) {
            // title
            contribution.appendChild(this.buildElement(doc, "title", singleContribution[1], true));
          }
          contributionsLabel.appendChild(contribution);
        }
      } // bilateral projects
      else if (this.convertToString(projectData.get("project_type")).equals("BILATERAL")) {
        contributions = this.convertToString(projectData.get("bilateral_contributions")).split(";");
        for (String eachContribution : contributions) {
          contribution = doc.createElement("cofundedProject");
          singleContribution = eachContribution.split("@@");
          // id
          contribution.appendChild(this.buildElement(doc, "id", singleContribution[0], false));
          if (singleContribution.length > 1) {
            // title
            contribution.appendChild(this.buildElement(doc, "title", singleContribution[1], true));
          }
          contributionsLabel.appendChild(contribution);
        }
      }
      project.appendChild(contributionsLabel);
      // outcomes
      outcomesLabel = doc.createElement("outcomes");
      outcomesLabel.appendChild(this.buildElement(doc, "statement",
        this.convertToString(projectData.get("outcome_statement")), true));
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