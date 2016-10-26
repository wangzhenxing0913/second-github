package com.umpay.book.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	// 打开数据库
	public void openConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/house", "root", "123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 关闭数据库
	public void closeConn() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 查询 数据
	public ResultSet myQuery(String sql, Object[] parms) {
        try {
        	this.openConn();
			ps = conn.prepareStatement(sql);
			if(parms!=null){
				for (int i = 0; i < parms.length; i++) {
					ps.setObject(i+1, parms[i]);
				}
			}
			rs=ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
    //增删改数据库
	public int update(String sql,Object[] parms){
		int count=0;
		try {
			this.openConn();
			ps=conn.prepareStatement(sql);
			if(parms!=null){
				for (int i = 0; i < parms.length; i++) {
					ps.setObject(i+1, parms[i]);
				}
			}
			count = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
}
