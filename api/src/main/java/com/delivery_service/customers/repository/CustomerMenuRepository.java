package com.delivery_service.customers.repository;

import com.delivery_service.owners.entity.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerMenuRepository extends JpaRepository<Menu, Integer> {

  public List<Menu> findAllByShopId(int shopId);
}
