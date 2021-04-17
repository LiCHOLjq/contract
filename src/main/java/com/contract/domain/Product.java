package com.contract.domain;

public class Product {
    private String productId;

    private String productAgreement;

    private String productType;

    private String productSeries;

    private String productModel;

    private Double productNumber;

    private Integer productSort;

    private Dictionary productTypeObj;

    private Dictionary productSeriesObj;

    public Dictionary getProductSeriesObj() {
        return productSeriesObj;
    }

    public void setProductSeriesObj(Dictionary productSeriesObj) {
        this.productSeriesObj = productSeriesObj;
    }

    public Dictionary getProductTypeObj() {
        return productTypeObj;
    }

    public void setProductTypeObj(Dictionary productTypeObj) {
        this.productTypeObj = productTypeObj;
    }

    public String getProductSeries() {
        return productSeries;
    }

    public void setProductSeries(String productSeries) {
        this.productSeries = productSeries == null ? null : productSeries.trim();;
    }

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

    public Integer getProductSort() {
        return productSort;
    }

    public void setProductSort(Integer productSort) {
        this.productSort = productSort;
    }
}