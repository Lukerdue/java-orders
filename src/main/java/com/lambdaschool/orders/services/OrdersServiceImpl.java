package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
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
    @Autowired
    private PaymentRepository payrepo;

    @Transactional
    @Override
    public Order save(Order temporder) {
        Order neworder = new Order();
        if(temporder.getOrdnum() != 0){
            orderrepo.findById(temporder.getOrdnum())
                    .orElseThrow(()->new EntityNotFoundException("order number "+temporder.getOrdnum() + "could not be found"));
            neworder.setOrdnum(temporder.getOrdnum());
        }

        neworder.setCustomer(temporder.getCustomer());
        neworder.setOrderdescription(temporder.getOrderdescription());
        neworder.setOrdamount(temporder.getOrdamount());
        neworder.setAdvanceamount(temporder.getAdvanceamount());

        for(Payment pay:temporder.getPayments()){
            Payment p = payrepo.findById(pay.getPaymentid())
                    .orElseThrow(()->new EntityNotFoundException("payment with id: "+pay.getPaymentid()+" could not be found"));
            neworder.getPayments().add(p);
        }

        return orderrepo.save(neworder);
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

    @Override
    public void deleteById(long id) {
        if(orderrepo.findById(id).isPresent()){
            orderrepo.deleteById(id);
        }
        else{
            throw new EntityNotFoundException("order with number: "+id+"could not be found");
        }
    }
}
