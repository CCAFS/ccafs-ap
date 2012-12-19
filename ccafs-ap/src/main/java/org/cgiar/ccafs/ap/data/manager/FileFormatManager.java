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
}
