package com.contract.util;

import java.util.List;

public class Sheet {
    private String sheetName;
    private String title;
    private List<?> list;
    private Class<?> pojoClass;
    private Integer titleRows;
    private Integer headerRows;
    private Integer sheetNum;

    public Sheet(String sheetName, String title, List<?> list, Class<?> pojoClass) {
        this.sheetName = sheetName;
        this.title = title;
        this.list = list;
        this.pojoClass = pojoClass;
    }

    public Sheet(Integer titleRows, Integer headerRows, Integer sheetNum, Class<?> pojoClass) {
        this.pojoClass = pojoClass;
        this.titleRows = titleRows;
        this.headerRows = headerRows;
        this.sheetNum = sheetNum;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    public Class<?> getPojoClass() {
        return pojoClass;
    }

    public void setPojoClass(Class<?> pojoClass) {
        this.pojoClass = pojoClass;
    }

    public Integer getTitleRows() {
        return titleRows;
    }

    public void setTitleRows(Integer titleRows) {
        this.titleRows = titleRows;
    }

    public Integer getHeaderRows() {
        return headerRows;
    }

    public void setHeaderRows(Integer headerRows) {
        this.headerRows = headerRows;
    }

    public Integer getSheetNum() {
        return sheetNum;
    }

    public void setSheetNum(Integer sheetNum) {
        this.sheetNum = sheetNum;
    }
}
