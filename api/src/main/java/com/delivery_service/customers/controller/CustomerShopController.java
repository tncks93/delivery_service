package com.delivery_service.customers.controller;

import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.customers.service.CustomerMenuService;
import com.delivery_service.customers.service.CustomerShopService;
import com.delivery_service.owners.dto.MenuInfoDto;
import com.delivery_service.owners.dto.ShopInfoDto;
import com.delivery_service.owners.entity.Menu;
import com.delivery_service.owners.entity.Shop;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/shops")
public class CustomerShopController {

  private final CustomerShopService customerShopService;
  private final CustomerMenuService customerMenuService;

  @GetMapping("/${shopId}")
  public ResponseEntity<CommonResponse<ShopInfoDto>> getShop(@PathVariable int shopId) {
    Shop shop = customerShopService.getShop(shopId);
    CommonResponse<ShopInfoDto> response = CommonResponse.success(ShopInfoDto.convertToDto(shop));

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/${shopId}/menus")
  public ResponseEntity<CommonResponse<ArrayList<MenuInfoDto>>> getShopMenus(
      @PathVariable int shopId) {
    ArrayList<MenuInfoDto> MenusDto = new ArrayList<>();
    List<Menu> menus = customerMenuService.getAllMenus(shopId);
    for (Menu menu : menus) {
      MenusDto.add(MenuInfoDto.convertToDto(menu));
    }

    CommonResponse<ArrayList<MenuInfoDto>> response = CommonResponse.success(MenusDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/${shopId}/menus/${menuId}")
  public ResponseEntity<CommonResponse<MenuInfoDto>> getShopMenu(@PathVariable int shopId,
      @PathVariable int menuId) {
    //shopId의 필요성...
    Menu menu = customerMenuService.getMenu(menuId);

    CommonResponse<MenuInfoDto> response = CommonResponse.success(MenuInfoDto.convertToDto(menu));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
