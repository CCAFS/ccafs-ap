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
