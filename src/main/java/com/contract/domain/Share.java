package com.contract.domain;

import java.util.Date;
import java.util.List;

public class Share {
    private String shareId;

    private String shareAdmin;

    private String shareType;

    private Date shareBeginDate;

    private Date shareEndDate;

    private Boolean shareHasPassword;

    private String sharePassword;

    private Boolean shareDelete;

    private String shareBeginDateStr;

    private String shareEndDateStr;


    //=============
    private Admin shareAdminObj;

    private Dictionary shareTypeObj;

    private String shareDateStr;

    private List<ShareAgreement> shareAgreementList;

    public List<ShareAgreement> getShareAgreementList() {
        return shareAgreementList;
    }

    public void setShareAgreementList(List<ShareAgreement> shareAgreementList) {
        this.shareAgreementList = shareAgreementList;
    }

    public String getShareDateStr() {
        return shareDateStr;
    }

    public void setShareDateStr(String shareDateStr) {
        this.shareDateStr = shareDateStr;
    }

    public Dictionary getShareTypeObj() {
        return shareTypeObj;
    }

    public void setShareTypeObj(Dictionary shareTypeObj) {
        this.shareTypeObj = shareTypeObj;
    }

    public Admin getShareAdminObj() {
        return shareAdminObj;
    }

    public void setShareAdminObj(Admin shareAdminObj) {
        this.shareAdminObj = shareAdminObj;
    }

    public String getShareBeginDateStr() {
        return shareBeginDateStr;
    }

    public void setShareBeginDateStr(String shareBeginDateStr) {
        this.shareBeginDateStr = shareBeginDateStr;
    }

    public String getShareEndDateStr() {
        return shareEndDateStr;
    }

    public void setShareEndDateStr(String shareEndDateStr) {
        this.shareEndDateStr = shareEndDateStr;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

    public String getShareAdmin() {
        return shareAdmin;
    }

    public void setShareAdmin(String shareAdmin) {
        this.shareAdmin = shareAdmin == null ? null : shareAdmin.trim();
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType == null ? null : shareType.trim();
    }

    public Date getShareBeginDate() {
        return shareBeginDate;
    }

    public void setShareBeginDate(Date shareBeginDate) {
        this.shareBeginDate = shareBeginDate;
    }

    public Date getShareEndDate() {
        return shareEndDate;
    }

    public void setShareEndDate(Date shareEndDate) {
        this.shareEndDate = shareEndDate;
    }

    public Boolean getShareHasPassword() {
        return shareHasPassword;
    }

    public void setShareHasPassword(Boolean shareHasPassword) {
        this.shareHasPassword = shareHasPassword;
    }

    public String getSharePassword() {
        return sharePassword;
    }

    public void setSharePassword(String sharePassword) {
        this.sharePassword = sharePassword == null ? null : sharePassword.trim();
    }

    public Boolean getShareDelete() {
        return shareDelete;
    }

    public void setShareDelete(Boolean shareDelete) {
        this.shareDelete = shareDelete;
    }
}