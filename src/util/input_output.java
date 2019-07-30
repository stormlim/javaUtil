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
 * ������������ܽ�
 * 
 * @author limstorm
 * @create 2019-05-04 11:47:09
 */
public class input_output {
	
	//��ȡ���̸�Ŀ¼
	String basePath = System.getProperty("user.dir");
	
	/**
	 * ����̨����
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
	 * �ļ��ֽ����������
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
	 * �ļ��ַ����������
	 */
	public void fileReaderWriter() {
		File inputFile = new File(basePath+"/source/txt/input.txt");
		BufferedReader in = null;
		//FileReader�Ѿ���װ����InputStream��InputStreamReader��������
		FileReader fr=null;
		File outputFile = new File(basePath+"/source/txt/output_"+new Date().getTime()%1000000000+".txt");
        Writer out=null;
		try {
			fr=new FileReader(inputFile);
			in = new BufferedReader(fr);
			out = new FileWriter(outputFile,true);//׷��
//			��ȡ�ַ����ķ���1�����Ͷ�ȡ�ֽ�һ����
//			char[] chars = new char[1024];
//			int len = 0;
//			while ((len = reader.read(chars)) != -1) {
//				�ַ�������ֱ�Ӵ�ӡ������ֽ�Ҫ����ӵ��ַ����������
//				System.out.println(new String(chars, 0, len));
//			}
//			
			// ��ȡ�ַ����ķ���2���� �ַ������еģ�
			String line = "";
			// �������null�ͱ�����û��������
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
