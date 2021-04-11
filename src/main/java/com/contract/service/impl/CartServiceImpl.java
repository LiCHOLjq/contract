package com.contract.service.impl;

import com.contract.domain.Agreement;
import com.contract.domain.Cart;
import com.contract.mapper.AgreementMapper;
import com.contract.mapper.CartMapper;
import com.contract.service.CartService;
import com.contract.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private AgreementMapper agreementMapper;

    @Override
    public List<Cart> getCartByAdmin(String adminId) {
        List<Cart> cartList = cartMapper.selectByAdmin(adminId);
        for(Cart cart : cartList){
            cart.setCartAgreementObj(agreementMapper.selectByPrimaryKey(cart.getCartAgreement()));
        }
        return cartList;

    }

    @Override
    public void addToCart(String adminId, Agreement agreement) {
        Cart cart = cartMapper.selectByAdminAgreement(adminId,agreement.getAgreementId());
        if(cart==null){
            cart = new Cart();
            cart.setCartId(UUIDUtil.getUUID());
            cart.setCartAdmin(adminId);
            cart.setCartAgreement(agreement.getAgreementId());
            cartMapper.insertSelective(cart);
        }
    }
    @Override
    public void delFromCart(String adminId, Agreement agreement) {
        Cart cart = cartMapper.selectByAdminAgreement(adminId,agreement.getAgreementId());
        if(cart!=null){
            cartMapper.deleteByPrimaryKey(cart.getCartId());
        }
    }

    @Override
    public void delById(Cart cart) {
        cartMapper.deleteByPrimaryKey(cart.getCartId());
    }
}
