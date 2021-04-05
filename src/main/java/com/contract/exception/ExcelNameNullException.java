package com.contract.exception;

public class ExcelNameNullException extends ExcelImportException {

    public ExcelNameNullException(String sheet, String field,int row){
        super("在【"+sheet+"】Sheet中发现存在【"+field+"】列第【"+row+"】行出现空值，导入Excel失败");
    }
}
