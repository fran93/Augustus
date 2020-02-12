package com.fran.augustus.services;

import com.fran.augustus.enums.BuildingEnum;
import com.fran.augustus.model.Field;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Log4j2
public class CitizenService extends BuildingService {

  @Autowired
  @Lazy
  FirefoxClient firefox;

  public void buildOurCity() throws InterruptedException {
    firefox.loading(1);
    firefox.jsClick(firefox.get().findElement(By.id("optimizly_mainnav_village")));

    ArrayList<Field> fields = new ArrayList<>();
    processBuilding(fields, BuildingEnum.GRANARY, 20);
    processBuilding(fields, BuildingEnum.WAREHOUSE, 20);
    processBuilding(fields, BuildingEnum.MAIN, 20);
    processBuilding(fields, BuildingEnum.VERSTECK, 10);
    processBuilding(fields, BuildingEnum.MAUER, 20);

    build(fields);
  }
}
