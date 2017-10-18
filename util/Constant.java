package me.shakealert.util;

public enum Constant {

  TELEGRAM_BOT_TOKEN("telegram.token"),
  TELEGRAM_BOT_USERNAME("telegram.username"),
  SSD_URL("url.ssd"),
  AVACHA_GROUP("url.avacha"),
  NORTHEN_GROUP("url.northen");

  private String code;

  Constant(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}
