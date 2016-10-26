package com.umpay.book.dao.impl;

import com.umpay.book.entity.Book;
import com.umpay.book.util.BaseDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.umpay.book.dao.BookDao;

public class BookDaoImpl extends BaseDao implements BookDao {

	@Override
	public int totalCount() {
		int count = 0;
		String sql = "select count(*) from book";
		ResultSet rs = this.myQuery(sql, null);
		try {
			while (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	@Override
	public List<Book> pageContent(int pageSize, int pageIndex) {
		List<Book> list = new ArrayList<Book>();
		StringBuffer sql = new StringBuffer("select * from book limit "
				+ (pageIndex - 1) * pageSize + "," + pageSize + "");
		ResultSet rs = this.myQuery(sql.toString(), null);
		Book b = null;
		try {
			while (rs.next()) {
				b = new Book();
				b.setBid(rs.getInt("bid"));
				b.setBaddress(rs.getString("baddress"));
				b.setBauthor(rs.getString("bauthor"));
				b.setBname(rs.getString("bname"));
				b.setBprice(rs.getDouble("bprice"));
				b.setBpic(rs.getString("bpic"));
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Book> selectByBids(String bids) {
		List<Book> books = new ArrayList<Book>();
		Book b = null;
		String sql = "select * from book where bid in (" + bids + ")";
		ResultSet rs = this.myQuery(sql, null);
		try {
			while (rs.next()) {
				b = new Book();
				b.setBid(rs.getInt("bid"));
				b.setBaddress(rs.getString("baddress"));
				b.setBauthor(rs.getString("bauthor"));
				b.setBname(rs.getString("bname"));
				b.setBprice(rs.getDouble("bprice"));
				b.setBpic(rs.getString("bpic"));
				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}

	@Override
	public int addBook(Book book) {
		String sql = "insert into book values (null,?,?,?,?,?)";
		Object[] parms = { book.getBname(), book.getBauthor(),
				book.getBprice(), book.getBpic(), book.getBaddress() };
		int count = this.update(sql, parms);
		return count;
	}

	@Override
	public List<Book> getAll() {
		List<Book> list = new ArrayList<Book>();
		StringBuffer sql = new StringBuffer("select * from book");
		ResultSet rs = this.myQuery(sql.toString(), null);
		Book b = null;
		try {
			while (rs.next()) {
				b = new Book();
				b.setBid(rs.getInt("bid"));
				b.setBaddress(rs.getString("baddress"));
				b.setBauthor(rs.getString("bauthor"));
				b.setBname(rs.getString("bname"));
				b.setBprice(rs.getDouble("bprice"));
				b.setBpic(rs.getString("bpic"));
				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean isExist(int bid) {
		String sql = "select * from book where bid = ?";
		Book b = null;
		Object[] parms = { bid };
		ResultSet rs = this.myQuery(sql, parms);
		try {
			while (rs.next()) {
				b = new Book();
				b.setBid(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
         if(b==null){
        	 return false;
         }
		return true;
	}

	@Override
	public int updateBook(Book book) {
		String sql="update book set bname=?,bauthor=?,baddress=?,bprice=? where bid=?";
		Object[] parms={book.getBname(),book.getBauthor(),book.getBaddress(),book.getBprice(),book.getBid()};
		int update = this.update(sql, parms);
		return update;
	}

	@Override
	public Book getBid(String bid) {
		String sql = "select * from book where bid = ?";
		Book b = null;
		Object[] parms = { bid };
		ResultSet rs = this.myQuery(sql, parms);
		try {
			while (rs.next()) {
				b = new Book();
				b.setBid(rs.getInt("bid"));
				b.setBaddress(rs.getString("baddress"));
				b.setBauthor(rs.getString("bauthor"));
				b.setBname(rs.getString("bname"));
				b.setBprice(rs.getDouble("bprice"));
				b.setBpic(rs.getString("bpic"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

}
