package com.contract.domain;

import java.util.Date;

public class Share {
    private Integer shareId;

    private String shareAdmin;

    private String shareType;

    private Date shareBeginDate;

    private Date shareEndDate;

    private Boolean shareHasPassword;

    private String sharePassword;

    private Boolean shareDelete;

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
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