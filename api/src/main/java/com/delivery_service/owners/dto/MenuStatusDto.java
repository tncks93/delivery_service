package com.delivery_service.owners.dto;

import com.delivery_service.owners.entity.Menu;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuStatusDto {

  private Integer id;
  private Boolean isOnSale;

  public Menu convertToEntity() {
    Menu menu = new Menu();
    menu.setId(id);
    menu.setIsOnSale(isOnSale);

    return menu;
  }

}
