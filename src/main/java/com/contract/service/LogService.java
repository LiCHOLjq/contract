package com.contract.service;




import com.contract.domain.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface LogService {
    void addLog(Log log);
    void addLoginLog(String adminId, HttpServletRequest httpServletRequest);
}
