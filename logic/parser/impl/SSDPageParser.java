package me.shakealert.logic.parser.impl;

import static me.shakealert.util.NumbersUtil.parseFloat;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import me.shakealert.logic.parser.api.IParser;
import me.shakealert.model.Shake;
import me.shakealert.util.DateUtil;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class SSDPageParser implements IParser<HtmlPage> {

  public List<Shake> parse(HtmlPage htmlPage) {
    List<Shake> shakes = new ArrayList<>();
    DomNodeList<DomElement> tables = htmlPage.getElementsByTagName("table");
    DomElement table = tables.get(0);
    DomElement tbody = table.getFirstElementChild();
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
    DateTime shakeDate = DateUtil.parseDate(columns.get(3).getTextContent().trim());
    shake.setDateTime(shakeDate);
    shake.setLatitude(parseFloat(columns.get(4).getTextContent()));
    shake.setLongitude(parseFloat(columns.get(5).getTextContent()));
    shake.setDepth(parseFloat(columns.get(7).getTextContent()));
    shake.setAmplitudeClass(parseFloat(columns.get(8).getTextContent()));
    shake.setMagnitude(parseFloat(columns.get(9).getTextContent()));
    return shake;
  }
}
