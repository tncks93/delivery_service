package com.delivery_service.controller;

import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.dto.MenuInfoDto;
import com.delivery_service.dto.ShopInfoDto;
import com.delivery_service.entity.Menu;
import com.delivery_service.entity.Shop;
import com.delivery_service.service.MenuService;
import com.delivery_service.service.ShopService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/shops")
public class ShopController {

  private final ShopService shopService;
  private final MenuService menuService;

  @GetMapping("/{shopId}")
  public ResponseEntity<CommonResponse<ShopInfoDto>> getShop(
      @PathVariable("shopId") Integer shopId) {
    Shop shop = shopService.getShop(shopId);
    CommonResponse<ShopInfoDto> response = CommonResponse.success(ShopInfoDto.convertToDto(shop));

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{shopId}/menus")
  public ResponseEntity<CommonResponse<ArrayList<MenuInfoDto>>> getShopMenus(
      @PathVariable("shopId") Integer shopId) {
    ArrayList<MenuInfoDto> MenusDto = new ArrayList<>();
    List<Menu> menus = menuService.getAllMenus(shopId);
    for (Menu menu : menus) {
      MenusDto.add(MenuInfoDto.convertToDto(menu));
    }

    CommonResponse<ArrayList<MenuInfoDto>> response = CommonResponse.success(MenusDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/{shopId}/menus/{menuId}")
  public ResponseEntity<CommonResponse<MenuInfoDto>> getShopMenu(
      @PathVariable("shopId") Integer shopId,
      @PathVariable("menuId") Integer menuId) {
    //shopId의 필요성...
    Menu menu = menuService.getMenu(menuId);

    CommonResponse<MenuInfoDto> response = CommonResponse.success(MenuInfoDto.convertToDto(menu));
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
