package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrdersService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService orderservice;

    @GetMapping(value="/")
    public ResponseEntity<?> getAllOrders()
    {
        List<Order> orders = orderservice.findAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping(value="/order/{ordnum}")
    public ResponseEntity<?> getOrderById(@PathVariable long ordnum)
    {
        Order order = orderservice.findOrderById(ordnum);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    //
}
