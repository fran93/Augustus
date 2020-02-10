package com.fran.augustus.services;

import com.fran.augustus.enums.ResourceEnum;
import com.fran.augustus.model.Field;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Log4j2
public class PeasantService extends BuildingService {

  @Autowired @Lazy
  FirefoxClient firefox;

  @Autowired @Lazy
  ResourceService resourceService;

  public void workOnFields() throws InterruptedException {
    firefox.loading(1);
    firefox.jsClick(firefox.get().findElement(By.id("optimizly_mainnav_resources")));

    ResourceEnum resource = resourceService.getLowerResource();
    ArrayList<Field> fields = new ArrayList<>();
    firefox.get().findElements(By.className(resource.getValue())).forEach(field -> processBuilding(fields, field));

    build(fields);
  }

}
