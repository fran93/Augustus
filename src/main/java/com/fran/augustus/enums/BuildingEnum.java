package com.fran.augustus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildingEnum {

  GRANARY("type_11"),
  WAREHOUSE("type_10"),
  MAIN("type_15"),
  CRANNY("type_23"),
  MAUER("type_31"),
  RALLY_POINT("type_16"),
  MARKET_PLACE(""),
  EMBASSY(""),
  TOWN_HALL(""),
  RESIDENCE(""),
  PALACE(""),
  TREASURY(""),
  TRADE_OFFICE(""),
  STONE_MANSON(""),
  SMITHY(""),
  TOURNAMENT_SQUARE(""),
  BARRACKS(""),
  STABLE(""),
  WORKSHOP(""),
  ACADEMY(""),
  GREAT_BARRACKS(""),
  GREAT_STABLE(""),
  SAWMILL(""),
  BRICKYARD(""),
  IRON_FOUNDRY(""),
  GRAIN_MILL(""),
  BAKERY(""),
  PALISADE(""),
  WATER_DITCH(""),
  GAUL_TRAPPER("");

  private String value;


}
