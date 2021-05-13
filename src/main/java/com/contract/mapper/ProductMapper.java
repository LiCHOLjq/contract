package com.contract.mapper;

import com.contract.domain.Dictionary;
import com.contract.domain.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    int deleteByPrimaryKey(String productId);
    int deleteByAgreement(String agreementId);
    int insert(Product record);

    int insertList(List<Product> record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(String productId);

    List<Product> selectByAgreement(String agreementId);

    List<Product> selectAll();

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}