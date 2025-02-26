package com.delivery_service.repository;

import com.delivery_service.entity.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

  public List<Menu> findAllByShopId(int shopId);
}
