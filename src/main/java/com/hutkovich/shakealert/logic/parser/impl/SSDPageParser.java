package com.hutkovich.shakealert.logic.parser.impl;

import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hutkovich.shakealert.logic.parser.api.IParser;
import com.hutkovich.shakealert.model.Shake;
import com.hutkovich.shakealert.util.DateUtil;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.hutkovich.shakealert.util.NumbersUtil.parseFloat;

public class SSDPageParser implements IParser<HtmlPage> {

  public List<Shake> parse(HtmlPage htmlPage) {
    List<Shake> shakes = new ArrayList<>();
    DomNodeList<HtmlElement> tables = htmlPage.getElementsByTagName("table");
    HtmlElement table = tables.get(1);
    HtmlElement tbody = table.getElementsByTagName("tbody").get(0);
    DomNodeList<HtmlElement> rows = tbody.getElementsByTagName("tr");
    // skip first table header row
    for (int i = 1; i < rows.size(); i++) {
      DomNodeList<HtmlElement> columns = rows.get(i).getElementsByTagName("td");
      shakes.add(createShake(columns));
    }
    return shakes;
  }

  private Shake createShake(DomNodeList<HtmlElement> columns) {
    Shake shake = new Shake();
    DateTime shakeDate = DateUtil.parseDate(columns.get(2).getTextContent().trim());
    shake.setDateTime(shakeDate);
    shake.setLatitude(parseFloat(columns.get(4).getTextContent()));
    shake.setLongitude(parseFloat(columns.get(5).getTextContent()));
    shake.setDepth(parseFloat(columns.get(7).getTextContent()));
    shake.setAmplitudeClass(parseFloat(columns.get(8).getTextContent()));
    shake.setMagnitude(parseFloat(columns.get(9).getTextContent()));
    return shake;
  }
}
