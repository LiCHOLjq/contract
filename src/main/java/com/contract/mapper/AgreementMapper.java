package com.contract.mapper;

import com.contract.domain.Admin;
import com.contract.domain.Agreement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgreementMapper {
    int deleteByPrimaryKey(String agreementId);

    int insert(Agreement record);

    int insertSelective(Agreement record);

    Agreement selectByPrimaryKey(String agreementId);

    List<Agreement> selectBySearch(@Param("agreement") Agreement agreement,String sort);

    int selectBySearchCount(@Param("agreement") Agreement agreement);

    List<Agreement> selectByUploadAdmin(String adminId);

    int updateByPrimaryKeySelective(Agreement record);

    int updateByPrimaryKeyWithBLOBs(Agreement record);

    int updateByPrimaryKey(Agreement record);
}