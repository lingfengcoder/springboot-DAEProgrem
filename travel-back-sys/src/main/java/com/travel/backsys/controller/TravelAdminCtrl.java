package com.travel.backsys.controller;

import com.travel.common.superHttpCenter.controller.HttpInterfaceImpl;
import com.travel.backsys.controller.base.BaseController;
import com.travel.backsys.entity.TravelAdmin;
import com.travel.backsys.service.TravelAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: wz
 * @Date: 2018/12/20 11:48
 * @Description:
 */
@RestController
public class TravelAdminCtrl extends HttpInterfaceImpl implements BaseController{


    @Autowired
    TravelAdminServiceImpl travelAdminService;

    @GetMapping("/testAdmin")
    public Object TestAdmin(HttpServletRequest req){
       String adminId=req.getParameter("adminId");
       TravelAdmin travelAdmin= travelAdminService.queryTravelAdmin(adminId);
        return super.SUCCESS(travelAdmin);
    }

}
