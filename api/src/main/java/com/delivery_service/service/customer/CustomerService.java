package com.delivery_service.service.customer;

import com.delivery_service.entity.Customer;
import com.delivery_service.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;

  public Customer getCustomer(Integer customerId) {
    return repository.findById(customerId).get();
  }

  public Customer updateCustomer(Customer customer) {
    return repository.save(customer);
  }
}
