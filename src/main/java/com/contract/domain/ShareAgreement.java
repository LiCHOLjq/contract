package com.contract.domain;

import java.util.Date;

public class ShareAgreement {
    private String shareAgreementId;

    private String shareId;

    private String agreementId;

    private String agreementName;

    private String agreementType;

    private String agreementUploadDate;

    private String agreementExtend;

    public String getAgreementType() {
        return agreementType;
    }

    public void setAgreementType(String agreementType) {
        this.agreementType = agreementType;
    }

    public String getAgreementUploadDate() {
        return agreementUploadDate;
    }

    public void setAgreementUploadDate(String agreementUploadDate) {
        this.agreementUploadDate = agreementUploadDate;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getAgreementExtend() {
        return agreementExtend;
    }

    public void setAgreementExtend(String agreementExtend) {
        this.agreementExtend = agreementExtend;
    }

    public String getShareAgreementId() {
        return shareAgreementId;
    }

    public void setShareAgreementId(String shareAgreementId) {
        this.shareAgreementId = shareAgreementId == null ? null : shareAgreementId.trim();
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    public String getAgreementId() {
        return agreementId;
    }

    public void setAgreementId(String agreementId) {
        this.agreementId = agreementId == null ? null : agreementId.trim();
    }
}