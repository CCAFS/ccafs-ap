package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class DeliverablesConverter extends StrutsTypeConverter {

  private DeliverableManager deliverableManager;

  @Inject
  public DeliverablesConverter(DeliverableManager deliverableManager) {
    this.deliverableManager = deliverableManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      // TO DO create a method that returns a deliverable list
      // return deliverableManager.getCountryList(values);
      return new ArrayList<Product>();
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<Product> deliverablesArray = (List<Product>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (Product c : deliverablesArray) {
      temp.add(c.getId() + "");
    }
    // TODO
    return temp.toString();
  }
}
