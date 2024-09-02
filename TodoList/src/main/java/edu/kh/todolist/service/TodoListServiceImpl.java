package edu.kh.todolist.service;

// 지정된 클래스의 static 메서드를 모두 얻어와 사용
import static edu.kh.jdbc.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.SQLException;

import edu.kh.todolist.dao.TodoListDao;
import edu.kh.todolist.dao.TodoListDaoImpl;
import edu.kh.todolist.dto.Todo;

public class TodoListServiceImpl implements TodoListService{

	private TodoListDao dao = new TodoListDaoImpl();
	
	@Override
	public int inserTodo(Todo todo) throws SQLException {

		Connection conn = getConnection();
		
		int result = dao.insertTodo(conn, todo);
		
		if(result > 0) commit(conn);
		else 		   rollback(conn);
		
		close(conn);
		
		return result;
	}


	
}
