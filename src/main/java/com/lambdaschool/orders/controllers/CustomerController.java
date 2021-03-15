package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @DeleteMapping(value="customer/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long id)
    {
        custservice.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="/customer", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewCustomer(@Validated @RequestBody Customer customer)
    {
        customer.setCustcode(0);
        customer = custservice.save(customer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(customer, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value="/customer/{id}", produces = {"application/json"}, consumes={"application/json"})
    public ResponseEntity<?> replaceCustomer(@Validated @RequestBody Customer customer, @PathVariable long id)
    {
        customer.setCustcode(id);
        customer = custservice.save(customer);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    @PatchMapping(value="/customer/{id}", produces = {"application/json"}, consumes={"application/json"})
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable long id)
    {
        Customer newcustomer = custservice.updateCustomer(id, customer);

        return new ResponseEntity<>(newcustomer, HttpStatus.OK);
    }
}
