package com.contract.service.impl;

import com.contract.domain.Admin;
import com.contract.domain.Cart;
import com.contract.domain.Share;
import com.contract.domain.ShareAgreement;
import com.contract.mapper.AdminMapper;
import com.contract.mapper.CartMapper;
import com.contract.mapper.ShareAgreementMapper;
import com.contract.mapper.ShareMapper;
import com.contract.service.ShareService;
import com.contract.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

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
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @Override
    public Share getShareByIdHasPassword(String shareId) {
        return shareMapper.selectByPrimaryKeyHasPassword(shareId);
    }

    @Override
    public Share addDownLoadShare(Share share, String adminId) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            share.setShareId(UUIDUtil.getUUID());
            share.setShareAdmin(adminId);
            share.setShareType("share_type_download");
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
            if(share.getShareBeginDate()!=null&&share.getShareEndDateStr()!=null&&!share.getShareBeginDate().before(share.getShareEndDate())){
                throw new Exception("开始时间不能早于结束时间");
            }


            shareMapper.insertSelective(share);

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

            share.setShareAdminObj(adminMapper.selectByPrimaryKey(adminId));

            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
        return share;
    }
}
