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
	 * excelתpdf
	 * 
	 * @return
	 */
	public static String ExcelToPdf(String excelname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// ƾ֤�ļ�
			InputStream fileInput = new FileInputStream(basePath+"/source/"+excelname);// ��������ļ�
			String pdfName = String.valueOf(new Date().getTime())+".pdf";
			File outputFile = new File(basePath+"/source/pdf/"+pdfName);// ���·��
			
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
	 * excelתhtml
	 * 
	 * @return
	 */
	public static String ExcelToHtml(String excelname) {
		try {
			String basePath = System.getProperty("user.dir");
			license = new FileInputStream(basePath+"/source/license.xml");// ƾ֤�ļ�
			InputStream fileInput = new FileInputStream(basePath+"/source/"+excelname);// ��������ļ�
			String htmlName = String.valueOf(new Date().getTime())+".html";
			File outputFile = new File(basePath+"/source/html/"+htmlName);// ���·��
			
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
		//System.out.println(ExcelToPdf("�������2016�ڶ�ѧ��רҵ�������� (2).xlsx"));
		System.out.println(ExcelToHtml("�������2016�ڶ�ѧ��רҵ�������� (2).xlsx"));
	}
}