package com.delivery_service.owners.dto;

import com.delivery_service.owners.entity.Shop;
import lombok.*;

@Getter
@Setter
@ToString
public class ShopInfoDto {
    private int id;
    private String name;
    private String description;
    private String address;
    private boolean is_open;
    private String category;
    private String image;

    public Shop convertToEntity() {
        Shop shop = new Shop();
        shop.setId(id);
        shop.setName(name);
        shop.setDescription(description);
        shop.setAddress(address);
        shop.set_open(is_open);
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
        shopInfoDto.set_open(shop.is_open());
        shopInfoDto.setCategory(shop.getCategory());
        shopInfoDto.setImage(shop.getImage());

        return shopInfoDto;
    }
}


