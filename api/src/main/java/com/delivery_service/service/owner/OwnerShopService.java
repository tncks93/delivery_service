package com.delivery_service.service.owner;

import com.delivery_service.entity.Owner;
import com.delivery_service.entity.Shop;
import com.delivery_service.exception.MismatchedShopOwnerException;
import com.delivery_service.exception.ShopAlreadyExistsException;
import com.delivery_service.exception.ShopNotFoundException;
import com.delivery_service.repository.owner.OwnerShopRepository;
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

    return optionalShop.map(shop -> {
      shop.setName(updatedShop.getName());
      shop.setDescription(updatedShop.getDescription());
      //TODO address가 바뀌면 latitude,longitude도 바뀌어야 한다.
      shop.setAddress(updatedShop.getAddress());
      shop.setCategory(updatedShop.getCategory());
      shop.setImage(updatedShop.getImage());
      return shop;
    }).orElseThrow(() -> new ShopNotFoundException(owner.getId()));
  }

  public Boolean updateShopStatus(Owner owner, Boolean isOpen) {

    return repository.findById(owner.getShopId()).map(shop -> {
      shop.setIsOpen(isOpen);
      return shop.getIsOpen();
    }).orElseThrow(() -> new ShopNotFoundException(owner.getShopId()));
  }

}
