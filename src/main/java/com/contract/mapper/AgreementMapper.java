package com.contract.mapper;

import com.contract.domain.Agreement;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AgreementMapper {
    int deleteByPrimaryKey(String agreementId);

    int insert(Agreement record);

    int insertSelective(Agreement record);

    Agreement selectByPrimaryKey(String agreementId);

    int updateByPrimaryKeySelective(Agreement record);

    int updateByPrimaryKeyWithBLOBs(Agreement record);

    int updateByPrimaryKey(Agreement record);
}