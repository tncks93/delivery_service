package com.delivery_service.owners.dto;

import com.delivery_service.owners.entity.Shop;
import lombok.*;

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
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(name);
        shop.setDescription(description);
        shop.setAddress(address);
        shop.setIsOpen(isOpen);
        shop.setCategory(category);
        shop.setImage(image);

        return shop;
    }

    public ShopInfoDto convertToDto(Shop shop) {
        ShopInfoDto shopInfoDto = new ShopInfoDto();
        shopInfoDto.setId(shop.getId());
        shopInfoDto.setName(shop.getName());
        shopInfoDto.setDescription(shop.getDescription());
        shopInfoDto.setAddress(shop.getAddress());
        shopInfoDto.setIsOpen(shop.getIsOpen());
        shopInfoDto.setCategory(shop.getCategory());
        shopInfoDto.setImage(shop.getImage());

        return shopInfoDto;
    }
}


