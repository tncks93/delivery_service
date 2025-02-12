package com.delivery_service.customers.service;

import com.delivery_service.customers.repository.CustomerShopRepository;
import com.delivery_service.owners.entity.Shop;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShopService {

  private final CustomerShopRepository repository;

  //고객 주소 범위안에서 가져와야 함.
//  public List<Shop> getShopsByCategory(String category) {
//    return null;
//  }

  public Shop getShop(Integer shopId) {
    return repository.findById(shopId).get();
  }

}
