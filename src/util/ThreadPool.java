package util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	public static void main(String[] args) {
		// ����һ�������ù̶��߳������̳߳�
		ExecutorService pool = Executors.newFixedThreadPool(100);
		// ����ʵ����Runnable�ӿڶ���Thread����ȻҲʵ����Runnable�ӿ�
		for(int i=0;i<100;i++) {
			Thread t=new MyThread();
			pool.execute(t);
		}
		// �ر��̳߳�
		pool.shutdown();
	}

}

class MyThread extends Thread{
	
	private static int money=0;

	@Override
	public void run() {
		//System.out.println("�߳�"+Thread.currentThread().getName()+"�������У�");
		addMoney();
	}
	
	public static synchronized void addMoney() {
		for(int i=0;i<10000;i++)
			money+=1;
		System.out.println(money);
	}
	
}