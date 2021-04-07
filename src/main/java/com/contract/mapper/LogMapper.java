package com.contract.mapper;

import com.contract.domain.Admin;
import com.contract.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogMapper {
    int deleteByPrimaryKey(String logId);

    int deleteAll();

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(String logId);

    List<Log> selectBySearch(@Param("log") Log log);

    int selectBySearchCount(@Param("log") Log log);

    List<Log> selectAll();

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKeyWithBLOBs(Log record);

    int updateByPrimaryKey(Log record);
}