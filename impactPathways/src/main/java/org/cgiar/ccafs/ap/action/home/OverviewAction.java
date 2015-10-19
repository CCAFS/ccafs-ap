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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This action will be in charge of show all the P&R facts that can be stand out.
 * 
 * @author HÃ©ctor Fabio TobÃ³n R.
 * @author Javier AndrÃ©s Gallego B.
 */
public class OverviewAction extends BaseAction {

  private static final long serialVersionUID = -8002068803922618439L;

  // Logging
  private static final Logger LOG = LoggerFactory.getLogger(OverviewAction.class);

  // Manager
  private DeliverableManager deliverableManager;

  // Model for the front end
  private List<Map<String, String>> deliverablesCountByType;
  private List<Map<String, String>> expectedDeliverablesCountByYear;
  private String jsonDeliverable = new String();
  private String jsonexpectedDeliverable = new String();


  @Inject
  public OverviewAction(APConfig config, DeliverableManager deliverableManager) {
    super(config);
    this.deliverableManager = deliverableManager;
  }


  public List<Map<String, String>> getDeliverablesCountByType() {
    return deliverablesCountByType;
  }


  public List<Map<String, String>> getExpectedDeliverablesCountByYear() {
    return expectedDeliverablesCountByYear;
  }


  public String getJsonDeliverable() {
    return jsonDeliverable;
  }


  public String getJsonexpectedDeliverable() {
    return jsonexpectedDeliverable;
  }

  public String listmap_to_json_string(List<Map<String, String>> list) {
    JSONArray json_arr = new JSONArray();
    for (Map<String, String> map : list) {
      JSONObject json_obj = new JSONObject();
      for (Map.Entry<String, String> entry : map.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        try {
          json_obj.put(key, value);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      json_arr.put(json_obj);
    }
    return json_arr.toString();
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    expectedDeliverablesCountByYear = deliverableManager.getExpectedDeliverablesCountByYear();
    deliverablesCountByType = deliverableManager.getDeliverablesCountByType();
    jsonDeliverable = listmap_to_json_string(deliverablesCountByType);
    jsonexpectedDeliverable = listmap_to_json_string(expectedDeliverablesCountByYear);

  }


  public void setDeliverablesCountByType(List<Map<String, String>> deliverablesCountByType) {
    this.deliverablesCountByType = deliverablesCountByType;
  }


  public void setExpectedDeliverablesCountByYear(List<Map<String, String>> expectedDeliverablesCountByYear) {
    this.expectedDeliverablesCountByYear = expectedDeliverablesCountByYear;
  }

  public void setJsonDeliverable(String jsonDeliverable) {
    this.jsonDeliverable = jsonDeliverable;
  }

  public void setJsonexpectedDeliverable(String jsonexpectedDeliverable) {
    this.jsonexpectedDeliverable = jsonexpectedDeliverable;
  }


}
