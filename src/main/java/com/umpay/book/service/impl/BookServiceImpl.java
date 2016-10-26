package com.umpay.book.service.impl;

import java.util.List;

import com.umpay.book.dao.BookDao;
import com.umpay.book.dao.impl.BookDaoImpl;
import com.umpay.book.entity.Book;
import com.umpay.book.service.BookService;

public class BookServiceImpl implements BookService {
	BookDao dao = new BookDaoImpl();

	@Override
	public int totalCount() {
		return dao.totalCount();
	}

	@Override
	public List<Book> pageContent(int pageSize,int pageIndex) {
		return dao.pageContent(pageSize,pageIndex);
	}

	@Override
	public List<Book> selectByBids(String bids) {
		return dao.selectByBids(bids);
	}

	@Override
	public int addBook(Book book) {
		return dao.addBook(book);
	}

	@Override
	public List<Book> getAll() {
		return dao.getAll();
	}

	@Override
	public boolean isExist(int bid) {
		return dao.isExist(bid);
	}

	@Override
	public int updateBook(Book book) {
		return dao.updateBook(book);
	}

	@Override
	public Book getBid(String bid) {
		return dao.getBid(bid);
	}

}
