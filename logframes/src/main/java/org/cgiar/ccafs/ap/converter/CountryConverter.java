package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.model.Country;

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
    if (toClass == Country.class) {
      return countryManager.getCountry(values[0]);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Country country = (Country) o;
    return country.getId() + "";
  }


}
