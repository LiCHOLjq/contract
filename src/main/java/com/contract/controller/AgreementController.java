package com.contract.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.config.FileSaveConfig;
import com.contract.domain.Admin;
import com.contract.domain.Agreement;
import com.contract.domain.Cart;
import com.contract.domain.Dictionary;
import com.contract.domain.Product;
import com.contract.exception.AddException;
import com.contract.exception.BaseException;
import com.contract.service.*;
import com.contract.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping("/agreement")
public class AgreementController {
    @Resource
    private AgreementService agreementService;
    @Resource
    private AdminService adminService;
    @Resource
    private CartService cartService;
    @Resource
    private LogService logService;
    @Resource
    private DictionaryService dictionaryService;


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

    @RequestMapping(value = "/getAgreementUserBySearch", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject getAgreementUserBySearch(@RequestBody String params, HttpServletRequest httpServletRequest) {
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
            pageData = agreementService.getAgreementUserBySearch(adminId,agreement,currentPage, showCount,sort);
            result.put("object", pageData);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }


    @RequestMapping("/exportAgreement")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public void exportAgreement(HttpServletResponse response, HttpServletRequest httpServletRequest,@RequestBody String params) throws IOException {
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            String sort = paramsJson.getString("sort");
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            List<Agreement> agreementList = agreementService.getAgreementBySearch(agreement,sort);
            Set<String> titleList = new LinkedHashSet<>();
            List<Map<String,Object>> mapList = new ArrayList<>();
            titleList.add("??????");
            titleList.add("??????");
            titleList.add("??????");
            titleList.add("????????????");
            titleList.add("????????????");
            titleList.add("?????????");
            titleList.add("????????????");
            titleList.add("??????");
            titleList.add("?????????");
            titleList.add("????????????");
            titleList.add("?????????");
            List<Dictionary> dictionaryList = dictionaryService.getDictionaryByType("PRODUCT_TYPE");
            for(Dictionary dictionary : dictionaryList){
                titleList.add(dictionary.getDictionaryName());
            }
            for(Agreement item : agreementList){
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("??????",item.getAgreementName());
                map.put("??????",item.getAgreementTypeObj().getDictionaryName());
                map.put("??????",item.getAgreementInnovation() ? "???" : "???");
                map.put("????????????",item.getAgreementProvider());
                map.put("????????????",item.getAgreementClientObj().getDictionaryName());
                map.put("?????????",item.getAgreementAmount().toString());
                map.put("????????????",item.getAgreementSignDateStr());
                map.put("??????",item.getAgreementText());
                map.put("?????????",item.getAgreementUploadAdmin());
                map.put("????????????",item.getAgreementUploadDateStr());
                map.put("?????????",item.getAgreementExtend());
                for(Dictionary dictionary : dictionaryList){
                    map.put(dictionary.getDictionaryName(),0);
                }
                int i = 1;
                for(Product product : item.getProductList()){
                    map.put(product.getProductTypeObj().getDictionaryName(),((Integer)map.get(product.getProductTypeObj().getDictionaryName())+1));
                    titleList.add("????????????"+i);
                    map.put("????????????"+i,product.getProductTypeObj().getDictionaryName());
                    titleList.add("????????????"+i);
                    map.put("????????????"+i,product.getProductSeriesObj().getDictionaryName());
                    titleList.add("????????????"+i);
                    map.put("????????????"+i,product.getProductModel());
                    titleList.add("????????????"+i);
                    map.put("????????????"+i,product.getProductNumber());
                    i++;
                }
                mapList.add(map);
            }
            ExcelUtils.exportExcel(mapList,titleList,"????????????","??????????????????.xlsx",response);

        }
        catch (Exception e) {
            e.printStackTrace();
            response.setHeader("content-type", "application/json");
            response.setCharacterEncoding("UTF-8");      //??????PrintWriter?????????
            JSONObject result = new JSONObject();
            PrintWriter out = response.getWriter();
            result.put("data", e.getMessage());
            result.put("code", 500);
            out.write(result.toJSONString());
        }
    }

