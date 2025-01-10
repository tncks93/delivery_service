package com.delivery_service.owners.service;

import com.delivery_service.owners.entity.Owner;
import com.delivery_service.owners.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OwnerService {

  private final OwnerRepository ownerRepository;

  public Owner getOwner(Integer ownerId) {
    return ownerRepository.findById(ownerId);
  }

  public void updateOwner(Owner owner) {
    ownerRepository.update(owner);
  }
}
