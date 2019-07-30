package util;

import java.time.LocalDate;

/**
 * ����С����
 * 
 * @author limstorm
 * @create 2019-05-05 09:24:36
 */
public class Calendar {
	
	//��ǰʱ��
	private LocalDate nowTime;
	
	//�Զ���ʱ��
	private LocalDate changeTime;

	/**
	 * �޲ι��캯��
	 */
	public Calendar() {
		super();
		LocalDate time = LocalDate.now();
		this.nowTime = time;
		this.changeTime = time.withDayOfMonth(1);
	}
	
	/**
	 * �Զ�����ݵĹ��캯��
	 * @param year ��Ϊnull
	 */
	public Calendar(int year) {
		super();
		LocalDate time = LocalDate.now();
		this.nowTime = time;
		this.changeTime = time.withYear(year).withDayOfYear(1);
	}

	/**
	 * �Զ�������·ݵĹ��캯��
	 * @param year ��Ϊnull
	 * @param month ��Ϊnull
	 */
	public Calendar(int year, int month) {
		super();
		LocalDate time = LocalDate.now();
		this.nowTime = time;
		this.changeTime = time.withYear(year).withMonth(month).withDayOfMonth(1);
	}
	
	/**
	 * �����·ݵ�����
	 * @param time Ĭ��ֵΪnull
	 */
	public void drawCalendarWithMonth(LocalDate time) {
		if(time == null)
			time = this.changeTime;
		else
			time = time.withDayOfMonth(1);
		int month = time.getMonthValue();
		int value = time.getDayOfWeek().getValue();
		System.out.println(time.getYear() + "-" + month);
		System.out.println("Mon  Tue  Wed  Thu  Fri  Sat  Sun  ");
		for(int i=1;i<value;i++)
			System.out.print("     ");
		while(time.getMonthValue()==month) {
			if(time.equals(this.nowTime))
				System.out.printf("%3d* ",time.getDayOfMonth());
			else
				System.out.printf("%3d  ",time.getDayOfMonth());
			if(time.getDayOfWeek().getValue()==7)
				System.out.println();
			time = time.plusDays(1);
		}
		System.out.println();
	}
	
	/**
	 * �����Զ���������ݵ�����
	 * @param yearTime Ĭ��ֵΪnull
	 */
	public void drawCalendarWithYear(LocalDate yearTime) {
		if(yearTime==null)
			yearTime = this.changeTime;
		for(int i=1; i<=12; i++) {
			yearTime = yearTime.withMonth(i).withDayOfMonth(1);
			drawCalendarWithMonth(yearTime);
		}
	}
	
	/**
	 * �����Զ���ʱ������
	 * @param year ��Ϊnull
	 */
	public void setYear(int year) {
		this.changeTime = this.changeTime.withYear(year);
	}
	
	/**
	 * �����Զ���ʱ����·�
	 * @param month ��Ϊnull ��Χ��1-12
	 */
	public void setMonth(int month) {
		this.changeTime = this.changeTime.withMonth(month);
	}
	
	/**
	 * �����Զ���ʱ����շ�
	 * @param day ��Ϊnull ��Χ��1-31
	 */
	public void setDay(int day) {
		this.changeTime = this.changeTime.withDayOfMonth(day);
	}
	
}
