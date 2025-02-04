package com.delivery_service.owners.dto;

import com.delivery_service.owners.entity.Shop;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShopRegisterDto {

  private String name;
  private String description;
  private String address;
  private String category;
  private String image;

  public Shop convertToEntity() {
    Shop entity = new Shop();
    entity.setName(name);
    entity.setDescription(description);
    entity.setAddress(address);
    entity.setIsOpen(false);
    entity.setCategory(category);
    entity.setImage(image);

    return entity;
  }

}
