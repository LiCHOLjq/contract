package com.contract.domain;

public class Product {
    private String productId;

    private String productAgreement;

    private String productType;

    private String productModel;

    private Double productNumber;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductAgreement() {
        return productAgreement;
    }

    public void setProductAgreement(String productAgreement) {
        this.productAgreement = productAgreement == null ? null : productAgreement.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel == null ? null : productModel.trim();
    }

    public Double getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Double productNumber) {
        this.productNumber = productNumber;
    }
}