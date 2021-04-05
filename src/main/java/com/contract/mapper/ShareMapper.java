package com.contract.mapper;

import com.contract.domain.Share;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShareMapper {
    int deleteByPrimaryKey(String shareId);

    int insert(Share record);

    int insertSelective(Share record);

    Share selectByPrimaryKey(String shareId);

    Share selectByPrimaryKeyHasPassword(String shareId);

    int updateByPrimaryKeySelective(Share record);

    int updateByPrimaryKey(Share record);
}