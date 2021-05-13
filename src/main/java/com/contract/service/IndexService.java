package com.contract.service;

import com.contract.domain.AgreementData;
import com.contract.domain.ProductData;

public interface IndexService {
    AgreementData getAgreementData();

    ProductData getProductData();
}
