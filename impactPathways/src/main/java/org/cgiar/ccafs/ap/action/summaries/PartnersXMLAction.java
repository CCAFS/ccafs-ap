package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
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


public class PartnersXMLAction extends BaseAction implements Summary {

  private static final long serialVersionUID = -6383250876172016481L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersXMLAction.class);

  // Managers
  private ProjectPartnerManager partnerManager;
  private IPProgramManager programManager;

  // Models
  private List<Map<String, Object>> partnersData;
  private List<IPProgram> flagships;
  private List<IPProgram> regions;

  // Model for the front-end
  private byte[] bytesXML;
  private InputStream inputStream;


  @Inject
  public PartnersXMLAction(APConfig config, IPProgramManager programManager, ProjectPartnerManager partnerManager) {
    super(config);
    this.partnerManager = partnerManager;
    this.programManager = programManager;
  }

  private Element buildElement(Document doc, String elementName, String elementValue) {
    Element partnerID = doc.createElement(elementName);
    partnerID.appendChild(doc.createTextNode(elementValue));
    return partnerID;
  }

  private void buildXML(Document doc) {
    // root element
    Element rootElement = doc.createElement("partners");
    doc.appendChild(rootElement);

    for (Map<String, Object> partnerData : partnersData) {
      Element partner = doc.createElement("partner");
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

      // --- location ---
      Element location = doc.createElement("location");

      // location.appendChild(this.buildElement(doc, elementName, elementValue))

      partner.appendChild(location);

      rootElement.appendChild(partner);
    }


    rootElement.appendChild(this.buildElement(doc, "id", "123"));

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
    System.out.println();
    partnersData = partnerManager.summaryGetActivePartners();

    // Getting all the flagships.
    flagships = programManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);

    // Getting all the regions.
    regions = programManager.getProgramsByType(APConstants.REGION_PROGRAM_TYPE);

    LOG.info("XML format for the CCAFS Website exported.");
  }
}