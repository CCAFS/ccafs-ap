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
