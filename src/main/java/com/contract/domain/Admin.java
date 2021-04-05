package com.contract.domain;

public class Admin {
    private String adminId;

    private String adminName;

    private String adminAccount;

    private String adminPassword;

    private String adminRole;

    private Boolean adminUseful;

    private Dictionary adminRoleObj;

    public Dictionary getAdminRoleObj() {
        return adminRoleObj;
    }

    public void setAdminRoleObj(Dictionary adminRoleObj) {
        this.adminRoleObj = adminRoleObj;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount == null ? null : adminAccount.trim();
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword == null ? null : adminPassword.trim();
    }

    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole == null ? null : adminRole.trim();
    }

    public Boolean getAdminUseful() {
        return adminUseful;
    }

    public void setAdminUseful(Boolean adminUseful) {
        this.adminUseful = adminUseful;
    }
}