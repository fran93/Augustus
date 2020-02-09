package com.fran.augustus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResourceEnum {

  WOOD("type_1"),
  CROP("type_4"),
  CLAY("type_2"),
  IRON("type_3");

  private String value;

}
