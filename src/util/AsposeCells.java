package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import com.aspose.cells.License;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

public class AsposeCells {

	private static InputStream license;

	/**
	 * excel转pdf
	 * 
	 * @return
	 */
	public static String ExcelToPdf(String excelname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// 凭证文件
			InputStream fileInput = new FileInputStream(basePath+"/source/"+excelname);// 待处理的文件
			String pdfName = String.valueOf(new Date().getTime())+".pdf";
			File outputFile = new File(basePath+"/source/pdf/"+pdfName);// 输出路径
			
			License aposeLic = new License();
			aposeLic.setLicense(license);
			
			Workbook wb = new Workbook(fileInput);
			FileOutputStream fileOS = new FileOutputStream(outputFile);

			wb.save(fileOS, SaveFormat.PDF);
			
			return "/source/pdf/"+pdfName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
	/**
	 * excel转html
	 * 
	 * @return
	 */
	public static String ExcelToHtml(String excelname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// 凭证文件
			InputStream fileInput = new FileInputStream(basePath+"/source/"+excelname);// 待处理的文件
			String htmlName = String.valueOf(new Date().getTime())+".html";
			File outputFile = new File(basePath+"/source/html/"+htmlName);// 输出路径
			
			License aposeLic = new License();
			aposeLic.setLicense(license);
			
			Workbook wb = new Workbook(fileInput);
			FileOutputStream fileOS = new FileOutputStream(outputFile);

			wb.save(fileOS, SaveFormat.HTML);

			return "/source/html/"+htmlName;
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
		//System.out.println(ExcelToPdf("软件工程2016第二学期专业绩点排名 (2).xlsx"));
		System.out.println(ExcelToHtml("软件工程2016第二学期专业绩点排名 (2).xlsx"));
	}
}