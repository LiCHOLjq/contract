package com.contract.service;




import com.contract.domain.DictionaryType;
import com.contract.domain.Log;
import com.contract.util.PageBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LogService {
    void addLog(Log log);
    void addLoginLog(String adminId, HttpServletRequest httpServletRequest);
    void addShareAccessLog(String shareId, HttpServletRequest httpServletRequest);
    void addShareUploadLog(String agreementId,String agreementName, HttpServletRequest httpServletRequest);
    void addShareDownloadLog(String agreementId,String agreementName, HttpServletRequest httpServletRequest);
    void addShareAddLog(String shareId, HttpServletRequest httpServletRequest);
    void addShareUpdateLog(String shareId, HttpServletRequest httpServletRequest);
    void addShareDeleteLog(String shareId, HttpServletRequest httpServletRequest);
    void addAgreementAddLog(String agreementId,String agreementName, HttpServletRequest httpServletRequest);
    void addAgreementUpdateLog(String agreementId,String agreementName, HttpServletRequest httpServletRequest);
    void addAgreementDeleteLog(String agreementId,String agreementName, HttpServletRequest httpServletRequest);
    void deleteAll();
    void deleteById(String logId);
    PageBean<Log> getLogBySearch(Log log, int currentPage, int pageSize);
    List<Log> getAllLogExcel(Log log);
}
