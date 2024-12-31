package com.delivery_service.owners.repository;

import com.delivery_service.owners.entity.Shop;

import java.util.Optional;

public interface ShopRepository {

    public void save(int ownerId, Shop shop);

    public Optional<Shop> findByOwnerId(int ownerId);

    public void update(int ownerId, Shop shop);

    public void updateIsOpen(int ownerId, boolean isOpen);


}
