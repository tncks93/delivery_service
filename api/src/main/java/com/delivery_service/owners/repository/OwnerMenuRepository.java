package com.delivery_service.owners.repository;

import com.delivery_service.owners.entity.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerMenuRepository extends JpaRepository<Menu, Integer> {

  List<Menu> findAllByShopId(Integer shopId);
}
