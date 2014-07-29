package org.cgiar.ccafs.ap.converter;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IPIndicatorConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(UserConverter.class);

  // Manager
  private IPIndicatorManager indicatorManager;

  @Inject
  public IPIndicatorConverter(IPIndicatorManager indicatorManager) {
    this.indicatorManager = indicatorManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    System.out.println("----------------------------Indicator converter");
    if (toClass == IPIndicator.class) {
      String id = values[0];
      try {
        LOG.debug(">> convertFromString > id = {} ", id);
        return indicatorManager.getIndicator(Integer.parseInt(id));
      } catch (NumberFormatException e) {
        // Do Nothing
        LOG.error("Problem to convert IPIndicator from String (convertFromString) for indicator_id = {} ", id,
          e.getMessage());
      }
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    IPIndicator indicator = (IPIndicator) o;
    LOG.debug(">> convertToString > id = {} ", indicator.getId());
    return indicator.getId() + "";
  }
}
