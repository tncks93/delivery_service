package com.delivery_service.owners.dto;

import com.delivery_service.owners.entity.Shop;
import lombok.*;

@Getter
@Setter
@ToString
public class ShopRegisterDto {

    private String name;
    private String description;
    private String address;
    private String category;

    public Shop toShop() {
        Shop shop = new Shop();
        shop.setName(name);
        shop.setDescription(description);
        shop.setAddress(address);
        shop.set_open(false);
        shop.setCategory(category);

        return shop;
    }

}
