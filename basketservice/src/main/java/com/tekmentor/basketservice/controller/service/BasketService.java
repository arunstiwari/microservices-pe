package com.tekmentor.basketservice.controller.service;

import com.tekmentor.basketservice.config.BasketConfig;
import com.tekmentor.basketservice.controller.model.*;
import com.tekmentor.basketservice.dto.CheckoutResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class BasketService {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private BasketConfig config;


    public CheckoutResponse checkout(long cartId, CreditCardInfo creditCardInfo){
        log.info("Entering checkout {} : {}",cartId, creditCardInfo);
        CheckoutResponse response = new CheckoutResponse();

        Cart cart = restTemplate.getForObject(config.getCartservice() + "/carts/" + cartId, Cart.class);
        log.info("cart {} ",cart);
        Order order = restTemplate.postForObject(config.getOrderservice() + "/orders", cart, Order.class);
        log.info("order {} ",order);
        response.setOrderNo(String.valueOf(order.getId()));
        response.setMessage("Order has been initiated");
        response.setStatus("ORDER_INITIATED");
        PaymentInfo paymentInfo = new PaymentInfo(creditCardInfo, order.getTotalValue(), cart.getCustomerId(), order.getId());
//        PaymentResponse paymentResponse = restTemplate.postForObject(config.getPaymentservice() + "/payments", paymentInfo, PaymentResponse.class);

//        if (paymentResponse.getPaymentStatus().equals("SUCCESS")) {
            response.setMessage("Payment is successfull");
            response.setStatus("PAYMENT_COMPLETED");

            log.info("Going to update the status ");
            restTemplate.put(config.getOrderservice()+"/orders/"+order.getId(),"CONFIRMED");

            response.setMessage("Order is confirmed");
            response.setStatus("ORDER_CONFIRMED");
            restTemplate.delete(config.getCartservice() + "/carts/" + cartId);
//        }else {
//            restTemplate.put(config.getOrderservice()+"/orders/"+order.getId(),"NOT_CONFIRMED");
//            response.setMessage("Payment is failed");
//            response.setStatus("ORDER_NOTCONFIRMED");
//        }

        return response;
    }
}
