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
	 * pdf转word
	 * 
	 * @return
	 */
	public static String PdfToWord(String pdfname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// 凭证文件
			InputStream fileInput = new FileInputStream(basePath+"/source/"+pdfname);// 待处理的文件
			String pdfName = String.valueOf(new Date().getTime())+".docx";
			File outputFile = new File(basePath+"/source/word/"+pdfName);// 输出路径
			
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
	 * pdf转html
	 * 
	 * @return
	 */
	public static String PdfToHtml(String pdfname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// 凭证文件
			InputStream fileInput = new FileInputStream(basePath+"/source/"+pdfname);// 待处理的文件
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
	 * pdf转word
	 * 
	 * @return
	 */
	public static String PdfToPpt(String pdfname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// 凭证文件
			InputStream fileInput = new FileInputStream(basePath+"/source/"+pdfname);// 待处理的文件
			String pdfName = String.valueOf(new Date().getTime())+".pptx";
			File outputFile = new File(basePath+"/source/ppt/"+pdfName);// 输出路径
			
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
		//System.out.println(PdfToWord("电子商务中的不安全因素及其防范措施探讨_李孟.pdf"));
		System.out.println(PdfToHtml("数据库系统课后习题.pdf"));
		//System.out.println(PdfToPpt("数据库系统课后习题.pdf"));
	}
}