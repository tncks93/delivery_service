package com.delivery_service.facade;

import com.delivery_service.dto.LoginUserInfo;
import com.delivery_service.entity.LoginUser;
import com.delivery_service.entity.Owner;
import com.delivery_service.entity.Shop;
import com.delivery_service.enumeration.UserRole;
import com.delivery_service.service.LoginUserInfoCacheService;
import com.delivery_service.service.LoginUserService;
import com.delivery_service.service.owner.OwnerService;
import com.delivery_service.service.owner.OwnerShopService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Component
@Slf4j
public class OwnerShopFacade {

  private final OwnerService ownerService;
  private final OwnerShopService ownerShopService;
  private final LoginUserService loginUserService;
  private final LoginUserInfoCacheService loginUserInfoCacheService;

  @Transactional
  public Shop addShop(Owner owner, Shop shop) {
    log.debug("Thread={}", Thread.currentThread().getName());
    Shop savedShop = ownerShopService.addShop(owner, shop);
    owner.setShopId(savedShop.getId());
    ownerService.updateOwner(owner);
    LoginUser loginOwner = loginUserService.getLoginUserByRoleAndUserId(UserRole.Owner,
        owner.getId());
    loginUserInfoCacheService.saveLoginUserInfo(loginOwner.getToken(),
        new LoginUserInfo<>(loginOwner, owner));

    return savedShop;
  }


  public Shop getShop(Owner owner) {
    return ownerShopService.getShop(owner);
  }

  public Shop updateShop(Owner owner, Shop shop) {
    return ownerShopService.updateShop(owner, shop);
  }

  public Boolean updateShopStatus(Owner owner, Boolean isOpen) {
    return ownerShopService.updateShopStatus(owner, isOpen);
  }


}
