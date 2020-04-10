package com.fran.augustus.services;

import com.fran.augustus.enums.TribesEnum;
import com.fran.augustus.enums.UnitEnum;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

  @Autowired
  @Lazy
  VillagesService villagesService;

  @Autowired
  @Lazy
  ResourceService resourceService;

  @Value( "${tribe}" )
  String tribe;

  public void sendTroops() {
    try {
      if(villagesService.isRaidingVillage()) {
        firefox.mouseOver(firefox.get().findElement(By.className("troop")));
        firefox.get().findElement(By.className("troop")).click();
        firefox.get().findElement(By.id("optimizely_maintab_FarmList")).click();
        if (firefox.existsElement(By.className("farmListEntry"))) {
          firefox.loading(By.className("farmListEntry"));
          WebElement farmList = firefox.get().findElement(By.className("farmListEntry"));
          firefox.loading(1);
          farmList.findElement(By.tagName("input")).click();
          if (!firefox.existsElement(By.className("troopsWarning")) &&
              !firefox.get().findElement(By.className("startRaid")).getAttribute("class").contains("disabled")) {

            firefox.get().findElement(By.className("startRaid")).click();
            log.info(messageSource.getMessage("military.send", new Object[]{}, Locale.ENGLISH));
          }
        }
      }
    } catch (ElementNotInteractableException | StaleElementReferenceException | InterruptedException ex) {
      log.info("sendTroops: " + ex.getMessage());
    }
  }

  public void trainingTroops() {
    String troopsRaw = firefox.getText(firefox.get().findElement(By.className("troop"))
        .findElement(By.className("value")));
    String popRaw = firefox.getText(firefox.get()
        .findElement(By.className("population"))
        .findElement(By.className("value")));

    double troopsCount = Integer.parseInt(troopsRaw);
    double popCount = Integer.parseInt(popRaw);
    double troopsRatio = popCount > 100 ? 1 : 0;
    troopsRatio *= villagesService.isHammerVillage() ? 2 : 1;
    Double desiredTroops = popCount * troopsRatio;

    if(troopsCount < desiredTroops) {
       if (villagesService.isAntiCavalryVillage()) {
         if(TribesEnum.GAULS.getValue().equals(tribe)) {
           trainingInfantry(UnitEnum.PHALANX);
         }
       } else if (villagesService.isAntiInfantryVillage()) {
         if(TribesEnum.GAULS.getValue().equals(tribe)) {
           trainingCavalry(UnitEnum.DRUIDRIDER);
         }
       } else if (villagesService.isHammerVillage()) {
         if(TribesEnum.GAULS.getValue().equals(tribe)) {
           trainingInfantry(UnitEnum.SWORDSMAN);
           trainingCavalry(UnitEnum.HAEDUAN);
           trainingMachinery(UnitEnum.TREBUCHET);
         }
       } else if (villagesService.isSpyVillage()) {
         if(TribesEnum.GAULS.getValue().equals(tribe)) {
           trainingCavalry(UnitEnum.PATHFINDER);
         }
       } else if(villagesService.isRaidingVillage()) {
         if(TribesEnum.GAULS.getValue().equals(tribe)) {
           trainingCavalry(UnitEnum.THEUTATES_THUNDER);
         }
       }
    }
  }

  private void trainingInfantry(UnitEnum unit) {
    try {
      if(isTrainingAvailable(19)) {
        firefox.get().findElements(By.className("slotContainer")).get(1).click();
        firefox.getParent(firefox.get().findElement(By.className("items")).findElement(By.className(unit.getValue()))).click();
        firefox.get().findElement(By.className("inputContainer")).findElement(By.tagName("input")).sendKeys("10");
        firefox.get().findElement(By.className("animate")).click();

        log.info(messageSource.getMessage("military.train", new Object[]{unit.name()}, Locale.ENGLISH));
      }
    } catch (NoSuchElementException ex) {
      log.info("trainingInfantry: " + ex.getMessage());
    }
  }

  private void trainingCavalry(UnitEnum unit) {
    try {
      if (isTrainingAvailable(20)) {
        firefox.get().findElements(By.className("slotContainer")).get(2).click();
        firefox.getParent(firefox.get().findElement(By.className("items")).findElement(By.className(unit.getValue()))).click();
        firefox.get().findElement(By.className("inputContainer")).findElement(By.tagName("input")).sendKeys("10");
        firefox.get().findElement(By.className("animate")).click();

        log.info(messageSource.getMessage("military.train", new Object[]{unit.name()}, Locale.ENGLISH));
      }
    } catch (NoSuchElementException ex) {
      log.info("trainingCavalry: " + ex.getMessage());
    }
  }

  private void trainingMachinery(UnitEnum unit) {
    try {
      if(isTrainingAvailable(21)) {
        firefox.get().findElements(By.className("slotContainer")).get(3).click();
        firefox.getParent(firefox.get().findElement(By.className("items")).findElement(By.className(unit.getValue()))).click();
        firefox.get().findElement(By.className("inputContainer")).findElement(By.tagName("input")).sendKeys("10");
        firefox.get().findElement(By.className("animate")).click();

        log.info(messageSource.getMessage("military.train", new Object[]{unit.name()}, Locale.ENGLISH));
      }
    } catch (NoSuchElementException ex) {
      log.info("trainingMachinery: " + ex.getMessage());
    }
  }

  private boolean isTrainingAvailable(int g) {
    StringBuilder green = new StringBuilder("building_g").append(g).append("_small_flat_green");
    StringBuilder white = new StringBuilder("building_g").append(g).append("_small_flat_white");

    return !firefox.existsElement(By.className(green.toString())) &&
        !firefox.get().findElement(By.className(white.toString())).getAttribute("class").contains("disabled") &&
        resourceService.isCropPositive();
  }
}
