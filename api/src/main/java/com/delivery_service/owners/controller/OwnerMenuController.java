package com.delivery_service.owners.controller;

import com.delivery_service.common.UserRole;
import com.delivery_service.common.annotation.User;
import com.delivery_service.common.dto.ImageUploadReqDto;
import com.delivery_service.common.dto.ImageUrlDto;
import com.delivery_service.common.response.CommonResponse;
import com.delivery_service.common.service.ImageUrlService;
import com.delivery_service.owners.dto.MenuInfoDto;
import com.delivery_service.owners.dto.MenuRegisterDto;
import com.delivery_service.owners.dto.MenuStatusDto;
import com.delivery_service.owners.entity.Menu;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.service.OwnerMenuService;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/owners/menus")
public class OwnerMenuController {

  private final OwnerMenuService ownerMenuService;
  private final ImageUrlService imageUrlService;

  @PostMapping
  public ResponseEntity<CommonResponse<MenuInfoDto>> addMenu(
      @User(role = UserRole.Owner) Owner owner,
      @RequestBody MenuRegisterDto menuRegisterDto) {
    Menu menu = ownerMenuService.addMenu(owner.getShopId(), menuRegisterDto.convertToEntity());

    CommonResponse<MenuInfoDto> response = CommonResponse.success(MenuInfoDto.convertToDto(menu));

    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/{menuId}")
  public ResponseEntity<CommonResponse<MenuInfoDto>> updateMenu(
      @User(role = UserRole.Owner) Owner owner,
      @PathVariable("menuId") Integer menuId, @RequestBody MenuInfoDto menuInfoDto) {
    Menu menu = ownerMenuService.updateMenu(owner.getShopId(), menuId,
        menuInfoDto.convertToEntity());

    CommonResponse<MenuInfoDto> response = CommonResponse.success(MenuInfoDto.convertToDto(menu));

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PatchMapping("/{menuId}/status")
  public ResponseEntity<CommonResponse<MenuStatusDto>> updateMenuStatus(
      @User(role = UserRole.Owner) Owner owner,
      @PathVariable("menuId") int menuId, MenuStatusDto menuStatusDto) {
    Boolean isOnSale = ownerMenuService.updateMenuStatus(owner.getShopId(), menuId,
        menuStatusDto.convertToEntity());

    menuStatusDto.setIsOnSale(isOnSale);
    CommonResponse<MenuStatusDto> response = CommonResponse.success(menuStatusDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<CommonResponse<ArrayList<MenuInfoDto>>> getAllMenus(
      @User(role = UserRole.Owner) Owner owner) {
    ArrayList<MenuInfoDto> MenusDto = new ArrayList<>();
    List<Menu> menus = ownerMenuService.getAllMenus(owner.getShopId());
    for (Menu menu : menus) {
      MenusDto.add(MenuInfoDto.convertToDto(menu));
    }

    CommonResponse<ArrayList<MenuInfoDto>> response = CommonResponse.success(MenusDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/image-url")
  public ResponseEntity<CommonResponse<ImageUrlDto>> getShopImageUrl(
      @User(role = UserRole.Owner) Owner owner, @RequestBody ImageUploadReqDto imageUploadReqDto) {

    String shopImageUploadUrl = imageUrlService.getPresignedUrl(ImageUrlService.USAGE_MENU,
        imageUploadReqDto.getOriginalImageName());
    String shopImageDownloadUrl = imageUrlService.getPublicUrl(shopImageUploadUrl);

    ImageUrlDto imageUrlDto = new ImageUrlDto(shopImageUploadUrl, shopImageDownloadUrl);

    CommonResponse<ImageUrlDto> response = CommonResponse.success(imageUrlDto);

    return new ResponseEntity<>(response, HttpStatus.OK);
  }


}
