package util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	public static void main(String[] args) {
		// 创建一个可重用固定线程数的线程池
		ExecutorService pool = Executors.newFixedThreadPool(100);
		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		for(int i=0;i<100;i++) {
			Thread t=new MyThread();
			pool.execute(t);
		}
		// 关闭线程池
		pool.shutdown();
	}

}

class MyThread extends Thread{
	
	private static int money=0;

	@Override
	public void run() {
		//System.out.println("线程"+Thread.currentThread().getName()+"正在运行！");
		addMoney();
	}
	
	public static synchronized void addMoney() {
		for(int i=0;i<10000;i++)
			money+=1;
		System.out.println(money);
	}
	
}