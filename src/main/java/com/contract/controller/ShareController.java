package com.contract.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.domain.Agreement;
import com.contract.domain.Product;
import com.contract.domain.Share;
import com.contract.service.ShareService;
import com.contract.util.TokenUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/share")
public class ShareController {

    @Resource
    private ShareService shareService;

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
            result.put("object",share1);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("data",e.getMessage());
        }
        return result;
    }
}
