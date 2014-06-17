/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IndicatorDAO;
import org.cgiar.ccafs.ap.data.manager.IndicatorManager;
import org.cgiar.ccafs.ap.data.model.Indicator;
import org.cgiar.ccafs.ap.data.model.IndicatorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IndicatorManagerImpl implements IndicatorManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(IndicatorManagerImpl.class);
  private IndicatorDAO indicatorDAO;

  @Inject
  public IndicatorManagerImpl(IndicatorDAO indicatorDAO) {
    this.indicatorDAO = indicatorDAO;
  }

  @Override
  public List<Indicator> getIndicatorList() {
    List<Indicator> indicators = new ArrayList<>();
    List<Map<String, String>> indicatorsDataList = indicatorDAO.getIndicatorsList();
    for (Map<String, String> indicatorData : indicatorsDataList) {
      Indicator ind = new Indicator();
      ind.setId(Integer.parseInt(indicatorData.get("id")));
      ind.setSerial(indicatorData.get("serial"));
      ind.setName(indicatorData.get("name"));
      ind.setDescription(indicatorData.get("description"));
      ind.setActive(indicatorData.get("is_active").equals("1"));

      // Indicator type object
      IndicatorType it = new IndicatorType();
      it.setId(Integer.parseInt(indicatorData.get("indicator_type_id")));
      it.setName(indicatorData.get("indicator_type_name"));

      ind.setType(it);
      indicators.add(ind);
    }

    return indicators;
  }

}