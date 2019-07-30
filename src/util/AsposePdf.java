package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import com.aspose.pdf.Document;
import com.aspose.pdf.HtmlSaveOptions;
import com.aspose.pdf.License;
import com.aspose.pdf.SaveFormat;

public class AsposePdf {

	private static InputStream license;

	/**
	 * pdfתword
	 * 
	 * @return
	 */
	public static String PdfToWord(String pdfname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// ƾ֤�ļ�
			InputStream fileInput = new FileInputStream(basePath+"/source/"+pdfname);// ��������ļ�
			String pdfName = String.valueOf(new Date().getTime())+".docx";
			File outputFile = new File(basePath+"/source/word/"+pdfName);// ���·��
			
			License aposeLic = new License();
			aposeLic.setLicense(license);
			
			Document wb = new Document(fileInput);
			FileOutputStream fileOS = new FileOutputStream(outputFile);

			wb.save(fileOS, SaveFormat.DocX);
			
			return "/source/word/"+pdfName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	/**
	 * pdfתhtml
	 * 
	 * @return
	 */
	public static String PdfToHtml(String pdfname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// ƾ֤�ļ�
			InputStream fileInput = new FileInputStream(basePath+"/source/"+pdfname);// ��������ļ�
			String pdfName = String.valueOf(new Date().getTime())+".html";
			
			License aposeLic = new License();
			aposeLic.setLicense(license);
			
			Document wb = new Document(fileInput);

			// Create HtmlSaveOption with tested feature
			HtmlSaveOptions newOptions = new HtmlSaveOptions();

			// Enable option to embed all resources inside the HTML
			newOptions.PartsEmbeddingMode = HtmlSaveOptions.PartsEmbeddingModes.EmbedAllIntoHtml;

			// This is just optimization for IE and can be omitted 
			newOptions.RasterImagesSavingMode = HtmlSaveOptions.RasterImagesSavingModes.AsEmbeddedPartsOfPngPageBackground;
			newOptions.FontSavingMode = HtmlSaveOptions.FontSavingModes.SaveInAllFormats;
			              
			// Save the output in HTML format
			wb.save(basePath+"/source/html/"+pdfName, newOptions);
			
			return "/source/html/"+pdfName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	/**
	 * pdfתword
	 * 
	 * @return
	 */
	public static String PdfToPpt(String pdfname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// ƾ֤�ļ�
			InputStream fileInput = new FileInputStream(basePath+"/source/"+pdfname);// ��������ļ�
			String pdfName = String.valueOf(new Date().getTime())+".pptx";
			File outputFile = new File(basePath+"/source/ppt/"+pdfName);// ���·��
			
			License aposeLic = new License();
			aposeLic.setLicense(license);
			
			Document wb = new Document(fileInput);
			FileOutputStream fileOS = new FileOutputStream(outputFile);

			wb.save(fileOS, SaveFormat.Pptx);
			
			return "/source/ppt/"+pdfName;
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
		//System.out.println(PdfToWord("���������еĲ���ȫ���ؼ��������ʩ̽��_����.pdf"));
		System.out.println(PdfToHtml("���ݿ�ϵͳ�κ�ϰ��.pdf"));
		//System.out.println(PdfToPpt("���ݿ�ϵͳ�κ�ϰ��.pdf"));
	}
}