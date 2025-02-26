package com.delivery_service.service.owner;

import com.delivery_service.entity.Menu;
import com.delivery_service.repository.owner.OwnerMenuRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OwnerMenuService {

  private final OwnerMenuRepository repository;

  public Menu addMenu(Integer shopId, Menu menu) {
    menu.setShopId(shopId);
    return repository.save(menu);
  }

  public Menu updateMenu(Integer shopId, Integer menuId, Menu updatedMenu) {
    if (!menuId.equals(updatedMenu.getId())) {
      return null;
    }

    Optional<Menu> optionalMenu = repository.findById(menuId);
    if (optionalMenu.isPresent()) {
      if (!shopId.equals(updatedMenu.getShopId())) {
        return null;
      }
      Menu menu = optionalMenu.get();
      menu.setName(updatedMenu.getName());
      menu.setDescription(updatedMenu.getDescription());
      menu.setPrice(updatedMenu.getPrice());
      menu.setImage(updatedMenu.getImage());
    }

    return null;
  }


  public Boolean updateMenuStatus(Integer shopId, Integer menuId, Menu menuStatus) {
    if (!menuId.equals(menuStatus.getId())) {
      return null;
    }

    Optional<Menu> optionalMenu = repository.findById(menuId);
    if (optionalMenu.isPresent()) {
      if (!shopId.equals(menuStatus.getShopId())) {
        return null;
      }
      Menu menu = optionalMenu.get();
      menu.setIsOnSale(menuStatus.getIsOnSale());

      return menu.getIsOnSale();
    }

    throw new RuntimeException("exception");
  }

  public List<Menu> getAllMenus(Integer shopId) {
    return repository.findAllByShopId(shopId);
  }
}
