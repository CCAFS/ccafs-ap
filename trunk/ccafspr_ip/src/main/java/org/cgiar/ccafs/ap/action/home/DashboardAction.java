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
import org.cgiar.ccafs.ap.config.APConfig;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This action will be in charge of managing all the P&R Dashboard after the user logins.
 *
 * @author Héctor Fabio Tobón R.
 */
public class DashboardAction extends BaseAction {

  private static final long serialVersionUID = -8002068803922618439L;

  // Logging
  private static final Logger LOG = LoggerFactory.getLogger(DashboardAction.class);


  @Inject
  public DashboardAction(APConfig config) {
    super(config);
  }

  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    System.out.println("PREPARE: ");
    System.out.println("CURRENT USER" + this.getCurrentUser());
  }


}
