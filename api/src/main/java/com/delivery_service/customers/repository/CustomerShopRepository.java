package com.delivery_service.customers.repository;

import com.delivery_service.owners.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerShopRepository extends JpaRepository<Shop, Integer> {

}
