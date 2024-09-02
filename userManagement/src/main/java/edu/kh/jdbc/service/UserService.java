package edu.kh.jdbc.service;

import edu.kh.jdbc.dto.User;

public interface UserService {

	/** 사용자 등록
	 * @param user
	 * @return result : 1 || 0
	 * @throws Exception
	 */
	int insertUser(User user) throws Exception;

}
