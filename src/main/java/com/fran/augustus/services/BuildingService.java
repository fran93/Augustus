package com.fran.augustus.services;

import com.fran.augustus.enums.BuildingEnum;
import com.fran.augustus.model.Field;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
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
    Optional<Field> fieldToBuild = fields.stream().min(Comparator.comparingInt(Field::getLevel));
    if(fieldToBuild.isPresent()) {
      WebElement locationElement = firefox.get().findElement(By.className(fieldToBuild.get().getPosition()));
      firefox.mouseOver(locationElement);
      firefox.loading(1);

      if (!locationElement.findElements(By.className("notNow")).isEmpty()) {
        if(locationElement.findElements(By.className("premiumOptionMenu")).isEmpty()) {
          firefox.jsClick(locationElement.findElement(By.className("buildingBubble")));
        } else {
          firefox.jsClick(locationElement.findElement(By.className("premiumOptionMenu")).findElement(By.className("buildingBubble")));
        }

        log.info(messageSource.getMessage("building.build", new Object[] {fieldToBuild.get()}, Locale.ENGLISH));
      }
    }

    if(!firefox.get().findElements(By.className("closeWindow")).isEmpty()) {
      firefox.get().findElement(By.className("cancel")).click();
    }
  }

  protected void processBuilding(ArrayList<Field> fields, BuildingEnum buildingEnum, int maxLevel) {
    WebElement building = firefox.get().findElement(By.className(buildingEnum.getValue()));
    processBuilding(fields, building, maxLevel);
  }

  protected void processBuilding(ArrayList<Field> fields, WebElement building, int maxLevel) {
    try {
      String status = building.findElement(By.className("buildingStatus")).getAttribute("class");
      Optional<String> location = Arrays.stream(status.split(" ")).filter(attr -> attr.contains("location")).findFirst();
      String position = location.orElse(null);
      int level = Integer.parseInt(building.findElement(By.className("buildingLevel")).getText());

      if(level < maxLevel) {
        fields.add(Field.builder().level(level).position(position).build());
      }
    } catch (NumberFormatException | StaleElementReferenceException ex) {
      log.info("processBuilding: " + ex.getMessage());
    }
  }
}
