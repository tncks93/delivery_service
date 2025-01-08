package com.delivery_service.owners.repository;

import com.delivery_service.owners.entity.Shop;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class OwnerShopRepository {

  private ConcurrentMap<Integer, Shop> storage = new ConcurrentHashMap<>();
  private AtomicInteger shopIdCounter = new AtomicInteger(0);

  public Shop save(Shop shop) {
    Integer shopId = shopIdCounter.incrementAndGet();
    shop.setId(shopId);
    log.debug("shop = {}", shop);
    storage.put(shopId, shop);

    return storage.get(shopId);
  }

  public Shop findByShopId(Integer shopId) {
    Shop shop = storage.get(shopId);
    log.debug("shop = {}", shop);

    return shop;
  }

  public Shop update(Integer shopId, Shop shop) {
    log.debug("shop = {}", shop);

    storage.replace(shopId, shop);
    return storage.get(shopId);
  }

  public Boolean updateIsOpen(Integer shopId, Boolean isOpen) {
    Shop shop = storage.get(shopId);
    shop.setIsOpen(isOpen);
    log.debug("shop.isOpen = {}", shop.getIsOpen());

    return shop.getIsOpen();
  }

  public void clear() {
    storage.clear();
    shopIdCounter.set(0);
  }
}
