package com.contract.domain;

import java.util.Date;

public class Agreement {
    private String agreementId;

    private String agreementName;

    private String agreementType;

    private Boolean agreementInnovation;

    private String agreementClient;

    private String agreementAmount;

    private Date agreementSignDate;

    private String agreementProvider;

    private String agreementUploadType;

    private String agreementUploadAdmin;

    private Date agreementUploadDate;

    private Boolean agreementDelete;

    private String agreementExtend;

    private String agreementText;

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

    public String getAgreementAmount() {
        return agreementAmount;
    }

    public void setAgreementAmount(String agreementAmount) {
        this.agreementAmount = agreementAmount == null ? null : agreementAmount.trim();
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
}