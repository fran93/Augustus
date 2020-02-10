package com.fran.augustus.services;

import com.fran.augustus.properties.TravianProperties;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  @Autowired
  @Lazy
  TravianProperties travianProperties;

  @Autowired
  @Lazy
  FirefoxClient firefox;

  public void login() {

    if(!isLogged()) {
      firefox.get().get(travianProperties.getUrl());
      firefox.get().findElement(By.id("loginButton")).click();
      firefox.get().switchTo().frame(firefox.get().findElement(By.className("mellon-iframe")));
      firefox.get().switchTo().frame(firefox.get().findElement(By.tagName("iframe")));
      firefox.get().findElement(By.name("email")).sendKeys(travianProperties.getEmail());
      firefox.get().findElement(By.name("password")).sendKeys(travianProperties.getPassword());
      firefox.get().findElement(By.name("submit")).click();
      firefox.get().switchTo().defaultContent();
      firefox.loading(By.className("last-active-game-world"));
      firefox.get().findElement(By.className("last-active-game-world")).findElement(By.tagName("button")).click();
    }
  }

  public boolean isLogged() {
    return firefox.get().getCurrentUrl().startsWith("https://com");
  }

}
