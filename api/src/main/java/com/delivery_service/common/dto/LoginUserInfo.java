package com.delivery_service.common.dto;

import com.delivery_service.common.entity.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoginUserInfo<T> {

  private LoginUser loginUser;
  private T user;


}
