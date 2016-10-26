package com.umpay.book.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import com.umpay.book.entity.Book;

public class ExcelImport {
	public List<Book> getAllByExcel(File file) {
		List<Book> list = new ArrayList<Book>();
		try {
			FileInputStream fs=new FileInputStream(file);
			Workbook rwb = Workbook.getWorkbook(fs);
			Sheet rs = rwb.getSheet(0);
			int cols = rs.getColumns();
			int rows = rs.getRows();
			for (int i = 2; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					String bid = rs.getCell(j++, i).getContents();
					String bname = rs.getCell(j++, i).getContents();
					String bauthor = rs.getCell(j++, i).getContents();
					String baddress = rs.getCell(j++, i).getContents();
					String bprice = rs.getCell(j++, i).getContents();
					String bpic = rs.getCell(j++, i).getContents();
					int id = Integer.parseInt(bid);
					double price = Double.parseDouble(bprice);
					list.add(new Book(id, bname, baddress, bauthor, price, bpic));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
