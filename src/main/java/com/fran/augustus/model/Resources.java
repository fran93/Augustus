package com.fran.augustus.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resources {
  private int wood;
  private int crop;
  private int clay;
  private int iron;
}
