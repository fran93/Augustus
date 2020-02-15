package com.fran.augustus.services;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Log4j2
public class HeroeService {

  @Autowired @Lazy
  FirefoxClient firefox;

  @Autowired @Lazy
  MessageSource messageSource;

  public void goIntoAnAdventure() {
    try {
      WebElement adventureLink = firefox.get().findElement(By.className("adventureLink"));
      if (adventureLink.findElements(By.className("disabled")).isEmpty()) {
        adventureLink.click();

        WebElement animate = firefox.get().findElement(By.className("animate"));
        if (!animate.getAttribute("class").contains("disabled")) {
          firefox.jsClick(animate);
          log.info(messageSource.getMessage("heroe.adventure", new Object[]{}, Locale.ENGLISH));
        }

        if (!firefox.get().findElements(By.className("closeWindow")).isEmpty()) {
          firefox.get().findElement(By.className("closeWindow")).click();
        }
      }
    } catch (ElementClickInterceptedException ex) {
      log.info("goIntoAnAdventure: " + ex.getMessage());
    }
  }
}
