package com.hutkovich.shakealert.util;

public class NumbersUtil {
  private NumbersUtil() {
  }

  public static Float parseFloat(String floatString) {
    if (floatString == null || floatString.isEmpty()) {
      return null;
    }
    String normalizedString = floatString.replaceAll("[^\\d\\.]", "").trim();
    if (normalizedString.isEmpty()) {
      return null;
    } else {
      return Float.parseFloat(normalizedString.trim());
    }
  }
}
