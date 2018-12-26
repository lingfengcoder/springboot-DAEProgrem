package com.travel.app.controller.common;


import com.travel.app.controller.base.BaseController;
import com.travel.common.annotation.DAEAspect.DAEAnnotation;
import com.travel.common.superHttpCenter.controller.HttpInterfaceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.travel.common.util.FileType.IMG;


@Controller
public class FileUploadCtrl extends HttpInterfaceImpl implements BaseController {

    @Autowired
    com.travel.common.service.common.FileUploadService FileUploadService;

    @ResponseBody
    @PostMapping("/uploadFileTests")
    @DAEAnnotation(allowFileType = IMG)// allowFileType = IMG
    //@DAEAnnotation(allowFileTypes ={"jpg","mp3"})
    public Object uploadFileTest(HttpServletRequest req,Map<String,Object> params){

        System.out.println(params);
        System.out.println(params.get("love"));
        String imgname[]= FileUploadService.uploadImg(req);
        System.out.println(imgname);

        Map map=new HashMap<>();
        map.put("name","wz");
        map.put("age",24);
        map.put("img",imgname);
        String msg="upload success";
        return super.SUCCESS(map,msg,200);
    }

}