    //alterAgreementDel
    @RequestMapping(value = "/alterAgreementDel", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject alterAgreementDel(HttpServletRequest httpServletRequest,@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            agreementService.delAgreement(agreement);
            logService.addAgreementDeleteLog(agreement.getAgreementId(),agreement.getAgreementName(),httpServletRequest);
            result.put("data", "????????????????????????");
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
            result.put("data", "????????????????????????");
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
            result.put("data", "??????????????????????????????");
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
        //????????????
        try {
            JSONObject paramsJson = JSONObject.parseObject(params);
            Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
            List<Product> productList = JSONArray.parseArray(paramsJson.getString("productList"), Product.class);
            String token = httpServletRequest.getHeader("token");// ??? http ?????????????????? token
            String adminId = TokenUtil.getId(token);
            agreementService.addAgreement(agreement,productList,file,adminId);
            logService.addAgreementAddLog(agreement.getAgreementId(),agreement.getAgreementName(),httpServletRequest);
            result.put("data","????????????????????????");
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
            result.put("data", "????????????????????????????????????");
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
            cartService.delFromCart(adminId,agreement);
            result.put("data", "????????????????????????????????????");
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
            result.put("data", "????????????????????????????????????");
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
            throw new Exception("???????????????");
        }
        if(agreement.getAgreementDelete()){
            throw new Exception("???????????????");
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
    //????????????
    try {
        JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
        Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
        List<Product> productList = JSONArray.parseArray(paramsJson.getString("productList"), Product.class);
        String token = httpServletRequest.getHeader("token");// ??? http ?????????????????? token
        String adminId = TokenUtil.getId(token);
        agreementService.updAgreement(agreement,productList,adminId);
        logService.addAgreementUpdateLog(agreement.getAgreementId(),agreement.getAgreementName(),httpServletRequest);
        result.put("data","????????????????????????");
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
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public void download(HttpServletResponse response,String type,String id) throws IOException {
        // ????????????
        try {
            if(type!=null&&type.equals("agreement")){
                Agreement agreement = agreementService.getAgreementById(id);
                if(agreement==null||agreement.getAgreementDelete()){
                    throw new Exception("???????????????");
                }
                FileUtil.downloadFile(response, FileSaveConfig.AGREEMENT,agreement.getAgreementId()+agreement.getAgreementExtend());
            }
            if(type!=null&&type.equals("share")){

            }
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setHeader("content-type", "application/json");
            response.setCharacterEncoding("UTF-8");      //??????PrintWriter?????????
            JSONObject result = new JSONObject();
            PrintWriter out = response.getWriter();
            result.put("data", e.getMessage());
            result.put("code", 500);
            out.write(result.toJSONString());
        }
    }
    @GetMapping("/downLoadCart")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public void downLoadCart(HttpServletRequest httpServletRequest,HttpServletResponse response) throws IOException {
        // ????????????
        try {
            String token = httpServletRequest.getHeader("token");// ??? http ?????????????????? token
            String adminId = TokenUtil.getId(token);
            List<Cart> cartList = cartService.getCartByAdmin(adminId);
            List<ZipFile> zipFileList = new ArrayList<>();
            int i=1;
            for(Cart cart : cartList){
                Agreement agreement = agreementService.getAgreementDetails(cart.getCartAgreement());
                zipFileList.add(new ZipFile(new File(FileSaveConfig.AGREEMENT+agreement.getAgreementId()+agreement.getAgreementExtend()),getFileName(agreement,i)));
                i++;
            }
            ZipUtils.downloadZip(zipFileList,response);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setHeader("content-type", "application/json");
            response.setCharacterEncoding("UTF-8");      //??????PrintWriter?????????
            JSONObject result = new JSONObject();
            PrintWriter out = response.getWriter();
            result.put("data", e.getMessage());
            result.put("code", 500);
            out.write(result.toJSONString());
        }
    }
    public String getFileName(Agreement agreement,int index) {
        String filename = index + ".";
        filename = filename + (agreement.getAgreementProvider() == null ? "" : agreement.getAgreementProvider()) + "-";
        filename = filename + (agreement.getAgreementName() == null ? "" : agreement.getAgreementName()) + "-";
        filename = filename + (agreement.getAgreementSignDateStr() == null ? "" : agreement.getAgreementSignDateStr()) + "-";
        filename = filename + (agreement.getAgreementAmount() == 0 ? "?????????" : ( BigDecimalUtil.div(agreement.getAgreementAmount(),10000.0)  + "???")) + "-";
        int i = 0;
        for (Product product : agreement.getProductList()) {
            filename = filename + (product.getProductSeriesObj() == null ? "" : product.getProductSeriesObj().getDictionaryName() == null ? "" : product.getProductSeriesObj().getDictionaryName()) + " ";
            filename = filename + (product.getProductModel() == null ? "" : product.getProductModel());
            filename = filename + "(" + (product.getProductNumber() == null ? "" : product.getProductNumber()) + "???)";
            if (i < agreement.getProductList().size() - 1) {
                filename = filename + ",";
            }
            i++;
        }
        filename = filename + "-" + (agreement.getAgreementClientObj() == null ? "" : agreement.getAgreementClientObj().getDictionaryName() == null ? "" : agreement.getAgreementClientObj().getDictionaryName());
        filename = filename + agreement.getAgreementExtend();
        return filename;
    }
    @RequestMapping("/clearCart")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject clearCart(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        //????????????
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            String token = httpServletRequest.getHeader("token");// ??? http ?????????????????? token
            String adminId = TokenUtil.getId(token);
            cartService.clearCart(adminId);
            result.put("data","??????????????????????????????");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("data",e.getMessage());
        }
        return result;
    }
    @RequestMapping(value = "/delAllDeleted", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject delAllDeleted(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            agreementService.delAllDeleted();
            result.put("data", "???????????????????????????");
            result.put("code", 200);

        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
//    addToCartByPage
@RequestMapping(value = "/addToCartByPage", method = RequestMethod.POST)
@UserLoginToken
@UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
public JSONObject addToCartByPage(@RequestBody String params, HttpServletRequest httpServletRequest) {
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
        cartService.addToCartByPage(adminId,agreement,currentPage, showCount,sort);
        result.put("data", "????????????????????????????????????????????????");
        result.put("code", 200);
    } catch (Exception e) {
        e.printStackTrace();
        result.put("data", e.getMessage());
        result.put("code", 500);

    }
    return result;
}
//    addToCartBySearch
@RequestMapping(value = "/addToCartBySearch", method = RequestMethod.POST)
@UserLoginToken
@UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
public JSONObject addToCartBySearch(@RequestBody String params, HttpServletRequest httpServletRequest) {
    JSONObject result = new JSONObject();
    try {
        JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));

        String sort = paramsJson.getString("sort");
        Agreement agreement = JSONObject.parseObject(paramsJson.getString("agreement"), Agreement.class);
        String token = httpServletRequest.getHeader("token");
        String adminId = TokenUtil.getId(token);
        Admin admin = adminService.getAdminById(adminId);
        if(admin.getAdminRole().equals("admin_role_normal")){
            agreement.setAgreementDelete(false);
        }
        cartService.addToCartBySearch(adminId,agreement,sort);
        result.put("data", "????????????????????????????????????????????????");
        result.put("code", 200);
    } catch (Exception e) {
        e.printStackTrace();
        result.put("data", e.getMessage());
        result.put("code", 500);

    }
    return result;
}
}

