package com.contract.util;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.params.ExcelForEachParams;
import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;
import org.apache.poi.ss.usermodel.*;

public class MyExcelExportStylerImpl extends AbstractExcelExportStyler implements IExcelExportStyler {

    private CellStyle headerStyle;
    private CellStyle titleStyle;
    private CellStyle cellStyle;


    public MyExcelExportStylerImpl(Workbook workbook) {
        // 标题靠上对齐








        super.createStyles(workbook);
    }

    @Override
    public CellStyle getHeaderStyle(short headerColor) {

        //填充方式及颜色
        //cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        if(headerStyle==null){
            Font font = this.workbook.createFont();
            font.setFontName("微软雅黑");
            font.setBold(true);
            font.setFontHeightInPoints((short)16);
            headerStyle = workbook.createCellStyle();
            headerStyle.setFont(font);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            // 靠上对齐
            headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        return headerStyle;
    }

    @Override
    public CellStyle getTitleStyle(short color) {
        if(titleStyle == null){
            Font font2 = this.workbook.createFont();
            font2.setBold(true);
            font2.setColor(IndexedColors.WHITE.getIndex());
            titleStyle = workbook.createCellStyle();
            titleStyle.setFont(font2);
            titleStyle.setWrapText(true);
            // 标题靠上对齐
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            // 靠上对齐
            titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
            //下边框
            titleStyle.setBorderBottom(BorderStyle.THIN);
            //左边框
            titleStyle.setBorderLeft(BorderStyle.THIN);
            //上边框
            titleStyle.setBorderTop(BorderStyle.THIN);
            //右边框
            titleStyle.setBorderRight(BorderStyle.THIN);
        }
        return titleStyle;
    }

    @Override
    public CellStyle getStyles(boolean parity, ExcelExportEntity entity) {
        return getStyles(null, 0, null, null, null);
    }

    @Override
    public CellStyle getStyles(Cell cell, int dataRow, ExcelExportEntity entity, Object obj, Object data) {
        if(cellStyle == null){
            cellStyle = workbook.createCellStyle();
            // 左对齐
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            // 靠上对齐
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setWrapText(true);
        }

        return cellStyle;
    }

    @Override
    public CellStyle getTemplateStyles(boolean isSingle, ExcelForEachParams excelForEachParams) {
        return null;
    }
}

