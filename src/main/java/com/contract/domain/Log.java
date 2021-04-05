package com.contract.domain;

import java.util.Date;

public class Log {
    private String logId;

    private Date logDate;

    private String logAdmin;

    private String logAdminName;

    private String logType;

    private String logRemoteIp;

    private String logMessage1;

    private String logMessage2;

    private String logMessage3;

    private String logUserAgent;

    private String logXRequestedWidth;

    private String logLongMessage;

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