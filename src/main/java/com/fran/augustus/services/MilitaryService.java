package com.fran.augustus.services;

import com.fran.augustus.enums.UnitEnum;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
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

  @Value( "${troops.ratio}" )
  int troopsRatio;

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
    String troopsRaw = firefox.getText(firefox.get()
        .findElement(By.className("troop"))
        .findElement(By.className("value")));
    String popRaw = firefox.getText(firefox.get()
        .findElement(By.className("population"))
        .findElement(By.className("value")));

    double troopsCount = Integer.parseInt(troopsRaw);
    double popCount = Integer.parseInt(popRaw);
    Double desiredTroops = (popCount / troopsRatio) * popCount;

    if(troopsCount < desiredTroops) {
       if (villagesService.isPhalanxVillage()) {
         trainingInfantry(UnitEnum.PHALANX);
       } else if (villagesService.isDruidVillage()) {
         trainingCavalry(UnitEnum.DRUIDRIDER);
       } else if (villagesService.isHammerVillage()) {
        trainingInfantry(UnitEnum.SWORDSMAN);
        trainingCavalry(UnitEnum.HAEDUAN);
        trainingMachinery(UnitEnum.TREBUCHET);
       } else if (villagesService.isSpyVillage()) {
        trainingCavalry(UnitEnum.PATHFINDER);
       } else if(villagesService.isRaidingVillage()) {
        trainingCavalry(UnitEnum.THEUTATES_THUNDER);
       }
    }
  }

  private void trainingInfantry(UnitEnum unit) {
    if(isTrainingAvailable(19)) {
      firefox.get().findElements(By.className("slotContainer")).get(1).click();
      firefox.getParent(firefox.get().findElement(By.className("items")).findElement(By.className(unit.getValue()))).click();
      firefox.get().findElement(By.className("inputContainer")).findElement(By.tagName("input")).sendKeys("10");
      firefox.get().findElement(By.className("animate")).click();

      log.info(messageSource.getMessage("military.train", new Object[]{unit.name()}, Locale.ENGLISH));
    }
  }

  private void trainingCavalry(UnitEnum unit) {
    if(isTrainingAvailable(20)) {
      firefox.get().findElements(By.className("slotContainer")).get(2).click();
      firefox.getParent(firefox.get().findElement(By.className("items")).findElement(By.className(unit.getValue()))).click();
      firefox.get().findElement(By.className("inputContainer")).findElement(By.tagName("input")).sendKeys("10");
      firefox.get().findElement(By.className("animate")).click();

      log.info(messageSource.getMessage("military.train", new Object[]{unit.name()}, Locale.ENGLISH));
    }
  }

  private void trainingMachinery(UnitEnum unit) {
    if(isTrainingAvailable(21)) {
      firefox.get().findElements(By.className("slotContainer")).get(3).click();
      firefox.getParent(firefox.get().findElement(By.className("items")).findElement(By.className(unit.getValue()))).click();
      firefox.get().findElement(By.className("inputContainer")).findElement(By.tagName("input")).sendKeys("10");
      firefox.get().findElement(By.className("animate")).click();

      log.info(messageSource.getMessage("military.train", new Object[]{unit.name()}, Locale.ENGLISH));
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
