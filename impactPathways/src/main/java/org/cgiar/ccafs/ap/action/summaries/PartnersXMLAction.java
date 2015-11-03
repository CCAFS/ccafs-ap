package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
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
import java.util.HashMap;
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


public class PartnersXMLAction extends BaseAction implements Summary {

  private static final long serialVersionUID = -6383250876172016481L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersXMLAction.class);

  // Managers
  private ProjectPartnerManager partnerManager;
  private IPProgramManager programManager;
  private ProjectManager projectManager;

  // Models
  private List<Map<String, Object>> partnersData;
  private Map<String, IPProgram> ipPrograms;
  private Map<String, Project> projects;


  // Model for the front-end
  private byte[] bytesXML;
  private InputStream inputStream;


  @Inject
  public PartnersXMLAction(APConfig config, IPProgramManager programManager, ProjectPartnerManager partnerManager,
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

  // rootElement.appendChild(this.buildElement(doc, "id", "123"));
  private void buildXML(Document doc) {
    // root element
    Element partner, location, country, region, type, flagshipsLabel, regionsLabel, flagship, regionEt, projectsLabel,
      projectLabel;
    Element rootElement = doc.createElement("partners");
    IPProgram ipProgram;
    doc.appendChild(rootElement);
    Project project;
    String[] ipProgramIds, projectIds;
    for (Map<String, Object> partnerData : partnersData) {
      partner = doc.createElement("partner");
      // id
      partner.appendChild(this.buildElement(doc, "id", this.convertToString(partnerData.get("id"))));
      // name
      partner.appendChild(this.buildElement(doc, "name", this.convertToString(partnerData.get("institution_name"))));
      // acronym
      partner
        .appendChild(this.buildElement(doc, "acronym", this.convertToString(partnerData.get("institution_acronym"))));
      // website
      partner
        .appendChild(this.buildElement(doc, "website", this.convertToString(partnerData.get("institution_website"))));

      // location ---
      location = doc.createElement("location");
      // --- country
      country = doc.createElement("country");
      country.appendChild(this.buildElement(doc, "iso2", this.convertToString(partnerData.get("country_code"))));
      country.appendChild(this.buildElement(doc, "name", this.convertToString(partnerData.get("country_name"))));
      // --- region
      region = doc.createElement("region");
      region.appendChild(this.buildElement(doc, "id", this.convertToString(partnerData.get("region_id"))));
      region.appendChild(this.buildElement(doc, "name", this.convertToString(partnerData.get("region_name"))));

      location.appendChild(country);
      location.appendChild(region);
      location.appendChild(this.buildElement(doc, "city", this.convertToString(partnerData.get("city"))));

      partner.appendChild(location);

      // type
      type = doc.createElement("type");
      type.appendChild(this.buildElement(doc, "id", this.convertToString(partnerData.get("institution_type_id"))));
      type.appendChild(this.buildElement(doc, "name", this.convertToString(partnerData.get("institution_type_name"))));
      type.appendChild(
        this.buildElement(doc, "acronym", this.convertToString(partnerData.get("institution_type_acronym"))));
      partner.appendChild(type);

      // flagships
      ipProgramIds = this.convertToString(partnerData.get("ip_programs")).split(",");

      flagshipsLabel = doc.createElement("flagship-programs");
      regionsLabel = doc.createElement("region-programs");

      for (String ipProgramId : ipProgramIds) {
        ipProgram = ipPrograms.get(ipProgramId);
        switch (ipProgram.getType().getId()) {
          case APConstants.FLAGSHIP_PROGRAM_TYPE:
            // --- flagship
            flagship = doc.createElement("flagship");
            flagship.appendChild(this.buildElement(doc, "id", this.convertToString(ipProgram.getId())));
            flagship.appendChild(this.buildElement(doc, "acronym", ipProgram.getAcronym()));
            flagship.appendChild(this.buildElement(doc, "name", ipProgram.getName()));
            flagshipsLabel.appendChild(flagship);
            break;

          case APConstants.REGION_PROGRAM_TYPE:
            // --- regions
            regionEt = doc.createElement("region");
            regionEt.appendChild(this.buildElement(doc, "id", this.convertToString(ipProgram.getId())));
            regionEt.appendChild(this.buildElement(doc, "acronym", ipProgram.getAcronym()));
            regionEt.appendChild(this.buildElement(doc, "name", ipProgram.getName()));
            regionsLabel.appendChild(regionEt);
            break;

          default:
            break;
        }
      }

      partner.appendChild(flagshipsLabel);
      partner.appendChild(regionsLabel);

      // projects
      projectsLabel = doc.createElement("projects");
      projectIds = this.convertToString(partnerData.get("project_ids")).split(",");
      for (String projectId : projectIds) {
        // --- project
        project = projects.get(projectId);
        projectLabel = doc.createElement("project");
        projectLabel.appendChild(this.buildElement(doc, "id", this.convertToString(project.getId())));
        projectLabel.appendChild(this.buildElement(doc, "title", project.getTitle()));
        projectLabel.appendChild(this.buildElement(doc, "type", project.getType()));
        projectsLabel.appendChild(projectLabel);
      }
      partner.appendChild(projectsLabel);

      // Add partner
      rootElement.appendChild(partner);
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
    return "partners.xml";
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
    LOG.info("Initiating the export for all the active partners in XML format for the CCAFS Website.");
    // Getting the partner info from the SQL query.
    // System.out.println();
    partnersData = partnerManager.summaryGetActivePartners();

    ipPrograms = new HashMap<String, IPProgram>();

    // Getting all the flagships.
    List<IPProgram> flagshipList = programManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);
    for (IPProgram iPProgram : flagshipList) {
      ipPrograms.put(String.valueOf(iPProgram.getId()), iPProgram);
    }

    // Getting all the regions.
    List<IPProgram> regionList = programManager.getProgramsByType(APConstants.REGION_PROGRAM_TYPE);
    for (IPProgram iPProgram : regionList) {
      ipPrograms.put(String.valueOf(iPProgram.getId()), iPProgram);
    }

    projects = new HashMap<String, Project>();
    List<Project> projectList = projectManager.getAllProjectsBasicInfo();
    for (Project project : projectList) {
      projects.put(String.valueOf(project.getId()), project);
    }


    LOG.info("XML format for the CCAFS Website exported.");
  }
}