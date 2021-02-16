package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService custservice;

    @GetMapping(value="/orders")
    public ResponseEntity<?> getAllCustomers()
    {
        List<Customer> custlist = custservice.findAllCustomers();
        return new ResponseEntity<>(custlist, HttpStatus.OK);
    }

    @GetMapping(value="/customer/{custid}")
    public ResponseEntity<?> getCustomerById(@PathVariable long custid)
    {
        Customer  customer= custservice.getCustomerById(custid);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping(value="/namelike/{name}")
    public ResponseEntity<?> getCustByNameLike(@PathVariable String name)
    {
        List<Customer> customers = custservice.custNameLike(name);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
}
