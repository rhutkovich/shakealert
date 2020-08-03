package com.hutkovich.shakealert.main;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.hutkovich.shakealert.logic.messaging.api.IMessenger;
import com.hutkovich.shakealert.logic.messaging.common.Message;
import com.hutkovich.shakealert.logic.messaging.impl.ShakeAlertBot;
import com.hutkovich.shakealert.logic.parser.api.IParser;
import com.hutkovich.shakealert.logic.parser.impl.GroupPageParser;
import com.hutkovich.shakealert.logic.parser.impl.SSDPageParser;
import com.hutkovich.shakealert.model.Shake;
import org.joda.time.DateTime;

import java.util.*;

import static com.hutkovich.shakealert.util.Constant.*;
import static com.hutkovich.shakealert.util.PropertiesHelper.getInstance;

public class Main {
  private static final Long INIT_DELAY = 1000L;
  private static final Long REPEAT_TIME = 60 * 60 * 1000L;

  public static void main(String[] args) {
    IMessenger messenger = new ShakeAlertBot();
    IParser<HtmlPage> groupParser = new GroupPageParser();
    IParser<HtmlPage> ssdParser = new SSDPageParser();
    Set<Shake> allShakes = new TreeSet<>(Comparator.comparing(Shake::getDateTime).reversed());

    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        Set<Shake> currentShakes =
            new TreeSet<>(Comparator.comparing(Shake::getDateTime).reversed());
        WebClient webClient = null;
        try {
          webClient = new WebClient();
          /*HtmlPage avachaPage = webClient.getPage(getInstance().getProperty(AVACHA_GROUP));
          webClient.waitForBackgroundJavaScript(5000);
          currentShakes.addAll(groupParser.parse(avachaPage));

          HtmlPage northenPage = webClient.getPage(getInstance().getProperty(NORTHEN_GROUP));
          webClient.waitForBackgroundJavaScript(5000);
          currentShakes.addAll(groupParser.parse(northenPage));*/

          HtmlPage ssdPage = webClient.getPage(getInstance().getProperty(SSD_URL));
          webClient.waitForBackgroundJavaScript(5000);
          currentShakes.addAll(ssdParser.parse(ssdPage));

          currentShakes.removeAll(allShakes);
          allShakes.addAll(currentShakes);
          currentShakes.stream()
              .filter(shake -> shake.getMagnitude() > 0.f)
              .filter(shake -> shake.getDateTime().isAfter(DateTime.now().minusHours(24)))
              .forEach(shake -> messenger.send(new Message<>(null, shake)));
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          if (webClient != null) {
            webClient.closeAllWindows();
          }
        }
      }
    }, INIT_DELAY, REPEAT_TIME);
  }
}
