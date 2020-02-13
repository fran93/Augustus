package com.fran.augustus.services;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MilitaryService {

  @Autowired @Lazy
  FirefoxClient firefox;

  public void sendTroops() {
    firefox.get().findElement(By.className("troop")).click();
    firefox.get().findElement(By.id("optimizely_maintab_FarmList")).click();
    firefox.get().findElement(By.className("farmListEntry")).findElement(By.tagName("input")).click();
    if (firefox.get().findElements(By.className("troopsWarning")).isEmpty()) {
      firefox.get().findElement(By.className("startRaid")).click();
    }

    if (!firefox.get().findElements(By.className("closeWindow")).isEmpty()) {
      firefox.get().findElement(By.className("closeWindow")).click();
    }
  }
}
