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

package org.cgiar.ccafs.ap.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class DualMap<K1, K2, V> {

  private final Map<K1, V> map1 = new HashMap<K1, V>();
  private final Map<K2, V> map2 = new HashMap<K2, V>();

  public V get(K1 key1, K2 key2) {
    if (map1.containsKey(key1) && map2.containsKey(key2)) {
      // As both maps contains the same element, we can return it of any of these.
      return map1.get(key1);
    }
    return null;
  }

  public Map<K1, V> getMap1() {
    return Collections.unmodifiableMap(map1);
  }

  public Map<K2, V> getMap2() {
    return Collections.unmodifiableMap(map2);
  }

  public void put(K1 key1, K2 key2, V value) {
    map1.put(key1, value);
    map2.put(key2, value);
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
