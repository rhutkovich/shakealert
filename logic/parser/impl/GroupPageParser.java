package me.shakealert.logic.parser.impl;

import static me.shakealert.util.NumbersUtil.parseFloat;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import me.shakealert.logic.parser.api.IParser;
import me.shakealert.model.Shake;
import me.shakealert.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class GroupPageParser implements IParser<HtmlPage> {
  private static final String UNWANTED_SYMBOLS = "[^\\d\\-.:]+";
  private static final String DELIMITER = "@";

  public List<Shake> parse(HtmlPage page) {
    List<Shake> shakes = new ArrayList<>();
    DomElement selectElement = page.getElementByName("flist");
    DomNodeList<HtmlElement> options = selectElement.getElementsByTagName("option");
    for (int i = 1; i < options.size(); i++) {
      // mysterious lifehack with regex to normalize string, but it works!
      StringBuilder sb = new StringBuilder(options.get(i).getTextContent().trim().replaceAll(UNWANTED_SYMBOLS, DELIMITER));
      // fix site error when latitude value sticks to time
      if (!DELIMITER.equals(String.valueOf(sb.charAt(23)))) {
        sb.insert(23, DELIMITER);
      }
      String[] shakeParts = sb.toString().split(DELIMITER);

      Shake shake = new Shake();
      shake.setDateTime(DateUtil.parseDate(shakeParts[0] + " " + shakeParts[1]));
      shake.setLatitude(parseFloat(shakeParts[2]));
      shake.setLongitude(parseFloat(shakeParts[3]));
      shake.setDepth(parseFloat(shakeParts[4]));
      shake.setAmplitudeClass(parseFloat(shakeParts[5]));
      shake.setMagnitude(parseFloat(shakeParts[6]));
      shakes.add(shake);
    }
    return shakes;
  }
}
