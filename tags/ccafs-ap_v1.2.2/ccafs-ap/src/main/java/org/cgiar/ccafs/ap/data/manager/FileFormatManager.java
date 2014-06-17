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

import org.cgiar.ccafs.ap.data.manager.impl.FileFormatManagerImpl;
import org.cgiar.ccafs.ap.data.model.FileFormat;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(FileFormatManagerImpl.class)
public interface FileFormatManager {


  /**
   * Get a list of file formats objects corresponding to the given array of ids
   * 
   * @param ids - Array of ids.
   * @return a List of FileFormat objects.
   */
  public List<FileFormat> getFileFormat(String[] ids);

  /**
   * Get all the file formats
   * 
   * @return a List whit all the file formats.
   */
  public FileFormat[] getFileFormats();

  /**
   * Add the given file format list to the deliverable identified with the given id.
   * 
   * @param deliverableId - deliverable id.
   * @param fileFormats - File format list.
   * @return true if all the file formats were successfully added, or false otherwise.
   */
  public boolean setFileFormats(int deliverableId, List<FileFormat> fileFormats);
}
