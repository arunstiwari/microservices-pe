package com.tekmentor.basketservice.controller;

import com.tekmentor.basketservice.controller.model.CheckoutDTO;
import com.tekmentor.basketservice.controller.service.BasketService;
import com.tekmentor.basketservice.dto.CheckoutResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping("/checkout")
    public ResponseEntity checkoutCart(@RequestBody CheckoutDTO checkoutDTO){
        log.info(" checkoutDTO : {}",checkoutDTO);
        CheckoutResponse response = basketService.checkout(checkoutDTO.getCartId(), checkoutDTO.getCreditCardInfo());
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}
