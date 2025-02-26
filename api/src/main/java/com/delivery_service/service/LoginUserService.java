package com.delivery_service.service;

import com.delivery_service.entity.LoginUser;
import com.delivery_service.enumeration.UserRole;
import com.delivery_service.exception.LoginRequiredException;
import com.delivery_service.repository.LoginUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginUserService {

  private final LoginUserRepository repository;

  public LoginUser saveLoginUser(LoginUser loginUser) {
    return repository.save(loginUser);
  }

  public LoginUser getLoginUser(String token) {
    return repository.findById(token)
        .orElseThrow(() -> new LoginRequiredException("login required"));
  }

  public LoginUser getLoginUserByRoleAndUserId(UserRole role, Integer userId) {
    return repository.findByRoleAndUserId(role, userId)
        .orElseThrow(() -> new LoginRequiredException("login required"));
  }

}
