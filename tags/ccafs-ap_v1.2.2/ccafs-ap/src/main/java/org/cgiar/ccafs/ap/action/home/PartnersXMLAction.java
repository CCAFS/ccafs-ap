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

package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersXMLAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersXMLAction.class);
  private static final long serialVersionUID = 6086474589587634639L;

  // Managers
  private PartnerManager partnerManager;
  private ThemeManager themeManager;

  // Models
  private Partner[] partners;
  private Map<Integer, Theme[]> themesByPartner;

  @Inject
  public PartnersXMLAction(APConfig config, LogframeManager logframeManager, PartnerManager partnerManager,
    ThemeManager themeManager) {
    super(config, logframeManager);
    this.partnerManager = partnerManager;
    this.themeManager = themeManager;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  public Partner[] getPartners() {
    return partners;
  }

  public Map<Integer, Theme[]> getThemesByPartner() {
    return themesByPartner;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    LOG.info("The Partner XML file with partner list is being generated.");
    partners = partnerManager.getPartnerForXML();

    if (partners == null) {
      partners = new Partner[0];
    } else {
      themesByPartner = new HashMap<Integer, Theme[]>();
      // For each partner get the list of themes in which the partner works
      for (Partner partner : partners) {
        Theme[] themes = themeManager.getThemesByPartner(partner);
        if (themes != null) {
          themesByPartner.put(partner.getId(), themes);
        }
      }
    }
  }
}