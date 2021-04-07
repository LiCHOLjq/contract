package com.contract.service.impl;



import com.contract.domain.Admin;
import com.contract.domain.Log;
import com.contract.mapper.AdminMapper;
import com.contract.mapper.DictionaryMapper;
import com.contract.mapper.LogMapper;
import com.contract.service.LogService;
import com.contract.util.PageBean;
import com.contract.util.TokenUtil;
import com.contract.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
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

    @Autowired
    private DictionaryMapper dictionaryMapper;

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
                Admin admin = adminMapper.selectByPrimaryKey(adminId);
                log.setLogAdmin(admin.getAdminAccount());
                log.setLogAdminName(admin.getAdminName());
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
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        log.setLogAdmin(admin.getAdminAccount());
        log.setLogAdminName(admin.getAdminName());
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            log.setLogDateBegin(sdf.parse(log.getLogDateBeginStr()));
        } catch (ParseException e) {
            log.setLogDateBegin(null);
        }
        try {
            log.setLogDateEnd(sdf.parse(log.getLogDateEndStr()));
        } catch (ParseException e) {
            log.setLogDateEnd(null);
        }
        PageHelper.startPage(currentPage,pageSize);
        List<Log> logList= logMapper.selectBySearch(log);
        for (Log item : logList){
            item.setLogDateStr(sdf.format(item.getLogDate()));
            item.setLogTypeStr(dictionaryMapper.selectByPrimaryKey(item.getLogType()).getDictionaryName());
        }
        int countNums = logMapper.selectBySearchCount(log);
        PageBean<Log> pageData = new PageBean<>(currentPage,pageSize,countNums);
        pageData.setItems(logList);
        return pageData;
    }

    @Override
    public List<Log> getAllLogExcel(Log log) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Log> logList= logMapper.selectBySearch(log);
        for (Log item : logList){
            item.setLogDateStr(sdf.format(item.getLogDate()));
            item.setLogTypeStr(dictionaryMapper.selectByPrimaryKey(item.getLogType()).getDictionaryName());
        }
        return logList;
    }
}
