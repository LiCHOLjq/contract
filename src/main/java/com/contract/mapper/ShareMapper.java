package com.contract.mapper;

import com.contract.domain.Agreement;
import com.contract.domain.Share;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShareMapper {
    int deleteByPrimaryKey(String shareId);

    int insert(Share record);

    int insertSelective(Share record);

    Share selectByPrimaryKey(String shareId);

    Share selectByPrimaryKeyHasPassword(String shareId);

    int updateByPrimaryKeySelective(Share record);

    int updateByPrimaryKey(Share record);

    List<Share> selectBySearch(@Param("share") Share share);

    int selectBySearchCount(@Param("share") Share share);
}