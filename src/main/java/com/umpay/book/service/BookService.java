package com.umpay.book.service;

import java.util.List;

import com.umpay.book.entity.Book;

public interface BookService {
	// 查询总的条数
	public int totalCount();

	// 查询每页上的信息
	public List<Book> pageContent(int pageSize, int pageIndex);

	// 根据id集合查询对象
	public List<Book> selectByBids(String bids);

	// 新增图书到数据库中
	public int addBook(Book book);

	// 查询出数据中所有的数据
	public List<Book> getAll();

	// 根据id查询判断是否存在
	public boolean isExist(int bid);

	// 跟新数据库中的信息
	public int updateBook(Book book);

	// 根据id查询
	public Book getBid(String bid);
}
