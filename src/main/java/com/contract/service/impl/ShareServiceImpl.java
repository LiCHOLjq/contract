package com.contract.service.impl;

import com.contract.domain.Share;
import com.contract.mapper.ShareMapper;
import com.contract.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl implements ShareService {

    @Autowired
    private ShareMapper shareMapper;

    @Override
    public Share getShareByIdHasPassword(String shareId) {
        return shareMapper.selectByPrimaryKeyHasPassword(shareId);
    }
}
