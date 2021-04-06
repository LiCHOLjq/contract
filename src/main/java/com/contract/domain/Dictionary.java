package com.contract.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.contract.exception.ExcelImportException;
import com.contract.exception.ExcelMapTransferException;
import com.contract.exception.ExcelNameNullException;


import java.util.List;

public class Dictionary {
    @Excel(name = "ID",orderNum = "0", width = 20)
    private String dictionaryId;
    @Excel(name = "字典项代码",orderNum = "1", width = 20)
    private String dictionaryCode;
    @Excel(name = "字典项排序",orderNum = "2", width = 20)
    private Integer dictionarySort;
    @Excel(name = "字典项主名称",orderNum = "3", width = 20)
    private String dictionaryName;
    @Excel(name = "字典类型",orderNum = "4", width = 20)
    private String dictionaryType;
    @Excel(name = "字典父级别",orderNum = "5", width = 20)
    private String dictionaryFather;

    //==========额外字段==========

    private String submitState;

    private Dictionary dictionaryFatherObj;

    private List<DictionaryType> dictionaryTypeDic;

    //private List<Dictionary> dictionaryDic;

    private List<Dictionary> childrenList;

    private int excelRow;

    public int getExcelRow() {
        return excelRow;
    }

    public void setExcelRow(int excelRow) {
        this.excelRow = excelRow;
    }

    public void domainToExcel(){
        for (DictionaryType dictionaryType1 : dictionaryTypeDic) {
            if (dictionaryType1.getTypeId()==null||"".equals(dictionaryType1.getTypeId())||dictionaryType==null||"".equals(dictionaryType)) {
                continue;
            }
            if (dictionaryType1.getTypeId().equals(dictionaryType)) {
                this.dictionaryType = dictionaryType1.getTypeName();
            }
        }
//        for (Dictionary dictionary1 : dictionaryDic) {
//            if (dictionary1.getDictionaryId()==null||"".equals(dictionary1.getDictionaryId())||dictionaryFather==null||"".equals(dictionaryFather)) {
//                continue;
//            }
//            if (dictionary1.getDictionaryId().equals(dictionaryFather)) {
//                this.dictionaryFather = dictionary1.getDictionaryName();
//            }
//        }
    }

    public void excelToDomain() throws ExcelImportException {
        if(dictionaryType!=null&&!"".equals(dictionaryType)) {
            boolean mapState = false;
            for (DictionaryType dictionaryType1 : dictionaryTypeDic) {
                if (dictionaryType1.getTypeName() == null ||"".equals(dictionaryType1.getTypeName())) {
                    continue;
                }
                if (dictionaryType1.getTypeName().equals(dictionaryType)) {
                    this.dictionaryType = dictionaryType1.getTypeId();
                    mapState = true;
                    break;
                }
            }
            if (!mapState) {
                throw new ExcelMapTransferException("字典", "字典类别",dictionaryType,excelRow);
            }
        }else {
            throw new ExcelNameNullException("字典", "字典类别",excelRow);
        }
//        if(dictionaryFather!=null&&!"".equals(dictionaryFather)) {
//            boolean mapState = false;
//            for (Dictionary dictionary1 : dictionaryDic) {
//                if (dictionary1.getDictionaryName() == null ||"".equals(dictionary1.getDictionaryName())) {
//                    continue;
//                }
//                if (dictionary1.getDictionaryType().equals(dictionaryType) &&dictionary1.getDictionaryName().equals(dictionaryFather)) {
//                    this.dictionaryFather = dictionary1.getDictionaryId();
//                    mapState = true;
//                    break;
//                }
//            }
//            if (!mapState) {
//                throw new ExcelMapTransferException("字典", "字典类别",dictionaryType,excelRow);
//            }
//        }
    }



    public List<DictionaryType> getDictionaryTypeDic() {
        return dictionaryTypeDic;
    }

    public void setDictionaryTypeDic(List<DictionaryType> dictionaryTypeDic) {
        this.dictionaryTypeDic = dictionaryTypeDic;
    }

//    public List<Dictionary> getDictionaryDic() {
//        return dictionaryDic;
//    }
//
//    public void setDictionaryDic(List<Dictionary> dictionaryDic) {
//        this.dictionaryDic = dictionaryDic;
//    }

    public Dictionary getDictionaryFatherObj() {
        return dictionaryFatherObj;
    }

    public void setDictionaryFatherObj(Dictionary dictionaryFatherObj) {
        this.dictionaryFatherObj = dictionaryFatherObj;
    }

    public String getSubmitState() {
        return submitState;
    }

    public void setSubmitState(String submitState) {
        this.submitState = submitState;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId == null ? null : dictionaryId.trim();
    }

    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode == null ? null : dictionaryCode.trim();
    }

    public Integer getDictionarySort() {
        return dictionarySort;
    }

    public void setDictionarySort(Integer dictionarySort) {
        this.dictionarySort = dictionarySort;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName == null ? null : dictionaryName.trim();
    }

    public String getDictionaryType() {
        return dictionaryType;
    }

    public void setDictionaryType(String dictionaryType) {
        this.dictionaryType = dictionaryType == null ? null : dictionaryType.trim();
    }

    public String getDictionaryFather() {
        return dictionaryFather;
    }

    public void setDictionaryFather(String dictionaryFather) {
        this.dictionaryFather = dictionaryFather == null ? null : dictionaryFather.trim();
    }

    public List<Dictionary> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<Dictionary> childrenList) {
        this.childrenList = childrenList;
    }
}