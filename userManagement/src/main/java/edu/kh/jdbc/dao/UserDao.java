package edu.kh.jdbc.dao;

import java.sql.Connection;

import edu.kh.jdbc.dto.User;

public interface UserDao {

	/** 사용자 등록
	 * @param conn
	 * @param user
	 * @return result
	 * @throws Exception
	 */
	int insertUser(Connection conn, User user) throws Exception;

}
