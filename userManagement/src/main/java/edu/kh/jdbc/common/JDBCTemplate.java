package edu.kh.jdbc.common;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/* Template : 양식, 주형, 본을 뜨기 위한 틀
 *  -> "미리 만들어뒀다"를 의미
 * 
 * JDBCTemplate : JDBC 관련 작업을 위한 코드를 미리 작성해서 제공하는 클래스
 * 
 * - getConnection() + AutoCommit false
 * - commit() / rollback()
 * - 각종 close()
 * 
 * ***** 중요 *****
 * 어디서든 JDBCTemplate 클래스를 객체로 만들지 않고도
 * 메서드를 사용할 수 있도록 하기 위해
 * 모든 메서드를 public static 으로 선언
 *  */
public class JDBCTemplate {
	
	// 필드
	private static Connection conn = null;
	// -> static 메서드에서 사용 가능한 필드는 static 필드만 가능
	
	// 메서드
	
	/**
	 * 호출 시 Connection 객체를 생성해서 반환하는 메서드
	 * @return conn
	 */
	public static Connection getConnection() {
		
		try {
			
			// 이전에 참조하던 Connection 객체가 존재하고 
			// 아직 close된 상태가 아니라면
			if(conn != null && !conn.isClosed()) {
				return conn; // 새로 만들지 않고 기존 Connection 반환
			}
			
			
			// driver.xml 파일 내용 읽어오기 */
			
			// 1. Properties 객체 생성
			// - Map의 자식 클래스
			// - K, V가 모두 String 타입
			// - xml파일 입출력을 쉽게 할 수 있는 메서드 제공
			Properties prop = new Properties();
			
			// 2. Properties 메서드를 이용해서
			//	  driver.xml 파일 내용을 읽어와 prop 에 저장
			
			String filePath = 
					JDBCTemplate.class
					.getResource("/edu/kh/jdbc/sql/driver.xml").getPath(); 
			
			// -> 빌드(코드를 실행 가능한 상태로 만드는 것)
			// 컴파일된 JDBCTemplate.class 파일의 위치에서
			// /edu/kh/jdbc/sql/driver.xml 파일을 찾아
			// 실제 경로를 얻어오기
			
			
			prop.loadFromXML(new FileInputStream(filePath));
			
			
			// prop에 저장된 값(driver.xml 에서 읽어온 값)을 이용해
			// Connection 객체 생성하기
			
			// prop.getProperty("KEY") : KEY가 일치하는 Value를 반환
			Class.forName(prop.getProperty("driver"));
			String url = prop.getProperty("url");
			String userName = prop.getProperty("userName");
			String password = prop.getProperty("password");
			
			conn = DriverManager.getConnection(url, userName, password);			
			
			// 만들어진 Connection에 AutoCommit 끄기
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	// ----------------------------------------------------------------
	
	/* 트랙잭션 제어 처리 메서드 (commit,rollback) */
	
	/**
	 * 전달 받은 커넥션에서 수행한 SQL을 COMMIT하는 메서드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 전달 받은 커넥션에서 수행한 SQL을 Rollback하는 메서드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.rollback();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// ----------------------------------------------------------------

	/**
	 * 전달 받은 커넥션을 close(자원 반환)하는 메서드
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 전달 받은 Statement를 close(자원 반환)하는 메서드
	 * + PreparedStatement도 close 처리 가능!!
	 * 	 왜?? PreparedStatement가 Statement의 자식이기 때문에!!
	 * 	(다형성 업캐스팅)
	 * @param stmt
	 */
	public static void close(Statement stmt) {
		try {
			if(stmt != null && !stmt.isClosed()) stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 전달 받은 ResultSet를 close(자원 반환)하는 메서드
	 * @param rs
	 */
	public static void close(ResultSet rs) {
		try {
			if(rs != null && !rs.isClosed()) rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
}
