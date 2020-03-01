package com.fran.augustus.services;

import com.fran.augustus.enums.BuildingEnum;
import com.fran.augustus.model.Field;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;

import java.util.*;

@Log4j2
public class BuildingService {

  @Autowired
  @Lazy
  FirefoxClient firefox;

  @Autowired
  @Lazy
  MessageSource messageSource;

  protected void build(ArrayList<Field> fields) throws InterruptedException {
    try {
      Optional<Field> fieldToBuild = fields.stream().min(Comparator.comparingInt(Field::getLevel));
      if (fieldToBuild.isPresent()) {
        if(fieldToBuild.get().getLevel() > 0) {
          upgradeBuilding(fieldToBuild);
        } else {
          newBuilding(fieldToBuild);
        }
      }
    } catch (WebDriverException ex) {
      log.info("build: " + ex.getMessage());
    }
  }

  private void newBuilding(Optional<Field> fieldToBuild) throws InterruptedException {
    String locationId = firefox.get().findElement(By.className("free"))
        .getAttribute("class").split(" ")[1]
        .replaceFirst("buildingLocation", "")
        .trim();
    StringBuilder url = new StringBuilder(firefox.get().getCurrentUrl().split("#")[0]);
    url.append("#/page:village/location:");
    url.append(locationId);
    url.append("/window:building");

    firefox.get().get(url.toString());
    firefox.loading(1);

    if(fieldToBuild.isPresent()) {
      firefox.getParent(firefox.get().findElement(By.className(fieldToBuild.get().getType()))).click();
      WebElement startConstruction = firefox.get().findElement(By.className("startConstruction"));
      if (!startConstruction.getAttribute("class").contains("disabled")) {
        firefox.jsClick(startConstruction);
        log.info(messageSource.getMessage("building.build", new Object[]{fieldToBuild.get()}, Locale.ENGLISH));
      }
    }
  }

  private void upgradeBuilding(Optional<Field> fieldToBuild) throws InterruptedException {
    WebElement locationElement = firefox.get().findElement(By.className(fieldToBuild.get().getPosition()));
    firefox.mouseOver(locationElement);
    firefox.loading(1);

    if (!locationElement.findElements(By.className("notNow")).isEmpty()) {
      if (locationElement.findElements(By.className("premiumOptionMenu")).isEmpty()) {
        firefox.jsClick(locationElement.findElement(By.className("buildingBubble")));
      } else {
        firefox.jsClick(locationElement.findElement(By.className("premiumOptionMenu")).findElement(By.className("buildingBubble")));
      }

      log.info(messageSource.getMessage("building.build", new Object[]{fieldToBuild.get()}, Locale.ENGLISH));
    }
  }

  protected void processBuilding(ArrayList<Field> fields, BuildingEnum buildingEnum, int maxLevel, int minLevel) {
    if(firefox.existsElement(By.className(buildingEnum.getValue()))) {
      WebElement building = firefox.get().findElement(By.className(buildingEnum.getValue()));
      processBuilding(fields, building, maxLevel, minLevel);
    } else {
      fields.add(Field.builder().level(minLevel).type(buildingEnum.getLongValue()).build());
    }
  }

  protected void processBuilding(ArrayList<Field> fields, WebElement building, int maxLevel, int minLevel) {
    try {
      String status = building.findElement(By.className("buildingStatus")).getAttribute("class");
      Optional<String> location = Arrays.stream(status.split(" ")).filter(attr -> attr.contains("location")).findFirst();
      String position = location.orElse(null);
      int level = position == null ? 0 : Integer.parseInt(building.findElement(By.className("buildingLevel")).getAttribute("innerText"));

      if(level < maxLevel) {
        fields.add(Field.builder().level(level+minLevel).position(position).build());
      }
    } catch (NumberFormatException | StaleElementReferenceException ex) {
      log.info("processBuilding: " + ex.getMessage());
    }
  }
}
