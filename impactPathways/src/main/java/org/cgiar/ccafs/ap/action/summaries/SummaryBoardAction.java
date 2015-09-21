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

package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.utils.APConfig;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the main board that will handle all the P&R summaries.
 * 
 * @author Héctor Fabio Tobón R - CIAT/CCAFS.
 */
public class SummaryBoardAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(SummaryBoardAction.class);
  private static final long serialVersionUID = 5110987672008315842L;

  @Inject
  public SummaryBoardAction(APConfig config) {
    super(config);
  }

  @Override
  public String execute() throws Exception {

    return INPUT;
  }


  @Override
  public void prepare() {
    // TODO
  }
}
