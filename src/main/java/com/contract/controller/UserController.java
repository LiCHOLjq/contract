package com.contract.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.annotation.UserShareToken;
import com.contract.config.FileSaveConfig;
import com.contract.domain.*;
import com.contract.exception.BaseException;
import com.contract.service.AgreementService;
import com.contract.service.LogService;
import com.contract.service.ShareService;
import com.contract.util.FileUtil;
import com.contract.util.TokenUtil;
import com.contract.util.ZipFile;
import com.contract.util.ZipUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private ShareService shareService;

    @Resource
    private LogService logService;

    @Resource
    private AgreementService agreementService;

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
            if(accessShare.getShareBeginDate()!=null&&new Date().before(accessShare.getShareBeginDate())){
                throw new BaseException("连接不存在",500);
            }
            if(accessShare.getShareEndDate()!=null&&new Date().after(accessShare.getShareEndDate())){
                throw new BaseException("连接不存在",500);
            }
            if(accessShare.getShareDelete()){
                throw new BaseException("连接不存在",500);
            }
            result.put("shareAdmin", accessShare.getShareAdminObj().getAdminName());
            result.put("shareType", accessShare.getShareTypeObj().getDictionaryName());
            result.put("shareDateStr", accessShare.getShareDateStr());
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
            if(accessShare.getShareBeginDate()!=null&&new Date().before(accessShare.getShareBeginDate())){
                throw new BaseException("连接不存在",500);
            }
            if(accessShare.getShareEndDate()!=null&&new Date().after(accessShare.getShareEndDate())){
                throw new BaseException("连接不存在",500);
            }
            if(accessShare.getShareDelete()){
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

    @GetMapping("/download")
    @UserShareToken
    public void download(HttpServletRequest httpServletRequest,HttpServletResponse response, String type, String id) throws IOException {
        // 导出操作
        try {
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String shareId = TokenUtil.getId(token);
            if(type!=null&&type.equals("agreement")){
                ShareAgreement shareAgreement = shareService.getShareAgreement(shareId,id);
                if(shareAgreement==null){
                    throw new Exception("合同不存在");
                }
                Agreement agreement = agreementService.getAgreementById(id);
                if(agreement==null||agreement.getAgreementDelete()){
                    throw new Exception("合同不存在");
                }
                FileUtil.downloadFile(response, FileSaveConfig.AGREEMENT,agreement.getAgreementId()+agreement.getAgreementExtend());
                logService.addShareDownloadLog(agreement.getAgreementId(),agreement.getAgreementName(),httpServletRequest);
            }
            if(type!=null&&type.equals("share")){
                List<ShareAgreement> shareAgreementList = shareService.getShareAgreementByShareId(shareId);
                List<ZipFile> zipFileList = new ArrayList<>();
                int i=1;
                for(ShareAgreement shareAgreement : shareAgreementList){
                    Agreement agreement = agreementService.getAgreementById(shareAgreement.getAgreementId());
                    zipFileList.add(new ZipFile(new File(FileSaveConfig.AGREEMENT+agreement.getAgreementId()+agreement.getAgreementExtend()),i+"."+agreement.getAgreementName()+agreement.getAgreementExtend()));
                    i++;
                }
                ZipUtils.downloadZip(zipFileList,response);
                logService.addShareDownloadLog("","合并下载",httpServletRequest);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setHeader("content-type", "application/json");
            response.setCharacterEncoding("UTF-8");      //获取PrintWriter输出流
            JSONObject result = new JSONObject();
            PrintWriter out = response.getWriter();
            result.put("data", e.getMessage());
            result.put("code", 500);
            out.write(result.toJSONString());
        }
    }
    //addAgreement
    @PostMapping("/addAgreement")
    @UserShareToken
    public JSONObject importAdminExcel(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file, @RequestParam("params") String params) {
        JSONObject result = new JSONObject();
        //导入操作
        try {
            JSONObject paramsJson = JSONObject.parseObject(params);
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String shareId = TokenUtil.getId(token);
            agreementService.addAgreement(agreement,shareId,file);
            logService.addShareUploadLog(agreement.getAgreementId(),agreement.getAgreementName(),httpServletRequest);
            result.put("data","【合同】添加成功");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("data",e.getMessage());
        }
        return result;
    }
}
