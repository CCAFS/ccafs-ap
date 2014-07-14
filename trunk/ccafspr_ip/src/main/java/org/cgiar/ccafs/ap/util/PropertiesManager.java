package org.cgiar.ccafs.ap.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class PropertiesManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PropertiesManager.class);
  private Properties properties;
  private static String PROPERTIES_FILE = "ccafsap.properties";

  public PropertiesManager() {
    properties = new Properties();
    try {
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(PROPERTIES_FILE));
    } catch (IOException e) {
      LOG.error("The indicated file has not been found, file needed: \"" + new File(PROPERTIES_FILE).getAbsolutePath()
        + "\"", e);
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
    for (int i = 0; i < str.length; i++) {
      array[i] = Float.parseFloat(str[i]);
    }

    return array;
  }

  public int getPropertiesAsInt(String name) {
    return Integer.parseInt(getPropertiesAsString(name));
  }

  public int[] getPropertiesAsIntArray(String name) {
    String[] str = getPropertiesAsString(name).split(";");
    int[] array = new int[str.length];
    for (int i = 0; i < str.length; i++) {
      array[i] = Integer.parseInt(str[i]);
    }

    return array;
  }

  public String getPropertiesAsString(String name) {
    return properties.getProperty(name);
  }

  public String[] getPropertiesAsStringArray(String name) {
    return getPropertiesAsString(name).split(";");
  }


}
