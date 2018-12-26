package com.travel.common.util;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;

@Component
public class FileOption {

    /**
     * 获取服务器路径
     * @return
     */
    public  String getServerPath() {
        String path = "";
        String servicePath = this.getClass().getResource("/").toString().split("WEB-INF")[0];
        servicePath = servicePath.substring(6, servicePath.length() - 1);
        path = servicePath;
        return path;
    }

    /**
     *  普通文本 读
     * @param fileName
     * @return
     */
    public static String read(String fileName) {
        File file = new File(fileName);
        if (!file.exists())
            return null;
        StringBuffer strBuffer = new StringBuffer();
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            fis = new FileInputStream(fileName);
            isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            String line = null;

            while ((line = br.readLine()) != null) {
                strBuffer.append(line);
                strBuffer.append("\r\n");// 补上换行符
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br!=null)br.close();
                if(isr!=null)isr.close();
                if(fis!=null)fis.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strBuffer.toString();
    }

    /**
     *  普通文本 写
     * @param str
     * @param fileName
     * @param append
     * @return
     */
    public static boolean write(String str, String fileName, boolean append) {
        File file = new File(fileName);
        File dir = new File((fileName).replace(file.getName(), ""));
        if (!dir.exists())
            dir.mkdirs();
        FileOutputStream fos = null;
        BufferedOutputStream bufWrite = null;
        try {
            if (!file.exists())
                file.createNewFile();
            fos = new FileOutputStream(fileName, append);
            bufWrite = new BufferedOutputStream(fos);
            bufWrite.write(str.getBytes());
            bufWrite.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bufWrite != null)
                    bufWrite.close();
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     *  对象 读
     * @param path
     * @return
     */
    public static Object readObj(String path) {
        File file = new File(path);
        if (!file.exists())
            return null;
        ObjectInputStream ois = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {

                if (ois != null)
                    ois.close();
                if (fis != null)
                    fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *  对象 写
     * @param obj
     * @param path
     * @return
     */

    public static boolean  writeObj(Object obj, String path) {
        File file = new File(path);
        File dir = new File((path).replace(file.getName(), ""));
        if (!dir.exists())
            dir.mkdirs();
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            if (!file.exists())
                file.createNewFile();
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (oos != null){
                    oos.close();
                }
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 判断文件是否存在
     * @param fileName
     * @return
     */
    public static boolean existsFile(String fileName) {
        File file=new File(fileName);
        return file.exists()?true:false;
    }

    /**
     * 获取文件最新修改时间
     * @param fileName
     * @return
     */
    public static Long getFileModiedTime(String fileName) {
        File f=new File("fileName");
        return  f.lastModified();
    }

    /**
     * 目录打散
     * @param storeDirectory
     * @param filename
     * @return
     */
    private String makeChildDirectory(File storeDirectory, String filename) {
        int hashcode = filename.hashCode();// 返回字符转换的32位hashcode码
        String code = Integer.toHexString(hashcode); // 把hashcode转换为16进制的字符
        String childDirectory = code.charAt(0) + File.separator
                + code.charAt(1);
        // 创建指定目录
        File file = new File(storeDirectory, childDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
        return childDirectory;
    }

    /**
     * 返回文件后缀名 没有点、小写
     * @param filename
     * @return
     */
    public static String getFileSuffixName(String filename){
        int suffixIndex= filename.lastIndexOf(".");
       return suffixIndex>0?filename.substring(suffixIndex+1).toLowerCase():null;
    }

	/*public static void main(String[] args) {
		try {
			System.out.println(new FileOption().write("123\r\nqwer", "g:/2.txt", true));
			System.out.println(new FileOption().read("g:/2.txt") );
			Object obj=new Object();
			obj="这是读写对象测试<<";
			new FileOption().writeObj(obj, "g:/obj.txt");
			System.out.println(new FileOption().readObj("g:/obj.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
