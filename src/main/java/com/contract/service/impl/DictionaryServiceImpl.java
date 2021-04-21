package com.contract.service.impl;


import com.contract.domain.Dictionary;
import com.contract.domain.DictionaryType;
import com.contract.exception.ExcelIdNameRepeatException;
import com.contract.exception.ExcelImportException;
import com.contract.mapper.DictionaryMapper;
import com.contract.mapper.DictionaryTypeMapper;
import com.contract.service.DictionaryService;
import com.contract.util.PageBean;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Autowired
    private DictionaryTypeMapper dictionaryTypeMapper;

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;

    @Override
    public PageBean<DictionaryType> getAllDictionaryType(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<DictionaryType> dtList= dictionaryTypeMapper.selectAll();
        int countNums = dictionaryTypeMapper.selectAllCount();
        PageBean<DictionaryType> pageData = new PageBean<>(currentPage,pageSize,countNums);
        pageData.setItems(dtList);
        return pageData;
    }

    @Override
    public DictionaryType getDictionaryTypeById(String dictionaryTypeById) {
        return dictionaryTypeMapper.selectByPrimaryKey(dictionaryTypeById);
    }

    @Override
    public DictionaryType getDictionaryTypeByName(DictionaryType dictionaryType) {
        return dictionaryTypeMapper.selectByName(dictionaryType.getTypeId(),dictionaryType.getTypeName());
    }

    @Override
    public void addDictionaryType(DictionaryType dictionaryType) {
        dictionaryTypeMapper.insertSelective(dictionaryType);
    }

    @Override
    public void updDictionaryType(DictionaryType dictionaryType) {
        dictionaryTypeMapper.updateByPrimaryKeySelective(dictionaryType);
    }

    @Override
    public void delDictionaryType(DictionaryType dictionaryType) {
        dictionaryTypeMapper.deleteByPrimaryKey(dictionaryType.getTypeId());
    }

    @Override
    public PageBean<Dictionary> getDictionaryBySearch(Dictionary dictionary, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Dictionary> dictionaryList= dictionaryMapper.selectBySearch(dictionary);

        for(Dictionary dictionarySel : dictionaryList){
            if(dictionarySel.getDictionaryFather()!=null&&!"".equals(dictionarySel.getDictionaryFather())){
                dictionarySel.setDictionaryFatherObj(dictionaryMapper.selectByPrimaryKey(dictionarySel.getDictionaryFather()));
            }
        }
        int countNums = dictionaryMapper.selectBySearchCount(dictionary);
        PageBean<Dictionary> pageData = new PageBean<>(currentPage,pageSize,countNums);
        pageData.setItems(dictionaryList);
        return pageData;
    }

    @Override
    public List<Dictionary> getAllDictionary() {
        return dictionaryMapper.selectAllEdit();
    }

    @Override
    public List<Dictionary> getDictionaryByType(String dictionaryType) {
        return dictionaryMapper.selectByType(dictionaryType);
    }

    @Override
    public List<Dictionary> getDictionaryByTypeAndFather(String dictionaryType, String dictionaryFather) {
        return dictionaryMapper.selectByTypeAndFather(dictionaryType,dictionaryFather);
    }

    @Override
    public List<Dictionary> getDictionaryByTypeAndFatherToTree(String dictionaryType, List<String> dictionaryFather) {
        List<Dictionary> result = new ArrayList<>();
        for(String father : dictionaryFather){
            Dictionary dictionary = dictionaryMapper.selectByPrimaryKey(father);
            dictionary.setChildrenList(dictionaryMapper.selectByTypeAndFather(dictionaryType,father));
            result.add(dictionary);
        }
        return result;
    }

    @Override
    public List<Dictionary> getDictionaryByTypeTree(String dictionaryType) {
        List<Dictionary> dictionaryList = dictionaryMapper.selectByType(dictionaryType);
        List<Dictionary> fatherList = new ArrayList<>();
        for(Dictionary dictionary : dictionaryList){
            if(dictionary.getDictionaryFather() == null|| "".equals(dictionary.getDictionaryFather())){
                dictionary.setChildrenList(new ArrayList<>());
                fatherList.add(dictionary);
            }
        }
        for(Dictionary dictionary : dictionaryList){
            if(dictionary.getDictionaryFather() != null&&!"".equals(dictionary.getDictionaryFather())){
                for(Dictionary father : fatherList){
                    if(father.getDictionaryId().equals(dictionary.getDictionaryFather())){
                        father.getChildrenList().add(dictionary);
                        break;
                    }
                }
            }
        }
        return fatherList;
    }

    @Override
    public Dictionary getDictionaryById(String dictionaryId) {
        return dictionaryMapper.selectByPrimaryKey(dictionaryId);
    }

    @Override
    public Dictionary getDictionaryByName(Dictionary dictionary) {
        Dictionary dictionarySQL = new Dictionary();
        dictionarySQL.setDictionaryName(dictionary.getDictionaryName());
        dictionarySQL.setDictionaryType(dictionary.getDictionaryType());
        List<Dictionary> dictionaryList = dictionaryMapper.selectBySearch(dictionarySQL);
        if(dictionaryList.size()>0){
            return dictionaryList.get(0);
        }else{
            return null;
        }

    }

    @Override
    public void addDictionary(Dictionary dictionary) {
        dictionaryMapper.insertSelective(dictionary);
    }

    @Override
    public void updDictionary(Dictionary dictionary) {
        dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    @Override
    public void delDictionary(Dictionary dictionary) {
        dictionaryMapper.deleteByPrimaryKey(dictionary.getDictionaryId());
    }

    @Override
    public List<Dictionary> getDictionaryExcel() {
        List<Dictionary> result = dictionaryMapper.selectBySearch(new Dictionary());
        List<DictionaryType> dictionaryTypeDic = dictionaryTypeMapper.selectAll();
        for(Dictionary dictionary : result){
//            dictionary.setDictionaryDic(result);
            dictionary.setDictionaryTypeDic(dictionaryTypeDic);
            dictionary.domainToExcel();
        }
        return result;
    }

    @Override
    public List<DictionaryType> getDictionaryTypeExcel() {
        return dictionaryTypeMapper.selectAll();
    }

    @Override
    public List<Dictionary> getDictionaryEditExcel() {
        List<Dictionary> result = dictionaryMapper.selectAllEdit();
        List<DictionaryType> dictionaryTypeDic = dictionaryTypeMapper.selectAllEdit();
        for(Dictionary dictionary : result){
//            dictionary.setDictionaryDic(result);
            dictionary.setDictionaryTypeDic(dictionaryTypeDic);
            dictionary.domainToExcel();
        }
        return result;
    }

    @Override
    public List<DictionaryType> getDictionaryTypeEditExcel() {
        return dictionaryTypeMapper.selectAllEdit();
    }
    @Override
    public void importDictionaryExcel(List<Dictionary> dictionaryList, List<DictionaryType> dictionaryTypeList) throws ExcelImportException {

        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            List<DictionaryType> dictionaryTypeRemove = dictionaryTypeMapper.selectAllEdit();
            List<Dictionary> dictionaryRemove = dictionaryMapper.selectAllEdit();
            for(DictionaryType dictionaryType : dictionaryTypeRemove){
                dictionaryTypeMapper.deleteByPrimaryKey(dictionaryType.getTypeId());
            }
            for(Dictionary dictionary : dictionaryRemove){
                dictionaryMapper.deleteByPrimaryKey(dictionary.getDictionaryId());
            }


            //1.检查DictionaryType表id名称的重复性
            List<DictionaryType> dictionaryTypeDic = dictionaryTypeMapper.selectAll();
            List<Dictionary> dictionaryDic = dictionaryMapper.selectBySearch(new Dictionary());
            Set<String> idSet = new HashSet();
            Set<String> nameSet = new HashSet();
            int count = 2;
            for(DictionaryType dictionaryType :dictionaryTypeList){


                dictionaryType.setTypeEdit(true);

                count++;
                idSet.add(dictionaryType.getTypeId());
                nameSet.add(dictionaryType.getTypeName());
                for(DictionaryType dictionaryTypeItem : dictionaryTypeDic){
                    if(dictionaryTypeItem.getTypeId().equals(dictionaryType.getTypeId())){
                        throw new ExcelIdNameRepeatException("字典类别","ID",count);
                    }
                    if(dictionaryTypeItem.getTypeName().equals(dictionaryType.getTypeName())){
                        throw new ExcelIdNameRepeatException("字典类别","字典类别名称",count);
                    }
                }
            }
            if(idSet.size()!=dictionaryTypeList.size()){
                throw new ExcelIdNameRepeatException("字典类别","ID");
            }
            if(nameSet.size()!=dictionaryTypeList.size()){
                throw new ExcelIdNameRepeatException("字典类别","字典类别名称");
            }
            //2.检查Dictionary表id名称的重复性
            idSet = new HashSet();
            count = 2;
            if(dictionaryTypeList.size()>0){
                dictionaryTypeMapper.insertList(dictionaryTypeList);
            }

            dictionaryTypeDic = dictionaryTypeMapper.selectAll();
            for(Dictionary dictionary :dictionaryList){
                count++;
                idSet.add(dictionary.getDictionaryId());
                dictionary.setDictionaryTypeDic(dictionaryTypeDic);
                dictionary.setExcelRow(count);
                dictionary.excelToDomain();
                for(Dictionary dictionaryItem : dictionaryDic){
                    if(dictionaryItem.getDictionaryId().equals(dictionary.getDictionaryId())){
                        throw new ExcelIdNameRepeatException("字典","ID",count);
                    }
                }
            }
            if(idSet.size()!=dictionaryList.size()){
                throw new ExcelIdNameRepeatException("字典","ID");
            }
            if(dictionaryList.size()>0){
                dictionaryMapper.insertList(dictionaryList);
            }
            dataSourceTransactionManager.commit(transactionStatus);     //手动提交
        }catch (Exception e){
            dataSourceTransactionManager.rollback(transactionStatus);       //事务回滚
            throw e;
        }
    }

}
