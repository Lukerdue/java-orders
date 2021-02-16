package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository orderrepo;

    @Transactional
    @Override
    public Order save(Order order) {
        return orderrepo.save(order);
    }
}
