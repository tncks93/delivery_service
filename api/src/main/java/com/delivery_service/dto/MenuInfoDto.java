package com.delivery_service.dto;

import com.delivery_service.entity.Menu;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuInfoDto {

  private Integer id;
  private String name;
  private String description;
  private Integer price;
  private String image;
  private Boolean isOnSale;

  public Menu convertToEntity() {
    Menu entity = new Menu();
    entity.setId(id);
    entity.setName(name);
    entity.setDescription(description);
    entity.setPrice(price);
    entity.setImage(image);
    entity.setIsOnSale(isOnSale);
    return entity;
  }

  public static MenuInfoDto convertToDto(Menu menu) {
    MenuInfoDto dto = new MenuInfoDto();
    dto.setId(menu.getId());
    dto.setName(menu.getName());
    dto.setDescription(menu.getDescription());
    dto.setPrice(menu.getPrice());
    dto.setImage(menu.getImage());
    dto.setIsOnSale(menu.getIsOnSale());

    return dto;
  }

}
