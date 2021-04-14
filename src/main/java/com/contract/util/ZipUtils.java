package com.contract.util;

import org.apache.logging.log4j.util.Strings;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    private static final int  BUFFER_SIZE = 2 * 1024;

    public static void downloadZip(List<ZipFile> srcFiles, HttpServletResponse response) throws IOException {

        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(response.getOutputStream());
            for (ZipFile zipFile : srcFiles) {
                byte[] buf = new byte[BUFFER_SIZE];
                zos.putNextEntry(new ZipEntry(zipFile.getNewName()));
                int len;
                FileInputStream in = new FileInputStream(zipFile.getFile());
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            //System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
