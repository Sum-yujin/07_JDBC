package edu.kh.todolist.dao;

import java.sql.Connection;
import java.sql.SQLException;

import edu.kh.todolist.dto.Todo;

public interface TodoListDao {
	
	/* 할 일 추가*/
	int insertTodo(Connection conn, Todo todo) throws SQLException;
	
	/* 할 일 수정하기*/
	Todo modifyTodo(Connection conn, Todo todo) throws SQLException;
}
