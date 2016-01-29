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


package org.cgiar.ccafs.ap.util;

import java.util.List;

import org.dom4j.Element;
import org.json.JSONObject;
import se.kb.oai.pmh.OaiPmhServer;
import se.kb.oai.pmh.Record;

/**
 * @author Christian David Garcia Oviedo
 */
public class ClientRepository {


  public JSONObject getMetadata(String linkRequest, String id) {


    JSONObject jo = new JSONObject();

    try {

      OaiPmhServer server = new OaiPmhServer(linkRequest);
      Record record = server.getRecord(id, "oai_dc");
      Element metadata = record.getMetadata();

      List<Element> elements = metadata.elements();
      for (Element element : elements) {

        jo.put(element.getQName().getName(), element.getStringValue());

      }


    } catch (Exception e) {
      jo = null;
      e.printStackTrace();
    }

    return jo;
  }


}
