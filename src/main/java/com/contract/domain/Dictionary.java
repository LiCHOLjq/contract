package com.contract.domain;

public class Dictionary {
    private String dictionaryId;

    private String dictionaryCode;

    private Integer dictionarySort;

    private String dictionaryName;

    private String dictionaryType;

    private String dictionaryFather;

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
}