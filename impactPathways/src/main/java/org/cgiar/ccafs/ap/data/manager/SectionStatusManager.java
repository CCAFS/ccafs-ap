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

import org.cgiar.ccafs.ap.data.manager.impl.SectionStatusManagerImpl;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.SectionStatus;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(SectionStatusManagerImpl.class)
public interface SectionStatusManager {

  /**
   * This method gets the section status of a given deliverable in a given cycle.
   * 
   * @param deliverable is the deliverable related to the status.
   * @param cycle is the project cycle (Planning or Reporting).
   * @param section is the name of a section.
   * @return SectionStatus object with all the information encapsulated on it.
   */
  public SectionStatus getSectionStatus(Deliverable deliverable, String cycle, String section);

  /**
   * This method gets the section status of a given project in a given cycle.
   * 
   * @param project is the project related to the status.
   * @param cycle is the project cycle (Planning or Reporting).
   * @param section is the name of a section.
   * @return SectionStatus object with all the information encapsulated on it.
   */
  public SectionStatus getSectionStatus(Project project, String cycle, String section);

  /**
   * This method gets all the statuses of a project for a given a specific cycle.
   * 
   * @param project is some project.
   * @param cycle could be 'Planning' or 'Reporting'.
   * @return a List if SectionStatus objects with the information requested.
   */
  public List<SectionStatus> getSectionStatuses(Project project, String cycle);

  /**
   * This method saves into the database the current section status with regards the missing fields.
   * 
   * @param status - corresponds to a given project status
   * @param project - is the project where the status will be related to.
   * @return a number greater than 0 meaning the identifier of the new record that was added, 0 if the information was
   *         updated, or -1 if some error occurred.
   */
  public int saveSectionStatus(SectionStatus status, Project project);

  /**
   * This method saves into the database the current section status with regards the missing fields of a deliverable.
   * 
   * @param status - corresponds to a given project status
   * @param project - is the project where the deliverable belongs to.
   * @param deliverable - is the deliverable where the status will be related t.
   * @return a number greater than 0 meaning the identifier of the new record that was added, 0 if the information was
   *         updated, or -1 if some error occurred.
   */
  public int saveSectionStatus(SectionStatus status, Project project, Deliverable deliverable);
}
