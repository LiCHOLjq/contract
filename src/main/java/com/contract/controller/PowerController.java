package com.contract.controller;

import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;

import com.contract.annotation.UserRoleToken;
import com.contract.service.PowerService;
import com.contract.util.TokenUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/power")
public class PowerController {

    @Resource
    private PowerService powerService;


    @RequestMapping(value = "/getPowerList", method = RequestMethod.POST)
    @UserLoginToken
    public JSONObject getPowerListWeb(@RequestBody String params, HttpServletRequest httpServletRequest){

        JSONObject result = new JSONObject();
        try {
            String token = httpServletRequest.getHeader("token");
            String adminId = TokenUtil.getId(token);
            result.put("data", "获取成功");
            result.put("object", powerService.getTreePowerList(adminId));
            result.put("code", 200);
            return result;
        }
//        catch (BaseException e){
//            e.printStackTrace();
//            result.put("data", e.getMessage());
//            result.put("code", e.getCode());
//            return result;
//        }
        catch (Exception e){
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 301);
            return result;
        }
    }

}
