package org.cgiar.ccafs.ap.data.manager;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.manager.impl.FileFormatManagerImpl;
import org.cgiar.ccafs.ap.data.model.FileFormat;

@ImplementedBy(FileFormatManagerImpl.class)
public interface FileFormatManager {


  /**
   * Get all the file formats
   * 
   * @return a List whit all the file formats.
   */
  public FileFormat[] getFileFormats();
}
