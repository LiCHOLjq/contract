package com.contract.service.impl;

import com.contract.domain.Admin;
import com.contract.domain.Agreement;
import com.contract.domain.Cart;
import com.contract.mapper.AgreementMapper;
import com.contract.mapper.CartMapper;
import com.contract.service.CartService;
import com.contract.util.BigDecimalUtil;
import com.contract.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private AgreementMapper agreementMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

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
    public void addToCartByPage(String adminId, Agreement agreement, int currentPage, int pageSize, String sort) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            agreement.setAgreementSignDateBegin(sdf.parse(agreement.getAgreementSignDateBeginStr()+" 00:00:00"));
        } catch (ParseException e) {
            agreement.setAgreementSignDateBegin(null);
        }
        try {
            agreement.setAgreementSignDateEnd(sdf.parse(agreement.getAgreementSignDateEndStr()+" 23:59:59"));
        } catch (ParseException e) {
            agreement.setAgreementSignDateEnd(null);
        }
        if(agreement.getAgreementAmountBegin()!=null){
            agreement.setAgreementAmountBegin(BigDecimalUtil.mul(agreement.getAgreementAmountBegin(),10000.0));
        }
        if(agreement.getAgreementAmountEnd()!=null){
            agreement.setAgreementAmountEnd(BigDecimalUtil.mul(agreement.getAgreementAmountEnd(),10000.0));
        }
        PageHelper.startPage(currentPage,pageSize);
        if(agreement.getProductList()!=null&&agreement.getProductList().size()==0){
            agreement.setProductList(null);
        }
        List<Agreement> agreementList = agreementMapper.selectBySearch(agreement,sort);

        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            for(Agreement agreement1 : agreementList){
                if(!agreement1.getAgreementDelete()){
                    Cart cart = cartMapper.selectByAdminAgreement(adminId,agreement1.getAgreementId());
                    if(cart==null){
                        cart = new Cart();
                        cart.setCartId(UUIDUtil.getUUID());
                        cart.setCartAdmin(adminId);
                        cart.setCartAgreement(agreement1.getAgreementId());
                        cartMapper.insertSelective(cart);
                    }
                }
            }
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
    }

    @Override
    public void addToCartBySearch(String adminId, Agreement agreement, String sort) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            agreement.setAgreementSignDateBegin(sdf.parse(agreement.getAgreementSignDateBeginStr()+" 00:00:00"));
        } catch (ParseException e) {
            agreement.setAgreementSignDateBegin(null);
        }
        try {
            agreement.setAgreementSignDateEnd(sdf.parse(agreement.getAgreementSignDateEndStr()+" 23:59:59"));
        } catch (ParseException e) {
            agreement.setAgreementSignDateEnd(null);
        }
        if(agreement.getAgreementAmountBegin()!=null){
            agreement.setAgreementAmountBegin(BigDecimalUtil.mul(agreement.getAgreementAmountBegin(),10000.0));
        }
        if(agreement.getAgreementAmountEnd()!=null){
            agreement.setAgreementAmountEnd(BigDecimalUtil.mul(agreement.getAgreementAmountEnd(),10000.0));
        }
        if(agreement.getProductList()!=null&&agreement.getProductList().size()==0){
            agreement.setProductList(null);
        }
        List<Agreement> agreementList = agreementMapper.selectBySearch(agreement,sort);
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            for(Agreement agreement1 : agreementList){
                if(!agreement1.getAgreementDelete()){
                    Cart cart = cartMapper.selectByAdminAgreement(adminId,agreement1.getAgreementId());
                    if(cart==null){
                        cart = new Cart();
                        cart.setCartId(UUIDUtil.getUUID());
                        cart.setCartAdmin(adminId);
                        cart.setCartAgreement(agreement1.getAgreementId());
                        cartMapper.insertSelective(cart);
                    }
                }
            }
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
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

    @Override
    public void clearCart(String adminId) {
        cartMapper.deleteByAdminId(adminId);
    }
}
