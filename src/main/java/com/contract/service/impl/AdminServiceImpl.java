package com.contract.service.impl;

import com.contract.domain.Admin;
import com.contract.domain.Agreement;
import com.contract.domain.Dictionary;
import com.contract.exception.ExcelIdNameRepeatException;
import com.contract.exception.ExcelImportException;
import com.contract.mapper.AdminMapper;
import com.contract.mapper.AgreementMapper;
import com.contract.mapper.CartMapper;
import com.contract.mapper.DictionaryMapper;
import com.contract.service.AdminService;
import com.contract.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private AgreementMapper agreementMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

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

    @Override
    public Admin getAdminById(String adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void addAdmin(Admin admin) {
        adminMapper.insertSelective(admin);
    }

    @Override
    public void updAdmin(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public void delAdmin(Admin admin) {

        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Admin adminSel = adminMapper.selectByPrimaryKey(admin.getAdminId());
            adminMapper.deleteByPrimaryKey(admin.getAdminId());
            cartMapper.deleteByAdminId(admin.getAdminId());
            List<Agreement> agreementList = agreementMapper.selectByUploadAdmin(admin.getAdminId());
            for(Agreement agreement : agreementList){
                Agreement updAgreement = new Agreement();
                updAgreement.setAgreementId(agreement.getAgreementId());
                updAgreement.setAgreementUploadType("agreement_upload_type_user");
                updAgreement.setAgreementUploadAdmin(adminSel.getAdminName());
                agreementMapper.updateByPrimaryKeySelective(updAgreement);
            }
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
    }

    @Override
    public Admin getAdminByAccountExceptSelf(Admin admin) {
        return adminMapper.selectByAccountExceptSelf(admin.getAdminId(),admin.getAdminAccount());
    }

    @Override
    public List<Admin> getAllAdminExcel() {
        List<Admin> adminList = adminMapper.selectAll();
        List<Dictionary> dictionaryList = dictionaryMapper.selectByType("ADMIN_ROLE");
        for(Admin admin :adminList){
            admin.setAdminRoleDic(dictionaryList);
            admin.domainToExcel();
        }
        return adminList;
    }

    @Override
    public void importAdminExcel(List<Admin> adminList) throws ExcelImportException {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            List<Dictionary> dictionaryList = dictionaryMapper.selectByType("ADMIN_ROLE");
            List<Admin> adminListDic = adminMapper.selectAll();
            //1导入用户列表
            Set<String> idSet = new HashSet();
            Set<String> accountSet = new HashSet();
            int count = 2;
            for(Admin admin : adminList){
                count++;
                idSet.add(admin.getAdminId());
                accountSet.add(admin.getAdminAccount());
                admin.setAdminRoleDic(dictionaryList);
                admin.setAdminDic(adminListDic);
                admin.setExcelRow(count);
                admin.excelToDomain();
            }
            if(idSet.size()!=adminList.size()){
                throw new ExcelIdNameRepeatException("用户","ID");
            }
            if(accountSet.size()!=adminList.size()){
                throw new ExcelIdNameRepeatException("用户","工号");
            }
            if(adminList.size()>0){
                adminMapper.insertList(adminList);
            }
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
    }
}
