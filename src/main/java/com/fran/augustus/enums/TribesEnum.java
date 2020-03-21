package com.fran.augustus.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TribesEnum {

  ROMANS("ROMANS"),
  GAULS("GAULS"),
  TEUTONS("TEUTONS");

  String value;
}
