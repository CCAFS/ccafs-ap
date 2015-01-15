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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DeliverableFileDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableFileManager;
import org.cgiar.ccafs.ap.data.model.DeliverableFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableFileManagerImpl implements DeliverableFileManager {

  private DeliverableFileDAO deliverableFileDAO;

  @Inject
  public DeliverableFileManagerImpl(DeliverableFileDAO deliverableFileDAO) {
    this.deliverableFileDAO = deliverableFileDAO;
  }

  @Override
  public List<DeliverableFile> getDeliverableFiles(int deliverableID) {
    List<DeliverableFile> files = new ArrayList<>();
    List<Map<String, String>> filesData = deliverableFileDAO.getDeliverableFiles(deliverableID);

    for (Map<String, String> fileData : filesData) {
      DeliverableFile file = new DeliverableFile();
      file.setId(Integer.parseInt(fileData.get("id")));
      file.setLink(fileData.get("link"));
      file.setHosted(fileData.get("hosted"));
      file.setName(fileData.get("filename"));

      files.add(file);
    }

    return files;
  }

  @Override
  public int saveDeliverableFile(DeliverableFile file, int deliverableID) {
    int fileID = -1;

    Map<String, Object> fileData = new HashMap<String, Object>();
    if (file.getId() == -1) {
      fileData.put("id", null);
    } else {
      fileData.put("id", file.getId());
    }
    fileData.put("link", file.getLink());
    fileData.put("hosted", file.getHosted());
    fileData.put("filename", file.getName());
    fileData.put("deliverable_id", deliverableID);

    fileID = deliverableFileDAO.saveDeliverableFile(fileData);

    return fileID;
  }

  @Override
  public boolean saveDeliverableFiles(List<DeliverableFile> files, int deliverableID) {
    boolean saved = true;

    for (DeliverableFile file : files) {
      Map<String, Object> fileData = new HashMap<String, Object>();
      if (file.getId() == -1) {
        fileData.put("id", null);
      } else {
        fileData.put("id", file.getId());
      }
      fileData.put("link", file.getLink());
      fileData.put("hosted", file.getHosted());
      fileData.put("filename", file.getName());
      fileData.put("deliverable_id", deliverableID);

      int fileID = deliverableFileDAO.saveDeliverableFile(fileData);
      saved = saved && (fileID >= 0);
    }

    return saved;
  }
}
