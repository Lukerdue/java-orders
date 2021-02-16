package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);
    List<Customer> findAllCustomers();
    Customer getCustomerById(long id);
    List<Customer> custNameLike(String name);
}
