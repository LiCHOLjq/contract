package com.contract.mapper;

import com.contract.domain.PowerRole;

public interface PowerRoleMapper {
    int deleteByPrimaryKey(String powerRoleId);

    int insert(PowerRole record);

    int insertSelective(PowerRole record);

    PowerRole selectByPrimaryKey(String powerRoleId);

    int updateByPrimaryKeySelective(PowerRole record);

    int updateByPrimaryKey(PowerRole record);
}