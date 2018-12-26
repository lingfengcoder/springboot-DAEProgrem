package com.travel.backsys.mapper;

import com.travel.backsys.entity.TravelAdmin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: wz
 * @Date: 2018/12/20 11:55
 * @Description:
 */
@Mapper
public interface TravelAdminMapper {

   //@Select("select * from tb_admin where admin_id = #{travelAdminId}")
   TravelAdmin queryTravelAdmin(String travelAdminId);
}
