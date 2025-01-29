package com.delivery_service.customers.service;

import com.delivery_service.customers.entity.Customer;
import com.delivery_service.customers.repository.CustomerRepository;
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
