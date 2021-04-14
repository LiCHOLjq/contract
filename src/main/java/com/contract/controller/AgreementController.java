package com.contract.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.config.FileSaveConfig;
import com.contract.domain.Admin;
import com.contract.domain.Agreement;
import com.contract.domain.Cart;
import com.contract.domain.Product;
import com.contract.exception.AddException;
import com.contract.exception.BaseException;
import com.contract.service.AdminService;
import com.contract.service.AgreementService;
import com.contract.service.CartService;
import com.contract.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/agreement")
public class AgreementController {
    @Resource
    private AgreementService agreementService;
    @Resource
    private AdminService adminService;
    @Resource
    private CartService cartService;


    //getAgreementBySearch
    @RequestMapping(value = "/getAgreementBySearch", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject getAdminBySearch(@RequestBody String params, HttpServletRequest httpServletRequest) {
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
            String sort = paramsJson.getString("sort");
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            String token = httpServletRequest.getHeader("token");
            String adminId = TokenUtil.getId(token);
            Admin admin = adminService.getAdminById(adminId);
            if(admin.getAdminRole().equals("admin_role_normal")){
                agreement.setAgreementDelete(false);
            }
            PageBean<Agreement> pageData;
            pageData = agreementService.getAgreementBySearch(adminId,agreement,currentPage, showCount,sort);
            result.put("object", pageData);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

    //alterAgreementDel
    @RequestMapping(value = "/alterAgreementDel", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject alterAgreementDel(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            agreementService.delAgreement(agreement);
            result.put("data", "【合同】删除成功");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
    //alterAgreementRestore
    @RequestMapping(value = "/alterAgreementRestore", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject alterAgreementRestore(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            agreementService.restoreAgreement(agreement);
            result.put("data", "【合同】还原成功");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
    //alterAgreementRelDel
    @RequestMapping(value = "/alterAgreementRelDel", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject alterAgreementRelDel(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            agreementService.relDelAgreement(agreement);
            result.put("data", "【合同】彻底删除成功");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
    //addAgreement
    @PostMapping("/addAgreement")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject importAdminExcel(HttpServletRequest httpServletRequest,@RequestParam("file") MultipartFile file,@RequestParam("params") String params) {
        JSONObject result = new JSONObject();
        //导入操作
        try {
            JSONObject paramsJson = JSONObject.parseObject(params);
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            List<Product> productList = JSONArray.parseArray(paramsJson.getString("productList"), Product.class);
            String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
            String adminId = TokenUtil.getId(token);
            agreementService.addAgreement(agreement,productList,file,adminId);
            result.put("data","【合同】添加成功");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("data",e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getCart", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject getCart(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));

            String token = httpServletRequest.getHeader("token");
            String adminId = TokenUtil.getId(token);
            result.put("object", cartService.getCartByAdmin(adminId));
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

//    addToCart
    @RequestMapping(value = "/addToCart", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject addToCart(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            String token = httpServletRequest.getHeader("token");
            String adminId = TokenUtil.getId(token);
            cartService.addToCart(adminId,agreement);
            result.put("data", "【合同】添加分享列表成功");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
//    delFromCart
    @RequestMapping(value = "/delFromCart", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject delFromCart(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            String token = httpServletRequest.getHeader("token");
            String adminId = TokenUtil.getId(token);
            cartService.addToCart(adminId,agreement);
            result.put("data", "【合同】已从分享列表移除");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
//    delCartById
    @RequestMapping(value = "/delCartById", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject delCartById(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Cart cart = JSONObject.parseObject(paramsJson.getString("cart"), Cart.class);

            cartService.delById(cart);
            result.put("data", "【合同】已从分享列表移除");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
//    getAgrementDetails

@RequestMapping(value = "/getAgreementDetails", method = RequestMethod.POST)
@UserLoginToken
@UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
public JSONObject getAgreementDetails(@RequestBody String params, HttpServletRequest httpServletRequest) {
    JSONObject result = new JSONObject();
    try {
        JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));

        String agreementId = paramsJson.getString("agreementId");
        Agreement agreement = agreementService.getAgreementDetails(agreementId);
        if(agreement==null){
            throw new Exception("合同不存在");
        }
        if(agreement.getAgreementDelete()){
            throw new Exception("合同不存在");
        }

        result.put("object", agreement);
        result.put("code", 200);
    } catch (Exception e) {
        e.printStackTrace();
        result.put("data", e.getMessage());
        result.put("code", 500);

    }
    return result;
}
//    updAgreement
@RequestMapping("/updAgreement")
@UserLoginToken
@UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
public JSONObject updAgreement(@RequestBody String params, HttpServletRequest httpServletRequest) {
    JSONObject result = new JSONObject();
    //导入操作
    try {
        JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
        Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
        List<Product> productList = JSONArray.parseArray(paramsJson.getString("productList"), Product.class);
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        String adminId = TokenUtil.getId(token);
        agreementService.updAgreement(agreement,productList,adminId);
        result.put("data","【合同】修改成功");
        result.put("code", 200);
    } catch (Exception e) {
        e.printStackTrace();
        result.put("code", 500);
        result.put("data",e.getMessage());
    }
    return result;
}
    @GetMapping("/admin/download")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public void exportAdminNullExcel(HttpServletResponse response,String type,String id) throws IOException {
        // 导出操作
        try {
            if(type!=null&&type.equals("agreement")){
                Agreement agreement = agreementService.getAgreementDetails(id);
                if(agreement==null||agreement.getAgreementDelete()){
                    throw new Exception("合同不存在");
                }
                FileUtil.downloadFile(response, FileSaveConfig.AGREEMENT,agreement.getAgreementId()+agreement.getAgreementExtend());
            }
            if(type!=null&&type.equals("share")){

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
}

