package com.contract.mapper;

import com.contract.domain.Power;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PowerMapper {
    int deleteByPrimaryKey(String powerId);

    int insert(Power record);

    int insertSelective(Power record);

    Power selectByPrimaryKey(String powerId);

    List<Power> selectByAdminRole(String roleId);

    int updateByPrimaryKeySelective(Power record);

    int updateByPrimaryKey(Power record);
}