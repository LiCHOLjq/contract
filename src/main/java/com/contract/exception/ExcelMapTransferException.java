package com.contract.exception;

public class ExcelMapTransferException extends ExcelImportException {
    public ExcelMapTransferException(String sheet, String field, String value,int row){
        super("在【"+sheet+"】Sheet中发现存在【"+field+"】列第【"+row+"】行设置的值【"+value+"】无法找到映射，导入Excel失败");
    }
}
