package com.contract.service;

import com.contract.domain.Admin;
import com.contract.domain.Share;

import java.text.ParseException;

public interface ShareService {
    Share getShareByIdHasPassword(String shareId);
    Share addDownLoadShare(Share share,String adminId) throws Exception;
}
