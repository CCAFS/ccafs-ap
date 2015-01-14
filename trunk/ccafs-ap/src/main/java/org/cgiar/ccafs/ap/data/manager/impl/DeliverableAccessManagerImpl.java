/*****************************************************************
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
 *****************************************************************/

package org.cgiar.ccafs.ap.data.manager.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.cgiar.ccafs.ap.data.dao.DeliverableAccessDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableAccessManager;
import org.cgiar.ccafs.ap.data.model.DeliverableAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableAccessManagerImpl implements DeliverableAccessManager {

  private DeliverableAccessDAO deliverableAccessDAO;
  public static Logger LOG = LoggerFactory.getLogger(DeliverableAccessManagerImpl.class);

  @Inject
  public DeliverableAccessManagerImpl(DeliverableAccessDAO deliverableAccessDAO) {
    this.deliverableAccessDAO = deliverableAccessDAO;
  }

  @Override
  public DeliverableAccess getDeliverableAccessData(int deliverableID) {
    DeliverableAccess deliverableAccess = new DeliverableAccess();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    Map<String, String> deliverableAccessData = deliverableAccessDAO.getDeliverableAccessData(deliverableID);


    if (deliverableAccessData.get("data_dictionary") != null) {
      deliverableAccess.setDataDictionary(deliverableAccessData.get("data_dictionary").equals("1"));
    } else {
      deliverableAccess.setDataDictionary(false);
    }

    deliverableAccess.setQualityProcedures(deliverableAccessData.get("quality_procedures"));
    deliverableAccess.setAccessRestrictions(deliverableAccessData.get("access_restrictions"));
    deliverableAccess.setAccessLimits(deliverableAccessData.get("access_limits"));

    try {
      if (deliverableAccessData.get("access_limit_start_date") != null) {
        deliverableAccess
          .setAccessLimitStartDate(dateFormat.parse(deliverableAccessData.get("access_limit_start_date")));
      }

      if (deliverableAccessData.get("access_limit_end_date") != null) {
        deliverableAccess.setAccessLimitEndDate(dateFormat.parse(deliverableAccessData.get("access_limit_end_date")));
      }
    } catch (ParseException e) {
      LOG.error("-- getDeliverableAccessData() > There was an exception trying to parse the access limit dates.", e);
    }


    if (deliverableAccessData.get("harvesting_protocols") != null) {
      deliverableAccess.setHarvestingProtocols(deliverableAccessData.get("harvesting_protocols").equals("1"));
    } else {
      deliverableAccess.setHarvestingProtocols(false);
    }

    deliverableAccess.setHarvestingProtocolDetails(deliverableAccessData.get("harvesting_protocol_details"));

    return deliverableAccess;
  }

  @Override
  public boolean saveDeliverableAccessData(DeliverableAccess deliverableAccess, int deliverableID) {
    boolean saved = false;
    Map<String, Object> values = new HashMap<String, Object>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    values.put("data_dictionary", deliverableAccess.isDataDictionary());
    values.put("quality_procedures", deliverableAccess.getQualityProcedures());
    values.put("access_restrictions", deliverableAccess.getAccessRestrictions());
    values.put("access_limits", deliverableAccess.getAccessLimits());

    if (deliverableAccess.getAccessLimitStartDate() != null) {
      values.put("access_limit_start_date", dateFormat.format(deliverableAccess.getAccessLimitStartDate()));
    } else {
      values.put("access_limit_start_date", null);
    }

    if (deliverableAccess.getAccessLimitEndDate() != null) {
      values.put("access_limit_end_date", dateFormat.format(deliverableAccess.getAccessLimitEndDate()));
    } else {
      values.put("access_limit_end_date", null);
    }

    values.put("harvesting_protocols", deliverableAccess.isHarvestingProtocols());
    values.put("harvesting_protocol_details", deliverableAccess.getHarvestingProtocolDetails());
    values.put("deliverable_id", deliverableID);

    return deliverableAccessDAO.saveDeliverableAccessData(values);
  }
}
