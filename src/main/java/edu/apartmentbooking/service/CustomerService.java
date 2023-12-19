package edu.viviane.apartmentbookingapp.service;

import edu.viviane.apartmentbookingapp.model.Customer;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> findByUserId(Long userId);

    Optional<Customer> findByUsername(String username);
}
