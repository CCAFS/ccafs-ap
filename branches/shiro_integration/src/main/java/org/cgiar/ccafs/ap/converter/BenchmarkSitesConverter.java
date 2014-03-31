package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.BenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class BenchmarkSitesConverter extends StrutsTypeConverter {

  private BenchmarkSiteManager benchmarkSiteManager;

  @Inject
  public BenchmarkSitesConverter(BenchmarkSiteManager benchmarkSiteManager) {
    this.benchmarkSiteManager = benchmarkSiteManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      return benchmarkSiteManager.getBenchmarkSiteList(values);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<BenchmarkSite> benchmarkSiteArray = (List<BenchmarkSite>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (BenchmarkSite bs : benchmarkSiteArray) {
      temp.add(bs.getId() + "");
    }
    // TODO
    return temp.toString();
  }
}
