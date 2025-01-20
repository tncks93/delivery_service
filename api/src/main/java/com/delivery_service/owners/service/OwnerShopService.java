package com.delivery_service.owners.service;

import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.entity.Shop;
import com.delivery_service.owners.repository.OwnerShopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OwnerShopService {

  private final OwnerShopRepository repository;

  public Shop addShop(Owner owner, Shop shop) {
    //이미 shop을 가지고 있을 경우?
//        if(owner.getShopId() != null){
//
//        }

    Shop savedShop = repository.save(shop);

    return savedShop;
  }

  public Shop getShop(Owner owner) {
    //shop을 가지지 않고 있을 경우?
//        if(owner.getShopId() == null){
//
//        }
    return repository.findById(owner.getShopId()).get();
  }

  public Shop updateShop(Owner owner, Shop shop) {
    //owner가 가지는 shopId 와 요청으로 온 shop의 shopId가 다르면?

    return repository.save(shop);
  }

  @Transactional
  public Boolean updateShopStatus(Owner owner, Boolean isOpen) {
    Shop shop = repository.findById(owner.getShopId()).get();
    shop.setIsOpen(isOpen);
    return shop.getIsOpen();
  }

}
