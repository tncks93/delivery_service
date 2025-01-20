package com.delivery_service.common.service;

import com.delivery_service.common.entity.LoginUser;
import com.delivery_service.common.repository.LoginUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginUserService {

  private final LoginUserRepository loginUserRepository;

  public LoginUser saveLoginUser(LoginUser loginUser) {
    return loginUserRepository.save(loginUser);
  }

  public LoginUser getLoginUser(String token) {
    return loginUserRepository.findById(token).get();
  }

}
