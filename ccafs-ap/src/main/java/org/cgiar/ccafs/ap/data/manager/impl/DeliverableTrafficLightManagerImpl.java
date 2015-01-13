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

import org.cgiar.ccafs.ap.data.dao.DeliverableTrafficLightDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableTrafficLightManager;
import org.cgiar.ccafs.ap.data.model.DeliverableTrafficLight;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableTrafficLightManagerImpl implements DeliverableTrafficLightManager {

  private DeliverableTrafficLightDAO trafficLightDAO;

  @Inject
  public DeliverableTrafficLightManagerImpl(DeliverableTrafficLightDAO trafficLightDAO) {
    this.trafficLightDAO = trafficLightDAO;
  }

  @Override
  public DeliverableTrafficLight getDeliverableTrafficLight(int deliverableID) {
    DeliverableTrafficLight trafficLight = new DeliverableTrafficLight();
    Map<String, String> trafficLightData = trafficLightDAO.getTrafficLightData(deliverableID);

    if (trafficLightData.get("is_metadata_documented") != null) {
      trafficLight.setHaveCollectionTools(trafficLightData.get("have_collection_tools").equals("1"));
    }
    if (trafficLightData.get("have_collection_tools") != null) {
      trafficLight.setMetadataDocumented(trafficLightData.get("is_metadata_documented").equals("1"));
    }
    if (trafficLightData.get("is_quality_documented") != null) {
      trafficLight.setQualityDocumented(trafficLightData.get("is_quality_documented").equals("1"));
    }

    return trafficLight;
  }

  @Override
  public boolean saveDeliverableTrafficLight(DeliverableTrafficLight trafficLight, int deliverableID) {
    Map<String, Object> trafficLightData = new HashMap<>();

    trafficLightData.put("is_metadata_documented", trafficLight.isMetadataDocumented());
    trafficLightData.put("have_collection_tools", trafficLight.isHaveCollectionTools());
    trafficLightData.put("is_quality_documented", trafficLight.isQualityDocumented());
    trafficLightData.put("deliverable_id", deliverableID);

    return trafficLightDAO.saveDeliverableTrafficLight(trafficLightData);
  }

}
