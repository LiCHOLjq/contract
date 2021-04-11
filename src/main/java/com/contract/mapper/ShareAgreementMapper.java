package com.contract.mapper;

import com.contract.domain.ShareAgreement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShareAgreementMapper {
    int deleteByPrimaryKey(String shareAgreementId);
    int deleteByAgreement(String agreementId);
    int insert(ShareAgreement record);

    int insertSelective(ShareAgreement record);

    ShareAgreement selectByPrimaryKey(String shareAgreementId);

    int updateByPrimaryKeySelective(ShareAgreement record);

    int updateByPrimaryKey(ShareAgreement record);
}