package test;

import org.junit.Test;

import util.Calendar;

/**
 * 日历产生代码的测试类
 * @author limstorm
 * @create 2019-05-21 12:24:01
 */
public class CalendarTest {

	@Test
	public void test() {
		Calendar calendar = new Calendar();
		calendar.drawCalendarWithYear(null);
	}

}