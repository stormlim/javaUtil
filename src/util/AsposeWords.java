package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import com.aspose.words.Document;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

public class AsposeWords {
	
	private static InputStream license;

	/**
	 * wordתpdf
	 * 
	 * @return
	 */
	public static String WordToPdf(String wordname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// ƾ֤�ļ�
			InputStream fileInput = new FileInputStream(basePath+"/source/"+wordname);// ��������ļ�
			String pdfName = String.valueOf(new Date().getTime())+".pdf";
			File outputFile = new File(basePath+"/source/pdf/"+pdfName);// ���·��
			
			License aposeLic = new License();
			aposeLic.setLicense(license);
			Document doc = new Document(fileInput);
			FileOutputStream fileOS = new FileOutputStream(outputFile);

			doc.save(fileOS, SaveFormat.PDF);
			
			return "/source/pdf/"+pdfName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	/**
	 * wordתpdf
	 * 
	 * @return
	 */
	public static String WordToHtml(String wordname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// ƾ֤�ļ�
			InputStream fileInput = new FileInputStream(basePath+"/source/"+wordname);// ��������ļ�
			String pdfName = String.valueOf(new Date().getTime())+".html";
			File outputFile = new File(basePath+"/source/html/"+pdfName);// ���·��
			
			License aposeLic = new License();
			aposeLic.setLicense(license);
			Document doc = new Document(fileInput);
			FileOutputStream fileOS = new FileOutputStream(outputFile);
			HtmlSaveOptions opts = new HtmlSaveOptions(SaveFormat.HTML);
			opts.setExportImagesAsBase64(true);
			doc.save(fileOS, opts);
			
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
		System.out.println(WordToPdf("������� ��������.doc"));
		System.out.println(WordToHtml("������� ��������.doc"));
	}

}
