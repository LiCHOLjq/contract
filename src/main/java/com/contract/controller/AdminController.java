package com.contract.controller;

import com.alibaba.fastjson.JSONObject;
import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.domain.Admin;
import com.contract.exception.AddException;
import com.contract.exception.BaseException;
import com.contract.service.AdminService;
import com.contract.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @RequestMapping(value = "/getAdminBySearch", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject getAdminBySearch(@RequestBody String params) {
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
            Admin admin = JSONObject.parseObject(paramsJson.getString("admin"), Admin.class);
            PageBean<Admin> pageData;
            pageData = adminService.getAdminBySearch(admin,currentPage, showCount);
            result.put("object", pageData);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

    @RequestMapping(value = "/alterAdmin", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject alterAdmin(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Admin admin = JSONObject.parseObject(paramsJson.getString("admin"), Admin.class);
            if (admin.getSubmitState().equals("Add")) {
                if (admin.getAdminId() == null || "".equals(admin.getAdminId())) {
                    admin.setAdminId(UUIDUtil.getUUID());
                }
                if (adminService.getAdminById(admin.getAdminId()) != null) {
                    throw new AddException("【用户】添加失败，设置的【ID】已存在");
                }
                if(adminService.getAdminByAccountExceptSelf(admin)!=null){
                    throw new AddException("【用户】添加失败，设置的【账号】已存在");
                }
                if(admin.getAdminName()==null|"".equals(admin.getAdminName())){
                    throw new BaseException("【用户】添加失败，姓名不能为空",505);
                }
                admin.setAdminPassword(MD5Util.getDefaultPassword());
                adminService.addAdmin(admin);
                result.put("data", "【用户】添加成功");
                result.put("code", 200);
            } else if (admin.getSubmitState().equals("Upd")) {
                if (adminService.getAdminById(admin.getAdminId()) == null) {
                    throw new AddException("【用户】修改失败，设置的【ID】不存在");
                }
                if(adminService.getAdminByAccountExceptSelf(admin)!=null){
                    throw new AddException("【用户】修改失败，设置的【账号】已存在");
                }
                if(admin.getAdminName()==null|"".equals(admin.getAdminName())){
                    throw new BaseException("【用户】修改失败，姓名不能为空",505);
                }

                adminService.updAdmin(admin);
                result.put("data", "【用户】修改成功");
                result.put("code", 200);
            } else if (admin.getSubmitState().equals("Del")) {
                if (adminService.getAdminById(admin.getAdminId()) == null) {
                    throw new AddException("【用户】删除失败，设置的【ID】不存在");
                }
                adminService.delAdmin(admin);
                result.put("data", "【用户】删除成功");
                result.put("code", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
    ///admin/resetAdminPassword
    @RequestMapping(value = "/resetAdminPassword", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject resetAdminPassword(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Admin admin = JSONObject.parseObject(paramsJson.getString("admin"), Admin.class);
            Admin updAdmin = new Admin();
            updAdmin.setAdminId(admin.getAdminId());
            updAdmin.setAdminPassword(MD5Util.getDefaultPassword());
            adminService.updAdmin(admin);
            result.put("data", "【用户】密码重置成功");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }
    @GetMapping("/exportAdminNullExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public void exportAdminNullExcel(HttpServletResponse response) {
        // 导出操作
        List<Sheet> sheetList = new ArrayList<>();
        sheetList.add(new Sheet("用户","用户表",new ArrayList<Admin>(), Admin.class));
        ExcelUtils.multSheetExport(sheetList, "用户数据导入模板.xlsx", response);
    }
    @GetMapping("/exportAdminExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public void exportAdminExcel(HttpServletResponse response) {
        // 导出操作
        List<Sheet> sheetList = new ArrayList<>();
        sheetList.add(new Sheet("用户","用户表",adminService.getAllAdminExcel(), Admin.class));
        ExcelUtils.multSheetExport(sheetList, "用户数据.xlsx", response);
    }
    @PostMapping("/importAdminExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject importAdminExcel(@RequestParam("file") MultipartFile file) {
        JSONObject result = new JSONObject();
        //导入操作
        try {
            List<Admin> adminList = ExcelUtils.multSheetImport(file,1,1,0,Admin.class);
            adminService.importAdminExcel(adminList);
            result.put("data","【用户、角色】Excel导入成功");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("data",e.getMessage());
        }
        return result;
    }

}
