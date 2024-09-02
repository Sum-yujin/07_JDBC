package edu.kh.jdbc.dao;


//지정된 클래스의 static 메서드를 모두 얻어와 사용
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

public class UserDaoImpl implements UserDao{

	// 필드
	
	// JDBC 객체 참조 변수 + Properties 참조 변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	// -> K,V가 모두 String인 Map, 파일 입출력이 쉬움
	
	
	// 기본 생성자 
	public UserDaoImpl() {
		
		// 객체 생성 시
		// 외부에 존재하는 sql.xml 파일을 읽어와
		// prop에 저장
		
		try {
			String filePath = 
					JDBCTemplate.class
					.getResource("/edu/kh/jdbc/sql/sql.xml").getPath(); 
			
			// 지정된 경로의 XML 파일 내용을 읽어와
			// Properties 객체에 K:V 세팅
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int insertUser(Connection conn, User user) throws Exception {

		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			// 2. SQL 작성 -> Properties를 이용해 외부 sql.xml파일에서
			//				  읽어온 sql 이용
			String sql = prop.getProperty("insertUser");
			
			// 3. PrepareStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			// 4. ?에 알맞은 값 세팅
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());
			
			
			// 5. SQL(INSERT) 수행(executeUpdate()) 후
			// 	  결과(삽입된 행의 개수, int) 반환
			result = pstmt.executeUpdate();
			
		} finally {
			// 6. 사용한 JDBC 객체 자원 반환(close)
			close(pstmt);
		}
		
		// 결과 반환
		return result;
	}
	
}
