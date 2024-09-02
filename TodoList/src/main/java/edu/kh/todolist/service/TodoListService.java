package edu.kh.todolist.service;

import java.sql.SQLException;

import edu.kh.todolist.dto.Todo;

//Service :  데이터 가공, 로직 처리 등 기능(비즈니스 로직)을 제공하는 역할
public interface TodoListService {

	int inserTodo(Todo todo) throws SQLException;	
	

}