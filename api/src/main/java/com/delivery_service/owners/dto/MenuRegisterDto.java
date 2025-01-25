package com.delivery_service.owners.dto;

import com.delivery_service.owners.entity.Menu;
import lombok.Getter;

@Getter
public class MenuRegisterDto {

  private String name;
  private String description;
  private Integer price;
  private String image;

  public Menu convertToEntity() {
    Menu entity = new Menu();
    entity.setName(name);
    entity.setDescription(description);
    entity.setPrice(price);
    entity.setImage(image);

    return entity;
  }

}
