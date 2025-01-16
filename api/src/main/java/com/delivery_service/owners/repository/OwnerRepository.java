package com.delivery_service.owners.repository;

import com.delivery_service.owners.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {


}
