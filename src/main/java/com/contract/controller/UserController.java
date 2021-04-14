package com.contract.controller;

import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.annotation.UserShareToken;
import com.contract.domain.Admin;
import com.contract.domain.Agreement;
import com.contract.domain.Share;
import com.contract.exception.BaseException;
import com.contract.service.LogService;
import com.contract.service.ShareService;
import com.contract.util.TokenUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private ShareService shareService;

    @Resource
    private LogService logService;

    @RequestMapping(value = "/getShareAvailable", method = RequestMethod.POST)
    public JSONObject getShareAvailable(@RequestBody String params, HttpServletRequest httpServletRequest){
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Share share = JSONObject.parseObject(paramsJson.getString("share"), Share.class);
            Share accessShare = shareService.getShareByIdHasPassword(share.getShareId());
            if(accessShare==null){
                throw new BaseException("连接不存在",500);
            }
            if(share.getShareBeginDate()!=null&&new Date().before(share.getShareBeginDate())){
                throw new BaseException("连接不存在",500);
            }
            if(share.getShareEndDate()!=null&&new Date().after(share.getShareEndDate())){
                throw new BaseException("连接不存在",500);
            }
            if(share.getShareDelete()){
                throw new BaseException("连接不存在",500);
            }
            result.put("code", 200);
            result.put("data", "验证成功");
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

    @RequestMapping(value = "/accessShare", method = RequestMethod.POST)
    public JSONObject adminLoginWeb(@RequestBody String params, HttpServletRequest httpServletRequest){
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Share share = JSONObject.parseObject(paramsJson.getString("share"), Share.class);
            Share accessShare = shareService.getShareByIdHasPassword(share.getShareId());
            if(accessShare==null){
                throw new BaseException("连接不存在",500);
            }
            if(share.getShareBeginDate()!=null&&new Date().before(share.getShareBeginDate())){
                throw new BaseException("连接不存在",500);
            }
            if(share.getShareEndDate()!=null&&new Date().after(share.getShareEndDate())){
                throw new BaseException("连接不存在",500);
            }
            if(share.getShareDelete()){
                throw new BaseException("连接不存在",500);
            }
            if(accessShare.getShareHasPassword()){
                if(!accessShare.getSharePassword().equals(share.getSharePassword())){
                    throw new BaseException("密码不正确",401);
                }
            }
            String token = TokenUtil.getShareToken(accessShare,600);
            logService.addShareAccessLog(accessShare.getShareId(),httpServletRequest);
            result.put("shaken", token);
            result.put("code", 200);
            result.put("data", "验证成功");
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

    @RequestMapping(value = "/getShareDetails", method = RequestMethod.POST)
    @UserShareToken
    public JSONObject getShareDetails(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            String shareId = paramsJson.getString("shareId");
            Share share = shareService.getShareDetails(shareId);
            result.put("object", share);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
}
