package com.contract.controller;

import com.alibaba.fastjson.JSONObject;

import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.domain.Log;
import com.contract.service.LogService;
import com.contract.util.ExcelUtils;
import com.contract.util.PageBean;
import com.contract.util.Sheet;
import com.contract.util.TokenUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {
    @Resource
    private LogService logService;

    @RequestMapping("/getLogBySearch")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject getLogBySearch(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Integer currentPage = 1;
            String currentPageStr = paramsJson.getString("currentPage");
            if (null != currentPageStr && !"".equals(currentPageStr)) {
                currentPage = Integer.parseInt(currentPageStr);
            }
            Integer showCount = 10;
            String showCountStr = paramsJson.getString("showCount");
            if (null != showCountStr && !"".equals(showCountStr)) {
                showCount = Integer.parseInt(showCountStr);
            }
            Log log = JSONObject.parseObject(paramsJson.getString("log"), Log.class);
            PageBean<Log> pageData = logService.getLogBySearch(log, currentPage, showCount);
            result.put("data", pageData);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

    @PostMapping("/getLogBySearchToExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public void getLogBySearchToExcel(@RequestBody String params, HttpServletResponse response) {
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Log log = JSONObject.parseObject(paramsJson.getString("log"), Log.class);
            List<Log> result = logService.getAllLogExcel(log);
            List<Sheet> sheetList = new ArrayList<>();
            Sheet sheet = new Sheet("日志表", "日志表", result, Log.class);
            sheetList.add(sheet);
            ExcelUtils.multSheetExport(sheetList, "日志表.xls", response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //delLog
    @RequestMapping("/delLog")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject delAnswer(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Log log = JSONObject.parseObject(paramsJson.getString("log"), Log.class);
            logService.deleteById(log.getLogId());
            result.put("data", "【记录】删除成功");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);
        }
        return result;
    }

    @RequestMapping("/delAllLog")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject delAllLog(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            logService.deleteAll();
            result.put("data", "【记录】全部删除成功");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);
        }
        return result;
    }
}
