package com.fran.augustus.services;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

@Service
@Log4j2
public class ChiefService {

  @Autowired @Lazy
  LoginService loginService;

  @Autowired @Lazy
  PeasantService peasantService;

  @Autowired @Lazy
  CitizenService citizenService;

  @Autowired @Lazy
  MessageSource messageSource;

  @Autowired @Lazy
  HeroeService heroeService;

  @Autowired @Lazy
  MilitaryService militaryService;

  @Autowired @Lazy
  VillagesService villagesService;

  @Autowired @Lazy
  FirefoxClient firefox;

  public void command() throws Exception {
    if(automaticMode()) {
      loginService.login();
      if (loginService.isLogged()) {
        while (villagesService.nextVillage()) {
          closePopUp();
          peasantService.workOnFields();
          closePopUp();
          citizenService.buildOurCity();
          closePopUp();
          heroeService.goIntoAnAdventure();
          closePopUp();
          militaryService.trainingTroops();
          closePopUp();
        }
      }
      log.info(messageSource.getMessage("work.done", new Object[]{}, Locale.ENGLISH));
    } else {
      log.info(messageSource.getMessage("work.waiting", null, Locale.ENGLISH));
    }
  }

  private void closePopUp() {
    try {
      if (firefox.existsElement(By.className("closeWindow"))) {
        firefox.get().findElement(By.className("closeWindow")).click();
      }
      if (firefox.existsElement(By.className("closeWarning"))) {
        firefox.get().findElement(By.className("closeWarning")).click();
      }
    } catch(ElementClickInterceptedException | StaleElementReferenceException ex) {
      log.info("closePopUp: " + ex.getMessage());
    }
  }

  private boolean automaticMode() throws IOException {
    Path path = Paths.get("automaticMode");
    boolean automaticMode = true;

    if (Files.exists(path)) {
      automaticMode = Files.readAllLines(path).stream().findFirst().orElse("1").trim().equals("1");
    }

    return automaticMode;
  }
}
