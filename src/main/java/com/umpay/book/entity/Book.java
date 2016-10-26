package com.umpay.book.entity;

public class Book {
	private int bid;
	private String bname;
	private String baddress;
	private String bauthor;
	private Double bprice;
	private String bpic;

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public String getBaddress() {
		return baddress;
	}

	public void setBaddress(String baddress) {
		this.baddress = baddress;
	}

	public String getBauthor() {
		return bauthor;
	}

	public void setBauthor(String bauthor) {
		this.bauthor = bauthor;
	}

	public Double getBprice() {
		return bprice;
	}

	public void setBprice(Double bprice) {
		this.bprice = bprice;
	}

	

	public Book(int bid, String bname, String baddress, String bauthor,
			Double bprice, String bpic) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.baddress = baddress;
		this.bauthor = bauthor;
		this.bprice = bprice;
		this.bpic = bpic;
	}

	public String getBpic() {
		return bpic;
	}

	public void setBpic(String bpic) {
		this.bpic = bpic;
	}

	public Book() {
		super();
	}

	public Book(String bname, String baddress, String bauthor, Double bprice,
			String bpic) {
		super();
		this.bname = bname;
		this.baddress = baddress;
		this.bauthor = bauthor;
		this.bprice = bprice;
		this.bpic = bpic;
	}
	
	

}
