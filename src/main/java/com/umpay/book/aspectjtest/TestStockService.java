package com.umpay.book.aspectjtest;

public class TestStockService {
	public static void main(String[] args) {
		String str="有注解";
		new StockService().methord1(str);
		new StockService().methord2();
		
	}

}
