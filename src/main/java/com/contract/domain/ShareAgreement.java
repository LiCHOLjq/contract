package com.contract.domain;

public class ShareAgreement {
    private String shareAgreementId;

    private String shareId;

    private String agreementId;

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