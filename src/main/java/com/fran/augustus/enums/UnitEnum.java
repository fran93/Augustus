package com.fran.augustus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UnitEnum {

  RAM(""),
  TREBUCHET(""),
  PHALANX("unitType1"),
  SWORDSMAN("unitType2"),
  THEUTATES_THUNDER(""),
  HAEDUAN(""),
  PATHFINDER("");

  private String value;
}
