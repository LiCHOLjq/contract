package com.contract.domain;

import java.util.List;

public class Power {
    private String powerId;

    private String powerName;

    private String powerRouter;

    private Integer powerLevel;

    private String powerFather;

    private String powerIcon;

    private List<Power> childrenPowerList;

    public String getPowerId() {
        return powerId;
    }

    public void setPowerId(String powerId) {
        this.powerId = powerId == null ? null : powerId.trim();
    }

    public String getPowerName() {
        return powerName;
    }

    public void setPowerName(String powerName) {
        this.powerName = powerName == null ? null : powerName.trim();
    }

    public String getPowerRouter() {
        return powerRouter;
    }

    public void setPowerRouter(String powerRouter) {
        this.powerRouter = powerRouter == null ? null : powerRouter.trim();
    }

    public Integer getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(Integer powerLevel) {
        this.powerLevel = powerLevel;
    }

    public String getPowerFather() {
        return powerFather;
    }

    public void setPowerFather(String powerFather) {
        this.powerFather = powerFather == null ? null : powerFather.trim();
    }

    public String getPowerIcon() {
        return powerIcon;
    }

    public void setPowerIcon(String powerIcon) {
        this.powerIcon = powerIcon == null ? null : powerIcon.trim();
    }

    public List<Power> getChildrenPowerList() {
        return childrenPowerList;
    }

    public void setChildrenPowerList(List<Power> childrenPowerList) {
        this.childrenPowerList = childrenPowerList;
    }
}