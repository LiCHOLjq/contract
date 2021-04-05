package com.contract.mapper;

import com.contract.domain.ShareAgreement;

public interface ShareAgreementMapper {
    int deleteByPrimaryKey(String shareAgreementId);

    int insert(ShareAgreement record);

    int insertSelective(ShareAgreement record);

    ShareAgreement selectByPrimaryKey(String shareAgreementId);

    int updateByPrimaryKeySelective(ShareAgreement record);

    int updateByPrimaryKey(ShareAgreement record);
}