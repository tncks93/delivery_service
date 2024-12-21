package com.delivery_service.owners.repository;

import com.delivery_service.owners.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Repository
public class TestShopRepository implements ShopRepository{
    private ConcurrentMap<Integer, Shop> STORAGE = new ConcurrentHashMap<>();
    private ConcurrentMap<Integer, Integer> ownerToShopId = new ConcurrentHashMap<>();//owner가 가지고 있는 shopId
    private AtomicInteger shopIdCounter = new AtomicInteger(0);


    @Override
    public void save(int ownerId, Shop shop) {
        int shopId = shopIdCounter.incrementAndGet();
        ownerToShopId.put(ownerId, shopId);
        shop.setId(shopId);

        STORAGE.put(shopId, shop);

    }

    @Override
    public Optional<Shop> findByOwnerId(int ownerId) {
        int shopId = ownerToShopId.get(ownerId);
        return Optional.ofNullable(STORAGE.get(shopId));


    }

    @Override
    public void update(int ownerId, Shop shop) {
        int shopId = ownerToShopId.get(ownerId);
        log.info("Updating shop {} with id {}", shop, shopId);
        STORAGE.replace(shopId, shop);


    }

    @Override
    public void updateIsOpen(int ownerId, boolean isOpen) {
        int shopId = ownerToShopId.get(ownerId);
        STORAGE.get(shopId).set_open(isOpen);
        log.info("updateIsOpen isOpen={}",isOpen);
    }

    public void clear() {
        STORAGE.clear();
        ownerToShopId.clear();
    }
}
