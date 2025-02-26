package com.delivery_service.service.owner;

import com.delivery_service.entity.Owner;
import com.delivery_service.exception.OwnerNotFoundException;
import com.delivery_service.repository.owner.OwnerRepository;
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
