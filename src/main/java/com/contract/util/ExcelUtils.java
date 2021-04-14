package com.contract.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

//Excel导入导出工具类
public class ExcelUtils {

    /**

     *   用poi导出Excel文件的静态方法

     * @param list                    数据：只能是List<Map<String, Object>>类型

     * @param sheetname        Excel的sheet名字

     * @param filepath             保存文件的地址

     * @throws IOException

     */

    public static void exportExcel(List<Map<String, Object>> list,Set<String> titles, String sheetname, String fileName, HttpServletResponse response){
        //新建工作簿
        XSSFWorkbook workbook=new XSSFWorkbook();
        //创建Excel的sheet
        XSSFSheet sheet=workbook.createSheet(sheetname);
        //从list任意一个Map对象里获取标题（字段名或属性名）放到sheet的第一行上，若第一条记录某字段值没有，则会没有该字段
        //Map<String, Object> map=list.get(0);
        int num=0;
        XSSFRow first=sheet.createRow(0);//创建sheet的第一行
        for(String title : titles){
            first.createCell(num).setCellValue(title);//创建num+1行并在第num+1列上赋值（字段名）
            num++;
        }
//        for(String key:map.keySet()) {
//            first.createCell(num).setCellValue(key);//创建num+1行并在第num+1列上赋值（字段名）
//            num++;
//        }
        //从list取第一行到最后一行的内容并放到对应的Excel里，若记录里某字段值没有会有问题
        int rownum=1;//行数
        for(Map<String, Object> data:list) {
            XSSFRow row=sheet.createRow(rownum);//创建sheet的第rownum+1行
            int n=0;//列数
            for(String key:data.keySet()) {
                row.createCell(n).setCellValue(data.get(key)==null?"":data.get(key).toString());//创建n+1行并在第n+1列上赋值
                n++;
            }
            rownum++;
        }
        //1.通过IO流把数据写到文件上：只能写到服务器端

              /*FileOutputStream out=new FileOutputStream(fileName);

              workbook.write(out);

              out.close();*/
        //2.响应到客户端：可以下载到客户端（两个选一个）
        downLoadExcel(fileName, response, workbook);
    }
//    public static void exportExcel(List<QueryGroup> queryGroupList, List<Map<String, Object>> list, String sheetname, String fileName, HttpServletResponse response, boolean encryption) throws Exception {
//        //新建工作簿
//        XSSFWorkbook workbook=new XSSFWorkbook();
//        //创建Excel的sheet
//        XSSFSheet sheet=workbook.createSheet(sheetname);
//        //从list任意一个Map对象里获取标题（字段名或属性名）放到sheet的第一行上，若第一条记录某字段值没有，则会没有该字段
//        //Map<String, Object> map=list.get(0);
//        int num=0;
//        XSSFRow first=sheet.createRow(0);//创建sheet的第一行
//        for(QueryGroup queryGroup : queryGroupList) {
//            first.createCell(num).setCellValue(queryGroup.getStudentAttrObj().getAttributeName());//创建num+1行并在第num+1列上赋值（字段名）
//            num++;
//        }
//        //从list取第一行到最后一行的内容并放到对应的Excel里，若记录里某字段值没有会有问题
//        int rownum=1;//行数
//        for(Map<String, Object> data:list) {
//            XSSFRow row=sheet.createRow(rownum);//创建sheet的第rownum+1行
//            int n=0;//列数
//            for(QueryGroup queryGroup : queryGroupList) {
//                row.createCell(n).setCellValue(data.get(queryGroup.getStudentAttrObj().getAttributeName())==null?"":data.get(queryGroup.getStudentAttrObj().getAttributeName()).toString());//创建n+1行并在第n+1列上赋值
//                n++;
//            }
//            rownum++;
//        }
//        //1.通过IO流把数据写到文件上：只能写到服务器端
//
//              /*FileOutputStream out=new FileOutputStream(fileName);
//
//              workbook.write(out);
//
//              out.close();*/
//        //2.响应到客户端：可以下载到客户端（两个选一个）
//
////        if(encryption){
////            //加密后下载
////            FileOutputStream out = new FileOutputStream(new File(FileSaveConfig.path+FileSaveConfig.temp+"\\temp.xlsx"));
////            workbook.write(out);
////            DESFileUtil.encryptFile(FileSaveConfig.path+FileSaveConfig.temp+"\\temp.xlsx",FileSaveConfig.path+FileSaveConfig.temp+"\\tempEcip.xlsx");
////            FileUtil.downloadFile(response,FileSaveConfig.path+FileSaveConfig.temp+"\\","tempEcip.xlsx");
////        }else{
//            downLoadExcel(fileName, response, workbook);
// //       }
//
//    }
//







    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     *
     * @param list 数据列表
     * @param title 标题
     * @param sheetName sheet名字
     * @param pojoClass 导出对象的Class类型
     * @param fileName 文件名
     * @param response
     */



    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName,
                                   HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    public static void multSheetExport(List<Sheet> sheetsList, String fileName, HttpServletResponse response) {
        // 将sheet1、sheet2、sheet3使用得map进行包装
        List<Map<String, Object>> excelSheetsList = new ArrayList<>();
        for (Sheet sheet : sheetsList) {
            // 创建参数对象（用来设定excel得sheet得内容等信息）
            ExportParams deptExportParams = new ExportParams();
            deptExportParams.setStyle(MyExcelExportStylerImpl.class);
            deptExportParams.setTitle(sheet.getTitle());
            // 设置sheet得名称
            deptExportParams.setSheetName(sheet.getSheetName());
            // 创建sheet1使用得map
            Map<String, Object> deptExportMap = new HashMap<>();
            // title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
            deptExportMap.put("title", deptExportParams);
            // 模版导出对应得实体类型
            deptExportMap.put("entity", sheet.getPojoClass());
            // sheet中要填充得数据
            deptExportMap.put("data", sheet.getList());
            excelSheetsList.add(deptExportMap);
        }
        // 执行方法
        Workbook workbook = ExcelExportUtil.exportExcel(excelSheetsList, ExcelType.XSSF);

//        Workbook workbook = new XSSFWorkbook();
//        Iterator var3 = excelSheetsList.iterator();
//        while(var3.hasNext()) {
//            Map<String, Object> map = (Map)var3.next();
//            ExcelExportService service = new ExcelExportService();
//            service.createSheet(workbook, (ExportParams)map.get("title"), (Class)map.get("entity"), (Collection)map.get("data"));
//        }
        if (workbook != null)
            ;
        downLoadExcel(fileName, response, workbook);
    }
    public static void multSheetExport(List<Sheet> sheetsList, File file) throws IOException {
        // 将sheet1、sheet2、sheet3使用得map进行包装
        List<Map<String, Object>> excelSheetsList = new ArrayList<>();
        for (Sheet sheet : sheetsList) {
            // 创建参数对象（用来设定excel得sheet得内容等信息）
            ExportParams deptExportParams = new ExportParams();
            deptExportParams.setStyle(MyExcelExportStylerImpl.class);
            deptExportParams.setTitle(sheet.getTitle());
            // 设置sheet得名称
            deptExportParams.setSheetName(sheet.getSheetName());
            // 创建sheet1使用得map
            Map<String, Object> deptExportMap = new HashMap<>();
            // title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
            deptExportMap.put("title", deptExportParams);
            // 模版导出对应得实体类型
            deptExportMap.put("entity", sheet.getPojoClass());
            // sheet中要填充得数据
            deptExportMap.put("data", sheet.getList());
            excelSheetsList.add(deptExportMap);
        }
        // 执行方法
        Workbook workbook = ExcelExportUtil.exportExcel(excelSheetsList, ExcelType.HSSF);

//        Workbook workbook = new XSSFWorkbook();
//        Iterator var3 = excelSheetsList.iterator();
//        while(var3.hasNext()) {
//            Map<String, Object> map = (Map)var3.next();
//            ExcelExportService service = new ExcelExportService();
//            service.createSheet(workbook, (ExportParams)map.get("title"), (Class)map.get("entity"), (Collection)map.get("data"));
//        }
        FileOutputStream out = null;
        try {
            if (workbook != null){
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                out = new FileOutputStream(file);
                workbook.write(out);;
            }
        }catch (IOException e){
            throw e;
        }finally {
            if(out !=null){
                out.close();
            }
        }

    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response,
                                      ExportParams exportParams) {

        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null)
            ;
        downLoadExcel(fileName, response, workbook);
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            // throw new NormalException(e.getMessage());
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null)
            ;
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            // throw new NormalException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            // throw new NormalException(e.getMessage());
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows,
                                          Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            // throw new NormalException("excel文件不能为空");
        } catch (Exception e) {
            // throw new NormalException(e.getMessage());
            System.out.println(e.getMessage());
        }
        return list;
    }
    public static <T> List<T> multSheetImport(MultipartFile file, Integer titleRows, Integer headerRows, Integer sheetNum,
                                              Class<T> pojoClass) throws ExcelImportException {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setStartSheetIndex(sheetNum);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }  catch (Exception e) {
            throw new ExcelImportException(e.getMessage());
            //System.out.println(e.getMessage());
        }
        return list;
    }
    public static <T> List<T> multSheetImport(File file, Integer titleRows, Integer headerRows, Integer sheetNum,
                                              Class<T> pojoClass) throws ExcelImportException {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        params.setStartSheetIndex(sheetNum);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new FileInputStream(file), pojoClass, params);
        }  catch (Exception e) {
            throw new ExcelImportException(e.getMessage());
            //System.out.println(e.getMessage());
        }
        return list;
    }
}

