package com.fran.augustus.services;

import com.fran.augustus.properties.TravianProperties;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.TimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LoginService {

  @Autowired
  @Lazy
  TravianProperties travianProperties;

  @Autowired
  @Lazy
  FirefoxClient firefox;

  public void login() {

    if(!isLogged()) {
      try {
        firefox.get().get(travianProperties.getUrl());
        if(!isInLobby()) {
          firefox.get().findElement(By.id("loginButton")).click();
          firefox.get().switchTo().frame(firefox.get().findElement(By.className("mellon-iframe")));
          firefox.get().switchTo().frame(firefox.get().findElement(By.tagName("iframe")));
          firefox.get().findElement(By.name("email")).sendKeys(travianProperties.getEmail());
          firefox.get().findElement(By.name("password")).sendKeys(travianProperties.getPassword());
          firefox.get().findElement(By.name("submit")).click();
          firefox.loading(1);
        }
        firefox.get().switchTo().defaultContent();
        firefox.loading(By.className("last-active-game-world"));
        firefox.get().findElement(By.className("last-active-game-world")).findElement(By.tagName("button")).click();
        firefox.loading(5);
      } catch(ElementNotInteractableException | TimeoutException | InterruptedException ex) {
        log.info("login: " + ex.getMessage());
      }
    }
  }

  public boolean isLogged() {
    return firefox.get().getCurrentUrl().startsWith("https://com");
  }

  public boolean isInLobby() {
    return firefox.get().getCurrentUrl().startsWith("https://lobby");
  }

}
