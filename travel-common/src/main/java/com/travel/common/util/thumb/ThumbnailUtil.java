//package com.travel.springboot.util.thumb;
//
//import java.awt.Color;
//import java.awt.Component;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Image;
//import java.awt.MediaTracker;
//import java.awt.Toolkit;
//import java.awt.image.BufferedImage;
//import java.awt.image.ConvolveOp;
//import java.awt.image.Kernel;
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.channels.FileChannel;
//import java.util.Arrays;
//import java.util.Date;
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import com.travel.springboot.util.FileOption;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//
///**
// * 图像压缩工具
// * <p>
// * wz
// */
//
//@Service
//public class ThumbnailUtil {
//
//    @Autowired
//    com.travel.springboot.util.thumb.ImgWebpUtil ImgWebpUtil;
//
//    @Autowired
//    FileOption fileOption;
//
//    private static int imgSize = 20;// 要进行压缩的图片下限 20Kb
//    private static float bigRates = 3; // 图片的压缩率
//
//    public static int getImgSize() {
//        return imgSize;
//    }
//
//    public static void setImgSize(int imgSize) {
//        ThumbnailUtil.imgSize = imgSize;
//    }
//
//    private static String servicePath = "";
//
//    public void setSerivePath(String servicePath) {
//        ThumbnailUtil.servicePath = servicePath;
//    }
//
//    public String getServicePath() {
//        return servicePath;
//    }
//
//    // 测试
//    public static void main(String[] args) {
//        /*
//         * Date date1 = new Date(); Date date2 = new Date(); long time =
//         * (date2.getTime() - date1.getTime()); String costTime = "" + (time / 1000 > 0
//         * ? time / 1000 + "." + time % 1000 + "s" : time + "ms"); costTime = "" +
//         * ((time / 1000 / 60) > 0 ? (time / 1000 / 60) + "分" + (time % 60 / 1000) + "秒"
//         * + (time % 60 % 1000) + "ms" : costTime); System.out.println("总耗时： " +
//         * costTime);
//         */
//    }
//
//    public String thumbImg(String src, String des, boolean thumbFlag, boolean webpFlag) { // thumbFlag
//        // true:缩略图 // false:详情页缩图
//        // String servicePath =
//        // this.getClass().getResource("/").toString().split("WEB-INF")[0];
//        // servicePath= servicePath.substring(6, servicePath.length() - 1);
//        // 设置服务器路径
//        servicePath = fileOption.getServerPath();
//        setSerivePath(servicePath);
//
//        if (webpFlag) {// 使用webp处理
//            int quality = 0;
//            quality = thumbFlag == true ? 10 : 75;
//            try {
//                return dealWebp(servicePath, src, des, quality);
//            } catch (Exception e) {
//                File file = new File(des);
//                thumbImgByCode(src, des, thumbFlag, webpFlag);
//                return file.getName();
//            }
//            // return;
//        } else {// 使用常规处理
//            thumbImgByCode(src, des, thumbFlag, webpFlag);
//            File file = new File(des);
//            return file.getName();
//        }
//    }
//
//    // 处理webp
//    public String dealWebp(String servicePath, String src, String des, int quality) throws Exception {
//
//        File file1 = new File(src);
//        int k = des.lastIndexOf(".");
//        des = des.substring(0, k > 0 ? k : des.length()) + ".webp";
//        File file2 = new File(des);
//        if (file2.exists()) {
//            return null;
//        }
//        File file3 = new File(des.replace(file2.getName(), ""));
//
//        if (!file3.exists()) {
//            file3.mkdirs();
//        }
//        if (file1.isFile()) {
//            try {
//                file2.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                ImgWebpUtil.imgToWebp(servicePath, file1.getPath(), file2.getPath(), quality);
//            } catch (Exception e) {
//                //删除图片
//                if (file2.exists())
//                    file2.delete();
//                throw new Exception();
//            }
//            return file2.getName();
//        }
//        return file2.getName();
//
//    }
//
//    // 常规压缩处理图片
//    public void thumbImgByCode(String src, String des, boolean thumbFlag, boolean webpFlag) {
//        File file1 = new File(src);
//        File file2 = new File(des);
//        if (file2.exists()) {
//            return;
//        }
//        File file3 = new File(des.replace(file2.getName(), ""));
//        if (!file3.exists()) {
//            file3.mkdirs();
//        }
//        if (file1.isFile()) {
//            try {
//                file2.createNewFile();
//            } catch (IOException e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//            boolean getOne = false;
//            long beforeFileSize = getFileSize(file1);
//            String suffix = null;
//            // ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG,
//            // JPEG, WBMP, GIF, gif]
//            String types = Arrays.toString(ImageIO.getReaderFormatNames());
//            if (beforeFileSize < imgSize) {// <50k的不压缩
//                getOne = false;
//            } else {
//                suffix = file1.getName().substring(file1.getName().lastIndexOf(".") + 1);
//                if (types.toLowerCase().indexOf(suffix.toLowerCase()) > 0) {
//                    getOne = true;
//                }
//            }
//            if (getOne) {
//                try {
//                    double rate = 0;
//                    // int bigRate=3;
//                    BufferedImage bufferedImage = ImageIO.read(file1);
//                    int width = bufferedImage.getWidth();
//                    // int height = bufferedImage.getHeight();
//                    rate = (int) beforeFileSize / imgSize;
//                    if (thumbFlag) {// 缩略图
//                        if (rate <= 1) {
//                            width = 400;
//                        } else {
//                            rate = rate < bigRates ? rate : bigRates;
//                            width = (int) (width / rate);
//                        }
//                    } else {// 详情页缩图
//
//                        if (suffix.toLowerCase().contains("jpg")) {
//                            width = width < 700 ? 700 : width;
//                            // width=width>1000?1000:width;
//                        } else { // 除了jpg
//                            if (beforeFileSize > 500) {// 大于500Kb 的进行压缩
//                                width = (int) (width / 1.5);
//                            } else {// 小于500Kb的复制
//                                allTypeFileCopy(src, des); // 文件拷贝的方法
//                                return;
//                            }
//                        }
//                    }
//                    Date beginDate = new Date();
//                    resize(file1, file2, width, suffix);
//                    String path = "/App/thumb/commonImgLog.txt";
//                    writeLog(path, "常规压缩图片-" + src + " 到 " + des, beginDate);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    // file2.delete();
//                    // 如果压缩图片失败,就选择拷贝
//                    allTypeFileCopy(src, des); // 文件拷贝的方法
//                }
//            } else {
//                // 复制
//                allTypeFileCopy(src, des); // 文件拷贝的方法
//            }
//        } else {
//            // 复制
//            allTypeFileCopy(src, des); // 文件拷贝的方法
//        }
//    }
//
//    /**
//     * @param originalFile 原图像
//     * @param resizedFile  压缩后的图像
//     * @param width        图像宽
//     * @param format       图片格式 jpg, png, gif(非动画)
//     * @throws IOException
//     */
//    public static void resize(File originalFile, File resizedFile, int width, String format) throws Exception {
//        if (format != null && "gif".equals(format.toLowerCase())) {
//            resize(originalFile, resizedFile, width, 1);
//            return;
//        }
//        FileInputStream fis = new FileInputStream(originalFile);
//        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//        int readLength = -1;
//        int bufferSize = 1024;
//        byte bytes[] = new byte[bufferSize];
//        while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
//            byteStream.write(bytes, 0, readLength);
//        }
//        byte[] in = byteStream.toByteArray();
//        fis.close();
//        byteStream.close();
//
//        Image inputImage = Toolkit.getDefaultToolkit().createImage(in);
//        waitForImage(inputImage);
//        int imageWidth = inputImage.getWidth(null);
//        if (imageWidth < 1)
//            throw new IllegalArgumentException("image width " + imageWidth + " is out of range");
//        int imageHeight = inputImage.getHeight(null);
//        if (imageHeight < 1)
//            throw new IllegalArgumentException("image height " + imageHeight + " is out of range");
//
//        // Create output image.
//        int height = -1;
//        double scaleW = (double) imageWidth / (double) width;
//        double scaleY = (double) imageHeight / (double) height;
//        if (scaleW >= 0 && scaleY >= 0) {
//            if (scaleW > scaleY) {
//                height = -1;
//            } else {
//                width = -1;
//            }
//        }
//        Image outputImage = inputImage.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
//        checkImage(outputImage);
//        encode(new FileOutputStream(resizedFile), outputImage, format);
//
//    }
//
//    /**
//     * Checks the given image for valid width and height.
//     */
//    private static void checkImage(Image image) throws Exception {
//        waitForImage(image);
//        int imageWidth = image.getWidth(null);
//        if (imageWidth < 1)
//            throw new IllegalArgumentException("image width " + imageWidth + " is out of range");
//        int imageHeight = image.getHeight(null);
//        if (imageHeight < 1)
//            throw new IllegalArgumentException("image height " + imageHeight + " is out of range");
//    }
//
//    /**
//     * Waits for given image to load. Use before querying image height/width/colors.
//     */
//    private static void waitForImage(Image image) throws Exception {
//        final MediaTracker tracker = new MediaTracker(new Component() {
//            private static final long serialVersionUID = 1234162663955668507L;
//        });
//        tracker.addImage(image, 0);
//        tracker.waitForID(0);
//        tracker.removeImage(image, 0);
//    }
//
//    /**
//     * Encodes the given image at the given quality to the output stream.
//     */
//    private static void encode(OutputStream outputStream, Image outputImage, String format) throws Exception {
//        int outputWidth = outputImage.getWidth(null);
//        if (outputWidth < 1)
//            throw new IllegalArgumentException("output image width " + outputWidth + " is out of range");
//        int outputHeight = outputImage.getHeight(null);
//        if (outputHeight < 1)
//            throw new IllegalArgumentException("output image height " + outputHeight + " is out of range");
//
//        // Get a buffered image from the image.
//        BufferedImage bi = new BufferedImage(outputWidth, outputHeight, BufferedImage.TYPE_INT_RGB);
//        Graphics2D biContext = bi.createGraphics();
//        biContext.drawImage(outputImage, 0, 0, null);
//        ImageIO.write(bi, format, outputStream);
//        outputStream.flush();
//        outputStream.close();
//    }
//
//    /**
//     * 缩放gif图片
//     *
//     * @param originalFile 原图片
//     * @param resizedFile  缩放后的图片
//     * @param newWidth     宽度
//     * @param quality      缩放比例 (等比例)
//     * @throws IOException
//     */
//    private static void resize(File originalFile, File resizedFile, int newWidth, float quality) throws Exception {
//        if (quality < 0 || quality > 1) {
//            throw new IllegalArgumentException("Quality has to be between 0 and 1");
//        }
//        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
//        Image i = ii.getImage();
//        Image resizedImage = null;
//        int iWidth = i.getWidth(null);
//        int iHeight = i.getHeight(null);
//        if (iWidth > iHeight) {
//            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
//        } else {
//            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
//        }
//        // This code ensures that all the pixels in the image are loaded.
//        Image temp = new ImageIcon(resizedImage).getImage();
//        // Create the buffered image.
//        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),
//                BufferedImage.TYPE_INT_RGB);
//        // Copy image to buffered image.
//        Graphics g = bufferedImage.createGraphics();
//        // Clear background and paint the image.
//        g.setColor(Color.white);
//        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
//        g.drawImage(temp, 0, 0, null);
//        g.dispose();
//        // Soften.
//        float softenFactor = 0.05f;
//        float[] softenArray = {0, softenFactor, 0, softenFactor, 1 - (softenFactor * 4), softenFactor, 0, softenFactor,
//                0};
//        Kernel kernel = new Kernel(3, 3, softenArray);
//        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
//        bufferedImage = cOp.filter(bufferedImage, null);
//        // Write the jpeg to a file.
//        FileOutputStream out = new FileOutputStream(resizedFile);
//        // Encodes image as a JPEG data stream
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
//        param.setQuality(quality, true);
//        encoder.setJPEGEncodeParam(param);
//        encoder.encode(bufferedImage);
//    }
//
//    /*
//     * 所有类型文件的复制
//     */
//    private void allTypeFileCopy(String src, String des) {
//        Date beginDate = new Date();
//        BufferedInputStream in = null;
//        BufferedOutputStream out = null;
//        FileInputStream fis = null;
//        FileOutputStream fos = null;
//        try {
//            fis = new FileInputStream(src);
//            fos = new FileOutputStream(des);
//            in = new BufferedInputStream(fis);
//            out = new BufferedOutputStream(fos);
//            int len = -1;
//            byte[] b = new byte[10];
//            while ((len = in.read(b)) != -1) {
//                out.write(b, 0, len);
//            }
//            out.flush();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                    fis.close();
//                }
//                if (out != null) {
//                    out.close();
//                    fos.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            String path = "App/thumb/copyImgLog.txt";
//            writeLog(path, "复制图片-" + src + " 到 " + des, beginDate);
//        }
//    }
//
//    public static long getFileSize(File f) {// 单位 Kb
//        FileChannel fc = null;
//        FileInputStream fis = null;
//        try {
//            if (f.exists() && f.isFile()) {
//                fis = new FileInputStream(f);
//                fc = fis.getChannel();
//                return fc.size() / 1024;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != fc) {
//                    fc.close();
//                }
//                if (fis != null) {
//                    fis.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return 0;
//
//    }
//
//    public void writeLog(String path, String str, Date beginDate) {// 打印日志
//        if (true) {//OnOffInter.allownPrintThumbLog
//           servicePath = fileOption.getServerPath();
//            FileOption.write(str,servicePath + path+"thumb.log",true );
//        }
//
//    }
//
//    public static void dirCreate(String src, String des) {
//        File file1 = new File(src);
//        File file2 = new File(des);
//        if (file2.exists()) {
//            return;
//        }
//        File file3 = new File(des.replace(file2.getName(), ""));
//        file3.mkdirs();
//        try {
//            file2.createNewFile();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        if (file1.isFile()) {
//            try {
//
//            } catch (Exception e1) {
//                // TODO Auto-generated catch block
//                e1.printStackTrace();
//            }
//        }
//    }
//
//}
