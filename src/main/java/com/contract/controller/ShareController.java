package com.contract.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.domain.Admin;
import com.contract.domain.Agreement;
import com.contract.domain.Product;
import com.contract.domain.Share;
import com.contract.service.AdminService;
import com.contract.service.LogService;
import com.contract.service.ShareService;
import com.contract.util.PageBean;
import com.contract.util.TokenUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/share")
public class ShareController {

    @Resource
    private ShareService shareService;

    @Resource
    private AdminService adminService;

    @Resource
    private LogService logService;

    //addShare
    @RequestMapping("/addDownLoadShare")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject addDownLoadShare(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        //导入操作
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Share share = JSONObject.parseObject(paramsJson.getString("share"), Share.class);
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String adminId = TokenUtil.getId(token);
            Share share1 = shareService.addDownLoadShare(share,adminId);
            logService.addShareAddLog(share1.getShareId(),httpServletRequest);
            result.put("object",share1);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("data",e.getMessage());
        }
        return result;
    }
    //    getShareBySearch

    @RequestMapping(value = "/getShareBySearch", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject getShareBySearch(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Integer currentPage = 1;
            String currentPageStr = paramsJson.getString("currentPage");
            if (currentPageStr != null && !"".equals(currentPageStr)) {
                currentPage = Integer.parseInt(currentPageStr);
            }
            Integer showCount = 10;
            String showCountStr = paramsJson.getString("showCount");
            if (showCountStr != null && !"".equals(showCountStr)) {
                showCount = Integer.parseInt(showCountStr);
            }

            Share share = JSONObject.parseObject(paramsJson.getString("share"), Share.class);
            String token = httpServletRequest.getHeader("token");
            String adminId = TokenUtil.getId(token);
            Admin admin = adminService.getAdminById(adminId);
            if(admin.getAdminRole().equals("admin_role_normal")){
                share.setShareDelete(false);
            }
            PageBean<Share> pageData;
            pageData = shareService.getShareBySearch(share,currentPage, showCount);
            result.put("object", pageData);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

    //    alterShareDel

    @RequestMapping(value = "/alterShareDel", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject alterShareDel(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Share share = JSONObject.parseObject(paramsJson.getString("share"), Share.class);
            shareService.delShare(share);
            logService.addShareDeleteLog(share.getShareId(),httpServletRequest);
            result.put("data", "【分享】删除成功");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
    //    alterShareRestore

    @RequestMapping(value = "/alterShareRestore", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject alterShareRestore(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Share share = JSONObject.parseObject(paramsJson.getString("share"), Share.class);
            shareService.restoreShare(share);
            result.put("data", "【分享】还原成功");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
    //    alterShareRelDel
    @RequestMapping(value = "/alterShareRelDel", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject alterShareRelDel(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Share share = JSONObject.parseObject(paramsJson.getString("share"), Share.class);
            shareService.relDelShare(share);
            result.put("data", "【分享】彻底删除成功");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
//    delOverdue
    @RequestMapping(value = "/delOverdue", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject delOverdue(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            shareService.delOverdue();
            result.put("data", "【删除过期分享】成功");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

//    delAllDeleted
    @RequestMapping(value = "/delAllDeleted", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject delAllDeleted(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            shareService.delAllDeleted();
            result.put("data", "【清空回收站】成功");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }


}
