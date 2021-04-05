package com.contract.mapper;

import com.contract.domain.DictionaryType;

public interface DictionaryTypeMapper {
    int deleteByPrimaryKey(String typeId);

    int insert(DictionaryType record);

    int insertSelective(DictionaryType record);

    DictionaryType selectByPrimaryKey(String typeId);

    int updateByPrimaryKeySelective(DictionaryType record);

    int updateByPrimaryKey(DictionaryType record);
}