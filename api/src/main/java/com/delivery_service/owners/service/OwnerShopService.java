package com.delivery_service.owners.service;

import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.entity.Shop;
import com.delivery_service.owners.repository.OwnerShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OwnerShopService {

  private final OwnerShopRepository shopRepository;

  public Shop addShop(Owner owner, Shop shop) {
    //이미 shop을 가지고 있을 경우?
//        if(owner.getShopId() != null){
//
//        }

    Shop savedShop = shopRepository.save(shop);

    return savedShop;
  }

  public Shop getShop(Owner owner) {
    //shop을 가지지 않고 있을 경우?
//        if(owner.getShopId() == null){
//
//        }
    return shopRepository.findByShopId(owner.getShopId());
  }

  public Shop updateShop(Owner owner, Shop shop) {
    //owner가 가지는 shopId 와 요청으로 온 shop의 shopId가 다르면?

    return shopRepository.update(owner.getShopId(), shop);
  }

  public Boolean updateShopStatus(Owner owner, Boolean isOpen) {
    return shopRepository.updateIsOpen(owner.getShopId(), isOpen);
  }

}
