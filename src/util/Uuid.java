package util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class Uuid {
	
	public static String generateUuidWithString() {
		String uuid = UUID.randomUUID().toString().replace("-", ""); 
		return uuid;
	}
	
	public static long generateUuidWithLong() {
		long uuid = UUID.randomUUID().hashCode()+new Random().nextLong();
		return uuid<0?-uuid:uuid;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long oldtime = new Date().getTime();
		for(int i=1;i<=1000;i++) {
			//generateUuidWithString();
			System.out.println(generateUuidWithLong());
		}
		long newTime = new Date().getTime();
		System.out.println("每秒可产生："+10000000/(newTime-oldtime)*1000+"个uuid");
	}

}
