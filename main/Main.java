package me.shakealert.main;

import static me.shakealert.util.Constant.AVACHA_GROUP;
import static me.shakealert.util.Constant.NORTHEN_GROUP;
import static me.shakealert.util.Constant.SSD_URL;
import static me.shakealert.util.PropertiesHelper.getInstance;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import me.shakealert.logic.messaging.api.IMessenger;
import me.shakealert.logic.messaging.common.Message;
import me.shakealert.logic.messaging.impl.ShakeAlertBot;
import me.shakealert.logic.parser.api.IParser;
import me.shakealert.logic.parser.impl.GroupPageParser;
import me.shakealert.logic.parser.impl.SSDPageParser;
import me.shakealert.model.Shake;
import org.joda.time.DateTime;

import java.util.Comparator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

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
        try (WebClient webClient = new WebClient()) {
          HtmlPage avachaPage = webClient.getPage(getInstance().getProperty(AVACHA_GROUP));
          webClient.waitForBackgroundJavaScript(5000);
          currentShakes.addAll(groupParser.parse(avachaPage));

          HtmlPage northenPage = webClient.getPage(getInstance().getProperty(NORTHEN_GROUP));
          webClient.waitForBackgroundJavaScript(5000);
          currentShakes.addAll(groupParser.parse(northenPage));

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
        }
      }
    }, INIT_DELAY, REPEAT_TIME);
  }
}
