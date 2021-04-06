package com.contract.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.contract.exception.ExcelImportException;
import com.contract.exception.ExcelMapTransferException;
import com.contract.exception.ExcelNameNullException;
import com.contract.util.UUIDUtil;

import java.util.List;

public class Admin {
    @Excel(name = "ID",orderNum = "0", width = 20)
    private String adminId;
    @Excel(name = "姓名",orderNum = "1", width = 20)
    private String adminName;
    @Excel(name = "账号",orderNum = "2", width = 20)
    private String adminAccount;

    private String adminPassword;
    @Excel(name = "角色",orderNum = "3", width = 20)
    private String adminRole;
    @Excel(name = "是否可用",orderNum = "4", width = 20)
    private Boolean adminUseful;

    private Dictionary adminRoleObj;

    private String submitState;

    private List<Dictionary> adminRoleDic;

    private int excelRow;

    private List<Admin> adminDic;

    public void domainToExcel(){
        for (Dictionary dictionary : adminRoleDic) {
            if (dictionary.getDictionaryId()==null||"".equals(dictionary.getDictionaryId())||adminRole==null||"".equals(adminRole)) {
                continue;
            }
            if (dictionary.getDictionaryId().equals(adminRole)) {
                this.adminRole = dictionary.getDictionaryName();
            }
        }

    }
    public void excelToDomain() throws ExcelImportException {
        if(adminId==null||"".equals(adminId)){
            adminId = UUIDUtil.getUUID();
        }
        if(adminAccount==null||"".equals(adminAccount)){
            throw new ExcelNameNullException("用户", "工号" , excelRow);
        }
        for(Admin admin : adminDic){
            if(admin.getAdminAccount().equals(adminAccount)){
                throw new ExcelImportException("在【用户】Sheet中发现存在【工号】列第【"+excelRow+"】行设置的值【"+adminAccount+"】与现有数据重复，导入Excel失败");
            }
            if(admin.getAdminId().equals(adminId)){
                throw new ExcelImportException("在【用户】Sheet中发现存在【ID】列第【"+excelRow+"】行设置的值【"+adminId+"】与现有数据重复，导入Excel失败");
            }
        }
        if(adminName==null||"".equals(adminName)){
            throw new ExcelNameNullException("用户", "姓名" , excelRow);
        }

        if(adminRole!=null&&!"".equals(adminRole)) {
            boolean mapState = false;
            for (Dictionary dictionary : adminRoleDic) {
                if (dictionary.getDictionaryName() == null ||"".equals(dictionary.getDictionaryName())) {
                    continue;
                }
                if (dictionary.getDictionaryName().equals(adminRole)) {
                    this.adminRole = dictionary.getDictionaryId();
                    mapState = true;
                    break;
                }
            }
            if (!mapState) {
                throw new ExcelMapTransferException("用户", "角色",adminRole,excelRow);
            }
        }else {
            throw new ExcelNameNullException("用户", "角色" , excelRow);
        }

    }

    public List<Dictionary> getAdminRoleDic() {
        return adminRoleDic;
    }

    public void setAdminRoleDic(List<Dictionary> adminRoleDic) {
        this.adminRoleDic = adminRoleDic;
    }

    public int getExcelRow() {
        return excelRow;
    }

    public void setExcelRow(int excelRow) {
        this.excelRow = excelRow;
    }

    public String getSubmitState() {
        return submitState;
    }

    public void setSubmitState(String submitState) {
        this.submitState = submitState;
    }

    public Dictionary getAdminRoleObj() {
        return adminRoleObj;
    }

    public void setAdminRoleObj(Dictionary adminRoleObj) {
        this.adminRoleObj = adminRoleObj;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId == null ? null : adminId.trim();
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount == null ? null : adminAccount.trim();
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword == null ? null : adminPassword.trim();
    }

    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole == null ? null : adminRole.trim();
    }

    public Boolean getAdminUseful() {
        return adminUseful;
    }

    public void setAdminUseful(Boolean adminUseful) {
        this.adminUseful = adminUseful;
    }

    public List<Admin> getAdminDic() {
        return adminDic;
    }

    public void setAdminDic(List<Admin> adminDic) {
        this.adminDic = adminDic;
    }
}