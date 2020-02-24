package com.fran.augustus.services;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class VillagesService {

  @Autowired @Lazy
  FirefoxClient firefox;
  private static String PHALANX_VILLAGE = "[BLAU]";
  private static String HAMMER_VILLAGE = "[SCHWARZ]";
  private static String BAUER_VILLAGE = "[GRÜN]";
  private static String SPY_VILLAGE = "[BRAUN]";
  private static String ENGINEER_VILLAGE = "[ROT]";

  private String lastVillageName = "";
  String currentVillageName = "";

  public boolean nextVillage() {
    boolean nextVillage = true;
    try {
      firefox.get().findElement(By.className("next")).click();

      currentVillageName = firefox.get()
          .findElement(By.className("currentVillageName"))
          .findElement(By.className("villageEntry"))
          .getAttribute("innerText");

      if (lastVillageName.isEmpty()) {
        lastVillageName = currentVillageName;
      } else if (lastVillageName.equals(currentVillageName)) {
        lastVillageName = "";
        nextVillage = false;
      }
    } catch(ElementClickInterceptedException ex) {
      log.info("nextVillage: " + ex.getMessage());
      firefox.refresh();
      nextVillage = false;
    }

     return nextVillage;
  }

  public boolean isPhalanxVillage() {
    return currentVillageName.contains(PHALANX_VILLAGE);
  }

  public boolean isHammerVillage() {
    return currentVillageName.contains(HAMMER_VILLAGE);
  }

  public boolean isBauerVillage() {
    return currentVillageName.contains(BAUER_VILLAGE);
  }

  public boolean isSpyVillage() {
    return currentVillageName.contains(SPY_VILLAGE);
  }

  public boolean isEngineerVillage() {
    return currentVillageName.contains(ENGINEER_VILLAGE);
  }
}
