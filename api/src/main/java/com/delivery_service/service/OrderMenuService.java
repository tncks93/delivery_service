package com.delivery_service.service;

import com.delivery_service.entity.OrderMenu;
import com.delivery_service.repository.OrderMenuRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderMenuService {

  private final OrderMenuRepository orderMenuRepository;

  public List<OrderMenu> saveOrderMenus(List<OrderMenu> orderMenus) {
    return orderMenuRepository.saveAll(orderMenus);
  }

}
