package com.umpay.book.aspectjtest;

public class StockService {
	@MonitorMethod
	public String methord1(String str) {
		System.out.println(str);
		return str;
	}
	public String methord2(){
		System.out.println("没有注解");
		return null;
	}
}
