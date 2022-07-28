package com.learning.os.api.service;

import com.learning.os.api.common.Payment;
import com.learning.os.api.common.TransactionRequest;
import com.learning.os.api.entity.Order;
import com.learning.os.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public Order saveOrder(TransactionRequest request){
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        // to make a rest api call for fetching to payment-service
        return repository.save(order);
    }
}
