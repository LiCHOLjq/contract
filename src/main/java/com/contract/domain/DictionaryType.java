package com.contract.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class DictionaryType {
    @Excel(name = "ID",orderNum = "0", width = 20)
    private String typeId;
    @Excel(name = "字典类别名称",orderNum = "1", width = 20)
    private String typeName;

    private Boolean typeEdit;

    private String submitState;

    public String getSubmitState() {
        return submitState;
    }

    public void setSubmitState(String submitState) {
        this.submitState = submitState;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public Boolean getTypeEdit() {
        return typeEdit;
    }

    public void setTypeEdit(Boolean typeEdit) {
        this.typeEdit = typeEdit;
    }
}