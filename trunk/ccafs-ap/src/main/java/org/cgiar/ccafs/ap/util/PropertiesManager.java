package org.cgiar.ccafs.ap.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.google.inject.Singleton;

@Singleton
public class PropertiesManager {

  private Properties properties;
  private static String PROPERTIES_FILE = "ccafsap.properties";

  public PropertiesManager() {
    properties = new Properties();
    try {
      properties.load(new FileInputStream(PROPERTIES_FILE));
    } catch (IOException e) {
      System.err.println("The indicated file has not been found, file needed: \""
        + new File(PROPERTIES_FILE).getAbsolutePath() + "\"");
      e.getLocalizedMessage();
      System.exit(-1);
    }
  }

  public boolean existProperty(String name) {
    return properties.get(name) != null;
  }

  public float getPropertiesAsFloat(String name) {
    return Float.parseFloat(getPropertiesAsString(name));
  }

  public float[] getPropertiesAsFloatArray(String name) {
    String[] str = getPropertiesAsString(name).split(";");
    float[] array = new float[str.length];
    for (int i = 0; i < str.length; i++)
      array[i] = Float.parseFloat(str[i]);

    return array;
  }

  public int getPropertiesAsInt(String name) {
    return Integer.parseInt(getPropertiesAsString(name));
  }

  public int[] getPropertiesAsIntArray(String name) {
    String[] str = getPropertiesAsString(name).split(";");
    int[] array = new int[str.length];
    for (int i = 0; i < str.length; i++)
      array[i] = Integer.parseInt(str[i]);

    return array;
  }

  public String getPropertiesAsString(String name) {
    return properties.getProperty(name);
  }

  public String[] getPropertiesAsStringArray(String name) {
    return getPropertiesAsString(name).split(";");
  }


}
