package com.delivery_service.common.facade;

import com.delivery_service.common.UserRole;
import com.delivery_service.common.dto.LoginUserInfo;
import com.delivery_service.common.entity.LoginUser;
import com.delivery_service.common.service.LoginUserInfoCacheService;
import com.delivery_service.common.service.LoginUserService;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.service.OwnerService;
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
    if (loginUser != null) {
      T user = null;
      switch (userRole) {
        case Owner:
          Owner owner = ownerService.getOwner(loginUser.getUserId());
          user = clazz.cast(owner);

        case Customer:

          break;

        case Rider:
          break;

      }

      return new LoginUserInfo<T>(loginUser, user);

    }

    //캐시,db 둘 다 없을때
    return null;
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
