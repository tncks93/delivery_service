package com.delivery_service.owners.service;

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
      return null;
    }

    Shop savedShop = repository.save(shop);

    return savedShop;
  }

  public Shop getShop(Owner owner) {
    if (owner.getShopId() == null) {
      return null;
    }
    return repository.findById(owner.getShopId()).get();
  }

  public Shop updateShop(Owner owner, Shop updatedShop) {
    if (!updatedShop.getId().equals(owner.getShopId())) {
      return null;
    }
    Optional<Shop> optionalShop = repository.findById(owner.getId());
    if (optionalShop.isPresent()) {
      Shop shop = optionalShop.get();
      shop.setName(updatedShop.getName());
      shop.setDescription(updatedShop.getDescription());
      //TODO address가 바뀌면 latitude,longitude도 바뀌어야 한다.
      shop.setAddress(updatedShop.getAddress());
      shop.setCategory(updatedShop.getCategory());
      shop.setImage(updatedShop.getImage());

      return shop;
    }

    return null;

  }

  public Boolean updateShopStatus(Owner owner, Boolean isOpen) {
    Shop shop = repository.findById(owner.getShopId()).get();
    shop.setIsOpen(isOpen);
    return shop.getIsOpen();
  }

}
