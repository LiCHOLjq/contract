package com.contract.util;

import cn.afterturn.easypoi.word.WordExportUtil;
import com.contract.config.FileSaveConfig;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class WordUtils {

    public static void exportWord(Map<String, Object> row, String fileName, HttpServletResponse response) throws Exception {
        XWPFDocument doc = WordExportUtil.exportWord07(FileSaveConfig.Template + fileName, row);
        downLoadWord(fileName,response,doc);

    }

    public static void exportWord(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws Exception {
        List<XWPFDocument> docList = new ArrayList<>();
        for (Map row : list){
            XWPFDocument doc = WordExportUtil.exportWord07(FileSaveConfig.Template + fileName, row);
            docList.add(doc);
        }
        XWPFDocument out = mergeWord(docList);
        downLoadWord(fileName,response,out);

    }
//    public static void exportWord(Group group, List<Map<String, Object>> list, String fileName, File file, boolean encryption) throws Exception {
//        List<XWPFDocument> docList = new ArrayList<>();
//        for (Map row : list){
//            XWPFDocument doc = WordExportUtil.exportWord07(FileSaveConfig.path + FileSaveConfig.template + "\\" + group.getGroupId() + ".docx", row);
//            docList.add(doc);
//        }
//        XWPFDocument out = mergeWord(docList);
//
//        FileOutputStream fileOutputStream =null;
//        try {
//            fileOutputStream = new FileOutputStream(file);
//            out.write(fileOutputStream);
//        }catch (Exception e){
//            throw e;
//        }finally {
//            if(fileOutputStream!=null){
//                fileOutputStream.close();
//            }
//        }
//
//    }
//    public static void exportRoomLabel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) throws Exception {
//        List<XWPFDocument> docList = new ArrayList<>();
//        for (Map row : list){
//           //System.out.println(row.get("group").toString());
//            XWPFDocument doc = WordExportUtil.exportWord07(FileSaveConfig.path + FileSaveConfig.template + "\\" + row.get("group").toString() + ".docx", row);
//            docList.add(doc);
//        }
//        XWPFDocument out = mergeWord(docList);
////        if(encryption){
////            //加密后下载
////            FileOutputStream outF = new FileOutputStream(new File(FileSaveConfig.path+FileSaveConfig.temp+"\\temp.docx"));
////            out.write(outF);
////            DESFileUtil.encryptFile(FileSaveConfig.path+FileSaveConfig.temp+"\\temp.docx",FileSaveConfig.path+FileSaveConfig.temp+"\\tempEcip.docx");
////            FileUtil.downloadFile(response,FileSaveConfig.path+FileSaveConfig.temp+"\\","tempEcip.docx");
////        }else{
//        downLoadWord(fileName,response,out);
////        }
//    }

//    public static void exportRoomLabel(List<Map<String, Object>> list, String fileName, File file) throws Exception {
//        List<XWPFDocument> docList = new ArrayList<>();
//        for (Map row : list){
//           // System.out.println(row.get("group").toString());
//            XWPFDocument doc = WordExportUtil.exportWord07(FileSaveConfig.path + FileSaveConfig.template + "\\" + row.get("group").toString() + ".docx", row);
//            docList.add(doc);
//        }
//        XWPFDocument out = mergeWord(docList);
////        if(encryption){
////            //加密后下载
////            FileOutputStream outF = new FileOutputStream(new File(FileSaveConfig.path+FileSaveConfig.temp+"\\temp.docx"));
////            out.write(outF);
////            DESFileUtil.encryptFile(FileSaveConfig.path+FileSaveConfig.temp+"\\temp.docx",FileSaveConfig.path+FileSaveConfig.temp+"\\tempEcip.docx");
////            FileUtil.downloadFile(response,FileSaveConfig.path+FileSaveConfig.temp+"\\","tempEcip.docx");
////        }else{
//        FileOutputStream fileOutputStream =null;
//        try {
//            fileOutputStream = new FileOutputStream(file);
//            out.write(fileOutputStream);
//        }catch (Exception e){
//            throw e;
//        }finally {
//            if(fileOutputStream!=null){
//                fileOutputStream.close();
//            }
//        }
////        }
//    }
//    public static List<String> exportOnePage(List<Map<String, Object>> list, String folder) throws Exception {
//        //List<XWPFDocument> docList = new ArrayList<>();
//        UZipFile.delAllFile(FileSaveConfig.path + FileSaveConfig.temp + "\\" + folder);
//        int i = 1;
//        List<String> result = new ArrayList<>();
//        for (Map row : list){
//            // System.out.println(row.get("group").toString());
//            XWPFDocument doc = WordExportUtil.exportWord07(FileSaveConfig.path + FileSaveConfig.template + "\\" + row.get("group").toString() + ".docx", row);
//            //docList.add(doc);
//            FileOutputStream fileOutputStream =null;
//            String fileName = FileSaveConfig.path + FileSaveConfig.temp + "\\"+folder+"\\" + (i++) + ".docx";
//            File file = new File(fileName);
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
//            try {
//                fileOutputStream = new FileOutputStream(file);
//                doc.write(fileOutputStream);
//                result.add(fileName);
//            }catch (Exception e){
//                throw e;
//            }finally {
//                if(fileOutputStream!=null){
//                    fileOutputStream.close();
//                }
//            }
//        }
//        return result;
//    }
    private static void downLoadWord(String fileName, HttpServletResponse response, XWPFDocument out) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            out.write(response.getOutputStream());
        } catch (IOException e) {
            // throw new NormalException(e.getMessage());
        }
    }


    /**
     * word文件合并
     * @param wordList
     * @return
     * @throws Exception
     */
    public static XWPFDocument mergeWord(List<XWPFDocument> wordList) throws Exception {
        if (CollectionUtils.isEmpty(wordList)) {
            throw  new RuntimeException("待合并的word文档list为空");
        }
        if(wordList.size()==1){
            return wordList.get(0);
        }else if(wordList.size()>1){
            System.out.println("文档数："+wordList.size());
            List<XWPFDocument> xwpfDocumentList = new ArrayList<>();
            if(wordList.size()%2==0){
                for(int i=0;i<wordList.size();i=i+2){
                    XWPFDocument doc1 = wordList.get(i);
                    XWPFDocument doc2 = wordList.get(i+1);
                    appendBody(doc1, doc2);
                    xwpfDocumentList.add(doc1);
                }
            }else {
                for(int i=0;i<wordList.size()-1;i=i+2){
                    XWPFDocument doc1 = wordList.get(i);
                    XWPFDocument doc2 = wordList.get(i+1);
                    appendBody(doc1, doc2);
                    xwpfDocumentList.add(doc1);
                }
                xwpfDocumentList.add(wordList.get(wordList.size()-1));
            }
            return mergeWord(xwpfDocumentList);
        }else {
            return null;
        }
//        XWPFDocument doc = wordList.get(0);
//        int size = wordList.size();
//        if (size > 1) {
//            //doc.createParagraph().setPageBreak(true);
//            for (int i = 1; i < size; i++) {
//                System.out.println("正在拼接："+i);
//                // 从第二个word开始合并
//                XWPFDocument nextPageDoc = wordList.get(i);
//                // 最后一页不需要设置分页符
//                if (i != (size-1)) {
//                    //nextPageDoc.createParagraph().setPageBreak(true);
//                }
//                appendBody(doc, nextPageDoc);
//            }
//        }
//        return doc;
    }
    private static void appendBody(XWPFDocument src, XWPFDocument append) throws Exception {
        CTBody src1Body = src.getDocument().getBody();
        CTBody src2Body = append.getDocument().getBody();
        List<XWPFPictureData> allPictures = append.getAllPictures();
        // 记录图片合并前及合并后的ID
        Map<String, String> map = new HashMap<>();
        for (XWPFPictureData picture : allPictures) {
            String before = append.getRelationId(picture);
            //将原文档中的图片加入到目标文档中
            String after = src.addPictureData(picture.getData(), Document.PICTURE_TYPE_PNG);
            map.put(before, after);
        }
        appendBody(src1Body, src2Body,map);
    }


    private static void appendBody(CTBody src, CTBody append, Map<String, String> map) throws Exception {
        XmlOptions optionsOuter = new XmlOptions();
        optionsOuter.setSaveOuter();
        String appendString = append.xmlText(optionsOuter);
        String srcString = src.xmlText();
        String prefix = srcString.substring(0,srcString.indexOf(">")+1);
        String mainPart = srcString.substring(srcString.indexOf(">")+1,srcString.lastIndexOf("<"));
        String sufix = srcString.substring( srcString.lastIndexOf("<") );
        String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
        if (map != null && !map.isEmpty()) {
            //对xml字符串中图片ID进行替换
            for (Map.Entry<String, String> set : map.entrySet()) {
                addPart = addPart.replace(set.getKey(), set.getValue());
            }
        }
        //将两个文档的xml内容进行拼接
        CTBody makeBody = CTBody.Factory.parse(prefix+mainPart+addPart+sufix);
        src.set(makeBody);
    }

}


