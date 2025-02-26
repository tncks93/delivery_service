package com.delivery_service.repository.owner;

import com.delivery_service.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerShopRepository extends JpaRepository<Shop, Integer> {


}
