package edu.kh.todolist.dao;

import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.todolist.dto.Todo;

public class TodoListDaoImpl implements TodoListDao{

	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties prop;
	
	public TodoListDaoImpl() {
		
		try {
			String filePath = 
						JDBCTemplate.class.
						getResource("/edu/kh/jdbc/sql/sql.xml").getPath();
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public int insertTodo(Connection conn, Todo todo) throws SQLException {

		// 1. 결과 저장용 변수 선언
		int result = 0;
		
		try {
			
			String sql = prop.getProperty("insertTodo");
			
			// 3. PrepareStatement 생성
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, todo.getTitle());
			pstmt.setString(2, todo.getDetail());
			
			result = pstmt.executeUpdate();
		
			
		} finally {
			close(pstmt);
			
		}
		
		return result;
	}



	@Override
	public Todo modifyTodo(Connection conn, Todo todo) throws SQLException {
		Todo con = null;
		
		
		String sql = prop.getProperty("modifyTodo");
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, todo.getTodoNo());
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			int todoNo = rs.getInt("TODO_NO");
			String title = rs.getString("TITLE");
			String detail = rs.getString("DETAIL");
			String complete = rs.getString("COMPLETE");
			String enrollDate = rs.getString("ENROLL_DATE");
			con = new Todo(todoNo,title, detail, complete, enrollDate);
		}
		
		return con;
	}
	
	
	
}