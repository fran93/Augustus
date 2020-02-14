package com.fran.augustus.services;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Log4j2
public class MilitaryService {

  @Autowired @Lazy
  FirefoxClient firefox;

  @Autowired
  @Lazy
  MessageSource messageSource;

  public void sendTroops() {
    try {
      firefox.mouseOver(firefox.get().findElement(By.className("troop")));
      firefox.get().findElement(By.className("troop")).click();
      firefox.get().findElement(By.id("optimizely_maintab_FarmList")).click();
      firefox.loading(By.className("farmListEntry"));
      WebElement farmList = firefox.get().findElement(By.className("farmListEntry"));
      firefox.loading(1);
      farmList.findElement(By.tagName("input")).click();
      if (!firefox.existsElement(By.className("troopsWarning"))) {
        firefox.get().findElement(By.className("startRaid")).click();
        log.info(messageSource.getMessage("military.send", new Object[]{}, Locale.ENGLISH));
      }

      if (firefox.existsElement(By.className("closeWindow"))) {
        firefox.get().findElement(By.className("closeWindow")).click();
      }
    } catch (ElementNotInteractableException | StaleElementReferenceException | InterruptedException ex) {
      log.info("sendTroops: " + ex.getMessage());
    }
  }
}
