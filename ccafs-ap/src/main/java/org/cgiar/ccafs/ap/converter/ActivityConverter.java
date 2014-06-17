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

package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.model.Activity;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;

public class ActivityConverter extends StrutsTypeConverter {

  private ActivityManager activityManager;

  @Inject
  public ActivityConverter(ActivityManager activityManager) {
    this.activityManager = activityManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (Integer.parseInt(values[0]) != -1 && toClass == Activity.class) {
      Activity activity = activityManager.getActivity(Integer.parseInt(values[0]));
      return activity;
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    if (o instanceof Activity) {
      Activity activity = (Activity) o;
      return activity.toString();
    }
    return null;
  }
}