package com.delivery_service.service;

import com.delivery_service.entity.Menu;
import com.delivery_service.repository.MenuRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuService {

  private final MenuRepository repository;

  public List<Menu> getAllMenus(Integer shopId) {
    return repository.findAllByShopId(shopId);
  }

  public Menu getMenu(Integer menuId) {
    return repository.findById(menuId).get();
  }

}
