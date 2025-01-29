package com.delivery_service.customers.service;

import com.delivery_service.customers.repository.CustomerMenuRepository;
import com.delivery_service.owners.entity.Menu;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerMenuService {

  private final CustomerMenuRepository repository;

  public List<Menu> getAllMenus(Integer shopId) {
    return repository.findAllByShopId(shopId);
  }

  public Menu getMenu(Integer menuId) {
    return repository.findById(menuId).get();
  }

}
