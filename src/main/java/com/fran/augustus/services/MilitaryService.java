package com.fran.augustus.services;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
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
      firefox.get().findElement(By.className("troop")).click();
      firefox.get().findElement(By.id("optimizely_maintab_FarmList")).click();
      firefox.get().findElement(By.className("farmListEntry")).findElement(By.tagName("input")).click();
      if (firefox.get().findElements(By.className("troopsWarning")).isEmpty()) {
        firefox.get().findElement(By.className("startRaid")).click();
        log.info(messageSource.getMessage("military.send", new Object[]{}, Locale.ENGLISH));
      }

      if (!firefox.get().findElements(By.className("closeWindow")).isEmpty()) {
        firefox.get().findElement(By.className("closeWindow")).click();
      }
    } catch (ElementNotInteractableException | StaleElementReferenceException ex) {
      log.info("sendTroops: " + ex.getMessage());
    }
  }
}
