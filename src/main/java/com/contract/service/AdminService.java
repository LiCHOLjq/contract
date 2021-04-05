package com.contract.service;

import com.contract.domain.Admin;
import com.contract.util.PageBean;

public interface AdminService {
    Admin getAdminByAccountHasPassword(String adminAccount);
    Admin getAdminByIdHasPassword(String adminId);

    PageBean<Admin> getAdminBySearch(Admin admin, int currentPage, int pageSize);
}
