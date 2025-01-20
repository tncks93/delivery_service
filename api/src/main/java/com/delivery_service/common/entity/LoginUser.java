package com.delivery_service.common.entity;

import com.delivery_service.common.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {

  @Id
  private String token;
  @Enumerated(EnumType.STRING)
  private UserRole role;
  private Integer userId;

}
