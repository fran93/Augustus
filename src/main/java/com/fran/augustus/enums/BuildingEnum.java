package com.fran.augustus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildingEnum {

  GRANARY("type_11"),
  WAREHOUSE("type_10"),
  MAIN("type_15"),
  VERSTECK("type_23"),
  MAUER("type_31"),
  RALLY_POINT("type_16");

  private String value;


}
