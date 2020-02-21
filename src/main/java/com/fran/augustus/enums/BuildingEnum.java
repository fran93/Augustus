package com.fran.augustus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
  TOWN_HALL("type_24", "buildingType24"),
  RESIDENCE("type_25", "buildingType25"),
  PALACE("type_26", "buildingType26"),
  TREASURY("type_45", "buildingType45"),
  TRADE_OFFICE("type_28", "buildingType28"),
  STONE_MANSON("type_34", "buildingType34"),
  SMITHY("type_13", "buildingType13"),
  TOURNAMENT_SQUARE("type_14", "buildingType14"),
  BARRACKS("type_19", "buildingType19"),
  STABLE("type_20", "buildingType20"),
  WORKSHOP("type_21", "buildingType21"),
  ACADEMY("type_22", "buildingType22"),
  GREAT_BARRACKS("type_", "buildingType"),
  GREAT_STABLE("type_", "buildingType"),
  SAWMILL("type_5", "buildingType5"),
  BRICKYARD("type_6", "buildingType6"),
  IRON_FOUNDRY("type_7", "buildingType7"),
  GRAIN_MILL("type_8", "buildingType8"),
  BAKERY("type_9", "buildingType9"),
  PALISADE("type_33", "buildingType33"),
  WATER_DITCH("type_", "buildingType"),
  GAUL_TRAPPER("type_36", "buildingType36");

  private String value;
  private String longValue;
}
