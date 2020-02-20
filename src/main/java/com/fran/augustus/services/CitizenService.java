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

  @Autowired
  @Lazy
  VillagesService villagesService;

  public void buildOurCity() throws InterruptedException {
    firefox.loading(1);
    firefox.jsClick(firefox.get().findElement(By.id("optimizly_mainnav_village")));

    ArrayList<Field> fields = new ArrayList<>();
    processBuilding(fields, BuildingEnum.GRANARY, 20, 1);
    processBuilding(fields, BuildingEnum.WAREHOUSE, 20, 1);
    processBuilding(fields, BuildingEnum.MAIN, 20, 0);
    processBuilding(fields, BuildingEnum.CRANNY, 10, 0);
    processBuilding(fields, BuildingEnum.PALISADE, 20, 0);
    processBuilding(fields, BuildingEnum.GAUL_TRAPPER, 20, 1);
    processBuilding(fields, BuildingEnum.GRAIN_MILL, 5, 10);
    processBuilding(fields, BuildingEnum.BAKERY, 5, 15);
    processBuilding(fields, BuildingEnum.EMBASSY, 20, 5);

    if(villagesService.isBauerVillage()) {
      processBuilding(fields, BuildingEnum.MARKET_PLACE, 20, 5);
      processBuilding(fields, BuildingEnum.RESIDENCE, 20, 5);
      processBuilding(fields, BuildingEnum.TRADE_OFFICE, 20, 25);
      processBuilding(fields, BuildingEnum.SAWMILL, 5, 15);
      processBuilding(fields, BuildingEnum.BRICKYARD, 5, 15);
      processBuilding(fields, BuildingEnum.IRON_FOUNDRY, 5, 15);
      processBuilding(fields, BuildingEnum.RALLY_POINT, 1, 0);
    } else if(villagesService.isEngineerVillage()) {
      processBuilding(fields, BuildingEnum.WORKSHOP, 20, 16);
      processBuilding(fields, BuildingEnum.SMITHY, 20, 7);
      processBuilding(fields, BuildingEnum.ACADEMY, 15, 6);
      processBuilding(fields, BuildingEnum.BARRACKS, 3, 3);
      processBuilding(fields, BuildingEnum.RESIDENCE, 20, 5);
      processBuilding(fields, BuildingEnum.RALLY_POINT, 20, 0);
      processBuilding(fields, BuildingEnum.TOWN_HALL, 20, 10);
    } else if(villagesService.isPhalanxVillage()) {
      processBuilding(fields, BuildingEnum.SMITHY, 20, 7);
      processBuilding(fields, BuildingEnum.ACADEMY, 1, 6);
      processBuilding(fields, BuildingEnum.BARRACKS, 20, 3);
      processBuilding(fields, BuildingEnum.RESIDENCE, 20, 5);
      processBuilding(fields, BuildingEnum.RALLY_POINT, 1, 0);
    } else if(villagesService.isHammerVillage()) {
      processBuilding(fields, BuildingEnum.PALACE, 20, 5);
      processBuilding(fields, BuildingEnum.STONE_MANSON, 20, 5);
      processBuilding(fields, BuildingEnum.TOURNAMENT_SQUARE, 20, 15);
      processBuilding(fields, BuildingEnum.SMITHY, 20, 7);
      processBuilding(fields, BuildingEnum.STABLE, 20, 11);
      processBuilding(fields, BuildingEnum.ACADEMY, 20, 6);
      processBuilding(fields, BuildingEnum.BARRACKS, 20, 3);
      processBuilding(fields, BuildingEnum.RALLY_POINT, 15, 0);
    } else if(villagesService.isSpyVillage()) {
      processBuilding(fields, BuildingEnum.SMITHY, 20, 7);
      processBuilding(fields, BuildingEnum.STABLE, 20, 11);
      processBuilding(fields, BuildingEnum.ACADEMY, 5, 6);
      processBuilding(fields, BuildingEnum.BARRACKS, 3, 3);
      processBuilding(fields, BuildingEnum.RESIDENCE, 20, 5);
      processBuilding(fields, BuildingEnum.RALLY_POINT, 1, 0);
    }

    build(fields);
  }
}
