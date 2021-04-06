package com.contract.mapper;

import com.contract.domain.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    int deleteByPrimaryKey(String adminId);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String adminId);

    Admin selectByPrimaryKeyHasPassword(String adminId);

    Admin selectByAccountKeyHasPassword(String adminAccount);

    List<Admin> selectBySearch(@Param("admin") Admin admin);

    int selectBySearchCount(@Param("admin") Admin admin);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
    //===========
    int insertList(List<Admin> adminList);
    Admin selectByAccountExceptSelf(String adminId, String adminAccount);

    List<Admin> selectAll();

}