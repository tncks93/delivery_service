package com.delivery_service.facade;

import com.delivery_service.dto.LoginUserInfo;
import com.delivery_service.entity.Customer;
import com.delivery_service.entity.LoginUser;
import com.delivery_service.entity.Owner;
import com.delivery_service.enumeration.UserRole;
import com.delivery_service.service.LoginUserInfoCacheService;
import com.delivery_service.service.LoginUserService;
import com.delivery_service.service.customer.CustomerService;
import com.delivery_service.service.owner.OwnerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class LoginUserInfoFacade {

  private final LoginUserInfoCacheService loginUserInfoCacheService;
  private final LoginUserService loginUserService;
  private final OwnerService ownerService;
  private final CustomerService customerService;

  public <T> LoginUserInfo<T> getLoginUserInfo(UserRole userRole, String token, Class<T> clazz) {

    LoginUserInfo<T> cache = loginUserInfoCacheService.getLoginUserInfo(token, userRole);
    if (cache != null) {
      //캐시에 저장되어있을 때
      log.debug("cache hit key={}", token);
      return cache;
    }

    //캐시에 없을 때 db에서 select
    log.debug("cache miss key={}", token);
    LoginUser loginUser = loginUserService.getLoginUser(token);

    T user = null;
    switch (userRole) {
      case Owner:
        Owner owner = ownerService.getOwner(loginUser.getUserId());
        user = clazz.cast(owner);

      case Customer:
        Customer customer = customerService.getCustomer(loginUser.getUserId());
        user = clazz.cast(customer);
        break;

      case Rider:
        break;

    }

    return new LoginUserInfo<T>(loginUser, user);

  }

  public <T> T saveLoginUserInfo(UserRole userRole, String token, Class<T> clazz, Integer userId) {
    LoginUser loginUser = new LoginUser(token, userRole, userId);

    //db저장
    loginUserService.saveLoginUser(loginUser);
    T user = null;
    switch (userRole) {
      case Owner:
        user = clazz.cast(ownerService.getOwner(userId));
        break;
      case Customer:
        user = clazz.cast(customerService.getCustomer(userId));
        break;
      case Rider:
        break;

    }
    log.debug("user={}", user);

    LoginUserInfo<T> loginUserInfo = new LoginUserInfo<>(loginUser, user);

    //캐시저장
    loginUserInfoCacheService.saveLoginUserInfo(token, loginUserInfo);
    return user;

  }
}
