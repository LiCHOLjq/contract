package com.contract.service;

import com.contract.domain.Admin;
import com.contract.domain.Agreement;
import com.contract.domain.Share;
import com.contract.domain.ShareAgreement;
import com.contract.util.PageBean;

import java.text.ParseException;
import java.util.List;

public interface ShareService {
    Share getShareByIdHasPassword(String shareId);

    Share getShareDetails(String shareId);

    Share addDownLoadShare(Share share,String adminId) throws Exception;

    PageBean<Share> getShareBySearch(Share share, int currentPage, int pageSize);

    void delShare(Share share);

    void restoreShare(Share share);

    void relDelShare(Share share);

    void delOverdue();

    void delAllDeleted();

    ShareAgreement getShareAgreement(String shareId,String agreementId);

    List<ShareAgreement> getShareAgreementByShareId(String shareId);



}
