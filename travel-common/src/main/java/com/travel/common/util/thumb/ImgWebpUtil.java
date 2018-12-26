//package com.travel.springboot.util.thumb;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Date;
//
//import com.travel.springboot.util.FileOption;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
///*wz
//*/
//@Service
//public class ImgWebpUtil {
//
//	@Autowired
//	FileOption fileOption;
//
//	// jpg/png to webp
//	public void imgToWebp(String servicePath, String inputFile, String outputFile, int quality) throws Exception {
//		// cwebp [-preset <...>] [options] in_file [-o out_file]
//		/*
//		 * cwebp [options] -q quality input.jpg -o output.webp 质量选项应该是 0 (差)到 100
//		 * (很好)之间的数字，典型的质量值大约是 80。
//		 */
//		quality = quality == 0 ? 75 : quality;
//		servicePath += "/webpUtil/libwebp-0.6.0-windows-x64/bin/cwebp.exe";
//		if (inputFile.toLowerCase().indexOf(".gif") > 0) {// GIF处理
//			servicePath += "/webpUtil/libwebp-0.6.0-windows-x64/bin/gif2webp.exe ";
//		}
//		String command = servicePath + " -q " + quality + " " + inputFile + " -o " + outputFile;
//		// Runtime.getRuntime().exec(command);
//		cmdProcess(command);
//	}
//
//	// webp To jpg/png
//	public void webpToImg(String servicePath, String inputFile, String outputFile, int quality) throws Exception {
//		// dwebp input_file.webp [options] [-o output_file]
//		// 质量选项应该是 0 (差)到 100 (很好)之间的数字，典型的质量值大约是 80
//		quality = quality == 0 ? 75 : quality;
//		servicePath += "/webpUtil/libwebp-0.6.0-windows-x64/bin/dwebp.exe";
//		if (inputFile.toLowerCase().indexOf(".gif") > 0) {// GIF处理
//			servicePath += "/webpUtil/libwebp-0.6.0-windows-x64/bin/gif2webp.exe ";
//		}
//		String command = servicePath + " " + inputFile + " -o " + outputFile;
//		// Runtime.getRuntime().exec(command);
//		cmdProcess(command);
//	}
//
//	// cmd G:\company\libwebp-0.6.0-windows-x64\bin\dwebp.exe
//	// G:\company\Standard\1.webp -o G:\company\Standard2\1.png
//
//	// 批处理方法
//
//	// 执行批处理文件
//	public void cmdProcess(String strcmd) throws Exception {
//		Runtime rt = Runtime.getRuntime();
//		Process ps = null;
//		ps = rt.exec(strcmd);
//		/*
//		 * ps.waitFor(); int i = ps.exitValue(); ps.destroy(); ps = null;
//		 */
//		InputStream stderr = ps.getErrorStream();
//		InputStream is1 = ps.getInputStream();
//		InputStreamReader isr = new InputStreamReader(stderr);
//		InputStreamReader isr2 = new InputStreamReader(is1);
//		BufferedReader br = new BufferedReader(isr);
//		BufferedReader br2 = new BufferedReader(isr2);
//		String line = null;
//		Date beginDate = new Date();
//
//		String path_str = "/App/thumb/wbpLog.txt";
//
//		String Log = "";
//
//		while ((line = br2.readLine()) != null) {
//			Log += (line + "\r\n");
//		}
//		Log += "\r\n";
//
//		while ((line = br.readLine()) != null) {
//			Log += (line + "\r\n");
//		}
//		Log += Log + "\r\n";
//
//		/* ThumbnailUtil.writeLog(path_str,"wbp: "+Log,beginDate); */
//		if (true) {//OnOffInter.allownPrintThumbLog
//			String servicePath = fileOption.getServerPath();
//			FileOption.write(Log,servicePath + path_str+"wbp.log ",true );
//		}
//		if (br != null) {
//			br.close();
//		}
//		if (br2 != null) {
//			br2.close();
//		}
//
//		if (isr != null) {
//			isr.close();
//		}
//		if (isr2 != null) {
//			isr2.close();
//		}
//
//		if (stderr != null) {
//			stderr.close();
//		}
//		if (is1 != null) {
//			is1.close();
//		}
//
//		int i = ps.waitFor();
//		ps.destroy();
//		ps = null;
//		// 批处理执行完后，根据cmd.exe进程名称
//		// kill掉cmd窗口(这个方法是好不容易才找到了，网上很多介绍的都无效，csdn废我3分才找到这个方法)
//		// killProcess();
//		if (i == 0) {
//			// System.out.println("执行完成.");
//		} else {
//			// System.out.println("执行失败.");
//			throw new Exception();
//		}
//	}
//
//	public static void killProcess() {
//		Runtime rt = Runtime.getRuntime();
//		Process p = null;
//		try {
//			rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//
//		/*
//		 * String locationServicePath=
//		 * "G:/company/APPWorkspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/sanfukeji";
//		 * String
//		 * inputPath="/seller/8ab21de4-a432-4d35-b9fe-c1a5c71acf92/images/555/20170903_101534_039 - 副本.jpg"
//		 * ; inputPath=locationServicePath+inputPath; String
//		 * targetPath="G:/company/APPWorkspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/sanfukeji/App_thumbs/20170903_101534_039 - 副本.jpg"
//		 * ;
//		 *
//		 * try { imgToWebp(locationServicePath,inputPath,targetPath,0);
//		 *
//		 * } catch (Exception e) { // TODO Auto-generated catch block
//		 * e.printStackTrace(); }
//		 */
//
//		/*
//		 * System.out.println(src.substring(0,253));
//		 * System.out.println(src.substring(253,src.indexOf("\"",253)));
//		 * System.out.println(src.substring(0,363));
//		 */
//	}
//
//	/*
//	 * String cwebp=""; //判断当前jdk的版本位数
//	 * if(System.getProperty("sun.arch.data.model").equals("64")){ cwebp =
//	 * System.getProperty("user.dir")+ "/libwebp-0.4.2-windows-	x64/bin/dwebp.exe";
//	 * }else{ cwebp = System.getProperty("user.dir")+
//	 * "/libwebp-0.4.2-windows-x86/bin/dwebp.exe"; } System.out.println(cwebp);
//	 */
//	// private static Logger log = Logger.getLogger(ImageFormatConverter.class);
//	/*
//	 * public static boolean convertToWebp(String inputFile, String outputFile) {
//	 * return convertToWebp(inputFile, outputFile, 75); }
//	 */
//
//	/*
//	 * public static boolean convertToWebp(String inputFile, String outputFile,
//	 * Integer quality) { if (!new File(inputFile).exists()) { return false; }
//	 *
//	 * String outputPath = FilenameUtils.getFullPath(outputFile); if (!new
//	 * File(outputPath).exists()) { new File(outputPath).mkdirs(); }
//	 *
//	 * return executeCWebp(inputFile, outputFile, quality); }
//	 */
//
//	/**
//	 * execute cwebp command：cwebp [options] input_file -o output_file.webp
//	 */
//	/*
//	 * private static boolean executeCWebp(String inputFile, String outputFile,
//	 * Integer quality) { boolean result = false; ClassLoader cl =
//	 * ImageFormatConverter.class.getClassLoader(); // get classloader // init cwebp
//	 * path，and set privilege of 755. // you can replace cwebpath in your case. in
//	 * this case, we used a macos-based cwebp String cwebpPath =
//	 * cl.getResource("libwebp/cwebp_macos").getPath(); try { String chmodCommand =
//	 * "chmod 755 " + cwebpPath; Runtime.getRuntime().exec(chmodCommand).waitFor();
//	 *
//	 * StringBuilder command = new StringBuilder(cwebpPath); command.append( " -q "
//	 * + (quality == 0 ? 75 : quality)); command.append(" " + inputFile);
//	 * command.append(" -o " + outputFile);
//	 *
//	 * Runtime.getRuntime().exec(command.toString());
//	 *
//	 * result = true; } catch (Exception e) { // log.error(
//	 * "An error happend when convert to webp. Img is: " + inputFile, e); } return
//	 * result; }
//	 */
//}
