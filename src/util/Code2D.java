package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

/**
 * 链接或文字转为二维码，文字少量可以
 * jar包：net.glxn.qrgen.javase\ net.glxn.qrgen.javase.core\com.google.zxing.core-3.3.0\com.google.zxing.javase-2.0
 * @author limstorm
 * @create 2019-05-19 08:44:54
 */
public class Code2D {
	
	public static boolean textOrUrlTo2Dcode(String content){
        OutputStream oute = null;
		try {
			content = new String(content.getBytes("UTF-8"), "ISO-8859-1"); 
			ByteArrayOutputStream out= QRCode.from(content).to(ImageType.JPG).stream();
			byte[] data = out.toByteArray();
			oute = new FileOutputStream(new File(System.getProperty("user.dir")+"/source/images/"+new Date().getTime()+".jpg"));
			oute.write(data);
			oute.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				oute.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        return false;
	}
	
	public static void main(String[] args) {
		textOrUrlTo2Dcode("https://blog.csdn.net/qq_21479345");
	}
}
