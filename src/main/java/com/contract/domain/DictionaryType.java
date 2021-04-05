package com.contract.domain;

public class DictionaryType {
    private String typeId;

    private String typeName;

    private Boolean typeEdit;

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