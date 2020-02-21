package com.fran.augustus.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Field {

  private int level;
  private String position;
  private String type;
}
