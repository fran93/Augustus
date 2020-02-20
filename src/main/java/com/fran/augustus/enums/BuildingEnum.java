package com.fran.augustus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum BuildingEnum {

  GRANARY("type_11", "buildingType11"),
  WAREHOUSE("type_10", "buildingType10"),
  MAIN("type_15", "buildingType15"),
  CRANNY("type_23", "buildingType23"),
  MAUER("type_31", "buildingType31"),
  RALLY_POINT("type_16", "buildingType16"),
  MARKET_PLACE("type_17", "buildingType17"),
  EMBASSY("type_18", "buildingType18"),
  TOWN_HALL("type_", "buildingType"),
  RESIDENCE("type_25", "buildingType25"),
  PALACE("type_", "buildingType"),
  TREASURY("type_", "buildingType"),
  TRADE_OFFICE("type_", "buildingType"),
  STONE_MANSON("type_", "buildingType"),
  SMITHY("type_", "buildingType"),
  TOURNAMENT_SQUARE("type_", "buildingType"),
  BARRACKS("type_", "buildingType"),
  STABLE("type_", "buildingType"),
  WORKSHOP("type_", "buildingType"),
  ACADEMY("type_", "buildingType"),
  GREAT_BARRACKS("type_", "buildingType"),
  GREAT_STABLE("type_", "buildingType"),
  SAWMILL("type_", "buildingType"),
  BRICKYARD("type_", "buildingType"),
  IRON_FOUNDRY("type_", "buildingType"),
  GRAIN_MILL("type_8", "buildingType8"),
  BAKERY("type_9", "buildingType9"),
  PALISADE("type_33", "buildingType33"),
  WATER_DITCH("type_", "buildingType"),
  GAUL_TRAPPER("type_36", "buildingType36");

  private String value;
  private String longValue;

  public static String getLongValueByValue(String value) {
    return Arrays.stream(values()).filter((build) -> build.getValue().equals(value)).findFirst().get().getLongValue();
  }
}
