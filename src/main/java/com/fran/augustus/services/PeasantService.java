package com.fran.augustus.services;

import com.fran.augustus.enums.ResourceEnum;
import com.fran.augustus.model.Field;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

@Service
@Log4j2
public class PeasantService {

  @Autowired @Lazy
  FirefoxClient firefox;

  @Autowired @Lazy
  ResourceService resourceService;

  public void workOnFields() throws InterruptedException {
    firefox.loading(1);
    firefox.jsClick(firefox.get().findElement(By.id("optimizly_mainnav_resources")));

    try {
      ResourceEnum resource = resourceService.getLowerResource();
      ArrayList<Field> fields = new ArrayList<>();
      firefox.get().findElements(By.className(resource.getValue())).forEach(field -> {
        String status = field.findElement(By.className("buildingStatus")).getAttribute("class");
        Optional<String> location = Arrays.stream(status.split(" ")).filter(attr -> attr.contains("location")).findFirst();
        String position = location.orElse(null);
        int level = Integer.parseInt(field.findElement(By.className("buildingLevel")).getText());

        fields.add(Field.builder().level(level).position(position).build());
      });

      build(fields);
    } catch (NumberFormatException ex) {
      log.info("workOnFields: " + ex.getMessage());
    }
  }

  private void build(ArrayList<Field> fields) throws InterruptedException {
    Optional<Field> fieldToBuild = fields.stream().min(Comparator.comparingInt(Field::getLevel));
    if(fieldToBuild.isPresent()) {
      WebElement locationElement = firefox.get().findElement(By.className(fieldToBuild.get().getPosition()));
      firefox.mouseOver(locationElement);
      firefox.loading(1);

      if (!locationElement.findElements(By.className("enoughRes")).isEmpty()) {
        if(locationElement.findElements(By.className("premiumOptionMenu")).isEmpty()) {
          locationElement.findElement(By.className("buildingBubble")).click();
        } else {
          locationElement.findElement(By.className("premiumOptionMenu")).findElement(By.className("buildingBubble")).click();
        }

        log.info("I order to build " + fieldToBuild.get());
      }
    }
  }

}
