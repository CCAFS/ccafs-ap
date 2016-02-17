package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IndicatorReportDAO;
import org.cgiar.ccafs.ap.data.manager.IndicatorReportManager;
import org.cgiar.ccafs.ap.data.model.Indicator;
import org.cgiar.ccafs.ap.data.model.IndicatorReport;
import org.cgiar.ccafs.ap.data.model.IndicatorType;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IndicatorReportManagerImpl implements IndicatorReportManager {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(IndicatorReportManagerImpl.class);

  private IndicatorReportDAO indicatorReportDAO;

  @Inject
  public IndicatorReportManagerImpl(IndicatorReportDAO indicatorReportDAO) {
    this.indicatorReportDAO = indicatorReportDAO;
  }

  @Override
  public List<IndicatorReport> getIndicatorReportsList(int leader, int year) {
    List<IndicatorReport> indicatorReports = new ArrayList<>();
    List<Map<String, String>> irDataList = indicatorReportDAO.getIndicatorReports(leader, year);

    for (Map<String, String> irData : irDataList) {
      IndicatorReport ir = new IndicatorReport();

      if (irData.get("id") != null) {
        ir.setId(Integer.parseInt(irData.get("id")));
      } else {
        ir.setId(-1);
      }

      ir.setTarget(irData.get("target"));
      ir.setNextYearTarget(irData.get("next_target"));
      ir.setActual(irData.get("actual"));

      ir.setYear(year);
      ir.setDeviation(irData.get("deviation"));
      ir.setSupportLinks(irData.get("support_links"));

      Indicator ind = new Indicator();
      ind.setId(Integer.parseInt(irData.get("indicator_id")));
      ind.setSerial(irData.get("indicator_serial"));
      ind.setName(irData.get("indicator_name"));
      ind.setDescription(irData.get("indicator_description"));
      ind.setActive(irData.get("indicator_active").equals("1"));

      // Indicator type object
      IndicatorType it = new IndicatorType();
      it.setId(Integer.parseInt(irData.get("indicator_type_id")));
      it.setName(irData.get("indicator_type_name"));

      ind.setType(it);

      ir.setIndicator(ind);
      indicatorReports.add(ir);
    }

    return indicatorReports;
  }

  @Override
  public boolean saveIndicatorReportsList(List<IndicatorReport> indicatorReports, LiaisonInstitution leader) {
    boolean saved = true;
    Map<String, String> indicatorReportData;
    for (IndicatorReport ir : indicatorReports) {


      indicatorReportData = new HashMap<String, String>();
      if (ir.getId() != -1) {
        indicatorReportData.put("id", String.valueOf(ir.getId()));
      } else {
        indicatorReportData.put("id", null);
      }
      indicatorReportData.put("target", ir.getTarget());
      indicatorReportData.put("next_target", ir.getNextYearTarget());
      indicatorReportData.put("actual", ir.getActual());
      indicatorReportData.put("support_links", ir.getSupportLinks());
      indicatorReportData.put("deviation", ir.getDeviation());
      indicatorReportData.put("indicator_id", String.valueOf(ir.getIndicator().getId()));

      // This function return true if all the information was saved successfully.
      saved = indicatorReportDAO.saveIndicatorReport(indicatorReportData, leader.getId(), ir.getYear());
    }

    return saved;
  }
}
