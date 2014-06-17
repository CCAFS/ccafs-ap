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

import org.cgiar.ccafs.ap.data.manager.impl.SubmissionManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Submission;

import com.google.inject.ImplementedBy;

@ImplementedBy(SubmissionManagerImpl.class)
public interface SubmissionManager {

  /**
   * Get the submission information related with the given data.
   * 
   * @param activityLeaderId - Leader identifier
   * @param logframeId - Logframe identifier
   * @param section
   * @return A submission object with the information if exists the submission. Null otherwise.
   */
  public Submission getSubmission(Leader leader, Logframe logframe, String section);

  /**
   * Make a submission relating the given leader, the given logframe and the section
   * indicated.
   * 
   * @param submission
   * @return
   */
  public boolean submit(Submission submission);
}
