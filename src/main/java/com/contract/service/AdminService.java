package com.contract.service;

import com.contract.domain.Admin;
import com.contract.exception.BaseException;
import com.contract.exception.ExcelImportException;
import com.contract.util.PageBean;

import java.util.List;

public interface AdminService {
    Admin getAdminByAccountHasPassword(String adminAccount);
    Admin getAdminByIdHasPassword(String adminId);
    PageBean<Admin> getAdminBySearch(Admin admin, int currentPage, int pageSize);



    Admin getAdminById(String adminId);

    void addAdmin(Admin admin);
    void updAdmin(Admin admin);
    void delAdmin(Admin admin);


    Admin getAdminByAccountExceptSelf(Admin admin);

    List<Admin> getAllAdminExcel();

    void importAdminExcel(List<Admin> adminList) throws ExcelImportException;


    void changePassword(String adminId,String oldPassword,String newPassword) throws BaseException;
}
