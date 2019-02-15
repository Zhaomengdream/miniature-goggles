package com.baizhi.Util;

import java.io.IOException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;



public class JDBCutil {
	private static Properties p = new Properties();
	private static ThreadLocal<Connection> t1 = new ThreadLocal<Connection>();
	static{
		InputStream is =JDBCutil.class.getResourceAsStream ("/jdbc.properties");
		try {
			p.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static Connection getConnection() throws Exception{
		Connection conn = t1.get ();
		if(conn==null){
				Class.forName(p.getProperty("driver"));
				conn = DriverManager.getConnection(p.getProperty("url"),p.getProperty("username"),p.getProperty("password"));
				t1.set(conn);
			return conn;
		}
		else{
			return conn;
		}
	}
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs){
		try {if(rs!=null)rs.close();} catch (SQLException e) {}
		try {if(ps!=null)ps.close();} catch (SQLException e) {}
		try {if(conn!=null)t1.remove();conn.close();}catch(SQLException e){}
	}
	public static void close(PreparedStatement ps,ResultSet rs){
		try {if(rs!=null)rs.close();} catch (SQLException e) {}
		try {if(ps!=null)ps.close();} catch (SQLException e) {}
	}
	public static void close(Connection conn,PreparedStatement ps){
		try {if(ps!=null)ps.close();} catch (SQLException e) {}
		try {if(conn!=null){
			t1.remove();
			conn.close();
			}
		}catch(SQLException e){}
	}
	public static void close(PreparedStatement ps){
		try {if(ps!=null)ps.close();} catch (SQLException e) {}
	}
	public static void close(Connection conn){
		try {if(conn!=null){
			t1.remove();
			conn.close();
		}
		}catch(SQLException e){}
		
	}
}
