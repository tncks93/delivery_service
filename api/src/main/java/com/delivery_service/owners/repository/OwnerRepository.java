package com.delivery_service.owners.repository;

import com.delivery_service.owners.entity.Owner;
import jakarta.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerRepository {

  private final ConcurrentMap<Integer, Owner> owners = new ConcurrentHashMap<>();

  @PostConstruct
  public void setOwners() {
    Owner owner1 = new Owner(1);
    Owner owner2 = new Owner(2);

    owners.put(1, owner1);
    owners.put(2, owner2);
  }

  public Owner findById(Integer id) {
    return owners.get(id);
  }

  public void saveShopId(Owner owner, Integer shopId) {
    owners.get(owner.getId()).setShopId(shopId);
  }
}
