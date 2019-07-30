package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 代码量统计
 * @author limstorm
 * @create 2019-05-21 12:47:31
 */
public class CoderCounter {
 
	static int normalLines=0;//代码行数行数
	static int commentLines=0;//注释代码行数
	static int whiteLines=0;//空白代码行数
 
	/**
	 * 统计java文件的代码量
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
					//判断空行   以空白且不是换行的开头，以换行的结尾
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
	 * 扫描路径下所有java文件，统计代码量
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
				//判断是否以.java结尾
				if(child.getName().matches(".*\\.java$")){
					fileLines(child);
				}
			}
			jsonData.append("status", 200);
			jsonData.append("代码行数", normalLines);
			jsonData.append("注释行数", commentLines);
			jsonData.append("空白行数", whiteLines);
		}catch (Exception e) {
			jsonData.append("status", 400);
		}finally {
			return jsonData;
		}
	}
	
	/**
	 * 统计输入文件路径的代码量
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
			jsonData.append("代码行数", normalLines);
			jsonData.append("注释行数", commentLines);
			jsonData.append("空白行数", whiteLines);
		}catch (Exception e) {
			jsonData.append("status", 400);
		}finally {
			return jsonData;
		}
	}
 
}
 
