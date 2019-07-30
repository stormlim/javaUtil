package util;

import java.time.LocalDate;

/**
 * 日历小程序
 * 
 * @author limstorm
 * @create 2019-05-05 09:24:36
 */
public class Calendar {
	
	//当前时间
	private LocalDate nowTime;
	
	//自定义时间
	private LocalDate changeTime;

	/**
	 * 无参构造函数
	 */
	public Calendar() {
		super();
		LocalDate time = LocalDate.now();
		this.nowTime = time;
		this.changeTime = time.withDayOfMonth(1);
	}
	
	/**
	 * 自定义年份的构造函数
	 * @param year 不为null
	 */
	public Calendar(int year) {
		super();
		LocalDate time = LocalDate.now();
		this.nowTime = time;
		this.changeTime = time.withYear(year).withDayOfYear(1);
	}

	/**
	 * 自定义年份月份的构造函数
	 * @param year 不为null
	 * @param month 不为null
	 */
	public Calendar(int year, int month) {
		super();
		LocalDate time = LocalDate.now();
		this.nowTime = time;
		this.changeTime = time.withYear(year).withMonth(month).withDayOfMonth(1);
	}
	
	/**
	 * 绘制月份的日历
	 * @param time 默认值为null
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
	 * 绘制自定义整个年份的日历
	 * @param yearTime 默认值为null
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
	 * 设置自定义时间的年份
	 * @param year 不为null
	 */
	public void setYear(int year) {
		this.changeTime = this.changeTime.withYear(year);
	}
	
	/**
	 * 设置自定义时间的月份
	 * @param month 不为null 范围：1-12
	 */
	public void setMonth(int month) {
		this.changeTime = this.changeTime.withMonth(month);
	}
	
	/**
	 * 设置自定义时间的日份
	 * @param day 不为null 范围：1-31
	 */
	public void setDay(int day) {
		this.changeTime = this.changeTime.withDayOfMonth(day);
	}
	
}
