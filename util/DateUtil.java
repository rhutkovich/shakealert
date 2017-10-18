package me.shakealert.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.DateTimeParser;
import org.joda.time.format.ISODateTimeFormat;

public class DateUtil {
  private DateUtil() {}
  private static DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
      .append(new DateTimeFormatterBuilder()
              .append(ISODateTimeFormat.date())
              .appendLiteral(" ")
              .append(ISODateTimeFormat.hourMinuteSecond())
              .toPrinter(),
          new DateTimeParser[]{
              DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").getParser(),
              DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SS").getParser(),
              DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS").getParser(),
          }).toFormatter();

  public static DateTime parseDate(String dateString) {
    return DateTime.parse(dateString, dateTimeFormatter);
  }

  public static String printDate(DateTime dateTime) {
    return dateTimeFormatter.print(dateTime);
  }
}
