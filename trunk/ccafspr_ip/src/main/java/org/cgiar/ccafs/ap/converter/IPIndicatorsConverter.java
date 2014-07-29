package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.model.IPIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IPIndicatorsConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(UserConverter.class);

  // Manager
  private IPIndicatorManager indicatorManager;

  @Inject
  public IPIndicatorsConverter(IPIndicatorManager indicatorManager) {
    this.indicatorManager = indicatorManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      return indicatorManager.getIndicatorsList(values);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<IPIndicator> indicatorArray = (List<IPIndicator>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (IPIndicator indicator : indicatorArray) {
      temp.add(indicator.getId() + "");
    }
    // TODO
    return temp.toString();
  }
}
