package com.delivery_service.owners.service;

import com.delivery_service.common.exception.OwnerNotFoundException;
import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OwnerService {

  private final OwnerRepository repository;

  public Owner getOwner(Integer ownerId) {
    return repository.findById(ownerId).orElseThrow(() -> new OwnerNotFoundException(ownerId));
  }

  public void updateOwner(Owner owner) {
    repository.save(owner);
  }
}
