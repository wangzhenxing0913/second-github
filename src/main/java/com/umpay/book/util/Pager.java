package com.umpay.book.util;

import java.util.List;

public class Pager {

	private int pageIndex;	//当前页码
	private int pageSize;	//每页大小
	private int totalCount;	//总条数
	private int pageCount;	//总页数
	private List recordList;
	
	private int pageStart;	//分页开始的页码
	private int pageEnd;	//分页结束的页码
	
	
	
	public int getPageStart() {
		return pageStart;
	}
	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}
	public int getPageEnd() {
		return pageEnd;
	}
	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public List getRecordList() {
		return recordList;
	}
	public void setRecordList(List recordList) {
		this.recordList = recordList;
	}
	public Pager() {
		super();
	}
	public Pager(int pageIndex, int pageSize, int totalCount, List recordList) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.recordList = recordList;
		//计算总页数
		if(totalCount%pageSize==0){
			pageCount=totalCount/pageSize;
		}else{
			pageCount=totalCount/pageSize+1;
		}
		
		// 计算 pageStart 与     pageEnd
				//  >> 总页码小于等于5页时，全部显示
				if (pageCount <= 5) {
					pageStart = 1;
					pageEnd = pageCount;
				}else {
					pageStart = pageIndex - 2; // 7 - 2 = 5;
					pageEnd = pageIndex + 2;   // 7 + 2 = 9; --> 5 ~ 9
					// 如果前面不足2个页码时，则显示前5页
					if (pageStart < 1) {
						pageStart = 1;
						pageEnd = 5;
					}
					//后面不足两页时
					if (pageIndex+2>pageCount) {
						pageEnd=pageIndex+1;
						pageStart=pageIndex-3;
					     if(pageIndex==pageCount){ 
						  pageEnd=pageIndex;
						  pageStart=pageIndex-4;
					   }   
					}
				}
				if(pageEnd>=pageCount){
					pageEnd=pageCount;
				}
	}
}
