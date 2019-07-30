package util;

import java.util.HashMap;

public class JsonData {
	private HashMap<String, Object> data;
	
	public JsonData() {
		data = new HashMap<String, Object>();
	}
	
	public void append(String key, Object value) {
		data.put(key, value);
	}

	public HashMap<String, Object> getData() {
		return data;
	}
	
	@Override
	public String toString() {
		StringBuffer str = new StringBuffer("{");
		int flag=0;
		for(String key:data.keySet()) {
			if(flag==1)
				str.append(",");
			str.append("\""+key+"\":"+String.valueOf(data.get(key)));
			flag=1;
		}
		return str.append("}").toString();
	}
}
