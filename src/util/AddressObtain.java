package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

/**
 * 获取地理信息
 * @author limstorm
 * @create 2019-05-22 08:35:11
 */
public class AddressObtain{
	
	/**
	 * 通过IP地址获取地理信息
	 * @param ip
	 * @return
	 */
	public static JSONObject AddressFromIP(String ip) {
		StringBuilder sb = new StringBuilder();
	    InputStream is = null;
	    BufferedReader br = null;
	    PrintWriter out = null;
	    try {
	        String address = "http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
	        URL uri = new URL(address);
	        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setReadTimeout(5000);
	        connection.setConnectTimeout(10000);
	        connection.setRequestProperty("accept", "*/*");
	        //接收结果
	        is = connection.getInputStream();
	        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String line;
	        //缓冲逐行读取
	        while ( ( line = br.readLine() ) != null ) {
	            sb.append(line);
	        }
	        return JSONObject.parseObject(sb.toString());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	        //关闭流
	        try {
	            if(is!=null){
	                is.close();
	            }
	            if(br!=null){
	                br.close();
	            }
	            if (out!=null){
	                out.close();
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }
		return null;
	}
	
	/**
	 * 通过经纬度获取地理信息
	 * @param ip
	 * @return
	 */
	public static JSONObject AddressFromXY(String X, String Y) {//X为纬度，Y为经度
		StringBuilder sb = new StringBuilder();
	    InputStream is = null;
	    BufferedReader br = null;
	    PrintWriter out = null;
	    String coords = X+","+Y;
	    try {
	    	String sn= getSn(coords);
	    	sn = URLEncoder.encode(sn,"UTF-8");
	    	coords = URLEncoder.encode(coords,"UTF-8");
	    	String address = "http://api.map.baidu.com/geocoder/v2/?location="+coords+"&output=json&pois=1&latest_admin=1&ak=YMz0iiqFTq5e4OIR8dF8rlScZcaT2THy&sn="+sn;
	        URL uri = new URL(address);
	        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setReadTimeout(5000);
	        connection.setConnectTimeout(10000);
	        connection.setRequestProperty("accept", "*/*");
	        //接收结果
	        is = connection.getInputStream();
	        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String line;
	        //缓冲逐行读取
	        while ( ( line = br.readLine() ) != null ) {
	            sb.append(line);
	        }
	        return JSONObject.parseObject(sb.toString());
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
	        //关闭流
	        try {
	            if(is!=null){
	                is.close();
	            }
	            if(br!=null){
	                br.close();
	            }
	            if (out!=null){
	                out.close();
	            }
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }
		return null;
	}

	public static String getSn(String coords) throws UnsupportedEncodingException,NoSuchAlgorithmException {
		Map<String, String> paramsMap = new LinkedHashMap<String, String>();
		paramsMap.put("location", coords);
		paramsMap.put("output", "json");
		paramsMap.put("pois", "1");
		paramsMap.put("latest_admin", "1");
		paramsMap.put("ak", "YMz0iiqFTq5e4OIR8dF8rlScZcaT2THy");
		String paramsStr = toQueryString(paramsMap);
		String wholeStr = new String("/geocoder/v2/?" + paramsStr + "7cjjZ34ylZbkyKkdD2vm7NYSfzr18Un2");
		String tempStr = URLEncoder.encode(wholeStr, "UTF-8");
		return MD5(tempStr);
	}

	
	public static String toQueryString(Map<?, ?> data)throws UnsupportedEncodingException {
		StringBuffer queryString = new StringBuffer();
		for (Entry<?, ?> pair : data.entrySet()) {
		    queryString.append(pair.getKey() + "=");
		    queryString.append(URLEncoder.encode((String) pair.getValue(),"UTF-8") + "&");
		}
		if (queryString.length() > 0) {
		    queryString.deleteCharAt(queryString.length() - 1);
		}
		return queryString.toString();
	}

	
	public static String MD5(String md5) {
		try {
		    java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		    byte[] array = md.digest(md5.getBytes());
		    StringBuffer sb = new StringBuffer();
		    for (int i = 0; i < array.length; ++i) {
		            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
		    }
		    return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(AddressFromXY("36.005536362757724","120.12463518749998"));
	}
}
