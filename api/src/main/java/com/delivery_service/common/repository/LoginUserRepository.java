package com.delivery_service.common.repository;

import com.delivery_service.common.entity.LoginUser;
import com.delivery_service.common.enumeration.UserRole;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, String> {

  public Optional<LoginUser> findByRoleAndUserId(UserRole role, Integer userId);

}
