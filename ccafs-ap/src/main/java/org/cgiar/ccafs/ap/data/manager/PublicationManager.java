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

import org.cgiar.ccafs.ap.data.manager.impl.PublicationManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Publication;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(PublicationManagerImpl.class)
public interface PublicationManager {

  /**
   * Get a list of publications that belong to a specific leader in a certain logframe.
   * 
   * @param leader - Leader that owns the publication list.
   * @param logframe - Logframe in which the publications were added.
   * @return An Array of Publication objects.
   */
  public List<Publication> getPublications(Leader leader, Logframe logframe);

  /**
   * Remove all the publications that belong to a specific leader in a certain logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object
   * @return true if the remove was successfully made, false if any problem occur.
   */
  public boolean removeAllPublications(Leader leader, Logframe logframe);

  /**
   * Save a list of publications into the database.
   * 
   * @param publications - List of Publication objects.
   * @param logframe - Logframe object
   * @param leaer - Leader object.
   * @return true if all the publications were successfully saved, or false if any problem occur.
   */
  public boolean savePublications(List<Publication> publications, Logframe logframe, Leader leaer);

}
