package edu.kh.todolist.controller;

import java.io.IOException;

import edu.kh.todolist.dto.Todo;
import edu.kh.todolist.service.TodoListService;
import edu.kh.todolist.service.TodoListServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/todo/add")
public class TodoAddServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/* 할 일 추가 */
		String title = req.getParameter("title");
		String detail = req.getParameter("detail");
		
//		System.out.println(title);
//		System.out.println(detail);
	
		try {
			Todo todo = new Todo();
			todo.setTitle(title);
			todo.setDetail(detail);
			
			TodoListService service = new TodoListServiceImpl();
			int result = service.inserTodo(todo);
			
			String message = null;
			
			if(result > 0) message = " Todo 등록 성공 ";
			else		   message = "등록 실패";
			
			HttpSession session = req.getSession();
			session.setAttribute("message", message);
			
			resp.sendRedirect("/");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
