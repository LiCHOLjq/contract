package com.contract.service;

import com.contract.domain.Agreement;
import com.contract.domain.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getCartByAdmin(String adminId);
    void addToCart(String adminId, Agreement agreement);
    void  addToCartByPage(String adminId,Agreement agreement,int currentPage, int pageSize,String sort);
    void  addToCartBySearch(String adminId,Agreement agreement,String sort);
    void delFromCart(String adminId, Agreement agreement);
    void delById(Cart cart);
    void clearCart(String adminId);
}
