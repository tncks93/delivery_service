package com.delivery_service.owners.entity;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Shop {
    private int id;
    private String name;
    private String description;
    private String address;
    private boolean is_open;
    private String latitude;
    private String longitude;
    private String category;
    private String image;
}
