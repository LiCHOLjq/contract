package com.contract.mapper;

import com.contract.domain.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartMapper {
    int deleteByPrimaryKey(String cartId);

    int deleteByAdminId(String adminId);

    int deleteByAgreementId(String agreementId);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(String cartId);

    List<Cart> selectByAdmin(String adminId);

    Cart selectByAdminAgreement(String adminId,String agreementId);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}