package com.travel.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomUtil {



    public static String getUUID() {

        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "" + (int) (Math.random() * 100000);
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
