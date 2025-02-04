package com.delivery_service.owners.service;

import com.delivery_service.common.exception.MismatchedShopOwnerException;
import com.delivery_service.common.exception.ShopAlreadyExistsException;
import com.delivery_service.common.exception.ShopNotFoundException;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.entity.Shop;
import com.delivery_service.owners.repository.OwnerShopRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OwnerShopService {

  private final OwnerShopRepository repository;

  public Shop addShop(Owner owner, Shop shop) {
    if (owner.getShopId() != null) {
      throw new ShopAlreadyExistsException("Owner already has a shop");
    }

    Shop savedShop = repository.save(shop);

    return savedShop;
  }

  public Shop getShop(Owner owner) {
    return repository.findById(owner.getShopId())
        .orElseThrow(() -> new ShopNotFoundException(owner.getShopId()));
  }

  public Shop updateShop(Owner owner, Shop updatedShop) {
    if (!updatedShop.getId().equals(owner.getShopId())) {
      throw new MismatchedShopOwnerException("Owner shop id doesn't match");
    }
    Optional<Shop> optionalShop = repository.findById(owner.getId());
    Shop shop = optionalShop.orElseThrow(() -> new ShopNotFoundException(owner.getId()));

    shop.setName(updatedShop.getName());
    shop.setDescription(updatedShop.getDescription());
    //TODO address가 바뀌면 latitude,longitude도 바뀌어야 한다.
    shop.setAddress(updatedShop.getAddress());
    shop.setCategory(updatedShop.getCategory());
    shop.setImage(updatedShop.getImage());

    return shop;


  }

  public Boolean updateShopStatus(Owner owner, Boolean isOpen) {
    Shop shop = repository.findById(owner.getShopId())
        .orElseThrow(() -> new ShopNotFoundException(owner.getShopId()));
    shop.setIsOpen(isOpen);
    return shop.getIsOpen();
  }

}
