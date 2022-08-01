package com.learning.os.api.service;

import com.learning.os.api.common.Payment;
import com.learning.os.api.common.TransactionRequest;
import com.learning.os.api.common.TransactionResponse;
import com.learning.os.api.entity.Order;
import com.learning.os.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    // Injecting rest template
    @Autowired
    private RestTemplate template;
    public TransactionResponse saveOrder(TransactionRequest request){
        String response = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        // to make a rest api call for fetching to payment-service
        Payment paymentResponse = template.postForObject("http://localhost:9191/payment/doPayment",payment,Payment.class);
        response = paymentResponse.getPaymentStatus().equals("success")?"Payment processed successfully and Order placed:)" : "Payment Failure in paymentApi";
        repository.save(order);
        return new TransactionResponse(order, paymentResponse.getAmount(),paymentResponse.getTransactionId(),response);
    }
}
