package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import com.aspose.slides.*;

public class AsposeSlides {
	
	private static InputStream license;

	/**
	 * ppt转pdf
	 * 
	 * @return
	 */
	public static String PptToPdf(String pptname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// 凭证文件
			InputStream fileInput = new FileInputStream(basePath+"/source/"+pptname);// 待处理的文件
			String pdfName = String.valueOf(new Date().getTime())+".pdf";
			File outputFile = new File(basePath+"/source/pdf/"+pdfName);// 输出路径
			
			License aposeLic = new License();
			aposeLic.setLicense(license);
			Presentation doc = new Presentation(fileInput);
			
			FileOutputStream fileOS = new FileOutputStream(outputFile);

			doc.save(fileOS, SaveFormat.Pdf);
			
			return "/source/pdf/"+pdfName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	/**
	 * ppt转pdf
	 * 
	 * @return
	 */
	public static String PptTHtml(String pptname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// 凭证文件
			InputStream fileInput = new FileInputStream(basePath+"/source/"+pptname);// 待处理的文件
			String pdfName = String.valueOf(new Date().getTime())+".html";
			File outputFile = new File(basePath+"/source/html/"+pdfName);// 输出路径
			
			License aposeLic = new License();
			aposeLic.setLicense(license);
			Presentation doc = new Presentation(fileInput);
		    
			FileOutputStream fileOS = new FileOutputStream(outputFile);

			doc.save(fileOS, SaveFormat.Html);
			
			return "/source/html/"+pdfName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println(PptToPdf("课程培训管理系统.pptx"));
		System.out.println(PptTHtml("课程培训管理系统.pptx"));
	}

}
