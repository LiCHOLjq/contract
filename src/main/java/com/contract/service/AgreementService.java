package com.contract.service;

import com.contract.domain.Agreement;
import com.contract.domain.Product;
import com.contract.domain.Share;
import com.contract.util.PageBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AgreementService {
    PageBean<Agreement> getAgreementBySearch(String adminId,Agreement agreement,int currentPage, int pageSize,String sort);
    PageBean<Agreement> getAgreementUserBySearch(String adminId,Agreement agreement,int currentPage, int pageSize,String sort);


    List<Agreement> getAgreementBySearch(Agreement agreement,String sort);




    void delAgreement(Agreement agreement);

    void restoreAgreement(Agreement agreement);

    void relDelAgreement(Agreement agreement);

    void delAllDeleted();

    void addAgreement(Agreement agreement, List<Product> productList, MultipartFile file,String adminId) throws Exception;

    void addAgreement(Agreement agreement, String shareId, MultipartFile file) throws ParseException, IOException;

    Agreement getAgreementDetails(String agreementId);

    Agreement getAgreementById(String agreementId);



    void updAgreement(Agreement agreement, List<Product> productList, String adminId) throws ParseException;
}
