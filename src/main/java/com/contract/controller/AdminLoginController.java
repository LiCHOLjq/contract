package com.contract.controller;

import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;
import com.contract.domain.Admin;
import com.contract.exception.BaseException;
import com.contract.service.AdminService;
import com.contract.service.LogService;
import com.contract.util.TokenUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class AdminLoginController {

    @Resource
    private AdminService adminService;

    @Resource
    private LogService logService;

    @RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
    public JSONObject adminLoginWeb(@RequestBody String params, HttpServletRequest httpServletRequest){
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Admin admin = JSONObject.parseObject(paramsJson.getString("admin"), Admin.class);
            Admin accessAdmin = adminService.getAdminByAccountHasPassword(admin.getAdminAccount());
            if(accessAdmin==null){
                throw new BaseException("用户不存在",500);
            }
            if(!accessAdmin.getAdminPassword().equals(admin.getAdminPassword())){
                throw new BaseException("密码不正确",500);
            }
            if(!accessAdmin.getAdminUseful()){
                throw new BaseException("用户已冻结，请联系管理员",500);
            }
            String token = TokenUtil.getAdminToken(accessAdmin,604800);
            logService.addLoginLog(accessAdmin.getAdminId(),httpServletRequest);
            JSONObject object = new JSONObject();
            //accessAdmin.setAdminPassword(null);
            object.put("admin", accessAdmin);
            object.put("token", token);
            result.put("code", 200);
            result.put("object",object);
            result.put("data", "登录成功");
            return result;
        }catch (BaseException e){
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", e.getCode());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 301);
            return result;
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    @UserLoginToken
    public JSONObject changePassword(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            String token = httpServletRequest.getHeader("token");

            String adminId = TokenUtil.getId(token);

            String passwordOld = paramsJson.getString("passwordOld");
            String passwordNew = paramsJson.getString("passwordNew");
            adminService.changePassword(adminId,passwordOld,passwordNew);
            result.put("data","修改成功");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);
        }
        return result;
    }
}
