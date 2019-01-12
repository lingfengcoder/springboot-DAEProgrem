package com.travel.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * @Auther: wz
 * @Date: 2019/1/12 0012 15:18
 * @Description:
 */
public class ObjectUtil {

    /**
     * @Description
     * @auther: wz
     * @date: 2019/1/13 1:09
     */
    public static <T> T copyClass(Object srcObj, Class<T> targetObjClass) {
        //生成新的对象
        try {
            Class  targetObj = Class.forName(targetObjClass.getName());
            Object result=typeHandler(srcObj,targetObj.newInstance());
            if(result==null)return null;
            return (T) result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //类型选择器
    private static Object typeHandler(Object srcObj,Object targetObj){
        if(srcObj instanceof Map){
            return copyMap((Map)srcObj,targetObj);
        }else{
            return copyObject(srcObj,targetObj);
        }
    }
    /**
     * @Description 对象转换器 将目标对象与原对象属性名相同的属性赋值
     * @auther: wz
     * @date: 2019/1/12 0012 16:02
     */
    public static Object copyObject(Object srcObj, Object targetObj) {

        try {
            Class<?> targetObjClass = targetObj.getClass();
            Field[] targetObjClassFields = targetObjClass.getDeclaredFields();

            Class<?> srcObjClass = srcObj.getClass();
            Field[] srcObjClassFields = srcObjClass.getDeclaredFields();

            for (Field targetField : targetObjClassFields) {
                if (Modifier.isFinal(targetField.getModifiers()))//如果是final 类型则不修改 如要修改添加 modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);//fianl标志位置0
                    continue;
                //获取private权限
                targetField.setAccessible(true);
                //获取属性名
                String targetFieldName = targetField.getName();
                //获取属性值
                for (Field srcField : srcObjClassFields) {
                    //获取private权限
                    srcField.setAccessible(true);
                    String srcFieldName = srcField.getName();
                    if (srcFieldName.equals(targetFieldName)) {//属性名字相同
                        Object srcValue = srcField.get(srcObj);
                        if (srcValue != null) {//属性值不为空 设置属性值
                            if (targetField.getType() == srcField.getType()) {
                                //方法1：直接赋值
                                targetField.set(targetObj, srcValue);

                                //方法2：调用setter
                                //PropertyDescriptor pd = new PropertyDescriptor(targetFieldName, targetObjClass);
                                // Method wM = pd.getWriteMethod();//获得写方法
                                //if (wM != null)
                                //  wM.invoke(targetObj, srcValue);
                            } else {
                                StringBuffer stringBuffer = new StringBuffer("属性类型不一致 ");
                                stringBuffer.append(targetObjClass.getCanonicalName()).append(" type: ").append(targetFieldName)
                                        .append(" ").append(targetField.getType()).append(" ").append(srcObjClass.getCanonicalName())
                                        .append(" type: ").append(srcFieldName).append(" ").append(srcValue.getClass());
                                throw new IllegalArgumentException(stringBuffer.toString());
                                //continue;
                            }
                        } else {//属性值为空不操作}
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            targetObj = null;
        }
        return targetObj;
    }

    /**
     * @Description Map 与对象转换
     * @param:
     * @return:
     * @auther: wz
     * @date: 2019/1/12 23:06
     */
    public static Object copyMap(Map<String, Object> srcObj, Object targetObj) {
        try {
            Class<?> targetObjClass = targetObj.getClass();
            Field[] targetObjClassFields = targetObjClass.getDeclaredFields();

            for (Field targetField : targetObjClassFields) {
                if (Modifier.isFinal(targetField.getModifiers()))//如果是final 类型则不修改 如要修改添加 modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);//fianl标志位置0
                    continue;
                //获取private权限
                targetField.setAccessible(true);
                //获取属性名
                String targetFieldName = targetField.getName();
                //获取属性值
                Object paramVal = srcObj.get(targetFieldName);
                if (paramVal != null) {
                    targetField.set(targetObj, paramVal);
                    if (paramVal.getClass() == targetField.getType()) {
                        targetField.set(targetObj, paramVal);
                    } else {
                        StringBuffer stringBuffer = new StringBuffer("属性类型不一致 ");
                        stringBuffer.append(targetObjClass.getCanonicalName()).append(" type: ").append(targetFieldName)
                                .append(" ").append(targetField.getType()).append(" ").append(targetField.getType())
                                .append(" type: ").append(targetFieldName).append(" ").append(paramVal.getClass());
                        throw new IllegalArgumentException(stringBuffer.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            targetObj = null;
        }
        return targetObj;
    }





}
