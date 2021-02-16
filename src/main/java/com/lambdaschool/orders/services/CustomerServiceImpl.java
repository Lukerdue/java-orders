package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value="customerServices")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomersRepository custrepo;

    @Transactional
    @Override
    public Customer save(Customer customer) {
        return custrepo.save(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> custList = new ArrayList<>();
        custrepo.findAll().iterator().forEachRemaining(custList::add);
        return custList;
    }

    @Override
    public Customer getCustomerById(long id) {
        Customer customer = custrepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Customer with id: "+id+" not found"));
        return customer;
    }

    @Override
    public List<Customer> custNameLike(String name) {
        List<Customer> customers = custrepo.findCustomersByCustnameContainingIgnoreCase(name);
        return customers;
    }
}
