package com.delivery_service.owners.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Owner {
    private Integer id;
    private Integer shopId;

    public Owner(Integer id) {
        this.id = id;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

}
