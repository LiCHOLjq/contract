package com.contract.service;


import com.contract.domain.Dictionary;
import com.contract.domain.DictionaryType;
import com.contract.exception.ExcelImportException;
import com.contract.util.PageBean;

import java.util.List;

public interface DictionaryService {
    PageBean<DictionaryType> getAllDictionaryType(int currentPage, int pageSize);
    DictionaryType getDictionaryTypeById(String dictionaryTypeById);
    DictionaryType getDictionaryTypeByName(DictionaryType dictionaryType);
    void addDictionaryType(DictionaryType dictionaryType);
    void updDictionaryType(DictionaryType dictionaryType);
    void delDictionaryType(DictionaryType dictionaryType);

    PageBean<Dictionary> getDictionaryBySearch(Dictionary dictionary, int currentPage, int pageSize);
    List<Dictionary> getAllDictionary();
    List<Dictionary> getDictionaryByType(String dictionaryType);
    List<Dictionary> getDictionaryByTypeAndFather(String dictionaryType,String dictionaryFather);
    List<Dictionary> getDictionaryByTypeAndFatherToTree(String dictionaryType,List<String> dictionaryFather);
    List<Dictionary> getDictionaryByTypeTree(String dictionaryType);
    Dictionary getDictionaryById(String dictionaryId);
    Dictionary getDictionaryByName(Dictionary dictionary);

    void addDictionary(Dictionary dictionary);
    void updDictionary(Dictionary dictionary);
    void delDictionary(Dictionary dictionary);

    List<Dictionary> getDictionaryExcel();
    List<DictionaryType> getDictionaryTypeExcel();
    List<Dictionary> getDictionaryEditExcel();
    List<DictionaryType> getDictionaryTypeEditExcel();
    void importDictionaryExcel(List<Dictionary> dictionaryList,List<DictionaryType> dictionaryTypeList) throws ExcelImportException;
}
