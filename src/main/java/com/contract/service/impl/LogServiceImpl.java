package com.contract.service.impl;



import com.contract.domain.Log;
import com.contract.mapper.AdminMapper;
import com.contract.mapper.LogMapper;
import com.contract.service.LogService;
import com.contract.util.PageBean;
import com.contract.util.TokenUtil;
import com.contract.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private LogMapper logMapper;

    @Override
    public void addLog(Log log) {
        if(log.getLogId()==null||"".equals(log.getLogId())){
            log.setLogId(UUIDUtil.getUUID());
        }
        if(log.getLogDate()==null){
            log.setLogDate(new Date());
        }
        logMapper.insertSelective(log);
    }

    public void setLogBase(HttpServletRequest httpServletRequest, Log log){
        log.setLogRemoteIp(httpServletRequest.getRemoteAddr());
        log.setLogUserAgent(httpServletRequest.getHeader("User-Agent"));
        log.setLogXRequestedWidth(httpServletRequest.getHeader("X-Requested-Width"));
    }


    public void setLogAdminByToken(HttpServletRequest httpServletRequest, Log log) {
        try {
            String token = httpServletRequest.getHeader("token");
            String adminId = null;
            adminId = TokenUtil.getId(token);
            if(adminId != null&&!"".equals(adminId)) {
                log.setLogAdmin(adminId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setLogShareByToken(HttpServletRequest httpServletRequest, Log log) {
        try {
            String token = httpServletRequest.getHeader("token");
            String adminId = null;
            adminId = TokenUtil.getId(token);
            if(adminId != null&&!"".equals(adminId)) {
                log.setLogMessage3(adminId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void addLoginLog(String adminId, HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_login");
        log.setLogAdmin(adminId);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }


    @Override
    public void addShareAccessLog(String shareId, HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_share_access");
        log.setLogMessage3(shareId);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }

    @Override
    public void addShareUploadLog(String agreementId, String agreementName,  HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_share_upload");
        log.setLogMessage1(agreementId);
        log.setLogMessage2(agreementName);
        setLogShareByToken(httpServletRequest,log);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }

    @Override
    public void addShareDownloadLog(String agreementId, String agreementName,  HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_share_download");
        log.setLogMessage1(agreementId);
        log.setLogMessage2(agreementName);
        setLogShareByToken(httpServletRequest,log);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }

    @Override
    public void addShareAddLog(String shareId, HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_share_add");
        log.setLogMessage3(shareId);
        setLogAdminByToken(httpServletRequest,log);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }

    @Override
    public void addShareUpdateLog(String shareId, HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_share_update");
        log.setLogMessage3(shareId);
        setLogAdminByToken(httpServletRequest,log);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }

    @Override
    public void addShareDeleteLog(String shareId, HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_share_delete");
        log.setLogMessage3(shareId);
        setLogAdminByToken(httpServletRequest,log);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }

    @Override
    public void addAgreementAddLog(String agreementId, String agreementName, HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_agreement_add");
        log.setLogMessage1(agreementId);
        log.setLogMessage2(agreementName);
        setLogAdminByToken(httpServletRequest,log);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }

    @Override
    public void addAgreementUpdateLog(String agreementId, String agreementName, HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_agreement_update");
        log.setLogMessage1(agreementId);
        log.setLogMessage2(agreementName);
        setLogAdminByToken(httpServletRequest,log);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }

    @Override
    public void addAgreementDeleteLog(String agreementId, String agreementName, HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_agreement_delete");
        log.setLogMessage1(agreementId);
        log.setLogMessage2(agreementName);
        setLogAdminByToken(httpServletRequest,log);
        setLogBase(httpServletRequest,log);
        addLog(log);
    }

    @Override
    public void deleteAll() {
        logMapper.deleteAll();
    }

    @Override
    public void deleteById(String logId) {
        logMapper.deleteByPrimaryKey(logId);
    }

    @Override
    public PageBean<Log> getLogBySearch(Log log, int currentPage, int pageSize) {
        return null;
    }

    @Override
    public List<Log> getAllLogExcel() {
        return null;
    }
}
