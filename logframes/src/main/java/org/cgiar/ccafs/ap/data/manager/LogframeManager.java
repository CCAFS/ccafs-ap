package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.LogframeManagerImpl;
import org.cgiar.ccafs.ap.data.model.Logframe;

import com.google.inject.ImplementedBy;

@ImplementedBy(LogframeManagerImpl.class)
public interface LogframeManager {

  /**
   * Get a logframe object based on the given id.
   * 
   * @param id - identifier.
   * @return a Logframe object.
   */
  public Logframe getLogframe(int id);

  /**
   * Get a logframe object based on the given year or null if no exists logframe for the given year.
   * 
   * @param year of the logframe.
   * @return a Logframe object.
   */
  public Logframe getLogframeByYear(int year);
}
