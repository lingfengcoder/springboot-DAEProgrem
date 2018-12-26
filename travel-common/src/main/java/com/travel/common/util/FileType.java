package com.travel.common.util;


public class FileType {

    public final static String IMG = "IMG";
    public final static String AUDIO = "AUDIO";
    public final static String VIDEO = "VIDEO";


    public final static String NO_ALLOW="NOT_ALLOW";//所有文件都不允许
    public final static String[] NO_ALLOWS={"NOT_ALLOW"};//所有文件都不允许

    public final static String ALL_FILE_ALLOW="*";//所有文件都允许

    public final static String[] ALLOW_UPLOAD_IMG = {"jpg", "png", "jpgp"};
    public final static String[] ALLOW_UPLOAD_AUDIO = {"mp3", "wma"};
    public final static String[] ALLOW_UPLOAD_VIDEO = {"mp4", "rmvb", "avi"};


    public static String[] queryAllowFileType(String fileType) {
        switch (fileType) {
            case IMG:
                return ALLOW_UPLOAD_IMG;
            case AUDIO:
                return ALLOW_UPLOAD_AUDIO;
            case VIDEO:
                return ALLOW_UPLOAD_VIDEO;
            default:
                return ALLOW_UPLOAD_IMG;
        }
    }

    /**
     * 文件类型检查器 rules传null或者*允许向所有文件上传
     * @param rules
     * @param target
     * @return
     */
    public static boolean fileTypeRulesCheck(String[] rules, String target) {
        if(rules==null){
            return true;
        }else if(rules.equals(ALL_FILE_ALLOW)){
            return true;
        }
        if (target == null) return false;
        for (String s : rules) {
            if (target.equals(s)) return true;
        }
        return false;
    }
}
