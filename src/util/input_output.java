package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Date;
import java.util.Scanner;

/**
 * 输入输出流的总结
 * 
 * @author limstorm
 * @create 2019-05-04 11:47:09
 */
public class input_output {
	
	//获取工程根目录
	String basePath = System.getProperty("user.dir");
	
	/**
	 * 控制台输入
	 */
	public void consoleInput() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("input your name,please");
		String username = sc.nextLine();
		System.out.println("input your age,please");
		int age = sc.nextInt();
		System.out.println("input your score,please");
		double score = sc.nextDouble();
		System.out.println("Your information:");
		System.out.println("name ==> "+username+" , age ==> "+age+" , score ==> "+score);
	}
	
	/**
	 * 文件字节输入输出流
	 */
	public void fileInputOutput() {
		File inputFile = new File(basePath+"/source/txt/input.txt");
		File outputFile = new File(basePath+"/source/txt/output_"+new Date().getTime()%1000000000+".txt");
		InputStream in;
		OutputStream out;
		try {
			in = new FileInputStream(inputFile);
			out = new FileOutputStream(outputFile);
			byte[] bt = new byte[1024];
			while(in.read(bt)!=-1) {
				out.write(bt);
			}
			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 文件字符流输入输出
	 */
	public void fileReaderWriter() {
		File inputFile = new File(basePath+"/source/txt/input.txt");
		BufferedReader in = null;
		//FileReader已经封装有了InputStream和InputStreamReader这两个类
		FileReader fr=null;
		File outputFile = new File(basePath+"/source/txt/output_"+new Date().getTime()%1000000000+".txt");
        Writer out=null;
		try {
			fr=new FileReader(inputFile);
			in = new BufferedReader(fr);
			out = new FileWriter(outputFile,true);//追加
//			读取字符流的方法1：（和读取字节一样）
//			char[] chars = new char[1024];
//			int len = 0;
//			while ((len = reader.read(chars)) != -1) {
//				字符流可以直接打印输出，字节要先添加到字符串中再输出
//				System.out.println(new String(chars, 0, len));
//			}
//			
			// 读取字符流的方法2：（ 字符流独有的）
			String line = "";
			// 如果返回null就表明是没有内容了
			while ((line = in.readLine()) != null) {
				out.write(line+"\r\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		input_output inOutput = new input_output();
//		inOutput.consoleInput();
//		inOutput.fileInputOutput();
		inOutput.fileReaderWriter();
	}
}
