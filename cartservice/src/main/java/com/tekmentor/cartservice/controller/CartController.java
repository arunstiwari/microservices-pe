package com.tekmentor.cartservice.controller;

import com.tekmentor.cartservice.model.Cart;
import com.tekmentor.cartservice.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/carts")
    public ResponseEntity createCart(@RequestBody Cart cart){
        log.info("create cart {}",cart);
        Cart createdCart =  cartService.createCart(cart);
        return new ResponseEntity(createdCart, HttpStatus.CREATED);
    }

    @GetMapping("/carts/{id}")
    public ResponseEntity fetchSpecificCart(@PathVariable("id") long id){
        Cart cart = cartService.fetchCartById(id);
        return new ResponseEntity(cart, HttpStatus.OK);
    }


    @PutMapping("/carts/{id}")
    public ResponseEntity addItemsToCart( @RequestBody Cart cart,@PathVariable("id") long id){
        log.info("update cart:  {}, id : {} ",cart,id);
        Cart createdCart =  cartService.updateCart(id,cart);
        return new ResponseEntity(createdCart, HttpStatus.CREATED);
    }

    @DeleteMapping("/carts/{id}")
    public ResponseEntity deleteCartWithId(@PathVariable("id") long id){
        log.info(" Going to delete cart with id {}",id);
        cartService.deleteCartById(id);
        return new ResponseEntity("Deleted the cart item", HttpStatus.OK);
    }
}
