package com.delivery_service.dto;

import com.delivery_service.entity.Shop;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ShopInfoDto {

  private Integer id;
  private String name;
  private String description;
  private String address;
  private Boolean isOpen;
  private String category;
  private String image;

  public Shop convertToEntity() {
    Shop entity = new Shop();
    entity.setId(id);
    entity.setName(name);
    entity.setDescription(description);
    entity.setAddress(address);
    entity.setIsOpen(isOpen);
    entity.setCategory(category);
    entity.setImage(image);

    return entity;
  }

  public static ShopInfoDto convertToDto(Shop entity) {
    ShopInfoDto dto = new ShopInfoDto();
    dto.setId(entity.getId());
    dto.setName(entity.getName());
    dto.setDescription(entity.getDescription());
    dto.setAddress(entity.getAddress());
    dto.setIsOpen(entity.getIsOpen());
    dto.setCategory(entity.getCategory());
    dto.setImage(entity.getImage());

    return dto;
  }
}


