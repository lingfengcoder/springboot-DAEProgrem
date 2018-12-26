package com.travel.common.service.common;



import com.travel.common.util.FileUploadUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class FileUploadService implements CommonServiceInterface{



    public String[] uploadImg(HttpServletRequest request){

        String targetPath="classpath:/upload_file/";

        String strArr[]= FileUploadUtil.fileUpload(request,targetPath);
        System.out.println(strArr);
        return strArr;
    }

    public String[] uploadFile(){

        return null;
    }




}
