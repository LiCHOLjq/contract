package com.contract.domain;

import java.util.Date;
import java.util.List;

public class Agreement {
    private String agreementId;

    private String agreementName;

    private String agreementType;

    private Boolean agreementInnovation;

    private String agreementClient;

    private Double agreementAmount;

    private Date agreementSignDate;

    private String agreementProvider;

    private String agreementUploadType;

    private String agreementUploadAdmin;

    private Date agreementUploadDate;

    private Boolean agreementDelete;

    private String agreementExtend;

    private String agreementText;


    //查询
    private List<String> agreementTypeList;

    private Date agreementSignDateBegin;

    private String agreementSignDateBeginStr;

    private Date agreementSignDateEnd;

    private String agreementSignDateEndStr;

    private List<String> productTypeList;

    private String productModel;

    private Double agreementAmountBegin;

    private Double agreementAmountEnd;

    private Double productNumberBegin;

    private Double productNumberEnd;
    //注入
    private Dictionary agreementTypeObj;

    private String agreementSignDateStr;

    private String  agreementUploadDateStr;

    private List<Product> productList;

    private Boolean shareState;

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId == null ? null : agreementId.trim();
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName == null ? null : agreementName.trim();
    }

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType == null ? null : agreementType.trim();
    }

    public Boolean getAgreementInnovation() {
        return agreementInnovation;
    }

    public void setAgreementInnovation(Boolean agreementInnovation) {
        this.agreementInnovation = agreementInnovation;
    }

    public String getAgreementClient() {
        return agreementClient;
    }

    public void setAgreementClient(String agreementClient) {
        this.agreementClient = agreementClient == null ? null : agreementClient.trim();
    }

    public Double getAgreementAmount() {
        return agreementAmount;
    }

    public void setAgreementAmount(Double agreementAmount) {
        this.agreementAmount = agreementAmount;
    }

    public Date getAgreementSignDate() {
        return agreementSignDate;
    }

    public void setAgreementSignDate(Date agreementSignDate) {
        this.agreementSignDate = agreementSignDate;
    }

    public String getAgreementProvider() {
        return agreementProvider;
    }

    public void setAgreementProvider(String agreementProvider) {
        this.agreementProvider = agreementProvider == null ? null : agreementProvider.trim();
    }

    public String getAgreementUploadType() {
        return agreementUploadType;
    }

    public void setAgreementUploadType(String agreementUploadType) {
        this.agreementUploadType = agreementUploadType == null ? null : agreementUploadType.trim();
    }

    public String getAgreementUploadAdmin() {
        return agreementUploadAdmin;
    }

    public void setAgreementUploadAdmin(String agreementUploadAdmin) {
        this.agreementUploadAdmin = agreementUploadAdmin == null ? null : agreementUploadAdmin.trim();
    }

    public Date getAgreementUploadDate() {
        return agreementUploadDate;
    }

    public void setAgreementUploadDate(Date agreementUploadDate) {
        this.agreementUploadDate = agreementUploadDate;
    }

    public Boolean getAgreementDelete() {
        return agreementDelete;
    }

    public void setAgreementDelete(Boolean agreementDelete) {
        this.agreementDelete = agreementDelete;
    }

    public String getAgreementExtend() {
        return agreementExtend;
    }

    public void setAgreementExtend(String agreementExtend) {
        this.agreementExtend = agreementExtend == null ? null : agreementExtend.trim();
    }

    public String getAgreementText() {
        return agreementText;
    }

    public void setAgreementText(String agreementText) {
        this.agreementText = agreementText == null ? null : agreementText.trim();
    }

    public List<String> getAgreementTypeList() {
        return agreementTypeList;
    }

    public void setAgreementTypeList(List<String> agreementTypeList) {
        this.agreementTypeList = agreementTypeList;
    }

    public Date getAgreementSignDateBegin() {
        return agreementSignDateBegin;
    }

    public void setAgreementSignDateBegin(Date agreementSignDateBegin) {
        this.agreementSignDateBegin = agreementSignDateBegin;
    }

    public String getAgreementSignDateBeginStr() {
        return agreementSignDateBeginStr;
    }

    public void setAgreementSignDateBeginStr(String agreementSignDateBeginStr) {
        this.agreementSignDateBeginStr = agreementSignDateBeginStr;
    }

    public Date getAgreementSignDateEnd() {
        return agreementSignDateEnd;
    }

    public void setAgreementSignDateEnd(Date agreementSignDateEnd) {
        this.agreementSignDateEnd = agreementSignDateEnd;
    }

    public String getAgreementSignDateEndStr() {
        return agreementSignDateEndStr;
    }

    public void setAgreementSignDateEndStr(String agreementSignDateEndStr) {
        this.agreementSignDateEndStr = agreementSignDateEndStr;
    }

    public List<String> getProductTypeList() {
        return productTypeList;
    }

    public void setProductTypeList(List<String> productTypeList) {
        this.productTypeList = productTypeList;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public Dictionary getAgreementTypeObj() {
        return agreementTypeObj;
    }

    public void setAgreementTypeObj(Dictionary agreementTypeObj) {
        this.agreementTypeObj = agreementTypeObj;
    }

    public String getAgreementSignDateStr() {
        return agreementSignDateStr;
    }

    public void setAgreementSignDateStr(String agreementSignDateStr) {
        this.agreementSignDateStr = agreementSignDateStr;
    }


    public String getAgreementUploadDateStr() {
        return agreementUploadDateStr;
    }

    public void setAgreementUploadDateStr(String agreementUploadDateStr) {
        this.agreementUploadDateStr = agreementUploadDateStr;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Boolean getShareState() {
        return shareState;
    }

    public void setShareState(Boolean shareState) {
        this.shareState = shareState;
    }

    public Double getAgreementAmountBegin() {
        return agreementAmountBegin;
    }

    public void setAgreementAmountBegin(Double agreementAmountBegin) {
        this.agreementAmountBegin = agreementAmountBegin;
    }

    public Double getAgreementAmountEnd() {
        return agreementAmountEnd;
    }

    public void setAgreementAmountEnd(Double agreementAmountEnd) {
        this.agreementAmountEnd = agreementAmountEnd;
    }

    public Double getProductNumberBegin() {
        return productNumberBegin;
    }

    public void setProductNumberBegin(Double productNumberBegin) {
        this.productNumberBegin = productNumberBegin;
    }

    public Double getProductNumberEnd() {
        return productNumberEnd;
    }

    public void setProductNumberEnd(Double productNumberEnd) {
        this.productNumberEnd = productNumberEnd;
    }
}