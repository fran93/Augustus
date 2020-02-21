package com.fran.augustus.services;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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

  public void command() throws InterruptedException {
    loginService.login();
    if(loginService.isLogged()) {
      while(villagesService.nextVillage()) {
        closePopUp();
        peasantService.workOnFields();
        citizenService.buildOurCity();
        closePopUp();
        heroeService.goIntoAnAdventure();
        closePopUp();
        militaryService.sendTroops();
        closePopUp();
        militaryService.trainingTroops();
        closePopUp();
      }
    }
    log.info(messageSource.getMessage("work.done", new Object[]{}, Locale.ENGLISH));
  }

  private void closePopUp() {
    if (firefox.existsElement(By.className("closeWindow"))) {
      firefox.get().findElement(By.className("closeWindow")).click();
    }
    if (firefox.existsElement(By.className("closeWarning"))) {
      firefox.get().findElement(By.className("closeWarning")).click();
    }
  }
}
