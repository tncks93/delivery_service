package com.delivery_service.owners.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ShopResDto {
    private int id;
    private String name;
    private String description;
    private String address;
    private boolean is_open;
    private String category;
    private String image;
}
