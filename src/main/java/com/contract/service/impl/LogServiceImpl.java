package com.contract.service.impl;



import com.contract.domain.Log;
import com.contract.mapper.AdminMapper;
import com.contract.mapper.LogMapper;
import com.contract.service.LogService;
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

    public void addLog(HttpServletRequest httpServletRequest, Log log) {

        try {
            String token = httpServletRequest.getHeader("token");
            String adminId = null;
            adminId = TokenUtil.getId(token);
            if(adminId != null&&!"".equals(adminId)) {
                log.setLogAdmin(adminId);
            }
            log.setLogRemoteIp(httpServletRequest.getRemoteAddr());
            log.setLogUserAgent(httpServletRequest.getHeader("User-Agent"));
            log.setLogXRequestedWidth(httpServletRequest.getHeader("X-Requested-Width"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addLog(log);
    }

    public void addLog(String adminId, HttpServletRequest httpServletRequest, Log log) {

        try {
//            String token = httpServletRequest.getHeader("token");
//            String adminId = null;
//            adminId = TokenUtil.getUserId(token);
//            if(adminId != null&&!"".equals(adminId)) {

//            }
//            String adminRoleGroupId = TokenUtil.getAdminRoleGroupId(token);
//            if(adminRoleGroupId != null&&!"".equals(adminRoleGroupId)){
//                log.setLogAdminRoleGroup(adminRoleGroupId);
//            }
            log.setLogAdmin(adminId);
            log.setLogRemoteIp(httpServletRequest.getRemoteAddr());
            log.setLogUserAgent(httpServletRequest.getHeader("User-Agent"));
            log.setLogXRequestedWidth(httpServletRequest.getHeader("X-Requested-Width"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addLog(log);
    }

    @Override
    public void addLoginLog(String adminId, HttpServletRequest httpServletRequest) {
        Log log = new Log();
        log.setLogType("log_type_login");
        addLog(adminId,httpServletRequest,log);


    }

}
