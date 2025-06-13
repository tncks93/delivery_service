package com.delivery_service.dto;

import com.delivery_service.entity.Menu;
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
    entity.setIsOnSale(true);

    return entity;
  }

}
