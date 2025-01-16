package com.delivery_service.owners.repository;

import com.delivery_service.owners.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerShopRepository extends JpaRepository<Shop, Integer> {


}
