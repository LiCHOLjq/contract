package com.contract.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class Log {
    @Excel(name = "ID",orderNum = "0", width = 20)
    private String logId;

    private Date logDate;
    @Excel(name = "用户账号",orderNum = "0", width = 20)
    private String logAdmin;
    @Excel(name = "用户姓名",orderNum = "0", width = 20)
    private String logAdminName;
    @Excel(name = "记录类型",orderNum = "0", width = 20)
    private String logType;
    @Excel(name = "访问者IP",orderNum = "0", width = 20)
    private String logRemoteIp;
    @Excel(name = "关联合同ID",orderNum = "0", width = 20)
    private String logMessage1;
    @Excel(name = "关联合同名称",orderNum = "0", width = 20)
    private String logMessage2;
    @Excel(name = "关联分享ID",orderNum = "0", width = 20)
    private String logMessage3;
    @Excel(name = "User-Agent",orderNum = "0", width = 20)
    private String logUserAgent;
    @Excel(name = "X-Requested-Width",orderNum = "0", width = 20)
    private String logXRequestedWidth;

    private String logLongMessage;

    @Excel(name = "时间",orderNum = "0", width = 20)
    private String logDateStr;

    private Admin logAdminObj;

    public Admin getLogAdminObj() {
        return logAdminObj;
    }

    public void setLogAdminObj(Admin logAdminObj) {
        this.logAdminObj = logAdminObj;
    }

    public String getLogDateStr() {
        return logDateStr;
    }

    public void setLogDateStr(String logDateStr) {
        this.logDateStr = logDateStr;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId == null ? null : logId.trim();
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getLogAdmin() {
        return logAdmin;
    }

    public void setLogAdmin(String logAdmin) {
        this.logAdmin = logAdmin == null ? null : logAdmin.trim();
    }

    public String getLogAdminName() {
        return logAdminName;
    }

    public void setLogAdminName(String logAdminName) {
        this.logAdminName = logAdminName == null ? null : logAdminName.trim();
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType == null ? null : logType.trim();
    }

    public String getLogRemoteIp() {
        return logRemoteIp;
    }

    public void setLogRemoteIp(String logRemoteIp) {
        this.logRemoteIp = logRemoteIp == null ? null : logRemoteIp.trim();
    }

    public String getLogMessage1() {
        return logMessage1;
    }

    public void setLogMessage1(String logMessage1) {
        this.logMessage1 = logMessage1 == null ? null : logMessage1.trim();
    }

    public String getLogMessage2() {
        return logMessage2;
    }

    public void setLogMessage2(String logMessage2) {
        this.logMessage2 = logMessage2 == null ? null : logMessage2.trim();
    }

    public String getLogMessage3() {
        return logMessage3;
    }

    public void setLogMessage3(String logMessage3) {
        this.logMessage3 = logMessage3 == null ? null : logMessage3.trim();
    }

    public String getLogUserAgent() {
        return logUserAgent;
    }

    public void setLogUserAgent(String logUserAgent) {
        this.logUserAgent = logUserAgent == null ? null : logUserAgent.trim();
    }

    public String getLogXRequestedWidth() {
        return logXRequestedWidth;
    }

    public void setLogXRequestedWidth(String logXRequestedWidth) {
        this.logXRequestedWidth = logXRequestedWidth == null ? null : logXRequestedWidth.trim();
    }

    public String getLogLongMessage() {
        return logLongMessage;
    }

    public void setLogLongMessage(String logLongMessage) {
        this.logLongMessage = logLongMessage == null ? null : logLongMessage.trim();
    }
}