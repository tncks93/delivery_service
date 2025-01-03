package com.delivery_service.owners.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ShopStatusDto {
    Integer shopId;
    Boolean isOpen;
}


