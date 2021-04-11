package com.contract.domain;

public class Cart {
    private String cartId;

    private String cartAdmin;

    private String cartAgreement;

    private Agreement cartAgreementObj;

    public Agreement getCartAgreementObj() {
        return cartAgreementObj;
    }

    public void setCartAgreementObj(Agreement cartAgreementObj) {
        this.cartAgreementObj = cartAgreementObj;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId == null ? null : cartId.trim();
    }

    public String getCartAdmin() {
        return cartAdmin;
    }

    public void setCartAdmin(String cartAdmin) {
        this.cartAdmin = cartAdmin == null ? null : cartAdmin.trim();
    }

    public String getCartAgreement() {
        return cartAgreement;
    }

    public void setCartAgreement(String cartAgreement) {
        this.cartAgreement = cartAgreement == null ? null : cartAgreement.trim();
    }
}