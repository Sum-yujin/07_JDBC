package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {

	public static void main(String[] args) {
		
		// 부서명을 입력 받아
		// 해당 부서에 근무하는 사원의
		// 사번, 이름, 부서명, 직급명을 
		// 직급코드 오름차순으로 조회
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");			
			
			String type = "jdbc:oracle:thin:@"; 
			String host = "localhost";
			String port = ":1521"; 
			String dbName = ":XE"; 
//			Stfing url = (type+host+port+dbName ==) "jdbc:oracle:thin:@localhost:1521:XE"
			String userName = "KH_HYJ"; 
			String password = "KH1234";
			
			conn = DriverManager.getConnection(type + host + port+ dbName, 
												userName, password);
//			conn = DriverManager.getConnection(url,userName,password); <- 이렇게 써도 됨
			
			Scanner sc = new Scanner(System.in);
			System.out.print("부서명 : ");
			String input = sc.next();
			
			String sql = """
					SELECT EMP_ID, EMP_NAME, JOB_NAME, DEPT_TITLE
					FROM EMPLOYEE
					JOIN DEPARTMENT ON (DEPT_CODE=DEPT_ID)
					JOIN JOB USING (JOB_CODE)
					WHERE DEPT_TITLE = ' """ + input + "'ORDER BY DEPT_CODE ASC";
			
			
/*
==
	String sql = """
					SELECT 
					EMP_ID, 
					EMP_NAME, 
					NVL(DEPT_TITLE, '없음') DEPT_TITLE, 
					JOB_NAME
				FROM EMPLOYEE
				JOIN JOB USING(JOB_CODE)
				LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)""";
			
			sql += String.format(" WHERE DEPT_TITLE = '%s' "
							   + " ORDER BY JOB_CODE ASC", input);
			
 */
			
			/* 4. Statement 객체 생성 */
			stmt = conn.createStatement();
			
			/* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환 받기 */
			rs = stmt.executeQuery(sql);
			
			int count = 0;
			boolean flag = true; // 조회 결과가 없으면 true, 있으면 false
			while( rs.next()) {
				flag = false;
				count++;
				
				String empId 	 = rs.getString("EMP_ID");
				String empName	 = rs.getString("EMP_NAME");
				String jobName	 = rs.getString ("JOB_NAME");
				String deptTitle = rs.getString ("DEPT_TITLE");
				
				System.out.printf("%s / %s / %s / %s \n",
						 empId, empName, jobName, deptTitle);
				
			}	
			
			if(flag) {
				System.out.println("일치하는 부서가 없습니다.");
			}else System.out.println("부서인원 : " + count + " 명");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
		
	}
}
