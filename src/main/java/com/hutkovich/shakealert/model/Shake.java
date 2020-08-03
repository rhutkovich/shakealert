package com.hutkovich.shakealert.model;

import org.joda.time.DateTime;

public class Shake {
  private DateTime dateTime;
  private Float latitude;
  private Float longitude;
  private Float depth;

  // class depending on the amplitude of S-wave, defined by Fedotov S.A.
  private Float amplitudeClass;
  private Float magnitude;

  public DateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(DateTime dateTime) {
    this.dateTime = dateTime;
  }

  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }

  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public Float getDepth() {
    return depth;
  }

  public void setDepth(Float depth) {
    this.depth = depth;
  }

  public Float getAmplitudeClass() {
    return amplitudeClass;
  }

  public void setAmplitudeClass(Float amplitudeClass) {
    this.amplitudeClass = amplitudeClass;
  }

  public Float getMagnitude() {
    return magnitude;
  }

  public void setMagnitude(Float magnitude) {
    this.magnitude = magnitude;
  }

  @Override
  public String toString() {
    return "Shake{" +
        "dateTime=" + dateTime +
        ", latitude='" + latitude + '\'' +
        ", longitude='" + longitude + '\'' +
        ", depth='" + depth + '\'' +
        ", amplitudeClass='" + amplitudeClass + '\'' +
        ", magnitude='" + magnitude + '\'' +
        '}';
  }

  @Override
  public int hashCode() {
    return dateTime.hashCode();
  }

  @Override
  // TODO implement more appropriately
  public boolean equals(Object obj) {
    return this.getDateTime().equals(((Shake) obj).getDateTime());
  }
}
