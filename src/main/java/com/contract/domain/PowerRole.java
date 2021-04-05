package com.contract.domain;

public class PowerRole {
    private String powerRoleId;

    private String powerId;

    private String roleId;

    public String getPowerRoleId() {
        return powerRoleId;
    }

    public void setPowerRoleId(String powerRoleId) {
        this.powerRoleId = powerRoleId == null ? null : powerRoleId.trim();
    }

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId == null ? null : powerId.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }
}