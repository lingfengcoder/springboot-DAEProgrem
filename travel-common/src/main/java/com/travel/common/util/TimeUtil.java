package com.travel.common.util;

import java.util.Date;

public class TimeUtil {


     private final static long timeThreshold=60000;//60000;//1分钟
    /**
     *  比较时间戳
     * @param timeStamp
     * @param date
     * @return 小于 等于true 大于false
     */
    public static boolean compareTimeWithThreshold(long timeStamp, Date date){
        if(timeStamp+timeThreshold>=date.getTime()&&timeStamp-timeThreshold<=date.getTime()){
            return true;
        }
        return false;
    }


    public static void main(String[] args) {
        Date date1=new Date();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Date date2=new Date();

        System.out.println(compareTimeWithThreshold(date1.getTime(),date2));

    }

}
