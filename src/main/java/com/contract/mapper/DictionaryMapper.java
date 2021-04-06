package com.contract.mapper;


import com.contract.domain.Dictionary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DictionaryMapper {
    int deleteByPrimaryKey(String dictionaryId);

    int insert(Dictionary record);

    int insertSelective(Dictionary record);

    int insertList(List<Dictionary> record);

    Dictionary selectByPrimaryKey(String dictionaryId);

    List<Dictionary> selectBySearch(@Param("dictionary")Dictionary dictionary);

    int selectBySearchCount(@Param("dictionary")Dictionary dictionary);

    List<Dictionary> selectAllEdit();

    List<Dictionary> selectByType(String dictionaryType);

    int updateByPrimaryKeySelective(Dictionary record);

    int updateByPrimaryKey(Dictionary record);
}