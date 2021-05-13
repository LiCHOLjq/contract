package com.contract.service.impl;

import com.contract.domain.*;
import com.contract.mapper.AgreementMapper;
import com.contract.mapper.DictionaryMapper;
import com.contract.mapper.ProductMapper;
import com.contract.service.IndexService;
import com.contract.util.BigDecimalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private AgreementMapper agreementMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public AgreementData getAgreementData() {
        AgreementData agreementData = new AgreementData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        List<Dictionary> typeList = dictionaryMapper.selectByType("AGREEMENT_TYPE");

        List<String> typeListStr = new ArrayList<>();
        Map<String,Integer> typeMap = new HashMap<>();
        int i = 0;
        for(Dictionary dictionary : typeList){
            typeListStr.add(dictionary.getDictionaryName());
            typeMap.put(dictionary.getDictionaryId(),i++);
        }
        agreementData.setTypeList(typeListStr);
        agreementData.setYearList(new ArrayList<>());
        agreementData.setDataList(new ArrayList<>());
        List<Agreement> agreementList = agreementMapper.selectAllNoDeleted();
        AgreementData.Data sum = agreementData.new Data();
        sum.setYear("全部");
        sum.setData(new int[typeListStr.size()]);
        sum.setSum(0);
        for(Agreement agreement : agreementList){
            String year = sdf.format(agreement.getAgreementSignDate());
            AgreementData.Data last;
            if(agreementData.getYearList().size()>0&&agreementData.getYearList().get(agreementData.getYearList().size()-1).equals(year+"年")){
                //操作最后一个
                last = agreementData.getDataList().get(agreementData.getYearList().size()-1);
            }else{
                //新建年并插入到最后
                agreementData.getYearList().add(year+"年");
                last = agreementData.new Data();
                last.setYear(year+"年");
                last.setData(new int[typeListStr.size()]);
                last.setSum(0);
                agreementData.getDataList().add(last);
            }
            last.getData()[typeMap.get(agreement.getAgreementType())]++;
            last.setSum(last.getSum()+1);
            sum.getData()[typeMap.get(agreement.getAgreementType())]++;
            sum.setSum(sum.getSum()+1);
        }
        agreementData.getYearList().add("全部");
        agreementData.getDataList().add(sum);
        return agreementData;
    }

    @Override
    public ProductData getProductData() {
        ProductData productData = new ProductData();
        List<String> titleList = new ArrayList<>();
        titleList.add("合同数");
        titleList.add("台数");
        titleList.add("金额");
        productData.setTitleList(titleList);
        List<Dictionary> typeList = dictionaryMapper.selectByType("PRODUCT_TYPE");
        List<String> typeListStr = new ArrayList<>();
        Map<String,Integer> typeMap = new HashMap<>();
        int i = 0;
        for(Dictionary dictionary : typeList){
            typeListStr.add(dictionary.getDictionaryName());
            typeMap.put(dictionary.getDictionaryId(),i++);
        }
        productData.setTypeList(typeListStr);
        typeListStr.add("XC");
        typeMap.put("XC",i++);
        typeListStr.add("合计");
        typeMap.put("合计",i++);
        productData.setDataList(new ArrayList<>());
        ProductData.Data data = productData.new Data();
        data.setTitle("合同数");
        data.setData(new double[typeListStr.size()]);
        productData.getDataList().add(data);
        data = productData.new Data();
        data.setTitle("台数");
        data.setData(new double[typeListStr.size()]);
        productData.getDataList().add(data);
        data = productData.new Data();
        data.setTitle("金额");
        data.setData(new double[typeListStr.size()]);
        productData.getDataList().add(data);
        List<Agreement> agreementList = agreementMapper.selectAllNoDeleted();
        List<Product> productList = productMapper.selectAll();
        for(Agreement agreement : agreementList){
            double[] hts = new double[typeListStr.size()];
            double[] ts = new double[typeListStr.size()];
            double[] je = new double[typeListStr.size()];
            hts[typeListStr.size()-1] = 1;
            ts[typeListStr.size()-1] = 0;
            je[typeListStr.size()-1] = BigDecimalUtil.div(agreement.getAgreementAmount(),10000);
            //je[typeListStr.size()-1] = agreement.getAgreementAmount();
            if(agreement.getAgreementInnovation()){
                hts[typeListStr.size()-2] = 1;
                ts[typeListStr.size()-2] = 0;
                je[typeListStr.size()-2] = BigDecimalUtil.div(agreement.getAgreementAmount(),10000);
                //je[typeListStr.size()-2] = agreement.getAgreementAmount();
                for(Product product : productList){
                    if(agreement.getAgreementId().equals(product.getProductAgreement())){
                        ts[typeListStr.size()-2] = BigDecimalUtil.add(ts[typeListStr.size()-2],product.getProductNumber());
                    }
                }
            }else{
                for(Product product : productList){
                    if(agreement.getAgreementId().equals(product.getProductAgreement())){
                        //System.out.print(product.getProductId());
                        int index = typeMap.get(product.getProductType());
                        if(hts[index]==0.0){
                            hts[index]=1.0;
                            je[index] = BigDecimalUtil.div(agreement.getAgreementAmount(),10000);
                            //je[index] = agreement.getAgreementAmount();
                        }
                        ts[index] = ts[index] + product.getProductNumber();
                        ts[typeListStr.size()-1] = BigDecimalUtil.add(ts[typeListStr.size()-1],product.getProductNumber());
                    }
                }
            }
            for(int htj = 0; htj< typeListStr.size();htj++){
                productData.getDataList().get(0).getData()[htj] = BigDecimalUtil.add(productData.getDataList().get(0).getData()[htj],hts[htj]);
                productData.getDataList().get(1).getData()[htj] = BigDecimalUtil.add(productData.getDataList().get(1).getData()[htj] ,ts[htj]);
                productData.getDataList().get(2).getData()[htj] = BigDecimalUtil.add(productData.getDataList().get(2).getData()[htj] ,je[htj]);
            }
        }
        return productData;
    }
}
