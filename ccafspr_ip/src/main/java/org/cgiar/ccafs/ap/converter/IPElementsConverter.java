package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class IPElementsConverter extends StrutsTypeConverter {

  private IPElementManager ipElementManager;

  @Inject
  public IPElementsConverter(IPElementManager ipElementManager) {
    this.ipElementManager = ipElementManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      return ipElementManager.getIPElementList(values);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<IPElement> elementsArray = (List<IPElement>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (IPElement c : elementsArray) {
      temp.add(c.getId() + "");
    }
    return temp.toString();
  }
}
