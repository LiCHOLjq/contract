package com.contract.service.impl;

import com.contract.config.FileSaveConfig;
import com.contract.domain.*;
import com.contract.mapper.*;
import com.contract.service.ShareService;
import com.contract.util.PageBean;
import com.contract.util.UUIDUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ShareAgreementMapper shareAgreementMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private AgreementMapper agreementMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @Override
    public Share getShareByIdHasPassword(String shareId) {
        Share item = shareMapper.selectByPrimaryKeyHasPassword(shareId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        item.setShareAdminObj(adminMapper.selectByPrimaryKey(item.getShareAdmin()));
        item.setShareTypeObj(dictionaryMapper.selectByPrimaryKey(item.getShareType()));
        if (item.getShareBeginDate() != null && item.getShareEndDate() != null) {
            item.setShareDateStr(sdf.format(item.getShareBeginDate())+ " 至 " +sdf.format(item.getShareEndDate()));
        }
        if (item.getShareBeginDate() == null && item.getShareEndDate() != null) {
            item.setShareDateStr( sdf.format(item.getShareEndDate()) + " 截止");
        }
        if (item.getShareBeginDate() != null && item.getShareEndDate() == null) {
            item.setShareDateStr( sdf.format(item.getShareBeginDate()) + " 开始");
        }
        if (item.getShareBeginDate() == null && item.getShareEndDate() == null) {
            item.setShareDateStr( "永久有效");
        }
        return item;
    }

    @Override
    public Share getShareDetails(String shareId) {
        Share share = shareMapper.selectByPrimaryKeyHasPassword(shareId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        share.setShareAdmin(adminMapper.selectByPrimaryKey(share.getShareAdmin()).getAdminName());
        share.setShareTypeObj(dictionaryMapper.selectByPrimaryKey(share.getShareType()));
        if (share.getShareBeginDate() != null && share.getShareEndDate() != null) {
            share.setShareDateStr(sdf.format(share.getShareBeginDate())+ " 至 " +sdf.format(share.getShareEndDate()));
        }
        if (share.getShareBeginDate() == null && share.getShareEndDate() != null) {
            share.setShareDateStr( sdf.format(share.getShareEndDate()) + " 截止");
        }
        if (share.getShareBeginDate() != null && share.getShareEndDate() == null) {
            share.setShareDateStr( sdf.format(share.getShareBeginDate()) + " 开始");
        }
        if (share.getShareBeginDate() == null && share.getShareEndDate() == null) {
            share.setShareDateStr( "永久有效");
        }



        List<ShareAgreement> shareAgreementList = shareAgreementMapper.selectByShare(shareId);
        List<ShareAgreement> shareAgreementListUseful = new ArrayList<>();
        for(ShareAgreement shareAgreement : shareAgreementList){
            Agreement agreement = agreementMapper.selectByPrimaryKey(shareAgreement.getAgreementId());
            if(!agreement.getAgreementDelete()){
                shareAgreement.setAgreementName(agreement.getAgreementName());
                shareAgreement.setAgreementUploadDate(sdf.format(agreement.getAgreementUploadDate()));
                shareAgreement.setAgreementType(dictionaryMapper.selectByPrimaryKey(agreement.getAgreementType()).getDictionaryName());
                shareAgreement.setAgreementExtend(agreement.getAgreementExtend());
                shareAgreementListUseful.add(shareAgreement);
            }
        }
        share.setShareAgreementList(shareAgreementListUseful);
        return share;
    }

    @Override
    public Share addDownLoadShare(Share share, String adminId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            share.setShareId(UUIDUtil.getUUID());
            share.setShareAdmin(adminId);
            share.setShareDelete(false);
            if(share.getShareBeginDateStr()==null||"".equals(share.getShareBeginDateStr())){
                share.setShareBeginDate(null);
            }else{
                share.setShareBeginDate(sdf.parse(share.getShareBeginDateStr()));
            }
            if(share.getShareEndDateStr()==null||"".equals(share.getShareEndDateStr())){
                share.setShareEndDate(null);
            }else{
                share.setShareEndDate(sdf.parse(share.getShareEndDateStr()));
            }
            if(share.getShareBeginDate()!=null&&share.getShareEndDateStr()!=null&&!"".equals(share.getShareBeginDate())&&!"".equals(share.getShareEndDateStr())&&!share.getShareBeginDate().before(share.getShareEndDate())){
                throw new Exception("开始时间不能早于结束时间");
            }
            if(!share.getShareHasPassword()){
                share.setSharePassword("");
            }
            shareMapper.insertSelective(share);

            if(share.getShareType().equals("share_type_download")){
                List<Cart> cartList = cartMapper.selectByAdmin(adminId);
                List<ShareAgreement> shareAgreementList = new ArrayList<>();
                for(Cart cart : cartList){
                    ShareAgreement shareAgreement = new ShareAgreement();
                    shareAgreement.setShareAgreementId(UUIDUtil.getUUID());
                    shareAgreement.setAgreementId(cart.getCartAgreement());
                    shareAgreement.setShareId(share.getShareId());
                    shareAgreementList.add(shareAgreement);
                }
                shareAgreementMapper.insertList(shareAgreementList);
                cartMapper.deleteByAdminId(adminId);
            }
            share.setShareAdminObj(adminMapper.selectByPrimaryKey(adminId));
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
        return share;
    }


    @Override
    public PageBean<Share> getShareBySearch(Share share, int currentPage, int pageSize) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        PageHelper.startPage(currentPage,pageSize);
        List<Share> shareList = shareMapper.selectBySearch(share);
        for(Share item : shareList){
            item.setShareAdminObj(adminMapper.selectByPrimaryKey(item.getShareAdmin()));
            item.setShareTypeObj(dictionaryMapper.selectByPrimaryKey(item.getShareType()));
            if (item.getShareBeginDate() != null && item.getShareEndDate() != null) {
                item.setShareDateStr(sdf.format(item.getShareBeginDate())+ " 至 " +sdf.format(item.getShareEndDate()));
            }
            if (item.getShareBeginDate() == null && item.getShareEndDate() != null) {
                item.setShareDateStr( sdf.format(item.getShareEndDate()) + " 截止");
            }
            if (item.getShareBeginDate() != null && item.getShareEndDate() == null) {
                item.setShareDateStr( sdf.format(item.getShareBeginDate()) + " 开始");
            }
            if (item.getShareBeginDate() == null && item.getShareEndDate() == null) {
                item.setShareDateStr( "永久有效");
            }
        }
        int countNums = shareMapper.selectBySearchCount(share);
        PageBean<Share> pageData = new PageBean<>(currentPage,pageSize,countNums);
        pageData.setItems(shareList);
        return pageData;

    }

    @Override
    public void delShare(Share share) {
        Share updShare = new Share();
        updShare.setShareId(share.getShareId());
        updShare.setShareDelete(true);
        shareMapper.updateByPrimaryKeySelective(updShare);
    }

    @Override
    public void restoreShare(Share share) {
        Share updShare = new Share();
        updShare.setShareId(share.getShareId());
        updShare.setShareDelete(false);
        shareMapper.updateByPrimaryKeySelective(updShare);
    }

    @Override
    public void relDelShare(Share share) {
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {

            shareAgreementMapper.deleteByShare(share.getShareId());
            shareMapper.deleteByPrimaryKey(share.getShareId());

            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
    }
}
