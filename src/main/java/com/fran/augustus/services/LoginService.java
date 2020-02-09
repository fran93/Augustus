package com.fran.augustus.services;

import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  @Value("${travian.email}")
  String email;

  @Value("${travian.password}")
  String password;

  @Value("${travian.url}")
  String url;

  @Autowired
  @Lazy
  FirefoxClient firefox;

  public void login() {

    if(!isLogged()) {
      firefox.get().get(url);
      firefox.get().findElement(By.id("loginButton")).click();
      firefox.get().switchTo().frame(firefox.get().findElement(By.className("mellon-iframe")));
      firefox.get().switchTo().frame(firefox.get().findElement(By.tagName("iframe")));
      firefox.get().findElement(By.name("email")).sendKeys(email);
      firefox.get().findElement(By.name("password")).sendKeys(password);
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
