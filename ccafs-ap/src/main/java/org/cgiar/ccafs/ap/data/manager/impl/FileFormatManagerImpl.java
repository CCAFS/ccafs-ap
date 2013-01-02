package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.FileFormatDAO;
import org.cgiar.ccafs.ap.data.manager.FileFormatManager;
import org.cgiar.ccafs.ap.data.model.FileFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class FileFormatManagerImpl implements FileFormatManager {

  private FileFormatDAO fileFormatDAO;

  @Inject
  public FileFormatManagerImpl(FileFormatDAO fileFormatDAO) {
    this.fileFormatDAO = fileFormatDAO;
  }

  @Override
  public List<FileFormat> getFileFormat(String[] ids) {
    List<FileFormat> formats = new ArrayList<>();
    for (FileFormat fileFormat : getFileFormats()) {
      for (String id : ids) {
        if (Integer.parseInt(id) == fileFormat.getId()) {
          formats.add(fileFormat);
        }
      }
    }
    return formats;
  }

  @Override
  public FileFormat[] getFileFormats() {
    List<Map<String, String>> fileFormatsList = fileFormatDAO.getFileFormats();
    Map<String, String> fileFormatsData;

    FileFormat[] fileFormats = new FileFormat[fileFormatsList.size()];
    for (int c = 0; c < fileFormatsList.size(); c++) {
      fileFormatsData = fileFormatsList.get(c);
      fileFormats[c] = new FileFormat(Integer.parseInt(fileFormatsData.get("id")), fileFormatsData.get("name"));
    }

    if (fileFormatsList.size() > 0) {
      return fileFormats;
    }

    return null;
  }

  @Override
  public boolean setFileFormats(int deliverableId, List<FileFormat> fileFormats) {
    int[] fileFormatIds = new int[fileFormats.size()];
    for (int c = 0; c < fileFormats.size(); c++) {
      fileFormatIds[c] = fileFormats.get(c).getId();
    }

    return fileFormatDAO.setFileFormats(deliverableId, fileFormatIds);
  }
}
