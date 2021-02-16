package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository orderrepo;

    @Transactional
    @Override
    public Order save(Order order) {
        return orderrepo.save(order);
    }

    @Override
    public List<Order> findAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderrepo.findAll().iterator().forEachRemaining(orders::add);
        return orders;
    }

    @Override
    public Order findOrderById(long id) {
        Order order = orderrepo.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Order with number: "+id+" not found"));
        return order;
    }
}
