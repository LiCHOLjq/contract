package com.contract.controller;

import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.domain.*;
import com.contract.domain.Dictionary;
import com.contract.service.IndexService;
import com.contract.util.ExcelUtils;
import com.contract.util.TokenUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private IndexService indexService;


    @RequestMapping(value = "/getAgreementData", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject getAgreementData(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {

            result.put("data",indexService.getAgreementData());
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

    @RequestMapping(value = "/getProductData", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public JSONObject getProductData(@RequestBody String params, HttpServletRequest httpServletRequest) {
        JSONObject result = new JSONObject();
        try {
            result.put("data",indexService.getProductData());
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

    @RequestMapping("/exportAgreementData")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public void exportAgreementData(HttpServletResponse response, HttpServletRequest httpServletRequest, @RequestBody String params) throws IOException {
        try {
            AgreementData agreementData = indexService.getAgreementData();
            Set<String> titleList = new LinkedHashSet<>();
            List<Map<String,Object>> mapList = new ArrayList<>();
            titleList.add("年份");
            titleList.addAll(agreementData.getTypeList());
            titleList.add("合计");
            for(AgreementData.Data data : agreementData.getDataList()){
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("年份",data.getYear());
                for(int i=0;i<data.getData().length;i++){
                    map.put(agreementData.getTypeList().get(i),data.getData()[i]);
                }
                map.put("合计",data.getSum());
                mapList.add(map);
            }

            ExcelUtils.exportExcel(mapList,titleList,"合同信息统计表","合同信息统计表.xlsx",response);

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

    @RequestMapping("/exportProductData")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master","admin_role_normal"})
    public void exportProductData(HttpServletResponse response, HttpServletRequest httpServletRequest, @RequestBody String params) throws IOException {
        try {
            ProductData productData = indexService.getProductData();
            Set<String> titleList = new LinkedHashSet<>();
            List<Map<String,Object>> mapList = new ArrayList<>();
            titleList.add("类型");
            titleList.addAll(productData.getTitleList());
            for(int i=0;i<productData.getTypeList().size();i++){
                Map<String,Object> map = new LinkedHashMap<>();
                map.put("类型",productData.getTypeList().get(i));
                for(int j=0;j<productData.getTitleList().size();j++){
                    map.put(productData.getTitleList().get(j),productData.getDataList().get(j).getData()[i]);
                }
                mapList.add(map);
            }
            ExcelUtils.exportExcel(mapList,titleList,"产品信息统计表","产品信息统计表.xlsx",response);

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
