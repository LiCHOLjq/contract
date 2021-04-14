package com.contract.service;

import com.contract.domain.Admin;
import com.contract.domain.Agreement;
import com.contract.domain.Share;
import com.contract.util.PageBean;

import java.text.ParseException;

public interface ShareService {
    Share getShareByIdHasPassword(String shareId);
    Share addDownLoadShare(Share share,String adminId) throws Exception;

    PageBean<Share> getShareBySearch(Share share, int currentPage, int pageSize);

    void delShare(Share share);

    void restoreShare(Share share);

    void relDelShare(Share share);
}
