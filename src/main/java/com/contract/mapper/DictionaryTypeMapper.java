package com.contract.mapper;


import com.contract.domain.DictionaryType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DictionaryTypeMapper {
    int deleteByPrimaryKey(String typeId);

    int insert(DictionaryType record);

    int insertSelective(DictionaryType record);

    int insertList(List<DictionaryType> record);

    DictionaryType selectByPrimaryKey(String typeId);

    List<DictionaryType> selectAll();

    List<DictionaryType> selectAllEdit();

    int selectAllCount();

    DictionaryType selectByName(String typeId,String typeName);

    int updateByPrimaryKeySelective(DictionaryType record);

    int updateByPrimaryKey(DictionaryType record);
}