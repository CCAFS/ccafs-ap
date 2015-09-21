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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.SubmissionManagerImpl;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.Submission;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

@ImplementedBy(SubmissionManagerImpl.class)
public interface SubmissionManager {

  /**
   * This method returns a list of submissions made to a specific project.
   * 
   * @param project is some existing project.
   * @return a list of Submission objects, an empty list if nothing found or null if some error occurred.
   */
  public List<Submission> getProjectSubmissions(Project project);

  /**
   * This method saves a specific submission to the given project.
   * 
   * @param project is some existing project where it is going to be saved the submission.
   * @param submission is the submission to be saved.
   * @return true if the submission was successfully saved, or false otherwise.
   */
  public boolean saveProjectSubmission(Project project, Submission submission);
}
