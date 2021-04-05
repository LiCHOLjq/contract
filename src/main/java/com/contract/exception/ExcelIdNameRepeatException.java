package com.contract.exception;

public class ExcelIdNameRepeatException extends ExcelImportException {
    public ExcelIdNameRepeatException(String sheet, String field,int row) {
        super("在【"+sheet+"】Sheet中第【"+row+"】行发现存在【"+field+"】列值与数据库已有数据重复，导入Excel失败");
    }
    public ExcelIdNameRepeatException(String sheet, String field) {
        super("在【"+sheet+"】Sheet中发现存在【"+field+"】列值重复，导入Excel失败");
    }
}
