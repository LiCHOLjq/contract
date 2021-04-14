package com.contract.service.impl;

import com.contract.config.FileSaveConfig;
import com.contract.domain.*;
import com.contract.mapper.*;
import com.contract.service.AgreementService;
import com.contract.service.LogService;
import com.contract.util.PageBean;
import com.contract.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AgreementServiceImpl implements AgreementService {
    @Autowired
    private AgreementMapper agreementMapper;
    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ShareAgreementMapper shareAgreementMapper;

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Resource
    private LogService logService;

    @Autowired
    private TransactionDefinition transactionDefinition;
    @Override
    public PageBean<Agreement> getAgreementBySearch(String adminId, Agreement agreement, int currentPage, int pageSize,String sort) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            agreement.setAgreementSignDateBegin(sdf.parse(agreement.getAgreementSignDateBeginStr()+" 00:00:00"));
        } catch (ParseException e) {
            agreement.setAgreementSignDateBegin(null);
        }
        try {
            agreement.setAgreementSignDateEnd(sdf.parse(agreement.getAgreementSignDateEndStr()+" 23:59:59"));
        } catch (ParseException e) {
            agreement.setAgreementSignDateEnd(null);
        }

        PageHelper.startPage(currentPage,pageSize);
        List<Agreement> agreementList = agreementMapper.selectBySearch(agreement,sort);
        for (Agreement item : agreementList){
            item.setAgreementTypeObj(dictionaryMapper.selectByPrimaryKey(item.getAgreementType()));
            item.setAgreementSignDateStr(sdf2.format(item.getAgreementSignDate()));
            if(item.getAgreementUploadType().equals("agreement_upload_type_admin")){
                Admin admin = adminMapper.selectByPrimaryKey(item.getAgreementUploadAdmin());
                item.setAgreementUploadAdmin(admin.getAdminName()+"(管理员)");
            }
            item.setAgreementUploadDateStr(sdf.format(item.getAgreementUploadDate()));
            List<Product> productList = productMapper.selectByAgreement(item.getAgreementId());
            for(Product product : productList){
                product.setProductTypeObj(dictionaryMapper.selectByPrimaryKey(product.getProductType()));
            }
            item.setProductList(productList);
            Cart cart = cartMapper.selectByAdminAgreement(adminId,item.getAgreementId());
            item.setShareState(cart!=null);


        }
        int countNums = agreementMapper.selectBySearchCount(agreement);
        PageBean<Agreement> pageData = new PageBean<>(currentPage,pageSize,countNums);
        pageData.setItems(agreementList);
        return pageData;
    }

    @Override
    public List<Agreement> getAgreementBySearch(Agreement agreement,String sort) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        List<Agreement> agreementList = agreementMapper.selectBySearch(agreement,sort);
        for (Agreement item : agreementList){
            item.setAgreementTypeObj(dictionaryMapper.selectByPrimaryKey(item.getAgreementType()));
            item.setAgreementSignDateStr(sdf2.format(item.getAgreementSignDate()));
            if(item.getAgreementUploadType().equals("agreement_upload_type_admin")){
                Admin admin = adminMapper.selectByPrimaryKey(item.getAgreementUploadAdmin());
                item.setAgreementUploadAdmin(admin.getAdminName()+"(管理员)");
            }
            item.setAgreementUploadDateStr(sdf.format(item.getAgreementUploadDate()));
            List<Product> productList = productMapper.selectByAgreement(item.getAgreementId());
            for(Product product : productList){
                product.setProductTypeObj(dictionaryMapper.selectByPrimaryKey(product.getProductType()));
            }
            item.setProductList(productList);
        }
        return agreementList;
    }

    @Override
    public void delAgreement(Agreement agreement) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Agreement updAgreement = new Agreement();
            updAgreement.setAgreementId(agreement.getAgreementId());
            updAgreement.setAgreementDelete(true);
            agreementMapper.updateByPrimaryKeySelective(updAgreement);
            cartMapper.deleteByAgreementId(agreement.getAgreementId());

            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }

    }

    @Override
    public void restoreAgreement(Agreement agreement) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Agreement updAgreement = new Agreement();
            updAgreement.setAgreementId(agreement.getAgreementId());
            updAgreement.setAgreementDelete(false);

            agreementMapper.updateByPrimaryKeySelective(updAgreement);
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
    }

    @Override
    public void relDelAgreement(Agreement agreement) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Agreement agreementData = agreementMapper.selectByPrimaryKey(agreement.getAgreementId());
            cartMapper.deleteByAgreementId(agreement.getAgreementId());
            productMapper.deleteByAgreement(agreement.getAgreementId());
            shareAgreementMapper.deleteByAgreement(agreement.getAgreementId());
            agreementMapper.deleteByPrimaryKey(agreement.getAgreementId());
            new File(FileSaveConfig.AGREEMENT + agreementData.getAgreementId() + agreementData.getAgreementExtend()).delete();
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
    }

    @Override
    public void addAgreement(Agreement agreement, List<Product> productList, MultipartFile file, String adminId) throws ParseException, IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //处理agreement productList
            agreement.setAgreementId(UUIDUtil.getUUID());
            agreement.setAgreementSignDate(sdf.parse(agreement.getAgreementSignDateStr()));
            agreement.setAgreementUploadType("agreement_upload_type_admin");
            agreement.setAgreementUploadAdmin(adminId);
            agreement.setAgreementUploadDate(new Date());
            agreement.setAgreementDelete(false);
            int sort = 1;
            for (Product product : productList){
                product.setProductId(UUIDUtil.getUUID());
                product.setProductAgreement(agreement.getAgreementId());
                product.setProductSort(sort);
                sort++;
            }
            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            agreement.setAgreementExtend(suffixName);
            File dest = new File(FileSaveConfig.AGREEMENT + agreement.getAgreementId() + suffixName);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file.transferTo(dest);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
            agreementMapper.insertSelective(agreement);

            if(productList.size()>0){
                productMapper.insertList(productList);
            }
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
    }

    @Override
    public Agreement getAgreementDetails(String agreementId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Agreement item = agreementMapper.selectByPrimaryKey(agreementId);
        item.setAgreementTypeObj(dictionaryMapper.selectByPrimaryKey(item.getAgreementType()));
        item.setAgreementSignDateStr(sdf2.format(item.getAgreementSignDate()));

        item.setAgreementUploadDateStr(sdf.format(item.getAgreementUploadDate()));
        List<Product> productList = productMapper.selectByAgreement(item.getAgreementId());
        for(Product product : productList){
            product.setProductTypeObj(dictionaryMapper.selectByPrimaryKey(product.getProductType()));
        }
        item.setProductList(productList);
        return item;
    }

    @Override
    public Agreement getAgreementById(String agreementId) {
        return agreementMapper.selectByPrimaryKey(agreementId);
    }

    @Override
    public void updAgreement(Agreement agreement, List<Product> productList, String adminId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            //处理agreement productList

            agreement.setAgreementSignDate(sdf.parse(agreement.getAgreementSignDateStr()));
            agreement.setAgreementUploadDate(null);
            agreement.setAgreementUploadAdmin(null);
            agreement.setAgreementUploadType(null);
            agreement.setAgreementDelete(null);
            agreement.setAgreementExtend(null);
            int sort = 1;
            for (Product product : productList){
                product.setProductId(UUIDUtil.getUUID());
                product.setProductAgreement(agreement.getAgreementId());
                product.setProductSort(sort);
                sort++;
            }
            productMapper.deleteByAgreement(agreement.getAgreementId());


            agreementMapper.updateByPrimaryKeySelective(agreement);
            if(productList.size()>0){
                productMapper.insertList(productList);
            }
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
    }
}
