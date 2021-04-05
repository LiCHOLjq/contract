package com.contract.service.impl;

import com.contract.domain.Admin;
import com.contract.mapper.AdminMapper;
import com.contract.mapper.DictionaryMapper;
import com.contract.service.AdminService;
import com.contract.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public Admin getAdminByAccountHasPassword(String adminAccount) {
        return adminMapper.selectByAccountKeyHasPassword(adminAccount);
    }

    @Override
    public Admin getAdminByIdHasPassword(String adminId) {
        return adminMapper.selectByPrimaryKeyHasPassword(adminId);
    }

    @Override
    public PageBean<Admin> getAdminBySearch(Admin admin, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Admin> adminList= adminMapper.selectBySearch(admin);
        for (Admin item : adminList){
            item.setAdminRoleObj(dictionaryMapper.selectByPrimaryKey(item.getAdminRole()));
        }
        int countNums = adminMapper.selectBySearchCount(admin);
        PageBean<Admin> pageData = new PageBean<>(currentPage,pageSize,countNums);
        pageData.setItems(adminList);
        return pageData;
    }
}
