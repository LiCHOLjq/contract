package com.contract.mapper;

import com.contract.domain.ShareAgreement;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShareAgreementMapper {
    int deleteByPrimaryKey(String shareAgreementId);
    int deleteByAgreement(String agreementId);
    int insert(ShareAgreement record);

    int insertSelective(ShareAgreement record);

    int insertList(List<ShareAgreement> record);

    ShareAgreement selectByPrimaryKey(String shareAgreementId);

    int updateByPrimaryKeySelective(ShareAgreement record);

    int updateByPrimaryKey(ShareAgreement record);
}