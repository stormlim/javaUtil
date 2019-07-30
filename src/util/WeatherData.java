package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSONObject;

public class WeatherData {
	
	public static JSONObject CityWeather(String city) {
	    StringBuilder sb = new StringBuilder();
	    InputStream is = null;
	    BufferedReader br = null;
	    PrintWriter out = null;
	    try {
	    	city = URLEncoder.encode(city, "UTF-8");
	        String address = "https://free-api.heweather.com/v5/weather?city="+city+"&key=0e235183d43544afb0999ed6ca72d412";
	        URL uri = new URL(address);
	        HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
	        connection.setRequestMethod("GET");
	        connection.setReadTimeout(5000);
	        connection.setConnectTimeout(10000);
	        connection.setRequestProperty("accept", "*/*");
	        //���ս��
	        is = connection.getInputStream();
	        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String line;
	        //�������ж�ȡ
	        while ( ( line = br.readLine() ) != null ) {
	            sb.append(line);
	        }
	        return JSONObject.parseObject(sb.toString());
	    } catch ( Exception ignored ) {
	    } finally {
	        //�ر���
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
	        } catch ( Exception ignored ) {}
	    }
		return null;
	}

	public static void main(String[] args) {
		System.out.println(CityWeather("�ൺ"));
	}

}
