package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.CountryManagerImpl;
import org.cgiar.ccafs.ap.data.model.Country;

import com.google.inject.ImplementedBy;

@ImplementedBy(CountryManagerImpl.class)
public interface CountryManager {

  /**
   * Get the countries list.
   * 
   * @return an array of Country objects or null if no data found
   */
  public Country[] getCountriesList();


  /**
   * Get the country object corresponding to the given id
   * 
   * @param id - The country identifier
   * @return An object with the country information or null if no data found
   */
  public Country getCountry(String id);
}
