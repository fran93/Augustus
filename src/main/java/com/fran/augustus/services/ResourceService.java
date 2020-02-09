package com.fran.augustus.services;

import com.fran.augustus.enums.ResourceEnum;
import com.fran.augustus.model.Resources;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.OptionalInt;

@Service
@Log4j2
public class ResourceService {

  @Autowired @Lazy
  FirefoxClient firefox;

  private Resources getResources() {
    String clay = firefox.get().findElement(By.className("clay")).findElement(By.className("progressbar")).getAttribute("value");
    String crop = firefox.get().findElement(By.className("crop")).findElement(By.className("progressbar")).getAttribute("value");
    String iron = firefox.get().findElement(By.className("iron")).findElement(By.className("progressbar")).getAttribute("value");
    String wood = firefox.get().findElement(By.className("wood")).findElement(By.className("progressbar")).getAttribute("value");

    return Resources.builder()
        .clay(Integer.parseInt(clay))
        .crop(Integer.parseInt(crop))
        .iron(Integer.parseInt(iron))
        .wood(Integer.parseInt(wood))
        .build();
  }

  public ResourceEnum getLowerResource() {
    Resources resource = getResources();
    OptionalInt min = Arrays.stream(new int[] {resource.getClay(), resource.getCrop(), resource.getIron(), resource.getWood()}).min();

    if(min.isPresent()) {
      if(min.getAsInt() == resource.getIron()) {
        return ResourceEnum.IRON;
      } else if(min.getAsInt() == resource.getWood()) {
        return ResourceEnum.WOOD;
      } else  if(min.getAsInt() == resource.getClay()) {
        return ResourceEnum.CLAY;
      }
    }

    return ResourceEnum.CROP;
  }
}
