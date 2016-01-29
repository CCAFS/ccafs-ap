package org.cgiar.ccafs.ap.action.json.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.util.ClientRepository;
import org.cgiar.ccafs.ap.util.Metadata;
import org.cgiar.ccafs.utils.APConfig;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MetadataByLink extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = -8827720754396815474L;


  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MetadataByLink.class);


  // Model
  private String page;


  private String id;
  private Metadata metadata;

  // Managers
  private ClientRepository clientRepository;

  private final String CGSPACE = "https://cgspace.cgiar.org/oai/request";
  private final String AGTRIALS = "http://oai2.agtrials.org/oai2.php";
  private final String AMKN = "http://lab.amkn.org/oai/";

  @Inject
  public MetadataByLink(APConfig config, ClientRepository clientRepository) {
    super(config);
    this.clientRepository = clientRepository;
  }


  @Override
  public String execute() throws Exception {


    if (page.equals("-1")) {
      return NOT_FOUND;
    }

    if (id.equals("-1")) {
      return NOT_FOUND;
    }

    String linkRequest = "";
    switch (page) {
      case "cgspace":
        linkRequest = CGSPACE;
        break;

      case "agtrials":
        linkRequest = AGTRIALS;
        break;

      case "amkn":
        linkRequest = AMKN;
        break;

      default:
        break;
    }
    metadata = clientRepository.getMetadata(linkRequest, id);


    return SUCCESS;
  }


  public Metadata getMetadata() {
    return metadata;
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a elementID parameter
    if (this.getRequest().getParameter(APConstants.METADATA_REQUEST_ID) == null) {
      id = "-1";
      return;
    }

    if (this.getRequest().getParameter(APConstants.PAGE_ID) == null) {
      page = "-1";
      return;
    }


    // If there is a parameter take its values
    id = StringUtils.trim(this.getRequest().getParameter(APConstants.METADATA_REQUEST_ID));
    page = StringUtils.trim(this.getRequest().getParameter(APConstants.PAGE_ID));


  }


  public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }
}