package com.contract.mapper;

import com.contract.domain.Dictionary;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DictionaryMapper {
    int deleteByPrimaryKey(String dictionaryId);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    Dictionary selectByPrimaryKey(String dictionaryId);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);
}