package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * ������ͳ��
 * @author limstorm
 * @create 2019-05-21 12:47:31
 */
public class CoderCounter {
 
	static int normalLines=0;//������������
	static int commentLines=0;//ע�ʹ�������
	static int whiteLines=0;//�հ״�������
 
	/**
	 * ͳ��java�ļ��Ĵ�����
	 * @param f
	 */
	private static void fileLines(File f) {
		BufferedReader br=null;
		boolean comment=false;
		try {
			br=new BufferedReader(new FileReader(f));
			String line;
			while((line=br.readLine())!=null){
				if(line.matches("^[\\s&&[^\\n]]*$")){
					//�жϿ���   �Կհ��Ҳ��ǻ��еĿ�ͷ���Ի��еĽ�β
					whiteLines++;
				}
				else if(line.startsWith("/*")&&!line.endsWith("/*")){
					commentLines++;
					comment=true;
				}
				else if(line.startsWith("/*")&&line.endsWith("/*")){
					commentLines++;
				}
				else if(comment) {
					commentLines++;
					if(line.endsWith("/*"))
						comment=false;
				}
				else {
					normalLines++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{  	
			if(br!=null){
				try {
					br.close();
					br=null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ɨ��·��������java�ļ���ͳ�ƴ�����
	 * @param path
	 * @return jsonData
	 */
	@SuppressWarnings("finally")
	public static JsonData pathFilesCounter(String path) {
		JsonData jsonData = new JsonData();
		try {
			File f=new File(path);
			File[] codeFiles=f.listFiles(); 
			for(File child:codeFiles){
				//�ж��Ƿ���.java��β
				if(child.getName().matches(".*\\.java$")){
					fileLines(child);
				}
			}
			jsonData.append("status", 200);
			jsonData.append("��������", normalLines);
			jsonData.append("ע������", commentLines);
			jsonData.append("�հ�����", whiteLines);
		}catch (Exception e) {
			jsonData.append("status", 400);
		}finally {
			return jsonData;
		}
	}
	
	/**
	 * ͳ�������ļ�·���Ĵ�����
	 * @param filePath
	 * @return jsonData
	 */
	@SuppressWarnings("finally")
	public static JsonData filesCounter(String filePath) {
		JsonData jsonData = new JsonData();
		try {
			File file=new File(filePath);
			if(file.getName().matches(".*\\.java$")){
				fileLines(file);
			}
			jsonData.append("status", 200);
			jsonData.append("��������", normalLines);
			jsonData.append("ע������", commentLines);
			jsonData.append("�հ�����", whiteLines);
		}catch (Exception e) {
			jsonData.append("status", 400);
		}finally {
			return jsonData;
		}
	}
 
}
 
