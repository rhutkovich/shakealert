package com.hutkovich.shakealert.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {
  private static final PropertiesHelper INSTANCE = new PropertiesHelper();

  private Properties properties;

  private PropertiesHelper() {
    properties = new Properties();
    try (InputStream is = PropertiesHelper.class.getClassLoader().getResourceAsStream("settings.properties")) {
      properties.load(is);
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public String getProperty(Constant constant) {
    return properties.getProperty(constant.getCode());
  }

  public static PropertiesHelper getInstance() {
    return INSTANCE;
  }
}
