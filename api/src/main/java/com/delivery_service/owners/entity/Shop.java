package com.delivery_service.owners.entity;

import com.delivery_service.owners.dto.ShopRegisterDto;
import lombok.*;

@Getter
@Setter
@ToString
public class Shop {

    private Integer id;
    private String name;
    private String description;
    private String address;
    private Boolean isOpen;
    private String latitude;
    private String longitude;
    private String category;
    private String image;

}
