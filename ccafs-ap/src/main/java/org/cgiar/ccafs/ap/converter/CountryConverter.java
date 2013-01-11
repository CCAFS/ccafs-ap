package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class CountryConverter extends StrutsTypeConverter {

  private CountryManager countryManager;

  @Inject
  public CountryConverter(CountryManager countryManager) {
    this.countryManager = countryManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      return countryManager.getCountriesList(values);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<Country> countryArray = (List<Country>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (Country c : countryArray) {
      temp.add(c.getId() + "");
    }
    return temp.toString();
  }


}
