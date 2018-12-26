package com.travel.common.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


public class FileUploadUtil {

    static final String[] ALLOW_FILE_TYPE={};
    static final String FILE_UPLOAD="file-upload-travel";
    /**
     * 文件上传
     * @param request
     * @param targetPath 目标文件名
     * @return s:filePath
     */
    public static String[] fileUpload(HttpServletRequest request, String targetPath) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(FILE_UPLOAD);
        int count=0;
        String[] fileNameArr=new String[files.size()];
        for (int i = 0; i < files.size(); ++i) {
            MultipartFile file = files.get(i);
            String srcFilename=file.getOriginalFilename();
            int suffixIndex= srcFilename.lastIndexOf(".");
            String suffixName=suffixIndex>0?srcFilename.substring(suffixIndex,srcFilename.length()):"";
            String name = RandomUtil.getUUID()+suffixName;//file.getOriginalFilename();
            String finalFileName = targetPath+"/"+name;
            File filePath=new File(targetPath);
            if(!filePath.exists()) {
                filePath.mkdirs();
            }
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(new File(finalFileName)));
                    stream.write(bytes);
                    stream.close();
                    fileNameArr[i]=name;
                } catch (Exception e) {
                    return null;
                }finally {

                }
            } else {
                count++;
                if(count==files.size()) {//没有文件
                    return null;
                }
            }
        }
        return fileNameArr;
    }




    public static void main(String[] args) {
        String srcFilename="adbmp3";
        int suffixIndex= srcFilename.lastIndexOf(".");
        String suffixName=suffixIndex>0?srcFilename.substring(suffixIndex,srcFilename.length()):"";
        System.out.println(suffixName);
    }
}
