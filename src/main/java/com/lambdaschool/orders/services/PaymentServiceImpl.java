package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentServiceImpl implements PaymentServices {
    @Autowired
    private PaymentRepository paymentrepo;

    @Override
    public Payment save(Payment payment) {
        return paymentrepo.save(payment);
    }
}
