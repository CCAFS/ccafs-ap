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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.MilestoneReportManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.cgiar.ccafs.ap.data.model.MilestoneReport;
import org.cgiar.ccafs.ap.data.model.Theme;
import org.cgiar.ccafs.ap.data.model.User.UserRole;

import com.google.inject.ImplementedBy;

@ImplementedBy(MilestoneReportManagerImpl.class)
public interface MilestoneReportManager {

  /**
   * Get a list of milestone reports that belongs to the activity leader and are
   * related to the logframe
   * 
   * @param activityLeaderId activity leader identifier
   * @param logframeId logframe identifier
   * @return A list of milestonReport objects
   */
  public MilestoneReport[] getMilestoneReports(Leader activityLeader, Logframe logframe, UserRole role);

  /**
   * Get a list of milestone report according to the parameters given.
   * 
   * @param logframe
   * @param theme
   * @param milestone
   * @return a list of Milestone report objects
   */
  public MilestoneReport[] getMilestoneReportsForSumamry(Logframe logframe, Theme theme, Milestone milestone);

  /**
   * Save into the DAO the milestone report information
   * 
   * @param milestoneReports list of milestoneReports objects
   * @return true if the process was successful. False otherwise
   */
  public boolean saveMilestoneReports(MilestoneReport[] milestoneReports);
}
