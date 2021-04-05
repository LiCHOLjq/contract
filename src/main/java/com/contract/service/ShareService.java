package com.contract.service;

import com.contract.domain.Admin;
import com.contract.domain.Share;

public interface ShareService {
    Share getShareByIdHasPassword(String shareId);
}
