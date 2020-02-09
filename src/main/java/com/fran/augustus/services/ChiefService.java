package com.fran.augustus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ChiefService {

  @Autowired @Lazy
  LoginService loginService;

  @Autowired @Lazy
  PeasantService peasantService;

  @Autowired @Lazy
  CitizenService citizenService;

  public void command() throws InterruptedException {
    loginService.login();
    peasantService.workOnFields();
    citizenService.buildOurCity();
  }
}
