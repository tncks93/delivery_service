package com.delivery_service.service;

import com.delivery_service.entity.Shop;
import com.delivery_service.repository.ShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShopService {

  private final ShopRepository repository;

  //고객 주소 범위안에서 가져와야 함.
//  public List<Shop> getShopsByCategory(String category) {
//    return null;
//  }

  public Shop getShop(Integer shopId) {
    return repository.findById(shopId).get();
  }

}
