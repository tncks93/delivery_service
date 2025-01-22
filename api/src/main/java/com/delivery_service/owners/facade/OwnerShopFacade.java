package com.delivery_service.owners.facade;

import com.delivery_service.common.UserRole;
import com.delivery_service.common.dto.LoginUserInfo;
import com.delivery_service.common.entity.LoginUser;
import com.delivery_service.common.service.LoginUserInfoCacheService;
import com.delivery_service.common.service.LoginUserService;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.entity.Shop;
import com.delivery_service.owners.service.OwnerService;
import com.delivery_service.owners.service.OwnerShopService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OwnerShopFacade {

  private final OwnerService ownerService;
  private final OwnerShopService ownerShopService;
  private final LoginUserService loginUserService;
  private final LoginUserInfoCacheService loginUserInfoCacheService;

  public Shop addShop(Owner owner, Shop shop) {
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
