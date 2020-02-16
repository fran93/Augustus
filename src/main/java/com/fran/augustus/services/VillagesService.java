package com.fran.augustus.services;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class VillagesService {

  @Autowired @Lazy
  FirefoxClient firefox;

  private String lastVillageName = "";


  public boolean nextVillage() {
    boolean nextVillage = true;
    firefox.get().findElement(By.className("next")).click();

    String currentVillageName = firefox.get()
        .findElement(By.className("currentVillageName"))
        .findElement(By.className("villageEntry"))
        .getAttribute("innerText");

    if(lastVillageName.isEmpty()) {
      lastVillageName = currentVillageName;
    } else if(lastVillageName.equals(currentVillageName)) {
      lastVillageName = "";
      nextVillage = false;
    }

     return nextVillage;
  }
}
