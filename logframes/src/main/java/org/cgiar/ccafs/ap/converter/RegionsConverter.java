package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.RegionManager;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class RegionsConverter extends StrutsTypeConverter {

  private RegionManager regionManager;

  @Inject
  public RegionsConverter(RegionManager regionManager) {
    this.regionManager = regionManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      return regionManager.getRegionList(values);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<Region> regionsArray = (List<Region>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (Region r : regionsArray) {
      temp.add(r.getId() + "");
    }
    // TODO
    return temp.toString();
  }
}
