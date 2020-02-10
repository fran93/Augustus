package com.fran.augustus.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "travian")
@Getter
@Setter
public class TravianProperties {

  String email;
  String password;
  String url;
}
