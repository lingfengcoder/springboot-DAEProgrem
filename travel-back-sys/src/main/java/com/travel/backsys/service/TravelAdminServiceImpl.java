package com.travel.backsys.service;

import com.travel.backsys.entity.TravelAdmin;
import com.travel.backsys.mapper.TravelAdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: wz
 * @Date: 2018/12/20 11:52
 * @Description:
 */
@Service
public class TravelAdminServiceImpl implements TravelAdminServiceInterface{


    @Resource
    TravelAdminMapper TravelAdminMapper;


    public TravelAdmin queryTravelAdmin(String adminId){
        return TravelAdminMapper.queryTravelAdmin(adminId);
    }

}
