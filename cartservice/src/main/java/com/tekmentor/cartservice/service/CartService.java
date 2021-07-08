package com.tekmentor.cartservice.service;

import com.tekmentor.cartservice.config.CartConfig;
import com.tekmentor.cartservice.exception.CartNotFoundException;
import com.tekmentor.cartservice.model.Cart;
import com.tekmentor.cartservice.model.Customer;
import com.tekmentor.cartservice.model.Product;
import com.tekmentor.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartConfig config;

    @Autowired
    private RestTemplate restTemplate;

    public Cart createCart(Cart cart) {
        Customer customer = restTemplate.getForObject(config.getCustomerservice() + "/customers/{id}"  , Customer.class,cart.getCustomerId());
         cart.setCustomerId(customer.getId());
        cart.getCartItems().stream().forEach(cartItem -> {
            Product product = restTemplate.getForObject(config.getProductservice() + "/products/{id}" , Product.class, cartItem.getProductId());
            cartItem.setProductId(product.getId());
            cartItem.setCart(cart);
        });
        Cart savedCart = cartRepository.save(cart);
        return savedCart;
    }

    public Cart updateCart(long id, Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart fetchCartById(long id) {
        return cartRepository
                .findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart with id " + id + " does not exist"));
    }

    public void deleteCartById(long id) {
        cartRepository.deleteById(id);
    }
}
